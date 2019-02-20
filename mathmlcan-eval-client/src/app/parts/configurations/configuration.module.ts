import {NgModule} from '@angular/core';
import {ConfigurationListComponent} from './configuration-list.component';
import {GenericModule} from '../../shared/generic.module';
import {ConfigurationService} from './configuration.service';
import {CovalentHighlightModule} from '@covalent/highlight';
import {NewConfigurationComponent} from './new-configuration.component';

@NgModule({
  declarations: [ConfigurationListComponent, NewConfigurationComponent],
  imports: [GenericModule, CovalentHighlightModule],
  providers: [ConfigurationService],
  entryComponents: [NewConfigurationComponent]
})
export class ConfigurationModule {
}
