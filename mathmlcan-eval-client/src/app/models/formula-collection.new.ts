import {Resource} from '../shared/resource';

export class FormulaCollectionNew extends Resource {
  name: string;
  note: string;
  visibleToPublic: boolean;
  formulas: number[];
}
