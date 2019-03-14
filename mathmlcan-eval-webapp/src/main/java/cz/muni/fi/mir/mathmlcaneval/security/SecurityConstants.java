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

public class SecurityConstants {

  public static final String[] SWAGGER = {"/v2/api-docs",
    "/configuration/ui",
    "/swagger-resources",
    "/configuration/security",
    "/swagger-ui.html",
    "/webjars/**",
    "/swagger-resources/configuration/ui",
    "/swagger-ui.html"};

  public static final String[] POST_PUBLIC_RESOURCES = {
    "/api/oauth/revoke"
  };

  public static final String[] GET_PUBLIC_RESOURCES = {
    "/api/configurations",
    "/api/configurations/*/app-runs",
    "/api/revisions",
    "/api/formulas",
    "/api/collections",
    "/api/test"
  };

  public static final String[] PUT_PUBLIC_RESOURCES = {

  };

  public static final String[] PATCH_PUBLIC_RESOURCES = {

  };

  public static final String RESOURCE_ID = "mathmleval";

  private SecurityConstants() {
    // prevent new
  }
}
