import {AbstractService} from '../../shared/abstract.service';
import {AppRunResponse} from '../../models/app-run.response';
import {AppRunRequest} from '../../models/app-run.request';
import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {ApprunSerializer} from './apprun.serializer';
import {Observable} from 'rxjs';
import {ApprunDetailResponse} from '../../models/apprun-detail.response';
import {map} from 'rxjs/operators';

@Injectable()
export class ApprunService extends AbstractService<AppRunResponse, number> {

  constructor(private httpClient: HttpClient) {
    super(httpClient, '/api/app-runs', new ApprunSerializer());
  }

  detailedAppRun(id: number): Observable<ApprunDetailResponse> {
    return this.httpClient
    .get<ApprunDetailResponse>(`${this.resource}/${id}/details`, {observe: 'response'})
    .pipe(
      map((response: HttpResponse<ApprunDetailResponse>) => response.body)
    );
  }
}
