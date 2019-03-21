import {NgModule} from '@angular/core';
import {FormulaListComponent} from './formula-list.component';
import {GenericModule} from '../../shared/generic.module';
import {FormulaService} from './formula.service';
import {FormulaComponent} from './formula.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {DROPZONE_CONFIG, DropzoneConfigInterface, DropzoneModule} from 'ngx-dropzone-wrapper';
import {FormulaImportComponent} from './formula-import.component';
import {RouterModule} from '@angular/router';

export const DEFAULT_DROPZONE_CONFIG: DropzoneConfigInterface = {
  // Change this to your upload POST address:
  url: 'https://httpbin.org/post',
  maxFilesize: 50,
  acceptedFiles: 'image/*',
  uploadMultiple: true
};

@NgModule({
  declarations: [FormulaListComponent, FormulaComponent, FormulaImportComponent],
  imports: [GenericModule, BrowserAnimationsModule, DropzoneModule, RouterModule],
  providers: [FormulaService, {
    provide: DROPZONE_CONFIG,
    useValue: DEFAULT_DROPZONE_CONFIG
  }]
})
export class FormulaModule {

}
