import {AbstractService} from '../../shared/abstract.service';
import {ConfigurationResponse} from '../../models/configuration.response';
import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {ConfigurationSerializer} from './configuration.serializer';
import {Observable} from 'rxjs';
import {AppRunResponse} from '../../models/app-run.response';
import {map} from 'rxjs/operators';

@Injectable()
export class ConfigurationService extends AbstractService<ConfigurationResponse, number> {

  constructor(private httpClient: HttpClient) {
    super(httpClient, '/api/configurations', new ConfigurationSerializer())
  }


  getRunsUsedByConfiguration(id: number): Observable<AppRunResponse[]> {
    return this.httpClient
    .get<AppRunResponse[]>(`${this.resource}/${id}/app-runs`, {observe: 'response'})
    .pipe(map((data: HttpResponse<AppRunResponse[]>) => data.body));
  }
}
