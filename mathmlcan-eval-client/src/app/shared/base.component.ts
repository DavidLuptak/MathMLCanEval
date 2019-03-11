import {AppTranslations} from '../app.translations';
import {ActivatedRoute} from '@angular/router';

export abstract class BaseComponent {
/*  protected constructor() {

  }*/

  public get translations(): any {
    return AppTranslations.instant;
  }

  protected idFromRoute(route: ActivatedRoute): number {
    return +route.snapshot.paramMap.get('id');
  }
}
