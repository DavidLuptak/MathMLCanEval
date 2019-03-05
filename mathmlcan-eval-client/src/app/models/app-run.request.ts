import {Resource} from '../shared/resource';

export class AppRunRequest extends Resource {
  configurationId: number;
  collectionId: number;
  revisionId: number;
  postProcessors: string[];
}
