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
import {TdDialogService} from '@covalent/core';
import {MatSnackBar} from '@angular/material';

const TOKEN_URL = `${environment.apiUrl}/api/oauth/token`;
const REVOKE_URL = `${environment.apiUrl}/api/oauth/revoke`;

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  private protectedResources: Map<string, RegExp[]>;

  constructor(private securityService: SecurityService,
              private snackService: MatSnackBar) {
    this.protectedResources = new Map<string, RegExp[]>(
      [
        ['GET', [new RegExp(/^\/api\/me$/i),
          new RegExp(/^\/api\/running-tasks$/i),
          new RegExp(/^\/api\/collections$/i),
          new RegExp(/^\/api\/app-runs$/i),
          new RegExp(/^\/api\/app-runs\/\d+\/details$/i)]],
        ['POST', [new RegExp(/^\/api\/configurations$/i),
          new RegExp(/^\/api\/collections$/i),
          new RegExp(/^\/api\/revisions$/i),
          new RegExp(/^\/api\/revisions\/latest$/i),
          new RegExp(/^\/api\/app-runs$/i)]],
        ['PATCH', [new RegExp(/^\/api\/revisions\/\d+$/i),
          new RegExp(/^\/api\/configurations\/\d+$/i)]]
      ]
    );
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // todo https://stackoverflow.com/a/45932412/1203690

    if(req.url !== TOKEN_URL && req.url !== REVOKE_URL) {
      for (const resource of this.protectedResources.get(req.method)) {
        if(resource.test(this.path(req.url))) {
         req = req.clone({
              headers: req.headers.set('Authorization', `Bearer ${this.securityService.getToken()}`)
            });
        }
      }
    }

    return next.handle(req).pipe(
      catchError((error: HttpErrorResponse) => {
        if(error.status === 401) {
          this.snackService.open('Token expired. You have been logged out.', 'Close', {
            duration: 3000
          });
          this.securityService.clearLocalStorage();
        } else if(error.status === 403) {
          this.securityService.deniedModal();
        }
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
