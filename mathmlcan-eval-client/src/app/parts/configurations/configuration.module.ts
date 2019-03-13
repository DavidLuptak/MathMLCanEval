import {NgModule} from '@angular/core';
import {ConfigurationListComponent} from './configuration-list.component';
import {GenericModule} from '../../shared/generic.module';
import {ConfigurationService} from './configuration.service';
import {CovalentHighlightModule} from '@covalent/highlight';
import {NewConfigurationComponent} from './new-configuration.component';
import {ConfigurationPreviewComponent} from './configuration-preview.component';
import {AppRoutingModule} from '../../app-routing.module';

@NgModule({
  declarations: [ConfigurationListComponent, NewConfigurationComponent, ConfigurationPreviewComponent],
  exports: [ConfigurationPreviewComponent],
  imports: [GenericModule, CovalentHighlightModule, AppRoutingModule],
  providers: [ConfigurationService],
  entryComponents: [NewConfigurationComponent]
})
export class ConfigurationModule {
}
