import {Serializer} from '../../shared/serializer';
import {RevisionResponse} from '../../models/revision.response';

export class RevisionSerializer implements Serializer<RevisionResponse> {
  fromJson(json: any): RevisionResponse {
    return json as RevisionResponse;
  }

  toJson(r: RevisionResponse): any {
  }
}
