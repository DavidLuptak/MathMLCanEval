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

import java.util.Optional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityServiceImpl implements SecurityService {

  @Override
  public MathUser getCurrentUser() {
    final var result = Optional
      .ofNullable(SecurityContextHolder.getContext().getAuthentication())
      .filter(a -> a.getPrincipal() instanceof MathUser)
      .map(a -> (MathUser) a.getPrincipal())
      .orElse(null); // todo shouldn't be null

    return result;
  }

  @Override
  public Long getCurrentUserId() {
    return getCurrentUser().getId();
  }
}
