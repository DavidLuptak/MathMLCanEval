import {NamedResource} from '../shared/named-resource';

export class RevisionResponse implements NamedResource {
  id: number;
  name: string;
  note: string;
  sha1: string;
  commitTime: Date;
  syncTime: Date
}
