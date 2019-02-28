import {Component, EventEmitter, OnInit} from '@angular/core';
import {TableComponent} from '../../shared/table.component';
import {ConfigurationResponse} from '../../models/configuration.response';
import {ConfigurationService} from './configuration.service';
import {Router} from '@angular/router';
import {MatDialog, MatTableDataSource} from '@angular/material';
import {NewConfigurationComponent} from './new-configuration.component';

@Component({
  selector: 'configuration-list',
  templateUrl: 'configuration-list.component.html'
})
export class ConfigurationListComponent extends TableComponent<ConfigurationResponse> implements OnInit {
  selectedConfiguration = new EventEmitter<ConfigurationResponse>();

  displayedColumns: string[] = ['id', 'name', 'user'];

  constructor(private configurationService: ConfigurationService,
              private router: Router,
              private dialog: MatDialog) {
    super();

    this.dataSource = new MatTableDataSource<ConfigurationResponse>();
  }

  ngOnInit(): void {
    this.configurationService
    .query()
    .subscribe((rows: ConfigurationResponse[]) => this.pushRows(rows));
  }

  selectConfig(id: number): void {
    for (const c of this.dataSource.data) {
      if (c.id === id) {
        this.selectedConfiguration.emit(c);
        break;
      }
    }
  }

  newConfigModal(): void {
    const ref = this.dialog.open(NewConfigurationComponent, {
      minWidth: 550
    });

    ref.afterClosed()
    .subscribe((conf: ConfigurationResponse) => {
      this.pushRow(conf);
    });
  }
}
