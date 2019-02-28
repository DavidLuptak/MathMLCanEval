import {BaseComponent} from '../../shared/base.component';
import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {FormulaCollectionNew} from '../../models/formula-collection.new';
import {ModalButtons} from '../../shared/modal.buttons';
import {CollectionsService} from './collections.service';

@Component({
  selector: 'new-collection',
  templateUrl: 'new-collection.component.html'
})
export class NewCollectionComponent extends BaseComponent implements OnInit, ModalButtons {

  formulaCollection = new FormulaCollectionNew();

  constructor(public dialogRef: MatDialogRef<NewCollectionComponent>,
              @Inject(MAT_DIALOG_DATA) public data: NewCollectionDialogData,
              private collectionService: CollectionsService) {
    super();
  }

  ngOnInit(): void {
  }


  save(): void {
    this.formulaCollection.formulas = Array.from(this.data.selectedFormulas);

    this.collectionService.save(this.formulaCollection).subscribe(() => console.log('saved'));
    this.dialogRef.close();
  }

  cancel(): void {
    this.dialogRef.close();
  }
}

export interface NewCollectionDialogData {
  selectedFormulas: Set<number>;
}
