import {AbstractService} from '../../shared/abstract.service';
import {RevisionResponse} from '../../models/revision.response';
import {HttpClient} from '@angular/common/http';
import {RevisionSerializer} from './revision.serializer';
import {Injectable} from '@angular/core';

@Injectable()
export class RevisionService extends AbstractService<RevisionResponse, number> {
  constructor(private httpClient: HttpClient) {
    super(httpClient, '/api/revisions', new RevisionSerializer());
  }
}
