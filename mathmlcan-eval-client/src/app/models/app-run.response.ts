import {Resource} from '../shared/resource';

export class AppRunResponse extends Resource {
  id: number;
  start: Date;
  end: Date;
  configurationId: number;
  revisionId: number;
  startedById: number;
  startedByName: number;
}
