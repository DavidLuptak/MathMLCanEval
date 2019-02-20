import {Component} from '@angular/core';
import {BaseComponent} from './shared/base.component';
import {MatDialog} from '@angular/material';
import {LoginModal} from './shared/security/login.modal';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent extends BaseComponent {

}
