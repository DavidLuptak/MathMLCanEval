import {TableComponent} from '../../shared/table.component';
import {RunningTaskResponse} from '../../models/running-task.response';
import {Component, OnInit} from '@angular/core';
import {RunningTasksService} from './running-tasks.service';
import {MatTableDataSource} from '@angular/material';

@Component({
  selector: 'running-task-list',
  templateUrl: 'running-task-list.component.html'
})
export class RunningTaskListComponent extends TableComponent<RunningTaskResponse> implements OnInit {

  displayedColumns = ['jobId', 'jobGroup', 'lastExecutionDate', 'controls'];

  constructor(private runningTaskService: RunningTasksService) {
    super();

    this.dataSource = new MatTableDataSource<RunningTaskResponse>();
  }

  ngOnInit(): void {
    this.runningTaskService
    .query()
    .subscribe((tasks: RunningTaskResponse[]) => this.pushRows(tasks));
  }


}
