import {AbstractService} from '../../shared/abstract.service';
import {RevisionResponse} from '../../models/revision.response';
import {HttpClient} from '@angular/common/http';
import {RevisionSerializer} from './revision.serializer';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';

@Injectable()
export class RevisionService extends AbstractService<RevisionResponse, number> {
  constructor(private httpClient: HttpClient) {
    super(httpClient, '/api/revisions', new RevisionSerializer());
  }

  syncLatest() : Observable<any> {
    return this.httpClient
    .post(`${this.resource}/latest`,{},{observe: 'response'});
  }
}
