import {Directive, ElementRef, Input, OnInit, TemplateRef, ViewContainerRef} from '@angular/core';
import {SecurityService} from './security.service';

@Directive({
  selector: '[authenticated]'
})
export class AuthenticatedDirective implements OnInit {
  constructor(private securityService: SecurityService,
              private element: ElementRef,
              private templateRef: TemplateRef<any>,
              private viewContainer: ViewContainerRef) {

  }

  ngOnInit(): void {
    if(this.securityService.isAuthenticated()) {
      console.log('authenticated');
      this.viewContainer.createEmbeddedView(this.templateRef);
    } else {
      this.viewContainer.clear();
    }
  }

}
