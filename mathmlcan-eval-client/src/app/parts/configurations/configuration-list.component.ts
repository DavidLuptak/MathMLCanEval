import {Component, OnInit, ViewChild} from '@angular/core';
import {TableComponent} from '../../shared/table.component';
import {ConfigurationResponse} from '../../models/configuration.response';
import {ConfigurationService} from './configuration.service';
import {Router} from '@angular/router';
import {MatDialog, MatTableDataSource} from '@angular/material';
import {TdHighlightComponent} from '@covalent/highlight';
import {NewConfigurationComponent} from './new-configuration.component';

@Component({
  selector: 'configuration-list',
  templateUrl: 'configuration-list.component.html'
})
export class ConfigurationListComponent extends TableComponent<ConfigurationResponse> implements OnInit {
  @ViewChild(TdHighlightComponent) preview: TdHighlightComponent;

  displayedColumns: string[] = ['id', 'name'];
  previewSelected = false;

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
    for(const c of this.dataSource.data) {
      if(c.id === id) {
        this.previewSelected = true;
        setTimeout(() => this.preview.content = c.content);
      }
    }
  }

  newConfigModal() : void {
    const ref = this.dialog.open(NewConfigurationComponent, {
      minWidth: 550
    });
  }
}
