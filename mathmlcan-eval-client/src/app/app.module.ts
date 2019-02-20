import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {GenericModule} from './shared/generic.module';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {ConfigurationModule} from './parts/configurations/configuration.module';
import {DashboardModule} from './parts/dashboard/dashboard.module';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserAnimationsModule,
    BrowserModule,
    AppRoutingModule,
    GenericModule,
    ConfigurationModule,
    DashboardModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
