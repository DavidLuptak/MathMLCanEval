/*
 * Copyright © 2013 the original author or authors (webmias@fi.muni.cz)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cz.muni.fi.mir.mathmlcaneval.security;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author dominik.szalai
 */
@AllArgsConstructor
public class MathUserImpl implements MathUser {

  private final Long id;
  private final String username;
  private final Set<GrantedAuthority> authorities;
  private String password;


  @Override
  public Long getId() {
    return id;
  }

  @Override
  public Collection<GrantedAuthority> getAuthorities() {
    return Collections.unmodifiableSet(authorities);
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public void eraseCredentials() {
    this.password = null;
  }
}
