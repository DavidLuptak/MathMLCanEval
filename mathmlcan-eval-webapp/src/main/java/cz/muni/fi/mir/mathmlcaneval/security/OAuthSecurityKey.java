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

import cz.muni.fi.mir.mathmlcaneval.configurations.props.OAuthProperties;
import java.security.KeyPair;
import lombok.Getter;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.stereotype.Component;

@Component
public class OAuthSecurityKey {

  @Getter
  private final KeyPair key;

  public OAuthSecurityKey(ResourceLoader resourceLoader, OAuthProperties oAuthProperties) {
    KeyStoreKeyFactory factory = new KeyStoreKeyFactory(
        resourceLoader.getResource("classpath:/jwt.jks"),
        // pass move to properties once cert is generated
        oAuthProperties.getKeyPass().toCharArray()
    );

    this.key = factory.getKeyPair("jwt");
  }
}
