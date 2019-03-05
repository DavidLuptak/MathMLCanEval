import {TableComponent} from '../../shared/table.component';
import {RevisionResponse} from '../../models/revision.response';
import {Component, OnInit} from '@angular/core';
import {RevisionService} from './revision.service';
import {MatDialog, MatTableDataSource} from '@angular/material';
import {NewRevisionComponent} from './new-revision.component';

@Component({
  selector: 'revision-list',
  templateUrl: 'revision-list.component.html'
})
export class RevisionListComponent extends TableComponent<RevisionResponse> implements OnInit {
  displayedColumns: string[] = ['id', 'name', 'sha1', 'commitTime', 'syncTime'];

  constructor(private revisionService: RevisionService,
              private dialog: MatDialog) {
    super();

    this.dataSource = new MatTableDataSource<RevisionResponse>();
  }

  ngOnInit(): void {
    this.revisionService
    .query()
    .subscribe((rows: RevisionResponse[]) => this.pushRows(rows));
  }

  requestManualSync(): void {
    this.dialog.open(NewRevisionComponent);
  }
}
