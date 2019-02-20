import {Component, ViewChild} from '@angular/core';
import {ConfigurationResponse} from '../../models/configuration.response';
import {ConfigurationNew} from '../../models/configuration.new';
import {MatDialogRef, MatTabGroup} from '@angular/material';
import {ConfigurationService} from './configuration.service';
import {NewResourceComponent} from '../../shared/new-resource.component';
import {ReadFile, ReadMode} from 'ngx-file-helpers';

@Component({
  selector: 'new-configuration-component',
  templateUrl: 'new-configuration.component.html',
  styleUrls: ['new-configuration.component.css']
})
export class NewConfigurationComponent extends NewResourceComponent<ConfigurationResponse, ConfigurationNew, number> {
  readMode = ReadMode.dataURL;
  @ViewChild(MatTabGroup) tabGroup;

  constructor(public dialogRef: MatDialogRef<NewConfigurationComponent>,
              private configurationService: ConfigurationService) {
    super(dialogRef, configurationService);
  }

  initNewResource(): ConfigurationNew {
    return new ConfigurationNew;
  }

  processFile(file: ReadFile): void {
    this.newResource.content = atob(file.content.substring(file.content.indexOf('base64') + 7));

    this.tabGroup.selectedIndex = 1;
  }
}
