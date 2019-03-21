import {Component} from '@angular/core';
import {BaseComponent} from '../../shared/base.component';
import {DropzoneConfigInterface} from 'ngx-dropzone-wrapper';
import {SecurityService} from '../../shared/security/security.service';

@Component({
  selector: 'app-formula-import-component',
  templateUrl: 'formula-import.component.html'
})
export class FormulaImportComponent extends BaseComponent {

  constructor(private securityService: SecurityService) {
    super();
  }


  dropzoneHeaders(): DropzoneConfigInterface {
    const config: DropzoneConfigInterface = {
      headers: {
        Authorization: `Bearer ${this.securityService.getToken()}`
      },
      url: 'http://localhost:8080/api/formulas',
      uploadMultiple: true,
      acceptedFiles: '.xml,.zip',
    };

    return config;
  }

  onError(event: any): void {
    console.log(event);
  }

  onSuccess(event: any) : void {
    console.log(event);
  }

  onCancel(event: any): void {
    console.log(event);
  }
}
