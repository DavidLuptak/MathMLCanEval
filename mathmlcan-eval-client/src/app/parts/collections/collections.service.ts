import {Injectable} from '@angular/core';
import {AbstractService} from '../../shared/abstract.service';
import {FormulaCollectionResponse} from '../../models/formula-collection.response';
import {FormulaCollectionNew} from '../../models/formula-collection.new';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {CollectionsSerializer} from './collections.serializer';
import {Observable} from 'rxjs';
import {FormulaResponse} from '../../models/formula.response';
import {map} from 'rxjs/operators';

@Injectable()
export class CollectionsService extends AbstractService<FormulaCollectionResponse, FormulaCollectionNew> {

  constructor(private httpClient: HttpClient) {
    super(httpClient, '/api/collections', new CollectionsSerializer());
  }


  getFormulas(collection: number): Observable<FormulaResponse[]> {
    return this.httpClient
    .get<FormulaResponse[]>(`${this.resource}/${collection}/formulas`, {observe: 'response'})
    .pipe(map((data: HttpResponse<FormulaResponse[]>) => data.body));
  }
}
