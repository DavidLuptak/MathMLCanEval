import {Component, OnInit} from '@angular/core';
import {BaseComponent} from '../../shared/base.component';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {RevisionResponse} from '../../models/revision.response';
import {RevisionService} from '../revisions/revision.service';
import {Observable} from 'rxjs';
import {debounceTime, distinctUntilChanged, startWith, switchMap} from 'rxjs/operators';
import {MatAutocompleteSelectedEvent} from '@angular/material';
import {ConfigurationResponse} from '../../models/configuration.response';
import {NamedResource} from '../../shared/named-resource';
import {ConfigurationService} from '../configurations/configuration.service';
import {FormulaCollectionResponse} from '../../models/formula-collection.response';
import {CollectionsService} from '../collections/collections.service';

@Component({
  selector: 'apprun-setup',
  templateUrl: 'apprun-setup.component.html'
})
export class ApprunSetupComponent extends BaseComponent implements OnInit {

  isLinear = false;
  //
  revisionFormGroup: FormGroup;
  filteredRevisions: Observable<RevisionResponse[]>;
  selectedRevision: RevisionResponse;

  //
  configurationFormGroup: FormGroup;
  filteredConfigurations: Observable<ConfigurationResponse[]>;
  selectedConfiguration: ConfigurationResponse;

  //
  collectionFormGroup: FormGroup;
  filteredCollections: Observable<FormulaCollectionResponse[]>;
  selectedCollection: FormulaCollectionResponse;

  constructor(private _formBuilder: FormBuilder,
              private revisionService: RevisionService,
              private configurationService: ConfigurationService,
              private collectionsService: CollectionsService) {
    super();

    this.revisionFormGroup = this._formBuilder.group({
      revisionControl: ['', Validators.required]
    });
    this.configurationFormGroup = this._formBuilder.group({
      configurationControl: ['', Validators.required]
    });

    this.collectionFormGroup = this._formBuilder.group({
      collectionControl: ['', Validators.required]
    });
  }

  ngOnInit() {
    this.filteredRevisions = this.revisionFormGroup.controls['revisionControl'].valueChanges
    .pipe(startWith(null),
      debounceTime(300),
      distinctUntilChanged(),
      switchMap(val => this._filterRevision(val))
    );

    this.filteredConfigurations = this.configurationFormGroup.controls['configurationControl'].valueChanges
    .pipe(startWith(null),
      debounceTime(300),
      distinctUntilChanged(),
      switchMap(val => this._filterConfiguration(val))
    );

    this.filteredCollections = this.collectionFormGroup.controls['collectionControl'].valueChanges
    .pipe(startWith(null),
      debounceTime(300),
      distinctUntilChanged(),
      switchMap(val => this._filterCollections(val))
    );
  }

  private _filterRevision(value: string): Observable<RevisionResponse[]> {
    return this.revisionService.query();
  }

  private _filterConfiguration(value: string): Observable<ConfigurationResponse[]> {
    return this.configurationService.query();
  }

  private _filterCollections(value: string): Observable<FormulaCollectionResponse[]> {
    return this.collectionsService.query();
  }

  selected(event: MatAutocompleteSelectedEvent, type: string): void {
    switch (type) {
      case 'revision':
        this.selectedRevision = event.option.value;
        break;
      case 'configuration':
        this.selectedConfiguration = event.option.value;
        break;
      case 'collection':
        this.selectedCollection = event.option.value;
        break;
      default:
        throw new Error(`Type: ${type} is not supported`);
    }
  }

  displayFn(namedResource: NamedResource): string | undefined {
    return namedResource ? namedResource.name : undefined;
  }
}
