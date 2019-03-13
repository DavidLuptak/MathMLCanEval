import {NgModule} from '@angular/core';
import {DashboardComponent} from './dashboard.component';
import {GenericModule} from '../../shared/generic.module';

@NgModule({
  declarations: [DashboardComponent],
  imports: [GenericModule
  ]
})
export class DashboardModule {

}
