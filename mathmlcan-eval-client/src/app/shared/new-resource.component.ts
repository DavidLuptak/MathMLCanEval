import {BaseComponent} from './base.component';
import {EventEmitter, OnInit} from '@angular/core';
import {MatDialogRef} from '@angular/material';
import {AbstractService} from './abstract.service';
import {Resource} from './resource';

export abstract class NewResourceComponent<T extends Resource, C extends Resource, ID>
  extends BaseComponent implements OnInit {

  valueChanged = new EventEmitter<T>();
  newResource: C;

  protected constructor(private _dialogRef: MatDialogRef<NewResourceComponent<T, C, ID>>,
              private _service: AbstractService<T, ID>) {
    super();
  }

  abstract initNewResource(): C;

  ngOnInit(): void {
    this.newResource = this.initNewResource();
  }

  protected cancel(): void {
    this._dialogRef.close();
  }

  protected save(): void {
    this._service
    .save(this.newResource)
    .subscribe((res: T) => this._dialogRef.close(res));
  }

  protected saveAndNext(): void {
    this._service
    .save(this.newResource)
    .subscribe((res: T) => {
      this.valueChanged.emit(res);
      this.newResource = this.initNewResource();
    })
  }
}
