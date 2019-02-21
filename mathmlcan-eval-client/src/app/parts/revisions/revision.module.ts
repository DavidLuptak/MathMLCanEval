import {NgModule} from '@angular/core';
import {RevisionListComponent} from './revision-list.component';
import {GenericModule} from '../../shared/generic.module';
import {RevisionService} from './revision.service';
import {NewRevisionComponent} from './new-revision.component';

@NgModule({
  declarations: [RevisionListComponent, NewRevisionComponent],
  imports: [GenericModule],
  providers: [RevisionService],
  entryComponents: [NewRevisionComponent]
})
export class RevisionModule {

}
