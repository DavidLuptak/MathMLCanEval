import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {SecurityService} from './security.service';
import {environment} from '../../../environments/environment';

const TOKEN_URL = `${environment.apiUrl}/api/oauth/token`;
const REVOKE_URL = `${environment.apiUrl}/api/oauth/revoke`;

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  private protectedResources: Map<string, RegExp[]>;

  constructor(private securityService: SecurityService) {
    this.protectedResources = new Map<string, RegExp[]>(
      [
        ['GET', [new RegExp(/^\/api\/me$/i), new RegExp(/^\/api\/running-tasks$/i), new RegExp(/^\/api\/app-runs$/i), new RegExp(/^\/api\/app-runs\/\d+\/details$/i)]],
        ['POST', [new RegExp(/^\/api\/configurations$/i), new RegExp(/^\/api\/collections$/i), new RegExp(/^\/api\/revisions$/i), new RegExp(/^\/api\/app-runs$/i)]],
        ['PATCH', [new RegExp(/^\/api\/revisions\/\d+$/i)]]
      ]
    );
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // todo https://stackoverflow.com/a/45932412/1203690
    if(req.url !== TOKEN_URL && req.url !== REVOKE_URL) {
      for (const resource of this.protectedResources.get(req.method)) {
        if(resource.test(this.path(req.url))) {
          if(this.securityService.isTokenExpired()) {
            this.securityService.logout();
          } else  {
            const cloned = req.clone({
              headers: req.headers.set('Authorization', `Bearer ${this.securityService.getToken()}`)
            });

            return next.handle(cloned);
          }
        }
      }
    }

    return next.handle(req);
  }

  private path(url: string): string {
    return url.substr(url.indexOf(environment.apiUrl) + environment.apiUrl.length);
  }
}
