import {Resource} from './resource';
import {HttpClient, HttpParams, HttpResponse} from '@angular/common/http';
import {Serializer} from './serializer';
import {environment} from '../../environments/environment';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {Operation} from 'fast-json-patch';
import {Page} from '../models/page';

const API = environment.apiUrl;

export abstract class AbstractService<T extends Resource, ID> {
  protected resource: string;

  protected constructor(protected _httpClient: HttpClient,
                        protected _endpoint: string,
                        protected _serializer: Serializer<T>) {
    this.resource = API + _endpoint;
  }

  public save<C extends Resource>(resource: C, serializer?: Serializer<C>): Observable<T> {
    return this._httpClient
    .post<T>(`${this.resource}`, this.delegateSerialization(resource, serializer), {observe: 'response'})
    .pipe(map((data: HttpResponse<T>) => this._serializer.fromJson(data.body)));
  }

  public delete(id: ID): Observable<Boolean> {
    return this._httpClient
      .delete(`${this.resource}/${id}`, {observe: 'response'})
      .pipe(
        map((data: HttpResponse<any>) => true)
      );
  }

  public get(id: ID): Observable<T> {
    return this._httpClient
    .get<T>(`${this.resource}/${id}`, {observe: 'response'})
    .pipe(map((data: HttpResponse<T>) => this._serializer.fromJson(data.body)));
  }

  public query(params?: HttpParams): Observable<Page<T>> {
    return this._httpClient
    .get<Page<T>>(`${this.resource}`, {observe: 'response', params: params})
    .pipe(map((data: HttpResponse<Page<any>>) => {
      const copy = Object.assign({}, data.body);
      copy.content = this.mapList(data.body.content);

      return copy;
    }));
  }

  public patch(id: ID, patch: Operation[]): Observable<T> {
    return this._httpClient
    .patch<T>(`${this.resource}/${id}`, patch, {observe: 'response'})
    .pipe(map((data: HttpResponse<T>) => this._serializer.fromJson(data.body)));
  }


  protected mapList(data: any): T[] {
    return data.map(item => this._serializer.fromJson(item));
  }

  private delegateSerialization<C>(resource: C, serializer?: Serializer<C>): any {
    if (serializer) {
      return serializer.toJson(resource);
    } else {
      return resource;
    }
  }
}
