import {BaseComponent} from '../../shared/base.component';
import {Component, OnInit} from '@angular/core';
import {RevisionService} from './revision.service';
import {MatDialogRef} from '@angular/material';
import {RevisionNew} from '../../models/revision.new';

@Component({
  selector: 'new-revision',
  templateUrl: 'new-revision.component.html'
})
export class NewRevisionComponent extends BaseComponent implements OnInit {
  revisionSync: RevisionNew;
  constructor(private revisionService: RevisionService,
              public dialogRef: MatDialogRef<NewRevisionComponent>) {
    super();
  }

  ngOnInit(): void {
    this.revisionSync = new RevisionNew();
  }




  protected cancel(): void {
    this.dialogRef.close();
  }

  protected save(): void {
    this.revisionService.save(this.revisionSync)
    .subscribe(() => this.dialogRef.close());
  }
}
