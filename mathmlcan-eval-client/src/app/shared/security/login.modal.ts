import {BaseComponent} from '../base.component';
import {MatDialogRef} from '@angular/material';
import {SecurityService} from './security.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Component, OnInit} from '@angular/core';
import {LoginModel} from './login.model';

@Component({
  selector: 'login-modal',
  templateUrl: 'login.modal.html'
})
export class LoginModal extends BaseComponent implements OnInit {
  private form: FormGroup;
  private hide = true;

  constructor(private dialog: MatDialogRef<LoginModal>,
              private securityService: SecurityService,
              private _fb: FormBuilder) {
    super();
  }

  ngOnInit(): void {
    this.form = this._fb.group({
      'username': ['', Validators.required],
      'password': ['', Validators.required]
    });
  }

  handleLogin(): void {
    const data = {
      username: this.form.get('username').value,
      password: this.form.get('password').value
    } as LoginModel;

    this.securityService.login(data)
    .subscribe((res: boolean) => {
      if(res) {
        this.dialog.close();
      }
    });
  }
}
