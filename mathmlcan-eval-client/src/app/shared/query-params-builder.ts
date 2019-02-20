import {HttpParams} from '@angular/common/http';

export class QueryParamsBuilder {
  page: number;
  pageSize: number;
  q: string;

  constructor() {
    this.page = 1;
    this.pageSize = 5;
  }

  public withPage(page: number): QueryParamsBuilder {
    this.page = page;
    return this;
  }

  public withSize(pageSize: number): QueryParamsBuilder {
    this.pageSize = pageSize;

    return this;
  }

  public withQuery(query: string): QueryParamsBuilder {
    this.q = query;

    return this;
  }

  public build(): HttpParams {
    let hp = new HttpParams();
    if (this.page) {
      hp = hp.append('page', `${this.page}`);
    }

    if (this.pageSize) {
      hp = hp.append('pageSize', `${this.pageSize}`);
    }

    if (this.q) {
      hp = hp.append('q', `${this.q}`);
    }

    if (hp.keys().length > 0) {
      return hp;
    } else {
      return null;
    }
  }
}
