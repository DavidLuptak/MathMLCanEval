import {BaseComponent} from '../../shared/base.component';
import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {ApprunService} from './apprun.service';
import {ApprunDetailResponse, CanonicalizationContainer} from '../../models/apprun-detail.response';
import {FormulaResponse} from '../../models/formula.response';
import {MathContent} from '../../shared/math/math-content';

@Component({
  selector: 'apprun-component',
  templateUrl: 'apprun.component.html'
})
export class ApprunComponent extends BaseComponent implements OnInit {
  appRun: ApprunDetailResponse;

  constructor(private route: ActivatedRoute,
              private apprunService: ApprunService) {
    super();
  }

  ngOnInit(): void {
    this.apprunService.detailedAppRun(this.idFromRoute(this.route))
    .subscribe(res => this.appRun = res);
  }


  convertCanonicOutput(container: CanonicalizationContainer, field: string): MathContent {
    if(field === 'formula') {
      return {
        mathml: container.formulaXml
      }
    } else {
      return {
        mathml: container.canonicXml
      }
    }
  }
}
