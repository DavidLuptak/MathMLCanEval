import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ConfigurationListComponent} from './parts/configurations/configuration-list.component';
import {DashboardComponent} from './parts/dashboard/dashboard.component';
import {RevisionListComponent} from './parts/revisions/revision-list.component';

const routes: Routes = [{
  path: '',
  redirectTo: 'dashboard',
  pathMatch: 'full'
}, {
  path: 'dashboard',
  component: DashboardComponent
}, {
  path: 'configurations',
  component: ConfigurationListComponent
},
  {
    path: 'revisions',
    component: RevisionListComponent
  }];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
