import {Component, OnInit} from '@angular/core';
import {TableComponent} from '../../shared/table.component';
import {AppRunResponse} from '../../models/app-run.response';
import {ApprunService} from './apprun.service';
import {MatTableDataSource} from '@angular/material';
import {Router} from '@angular/router';

@Component({
  selector: 'apprun-list',
  templateUrl: 'apprun-list.component.html'
})
export class ApprunListComponent extends TableComponent<AppRunResponse> implements OnInit {

  displayedColumns = ['id', 'start', 'end', 'startedById'];

  constructor(private appRunService: ApprunService,
              private router: Router) {
    super();

    this.dataSource = new MatTableDataSource<AppRunResponse>();
  }

  ngOnInit(): void {
    this.appRunService
    .query()
    .subscribe((runs: AppRunResponse[]) => this.pushRows(runs));
  }

  openAppRun(id: number): void {
    this.router.navigate(['/app-runs', id]);
  }
}
