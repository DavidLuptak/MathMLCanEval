import {AppTranslations} from '../app.translations';

export abstract class BaseComponent {
/*  protected constructor() {

  }*/

  public get translations(): any {
    return AppTranslations.instant;
  }
}
