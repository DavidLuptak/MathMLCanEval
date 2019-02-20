import {AbstractService} from '../../shared/abstract.service';
import {ConfigurationResponse} from '../../models/configuration.response';
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ConfigurationSerializer} from './configuration.serializer';

@Injectable()
export class ConfigurationService extends AbstractService<ConfigurationResponse, number> {

  constructor(private httpClient: HttpClient) {
    super(httpClient, '/api/configurations', new ConfigurationSerializer())
  }

}
