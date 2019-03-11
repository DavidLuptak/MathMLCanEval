import {BaseComponent} from '../../shared/base.component';
import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {ApprunService} from './apprun.service';
import {ApprunDetailResponse} from '../../models/apprun-detail.response';

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
}
