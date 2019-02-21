import {BaseComponent} from '../../shared/base.component';
import {Component} from '@angular/core';
import {RevisionService} from './revision.service';
import {MatDialogRef} from '@angular/material';

@Component({
  selector: 'new-revision',
  templateUrl: 'new-revision.component.html'
})
export class NewRevisionComponent extends BaseComponent {
  constructor(private revisionService: RevisionService,
              public dialogRef: MatDialogRef<NewRevisionComponent>) {
    super();
  }


  protected cancel(): void {
    this.dialogRef.close();
  }

  protected save(): void {

  }
}
