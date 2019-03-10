import {Serializer} from '../../shared/serializer';
import {RunningTaskResponse} from '../../models/running-task.response';

export class RunningTasksSerializer implements Serializer<RunningTaskResponse> {
  fromJson(json: any): RunningTaskResponse {
    return json as RunningTaskResponse;
  }

  toJson(r: RunningTaskResponse): any {
  }

}
