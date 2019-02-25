import {Injectable} from '@angular/core';
import {AbstractService} from '../../shared/abstract.service';
import {FormulaResponse} from '../../models/formula.response';
import {FormulaNew} from '../../models/formula.new';
import {HttpClient} from '@angular/common/http';
import {FormulaSerializer} from './formula.serializer';

@Injectable()
export class FormulaService extends AbstractService<FormulaResponse, FormulaNew> {

  constructor(private httpClient: HttpClient) {
    super(httpClient, '/api/formulas', new FormulaSerializer());
  }
}
