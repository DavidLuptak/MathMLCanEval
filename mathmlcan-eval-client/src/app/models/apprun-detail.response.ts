import {AppRunResponse} from './app-run.response';

export class ApprunDetailResponse extends AppRunResponse {
  configurationXml: string;
  revisionHash: string;
  canonicalizations: CanonicalizationContainer[];
}


export interface CanonicalizationContainer {
  formulaXml: string;
  formulaId: number;
  canonicId: number;
  canonicXml: string;
  canonicalizationError: string;
}
