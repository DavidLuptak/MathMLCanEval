import {Component, Input} from '@angular/core';
import {BaseComponent} from '../../shared/base.component';
import {FormulaResponse} from '../../models/formula.response';
import {Router} from '@angular/router';

@Component({
  selector: 'formula-preview',
  templateUrl: 'formula-preview.component.html'
})
export class FormulaPreviewComponent extends BaseComponent {
  @Input('formula') formula: FormulaResponse;
  @Input('selectMode') selectMode: false;

  constructor(private router: Router) {
    super();
  }

  open(): void {
    if (!this.selectMode) {
      this.router.navigate(['/formulas', this.formula.id]);
    }
  }
}
