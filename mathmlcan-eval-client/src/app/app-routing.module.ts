import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ConfigurationListComponent} from './parts/configurations/configuration-list.component';
import {DashboardComponent} from './parts/dashboard/dashboard.component';
import {RevisionListComponent} from './parts/revisions/revision-list.component';
import {FormulaListComponent} from './parts/formula/formula-list.component';
import {FormulaComponent} from './parts/formula/formula.component';
import {ApprunListComponent} from './parts/apprun/apprun-list.component';
import {ApprunSetupComponent} from './parts/apprun/apprun-setup.component';
import {CollectionsComponent} from './parts/collections/collections.component';

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
}, {
  path: 'revisions',
  component: RevisionListComponent
}, {
  path: 'formulas',
  component: FormulaListComponent
}, {
  path: 'formulas/:id',
  component: FormulaComponent
}, {
  path: 'app-runs',
  component: ApprunListComponent
}, {
  path: 'app-runs/new',
  component: ApprunSetupComponent
}, {
  path: 'collections',
  component: CollectionsComponent
}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}