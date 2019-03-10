import {AbstractService} from '../../shared/abstract.service';
import {RunningTaskResponse} from '../../models/running-task.response';
import {HttpClient} from '@angular/common/http';
import {RunningTasksSerializer} from './running-tasks.serializer';
import {Injectable} from '@angular/core';

@Injectable()
export class RunningTasksService extends AbstractService<RunningTaskResponse, number> {

  constructor(private httpClient: HttpClient) {
    super(httpClient, '/api/running-tasks', new RunningTasksSerializer());
  }
}
