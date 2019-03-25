import {Component, Inject} from '@angular/core';
import {BaseComponent} from '../../shared/base.component';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {ConfigurationResponse} from '../../models/configuration.response';

@Component({
  selector: 'app-rename-configuration',
  templateUrl: 'rename-configuration.component.html'
})
export class RenameConfigurationComponent extends BaseComponent {
  constructor(private dialogRef: MatDialogRef<RenameConfigurationComponent>,
              @Inject(MAT_DIALOG_DATA) private data: RenameConfigurationModalData) {
    super();
  }


  protected cancel(): void {
    this.dialogRef.close();
  }

  rename(): void {
    this.dialogRef.close(this.data.configuration);
  }
}


export interface RenameConfigurationModalData {
  configuration: ConfigurationResponse
}
