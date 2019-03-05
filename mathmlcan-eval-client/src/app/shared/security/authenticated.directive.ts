import {Directive, ElementRef, Input, OnInit, TemplateRef, ViewContainerRef} from '@angular/core';
import {SecurityService} from './security.service';

@Directive({
  selector: '[authenticated]'
})
export class AuthenticatedDirective implements OnInit {
  private permissions: string[];
  private isHidden = true; // this one prevents double elements

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

  @Input()
  set authenticated(permissions) {
    this.permissions = permissions;
    this.refreshView();
  }

  refreshView(): void {
    if (this.securityService.hasPermissions(this.permissions, 'AND')) {
      if(this.isHidden) {
        this.viewContainer.createEmbeddedView(this.templateRef);
        this.isHidden = false;
      }
    } else {
      this.isHidden = true;
      this.viewContainer.clear();
    }
  }

}
