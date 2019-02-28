import {NgModule} from '@angular/core';
import {ApprunListComponent} from './apprun-list.component';
import {GenericModule} from '../../shared/generic.module';
import {ApprunSetupComponent} from './apprun-setup.component';
import {RouterModule} from '@angular/router';
import {ReactiveFormsModule} from '@angular/forms';
import {ConfigurationModule} from '../configurations/configuration.module';

@NgModule({
  declarations: [ApprunListComponent, ApprunSetupComponent],
  imports: [GenericModule, RouterModule, ReactiveFormsModule, ConfigurationModule]
})
export class ApprunModule {

}
