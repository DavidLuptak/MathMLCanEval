import {Component, EventEmitter, OnInit} from '@angular/core';
import {TableComponent} from '../../shared/table.component';
import {ConfigurationResponse} from '../../models/configuration.response';
import {ConfigurationService} from './configuration.service';
import {Router} from '@angular/router';
import {MatDialog, MatTableDataSource} from '@angular/material';
import {NewConfigurationComponent} from './new-configuration.component';
import {AppRunResponse} from '../../models/app-run.response';
import {Page} from '../../models/page';
import {RenameConfigurationComponent} from './rename-configuration.component';
import {filter} from 'rxjs/operators';
import {generate, observe} from 'fast-json-patch';

@Component({
  selector: 'configuration-list',
  templateUrl: 'configuration-list.component.html'
})
export class ConfigurationListComponent extends TableComponent<ConfigurationResponse> implements OnInit {
  selectConfigurationEvent = new EventEmitter<ConfigurationResponse>();
  selectedConfiguration: ConfigurationResponse;
  displayedColumns: string[] = ['id', 'name', 'owner'];


  appRunsDs = new MatTableDataSource<AppRunResponse>();
  appRunsdisplayedColumns = ['id', 'finished', 'owner', 'numberOfOutputs'];

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
        this.selectConfigurationEvent.emit(c);
        this.selectedConfiguration = c;

        this.configurationService
        .getRunsUsedByConfiguration(c.id)
        .subscribe((res: AppRunResponse[]) => this.appRunsDs.data = res);

        break;
      }
    }
  }

  renameConfiguration(): void {
    const observer = observe(this.selectedConfiguration);
    const ref = this.dialog.open(RenameConfigurationComponent, {
      data: {
        configuration: this.selectedConfiguration
      }
    });

    ref.afterClosed()
    .pipe(
      filter(Boolean)
    )
    .subscribe((res: any) => {
      const changes = generate(observer);
      if(changes && changes.length) {
        // console.log(changes);
        this.configurationService
        .patch(this.selectedConfiguration.id, changes)
        .subscribe((res: ConfigurationResponse) => console.log('ok'));
      } else {
        console.log('no changes detected');
      }
    });
  }

  deleteConfiguration(): void {
    console.log('clicked');
    this.configurationService
    .delete(this.selectedConfiguration.id)
    .subscribe(() => {
      console.log('ok');
      this.dataSource
      .data = this.dataSource.data.filter(e => e.id !== this.selectedConfiguration.id);

      this.selectedConfiguration = null;
    })
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
