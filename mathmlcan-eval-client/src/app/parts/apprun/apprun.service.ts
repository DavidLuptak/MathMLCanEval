import {AbstractService} from '../../shared/abstract.service';
import {AppRunResponse} from '../../models/app-run.response';
import {AppRunRequest} from '../../models/app-run.request';
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ApprunSerializer} from './apprun.serializer';

@Injectable()
export class ApprunService extends AbstractService<AppRunResponse, AppRunRequest> {

  constructor(private httpClient: HttpClient) {
    super(httpClient, '/api/app-runs', new ApprunSerializer());
  }
}
