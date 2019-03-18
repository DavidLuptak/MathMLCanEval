import {NgModule} from '@angular/core';
import {FormulaListComponent} from './formula-list.component';
import {GenericModule} from '../../shared/generic.module';
import {FormulaService} from './formula.service';
import {FormulaComponent} from './formula.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

@NgModule({
  declarations: [FormulaListComponent, FormulaComponent],
  imports: [GenericModule, BrowserAnimationsModule],
  providers: [FormulaService]
})
export class FormulaModule {

}
