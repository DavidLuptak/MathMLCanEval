import {Resource} from '../shared/resource';
import {NamedResource} from '../shared/named-resource';

export class FormulaCollectionResponse extends Resource implements NamedResource {
  id: number;
  name: string;
  note: string;
  visibleToPublic: boolean;
  ownedById: number;
  ownedByName: string;
}
