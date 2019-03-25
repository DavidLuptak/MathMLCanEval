import {NgModule} from '@angular/core';
import {ConfigurationListComponent} from './configuration-list.component';
import {GenericModule} from '../../shared/generic.module';
import {ConfigurationService} from './configuration.service';
import {CovalentHighlightModule} from '@covalent/highlight';
import {NewConfigurationComponent} from './new-configuration.component';
import {ConfigurationPreviewComponent} from './configuration-preview.component';
import {AppRoutingModule} from '../../app-routing.module';
import {RenameConfigurationComponent} from './rename-configuration.component';

@NgModule({
  declarations: [ConfigurationListComponent, NewConfigurationComponent, ConfigurationPreviewComponent, RenameConfigurationComponent],
  exports: [ConfigurationPreviewComponent],
  imports: [GenericModule, CovalentHighlightModule, AppRoutingModule],
  providers: [ConfigurationService],
  entryComponents: [NewConfigurationComponent, RenameConfigurationComponent]
})
export class ConfigurationModule {
}
