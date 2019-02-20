import {Serializer} from '../../shared/serializer';
import {ConfigurationResponse} from '../../models/configuration.response';

export class ConfigurationSerializer implements Serializer<ConfigurationResponse> {
  fromJson(json: any): ConfigurationResponse {
    return json as ConfigurationResponse;
  }

  toJson(r: ConfigurationResponse): any {
    return {
      id: r.id
    };
  }

}
