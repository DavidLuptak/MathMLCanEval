/*
import {
  AfterViewInit,
  Directive,
  ElementRef,
  Input,
  OnChanges,
  OnInit,
  SimpleChanges
} from '@angular/core';
import {DomSanitizer, SafeHtml} from '@angular/platform-browser';
import {FormulaResponse} from '../../models/formula.response';

@Directive({
  selector: '[Math]'
})
export class RenderMathDirective implements OnInit, OnChanges, AfterViewInit {
  private readonly _el: HTMLElement;

  @Input('Math') formula: FormulaResponse;
  private rendering = false;

  constructor(private er: ElementRef,
              private domSanitizer: DomSanitizer) {
    this._el = er.nativeElement;
  }

  ngOnInit(): void {
    if(this.formula) {
      this.rendering = true;
      console.log('about to render');

      //console.log(this.math);

      //console.log(this.domSanitizer.bypassSecurityTrustHtml(this.formula.xml));
      //this._el.innerHTML =

      //console.log(this.formula);
      /!*console.log(this.math);
      this.er.nativeElement.innerHtml = this.math;*!/
     ///this.er.nativeElement.innerHtml = this.domSanitizer.bypassSecurityTrustHtml(this.formula.xml);
    }
  }

  ngOnChanges(changes: SimpleChanges): void {
    if(!this.rendering) {
      console.log('should rerender?');
    }
  }

  ngAfterViewInit(): void {
//    console.log(`AfterInit ${this.math}`);
  }


}
*/
