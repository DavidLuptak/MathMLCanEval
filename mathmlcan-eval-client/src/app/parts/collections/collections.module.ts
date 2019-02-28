import {NgModule} from '@angular/core';
import {CollectionsService} from './collections.service';
import {NewCollectionComponent} from './new-collection.component';
import {GenericModule} from '../../shared/generic.module';
import {CollectionsComponent} from './collections.component';

@NgModule({
  declarations: [NewCollectionComponent, CollectionsComponent],
  providers: [CollectionsService],
  imports: [GenericModule],
  entryComponents: [NewCollectionComponent]
})
export class CollectionsModule {

}
