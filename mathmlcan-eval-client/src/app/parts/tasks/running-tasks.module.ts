import {NgModule} from '@angular/core';
import {RunningTasksService} from './running-tasks.service';
import {RunningTaskListComponent} from './running-task-list.component';
import {GenericModule} from '../../shared/generic.module';

@NgModule({
  declarations: [RunningTaskListComponent],
  providers: [RunningTasksService],
  imports: [GenericModule]
})
export class RunningTasksModule {

}
