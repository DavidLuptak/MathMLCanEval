import {Resource} from './resource';

export interface Serializer<R extends Resource> {

  fromJson(json: any): R;

  toJson(r: R): any;

}
