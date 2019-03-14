import {TableComponent} from '../../shared/table.component';
import {FormulaCollectionResponse} from '../../models/formula-collection.response';
import {Component, OnInit} from '@angular/core';
import {CollectionsService} from './collections.service';
import {Router} from '@angular/router';
import {MatTableDataSource} from '@angular/material';
import {Page} from '../../models/page';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'collections-component',
  templateUrl: 'collections.component.html'
})
export class CollectionsComponent extends TableComponent<FormulaCollectionResponse> implements OnInit {
  displayedColumns: string[] = ['id', 'name', 'visibleToPublic', 'creatorName'];

  constructor(private collectionsService: CollectionsService,
              private router: Router) {
    super();

    this.dataSource = new MatTableDataSource<FormulaCollectionResponse>()
  }

  ngOnInit(): void {
    this.collectionsService
    .query()
    .subscribe((page: Page<FormulaCollectionResponse>) => this.pushRows(page.content));
  }
}
