import {NgModule} from '@angular/core';
import {ApprunListComponent} from './apprun-list.component';
import {GenericModule} from '../../shared/generic.module';
import {ApprunSetupComponent} from './apprun-setup.component';
import {RouterModule} from '@angular/router';
import {ReactiveFormsModule} from '@angular/forms';
import {ConfigurationModule} from '../configurations/configuration.module';
import {ApprunService} from './apprun.service';
import {ApprunComponent} from './apprun.component';
import {CovalentHighlightModule} from '@covalent/highlight';

@NgModule({
  declarations: [ApprunListComponent, ApprunSetupComponent, ApprunComponent],
  imports: [GenericModule, RouterModule, ReactiveFormsModule, ConfigurationModule, CovalentHighlightModule],
  providers: [ApprunService]
})
export class ApprunModule {

}
