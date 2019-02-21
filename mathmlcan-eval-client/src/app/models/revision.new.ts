import {Resource} from '../shared/resource';

export class RevisionNew extends Resource{
  from: Date;
  to: Date;
  sha1: string;
}
