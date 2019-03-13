import {BaseComponent} from '../../shared/base.component';
import {Component} from '@angular/core';

@Component({
  selector:'dashboard-component',
  templateUrl: 'dashboard.component.html'
})
export class DashboardComponent extends BaseComponent {
  times = [
    {
      'name': 'Candy',
      'series': [
        {
          'value': 69,
          'name': '2016-09-15T19:25:07.773Z',
        },
        {
          'value': 19,
          'name': '2016-09-17T17:16:53.279Z',
        },
        {
          'value': 85,
          'name': '2016-09-15T10:34:32.344Z',
        },
        {
          'value': 89,
          'name': '2016-09-19T14:33:45.710Z',
        },
        {
          'value': 33,
          'name': '2016-09-12T18:48:58.925Z',
        }
      ]
    },
    {
      'name': 'Ice Cream',
      'series': [
        {
          'value': 52,
          'name': '2016-09-15T19:25:07.773Z',
        },
        {
          'value': 49,
          'name': '2016-09-17T17:16:53.279Z',
        },
        {
          'value': 41,
          'name': '2016-09-15T10:34:32.344Z',
        },
        {
          'value': 38,
          'name': '2016-09-19T14:33:45.710Z',
        },
        {
          'value': 72,
          'name': '2016-09-12T18:48:58.925Z',
        }
      ]
    },
    {
      'name': 'Pastry',
      'series': [
        {
          'value': 40,
          'name': '2016-09-15T19:25:07.773Z',
        },
        {
          'value': 45,
          'name': '2016-09-17T17:16:53.279Z',
        },
        {
          'value': 51,
          'name': '2016-09-15T10:34:32.344Z',
        },
        {
          'value': 68,
          'name': '2016-09-19T14:33:45.710Z',
        },
        {
          'value': 54,
          'name': '2016-09-12T18:48:58.925Z',
        }
      ]
    },
  ];
}
