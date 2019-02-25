import {Serializer} from '../../shared/serializer';
import {FormulaResponse} from '../../models/formula.response';

export class FormulaSerializer implements Serializer<FormulaResponse> {
  fromJson(json: any): FormulaResponse {
    return json as FormulaResponse;
  }

  toJson(r: FormulaResponse): any {
  }

}
