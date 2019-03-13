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
package cz.muni.fi.mir.mathmlcaneval.resource;

import cz.muni.fi.mir.mathmlcaneval.requests.TokenRevocationRequest;
import cz.muni.fi.mir.mathmlcaneval.support.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OAuthTokenResource {
  private final DefaultTokenServices tokenService;


  @PostMapping("/api/oauth/revoke")
  public ResponseEntity<Response> revokeToken(TokenRevocationRequest request) {
    return tokenService.revokeToken(request.getToken()) ? Response.OK : Response.NOK;
  }
}
