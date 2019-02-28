import {BaseComponent} from '../../shared/base.component';
import {Component, EventEmitter, Input, OnInit, ViewChild} from '@angular/core';
import {ConfigurationResponse} from '../../models/configuration.response';
import {TdHighlightComponent} from '@covalent/highlight';

@Component({
  selector: 'configuration-preview-component',
  templateUrl: 'configuration-preview.component.html'
})
export class ConfigurationPreviewComponent extends BaseComponent implements OnInit {
  @ViewChild(TdHighlightComponent) preview: TdHighlightComponent;
  @Input('configurationEmitter') configurationEmitter: EventEmitter<ConfigurationResponse>;
  @Input('configuration') configuration: ConfigurationResponse;

  constructor() {
    super();
  }

  ngOnInit(): void {
    if (this.configuration) {
      this.preview.content = this.configuration.content;
    } else {
      this.configurationEmitter.subscribe((c: ConfigurationResponse) => {
        this.preview.content = c.content
      });
    }
  }
}
