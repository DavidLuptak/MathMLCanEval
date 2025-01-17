import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {GenericModule} from './shared/generic.module';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {ConfigurationModule} from './parts/configurations/configuration.module';
import {DashboardModule} from './parts/dashboard/dashboard.module';
import {HTTP_INTERCEPTORS} from '@angular/common/http';
import {AuthInterceptor} from './shared/security/auth.interceptor';
import {SecurityService} from './shared/security/security.service';
import {WebStorageModule} from 'ngx-store';
import {LoginModal} from './shared/security/login.modal';
import {ReactiveFormsModule} from '@angular/forms';
import {LoginDropdownComponent} from './shared/security/login-dropdown.component';
import {RevisionModule} from './parts/revisions/revision.module';
import {FormulaModule} from './parts/formula/formula.module';
import {ApprunModule} from './parts/apprun/apprun.module';
import {CollectionsModule} from './parts/collections/collections.module';
import {RunningTasksModule} from './parts/tasks/running-tasks.module';

@NgModule({
  declarations: [
    AppComponent,
    LoginModal,
    LoginDropdownComponent
  ],
  imports: [
    BrowserAnimationsModule,
    BrowserModule,
    AppRoutingModule,
    GenericModule,
    ReactiveFormsModule,
    ConfigurationModule,
    RevisionModule,
    DashboardModule,
    FormulaModule,
    WebStorageModule,
    ApprunModule,
    CollectionsModule,
    RunningTasksModule
  ],
  providers: [[{
    provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true
  }],
    SecurityService
  ],
  bootstrap: [AppComponent],
  entryComponents: [LoginModal]
})
export class AppModule {
}
