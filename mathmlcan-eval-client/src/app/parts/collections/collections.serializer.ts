import {Serializer} from '../../shared/serializer';
import {FormulaCollectionResponse} from '../../models/formula-collection.response';

export class CollectionsSerializer implements Serializer<FormulaCollectionResponse> {
  fromJson(json: any): FormulaCollectionResponse {
    return json as FormulaCollectionResponse;
  }

  toJson(r: FormulaCollectionResponse): any {
  }
}
