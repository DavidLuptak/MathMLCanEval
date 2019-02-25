import {Component, OnInit} from '@angular/core';
import {BaseComponent} from '../../shared/base.component';
import {IPageChangeEvent, TdMediaService} from '@covalent/core';
import {FormulaService} from './formula.service';
import {FormulaResponse} from '../../models/formula.response';
import {QueryParamsBuilder} from '../../shared/query-params-builder';

@Component({
  selector: 'formula-list',
  templateUrl: 'formula-list.component.html',
  styleUrls: ['formula-list.component.css']
})
export class FormulaListComponent extends BaseComponent implements OnInit {
  selectMode = false;
  eventResponsive: IPageChangeEvent;
  pageSizeResponsive: number = 25;
  formulas: FormulaResponse[];

  constructor(private formulaService: FormulaService,
              public media: TdMediaService) {
    super();
  }

  ngOnInit(): void {
    this.formulaService
    .query(new QueryParamsBuilder().withPage(2).build())
    .subscribe((res: FormulaResponse[]) => this.formulas = res);
  }


  changeResponsive(event: IPageChangeEvent): void {
    console.log(event);
    let qpb = new QueryParamsBuilder()
    .withPage(event.page);

    this.formulaService
    .query(qpb.build())
    .subscribe((res: FormulaResponse[]) => this.formulas = res);

    this.eventResponsive = event;
  }

  switchSelectMode(): void {
    this.selectMode = !this.selectMode
  }
}
