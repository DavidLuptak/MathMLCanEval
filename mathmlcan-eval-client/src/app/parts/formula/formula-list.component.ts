import {Component, OnInit} from '@angular/core';
import {BaseComponent} from '../../shared/base.component';
import {IPageChangeEvent, TdMediaService} from '@covalent/core';
import {FormulaService} from './formula.service';
import {FormulaResponse} from '../../models/formula.response';
import {QueryParamsBuilder} from '../../shared/query-params-builder';
import {MatDialog} from '@angular/material';
import {NewCollectionComponent} from '../collections/new-collection.component';
import {FormulaCollectionNew} from '../../models/formula-collection.new';
import {Page} from '../../models/page';
import {Router} from '@angular/router';
import {MathContent} from '../../shared/math/math-content';

@Component({
  selector: 'formula-list',
  templateUrl: 'formula-list.component.html',
  styleUrls: ['formula-list.component.css']
})
export class FormulaListComponent extends BaseComponent implements OnInit {
  selectMode = false;
  eventResponsive: IPageChangeEvent;
  pageSizeResponsive: number = 25;

  page: Page<FormulaResponse>;

  formulas: FormulaResponse[];
  selectedFormulas = new Set();

  constructor(private formulaService: FormulaService,
              public media: TdMediaService,
              private dialog: MatDialog,
              private router: Router) {
    super();
  }

  ngOnInit(): void {
    this.formulaService
    .query(new QueryParamsBuilder().withPage(1).build())
    .subscribe((page: Page<FormulaResponse>) => {
      this.formulas = page.content;
      this.page = page;
    });
  }


  changeResponsive(event: IPageChangeEvent): void {
    console.log(event);
    let qpb = new QueryParamsBuilder()
    .withPage(event.page);

    this.formulaService
    .query(qpb.build())
    .subscribe((page: Page<FormulaResponse>) => this.formulas = page.content);

    this.eventResponsive = event;
  }

  switchSelectMode(): void {
    this.selectMode = !this.selectMode;
  }

  displayNewCollectionModal(): void {
    const ref = this.dialog.open(NewCollectionComponent,{
      data: {selectedFormulas: this.selectedFormulas}
    });

    ref.afterClosed().subscribe((col: FormulaCollectionNew) => this.router.navigate(['/collections']));
  }

  openOrSelect(formula: FormulaResponse): void {
    if(this.selectMode) {
      if(this.selectedFormulas.has(formula.id)) {
        this.selectedFormulas.delete(formula.id);
      } else {
        this.selectedFormulas.add(formula.id);
      }
    } else {
      this.router.navigate(['/formulas', formula.id]);
    }
  }

  isSelected(formula: FormulaResponse) : boolean {
    return this.selectMode && this.selectedFormulas.has(formula.id);
  }

  convert(formula: FormulaResponse): MathContent {
    return {
      mathml: formula.xml
    }
  }
}
