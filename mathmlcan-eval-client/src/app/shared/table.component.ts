import {BaseComponent} from './base.component';
import {Input, ViewChild} from '@angular/core';
import {MatTable, MatTableDataSource} from '@angular/material';

export abstract class TableComponent<T> extends BaseComponent {
  @Input() displayedColumns: String[];
  @ViewChild('table') table: MatTable<T>;

  protected dataSource: MatTableDataSource<T>;

  public pushRows(rows: T[], overwrite?: boolean): void {
    if(this.dataSource) {
      if(overwrite) {
        this.dataSource.data = rows;
        this.table.renderRows();
      } else {
        this.dataSource.data = this.dataSource.data.concat(rows);
      }
    } else {
      this.dataSource = new MatTableDataSource<T>(rows);
    }
  }

  public pushRow(row: T) {
    if(this.dataSource) {
      this.dataSource.data.push(row);
      this.table.renderRows();
    } else {
      this.dataSource = new MatTableDataSource<T>([row]);
    }
  }
}
