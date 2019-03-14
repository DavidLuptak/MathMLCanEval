import {Resource} from '../shared/resource';

export class ConfigurationResponse extends Resource {
  id: number;
  content: string;
  note: string;
  name: string;
  visibleToPublic: boolean;
  ownedById: number;
  ownedByName: string;
}
