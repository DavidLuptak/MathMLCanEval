import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {SecurityService} from './security.service';
import {environment} from '../../../environments/environment';

const OAUTH_URL = `${environment.apiUrl}/api/oauth/token`;

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  private protectedResources: Map<string, RegExp[]>;

  constructor(private securityService: SecurityService) {
    this.protectedResources = new Map<string, RegExp[]>(
      [
        ['GET', [new RegExp(/^\/api\/me$/i)]],
        ['POST', [new RegExp(/^\/api\/configurations$/i), new RegExp(/^\/api\/collections$/i), new RegExp(/^\/api\/revisions$/i)]],
        ['PATCH', [new RegExp(/^\/api\/revisions\/\d+$/i)]]
      ]
    );
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if(req.url !== OAUTH_URL) {
      for (const resource of this.protectedResources.get(req.method)) {
        if(resource.test(this.path(req.url))) {
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
