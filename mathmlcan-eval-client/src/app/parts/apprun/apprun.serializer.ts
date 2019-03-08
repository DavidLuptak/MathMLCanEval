import {Serializer} from '../../shared/serializer';
import {AppRunResponse} from '../../models/app-run.response';

export class ApprunSerializer implements Serializer<AppRunResponse> {
  fromJson(json: any): AppRunResponse {
    return json as AppRunResponse;
  }

  toJson(r: AppRunResponse): any {
  }
}
