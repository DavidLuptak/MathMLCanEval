import {TableComponent} from '../../shared/table.component';
import {RevisionResponse} from '../../models/revision.response';
import {Component, OnInit} from '@angular/core';
import {RevisionService} from './revision.service';
import {MatDialog, MatTableDataSource} from '@angular/material';
import {NewRevisionComponent} from './new-revision.component';
import {generate, observe, Observer} from 'fast-json-patch';
import {forkJoin} from 'rxjs';
import {SecurityService} from '../../shared/security/security.service';

@Component({
  selector: 'revision-list',
  templateUrl: 'revision-list.component.html'
})
export class RevisionListComponent extends TableComponent<RevisionResponse> implements OnInit {
  displayedColumns: string[] = ['id', 'name', 'sha1', 'commitTime', 'syncTime'];

  revisionChanges = new Map<number, Observer<RevisionResponse>>();

  observer: Observer<RevisionResponse>;

  currentKey: number = null;

  constructor(private revisionService: RevisionService,
              private dialog: MatDialog,
              private securityService: SecurityService) {
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

  switchToEdit(id: number): void {
    if (this.securityService.isAuthenticated()) {
      if (!this.revisionChanges.get(id)) {
        for (let rev of this.dataSource.data) {
          if (rev.id === id) {
            this.revisionChanges.set(id, observe(rev));
            break;
          }
        }
      }

      this.currentKey = id;
    }
  }

  saveChanges(): void {
    const requests = [];
    this.revisionChanges.forEach((value: Observer<any>, key: number) => {
      const changes = generate(value);
      if (changes && changes.length > 0) {
        requests.push(this.revisionService.patch(key, changes));
      }
    });

    if (requests.length) {
      forkJoin(requests).subscribe(results => {
        console.log(results);
      });
    }
  }
}
