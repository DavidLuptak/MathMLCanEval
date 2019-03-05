import {environment} from '../../../environments/environment';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {EventEmitter, Injectable} from '@angular/core';
import {JwtHelperService} from '@auth0/angular-jwt';
import {LocalStorageService} from 'ngx-store';
import {LoginModel} from './login.model';
import {Observable} from 'rxjs';
import {OauthModel} from './oauth.model';
import {map, mergeMap} from 'rxjs/operators';
import {tap} from 'rxjs/internal/operators/tap';
import {MeResponse} from './me-response';
import {Authority, Principal} from './principal';

const API_URL = environment.apiUrl;
const AUTH_TOKEN = btoa(`${environment.clientId}:${environment.clientSecret}`);
const TOKEN_NAME = 'mathcaneval-token';
const PRINCIPAL = 'mathcaneval-principal';
const OAUTH_HEADERS = {
  observe: 'response' as 'body',
  headers: new HttpHeaders({
    'Content-Type': 'application/x-www-form-urlencoded',
    'Authorization': `Basic ${AUTH_TOKEN}`
  })
};

@Injectable({providedIn: 'root'})
export class SecurityService {
  jwtHelper = new JwtHelperService();
  eventEmitter = new EventEmitter<boolean>();

  constructor(private httpClient: HttpClient,
              private localStorageService: LocalStorageService) {
  }

  login(loginModel: LoginModel): Observable<boolean> {
    const body = new HttpParams()
    .set('username', loginModel.username)
    .set('password', loginModel.password)
    .set('grant_type', 'password');

    return this.httpClient
    .post<OauthModel>(`${API_URL}/api/oauth/token`, body, OAUTH_HEADERS)
    .pipe<OauthModel, OauthModel, MeResponse, boolean>(
      map((res: any) => this.convert(res.body)),
      tap((res: OauthModel) => this.storeToken(res)),
      mergeMap(() => this.httpClient.get<MeResponse>(`${API_URL}/api/me`)),
      map((res: MeResponse) => {
          this.storePrincipal(res);
          this.notify();

          return true;
        },
        () => false)
    );
  }


  userLoggedIn(): Observable<boolean> {
    return this.eventEmitter;
  }

  logout(): void {
    this.localStorageService.remove(TOKEN_NAME);
  }

  isLoggedIn(): boolean {
    return this.localStorageService.get(TOKEN_NAME) != null;
  }

  getToken(): string {
    return this.localStorageService.get(TOKEN_NAME);
  }

  isTokenExpired(): boolean {
    return !this.jwtHelper.isTokenExpired(this.getToken());
  }

  isAuthenticated(): boolean {
    return this.isLoggedIn() && this.isTokenExpired();
  }

  hasPermissions(permissions: string[], operator: string): boolean {
    if (!this.isAuthenticated()) {
      return false;
    } else {
      if (permissions && permissions.length > 0) {
        const authorities = this.getPrincipal().authorities;
        switch (operator) {
          case 'AND' :
            let result = false;
            return permissions.every(p => this.hasPermission(p, authorities));
          case 'OR' :
            return permissions.some(p => this.hasPermission(p, authorities));
          default:
            throw Error('Invalid operator.');
        }
      } else {
        return true;
      }
    }
  }

  private storeToken(oauthModel: OauthModel): void {
    this.localStorageService.set(TOKEN_NAME, oauthModel.accessToken);
  }

  private storePrincipal(res: MeResponse): void {
    this.localStorageService.set(PRINCIPAL, res.principal);
  }

  private getPrincipal(): Principal {
    return this.localStorageService.get(PRINCIPAL) as Principal;
  }

  private notify(): void {
    this.eventEmitter.emit(true);
  }

  private convert(json: any): OauthModel {
    return {
      accessToken: json.access_token
    } as OauthModel;
  }

  private hasPermission(permission: string, authorities: Authority[]): boolean {
    if (authorities) {
      return authorities.filter(p => p.authority.toLowerCase() === permission.toLowerCase()).length > 0;
    } else {
      return false;
    }
  }
}
