import {Component, EventEmitter, Input, Output} from '@angular/core';
import {BaseComponent} from '../../shared/base.component';
import {FormulaResponse} from '../../models/formula.response';
import {Router} from '@angular/router';
import {MathContent} from '../../shared/math/math-content';

@Component({
  selector: 'formula-preview',
  templateUrl: 'formula-preview.component.html',
  styleUrls: ['formula-preview.component.css']
})
export class FormulaPreviewComponent extends BaseComponent {
  @Input('formula') formula: FormulaResponse;
  @Input('selectMode') selectMode: false;
  @Output('selectedFormula') selectedFormula = new EventEmitter<number>();
  @Output('unselectedFormula') unselectedFormula = new EventEmitter<number>();

  isSelected = false;

  constructor(private router: Router) {
    super();
  }

  openOrSelect() : void {
    if(!this.selectMode) {
      this.router.navigate(['/formulas', this.formula.id]);
    } else {
      if(!this.isSelected) {
        this.selectedFormula.emit(this.formula.id);
        console.log(`FormulaPreviewComponent emit@select ${this.formula.id}`);
      } else {
        this.unselectedFormula.emit(this.formula.id);
        console.log(`FormulaPreviewComponent emit@unselect ${this.formula.id}`);
      }

      this.isSelected = !this.isSelected;
    }
  }

  convert(formula: FormulaResponse): MathContent {
    return {
      mathml: formula.xml
    }
  }
}
