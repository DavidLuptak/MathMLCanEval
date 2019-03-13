export interface Page<T> {
  content: T[];
  empty: boolean;
  first: boolean;
  last: boolean;
  number: number
  numberOfElements: number;
  size: number;
  totalElements: 0;
  totalPages: 0;
  sort: Sort;
  pageable: Pageable
}

export interface Sort {
  unsorted: boolean;
  sorter: boolean;
  empty: boolean
}

export interface Pageable {
  offset: number;
  pageNumber: number;
  pageSize: number;
  paged: boolean;
  unpaged: boolean;
  sort: Sort;
}

