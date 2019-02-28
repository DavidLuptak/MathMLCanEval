import {Resource} from '../shared/resource';
import {NamedResource} from '../shared/named-resource';

export class FormulaResponse extends Resource implements NamedResource {
  id: number;
  xml: string;
  name: string;
}
