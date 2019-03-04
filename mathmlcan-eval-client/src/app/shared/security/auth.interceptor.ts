import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {SecurityService} from './security.service';
import {environment} from '../../../environments/environment';

const OAUTH_URL = `${environment.apiUrl}/api/oauth/token`;

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  private protectedResources: Map<string, string[]>;

  constructor(private securityService: SecurityService) {
    this.protectedResources = new Map<string, string[]>(
      [
        ['GET', ['test']],
        ['POST', ['/api/configurations', '/api/collections', '/api/revisions']]
      ]
    );
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if(req.url !== OAUTH_URL) {
      for (const resource of this.protectedResources.get(req.method)) {
        if (resource === this.path(req.url)) { // todo this might need regex in future
          const cloned = req.clone({
            headers: req.headers.set('Authorization', `Bearer ${this.securityService.getToken()}`)
          });

          return next.handle(cloned);
        }
      }
    }

    return next.handle(req);
  }

  private path(url: string): string {
    return url.substr(url.indexOf(environment.apiUrl) + environment.apiUrl.length);
  }
}
