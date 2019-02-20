import {Component} from '@angular/core';
import {BaseComponent} from '../base.component';
import {SecurityService} from './security.service';
import {MatDialog} from '@angular/material';
import {LoginModal} from './login.modal';

@Component({
  selector: 'login-dropdown-component',
  templateUrl: 'login-dropdown.component.html'
})
export class LoginDropdownComponent extends BaseComponent {
  constructor(private securityService: SecurityService,
              private dialog: MatDialog) {
    super();
  }

  displayLoginModal(): void {
    this.dialog.open(LoginModal);
  }
}
