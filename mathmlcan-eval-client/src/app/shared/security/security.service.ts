import {environment} from '../../../environments/environment';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {EventEmitter, Injectable} from '@angular/core';
import {JwtHelperService} from '@auth0/angular-jwt';
import {LocalStorageService} from 'ngx-store';
import {LoginModel} from './login.model';
import {Observable} from 'rxjs';
import {OauthModel} from './oauth.model';
import {map} from 'rxjs/operators';

const API_URL = environment.apiUrl;
const AUTH_TOKEN = btoa(`${environment.clientId}:${environment.clientSecret}`);
const TOKEN_NAME = 'mathcaneval-token';
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
    .pipe(map((res: any) => this.convert(res.body)),
      map((res: OauthModel) => {
        this.localStorageService.set(TOKEN_NAME, res.accessToken);
        this.eventEmitter.emit(true);
        return true;
      }, () => {
        return false;
      }));
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

  convert(json: any): OauthModel {
    return {
      accessToken: json.access_token
    } as OauthModel;
  }
}
