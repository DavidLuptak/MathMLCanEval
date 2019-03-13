import {Component, EventEmitter, OnInit} from '@angular/core';
import {TableComponent} from '../../shared/table.component';
import {ConfigurationResponse} from '../../models/configuration.response';
import {ConfigurationService} from './configuration.service';
import {Router} from '@angular/router';
import {MatDialog, MatTableDataSource} from '@angular/material';
import {NewConfigurationComponent} from './new-configuration.component';
import {AppRunResponse} from '../../models/app-run.response';
import {Page} from '../../models/page';

@Component({
  selector: 'configuration-list',
  templateUrl: 'configuration-list.component.html'
})
export class ConfigurationListComponent extends TableComponent<ConfigurationResponse> implements OnInit {
  selectedConfiguration = new EventEmitter<ConfigurationResponse>();
  displayedColumns: string[] = ['id', 'name', 'user'];


  appRunsDs = new MatTableDataSource<AppRunResponse>();
  appRunsdisplayedColumns = ['id'];

  constructor(private configurationService: ConfigurationService,
              private router: Router,
              private dialog: MatDialog) {
    super();

    this.dataSource = new MatTableDataSource<ConfigurationResponse>();
  }

  ngOnInit(): void {
    this.configurationService
    .query()
    .subscribe((page: Page<ConfigurationResponse>) => this.pushRows(page.content));
  }

  selectConfig(id: number): void {
    for (const c of this.dataSource.data) {
      if (c.id === id) {
        this.selectedConfiguration.emit(c);

        this.configurationService
        .getRunsUsedByConfiguration(c.id)
        .subscribe((res: AppRunResponse[]) => this.appRunsDs.data = res);

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
