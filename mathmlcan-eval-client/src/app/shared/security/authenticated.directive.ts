import {Directive, ElementRef, OnInit, TemplateRef, ViewContainerRef} from '@angular/core';
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
    this.refreshView();

    this.securityService.userLoggedIn()
    .subscribe(() => this.refreshView());
  }

  refreshView(): void {
    if (this.securityService.isAuthenticated()) {
      this.viewContainer.createEmbeddedView(this.templateRef);
    } else {
      this.viewContainer.clear();
    }
  }

}
