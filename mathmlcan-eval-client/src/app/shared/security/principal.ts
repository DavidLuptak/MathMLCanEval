export interface Principal {
  id: number;
  username: string;
  authorities: Authority[];
  enabled: boolean;
  accountNonLocked: boolean;
  credentialsNonExpired: boolean;
  accountNonExpired: boolean;
}

export interface Authority {
  authority: string;
}
