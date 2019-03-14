import {Injectable} from '@angular/core';
import {
  HttpErrorResponse,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
  HttpResponse
} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {SecurityService} from './security.service';
import {environment} from '../../../environments/environment';
import {catchError, map} from 'rxjs/operators';

const TOKEN_URL = `${environment.apiUrl}/api/oauth/token`;
const REVOKE_URL = `${environment.apiUrl}/api/oauth/revoke`;

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  private protectedResources: Map<string, RegExp[]>;

  constructor(private securityService: SecurityService) {
    this.protectedResources = new Map<string, RegExp[]>(
      [
        ['GET', [new RegExp(/^\/api\/me$/i), new RegExp(/^\/api\/running-tasks$/i), new RegExp(/^\/api\/collections$/i), new RegExp(/^\/api\/app-runs$/i), new RegExp(/^\/api\/app-runs\/\d+\/details$/i)]],
        ['POST', [new RegExp(/^\/api\/configurations$/i), new RegExp(/^\/api\/collections$/i), new RegExp(/^\/api\/revisions$/i),new RegExp(/^\/api\/revisions\/latest$/i), new RegExp(/^\/api\/app-runs$/i)]],
        ['PATCH', [new RegExp(/^\/api\/revisions\/\d+$/i)]]
      ]
    );
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // todo https://stackoverflow.com/a/45932412/1203690
    var wrapper;

    if(req.url !== TOKEN_URL && req.url !== REVOKE_URL) {
      for (const resource of this.protectedResources.get(req.method)) {
        if(resource.test(this.path(req.url))) {
          if(this.securityService.isTokenExpired()) {
            this.securityService.logout();
          } else  {
            wrapper = req.clone({
              headers: req.headers.set('Authorization', `Bearer ${this.securityService.getToken()}`)
            });
          }
        }
      }
    }

    wrapper = wrapper || req;


    return next.handle(wrapper).pipe(
      map((event: HttpEvent<any>) => {
        if (event instanceof HttpResponse) {
          console.log('event--->>>', event);
          // this.errorDialogService.openDialog(event);
        }
        return event;
      }),
      catchError((error: HttpErrorResponse) => {
        let data = {};
        data = {
          reason: error && error.error.error ? error.error.error : '',
          status: error.status
        };
        console.log(data);

      /*  if(data.reason === 'invalid_token') {
          this.securityService.logout();
        }*/
        return throwError(error);
      }));
  }

  private path(url: string): string {
    return url.substr(url.indexOf(environment.apiUrl) + environment.apiUrl.length);
  }
}
