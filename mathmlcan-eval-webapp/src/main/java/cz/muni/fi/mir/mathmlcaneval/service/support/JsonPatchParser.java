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
package cz.muni.fi.mir.mathmlcaneval.service.support;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.github.fge.jsonpatch.JsonPatch;
import java.io.IOException;
import java.util.Map;

public class JsonPatchParser {

  public static enum ParserMode {
    OMIT,
    EXCEPTION
  }

  public static JsonPatch validateInput(JsonNode input, Map<String, String> forbiddenRules,
    ParserMode parserMode) {
    if (!(input instanceof ArrayNode)) {
      throw new IllegalArgumentException();
    }
    try {
      return JsonPatch.fromJson(input);
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }

    // todo
   /* ArrayNode tmp = (ArrayNode) input;

    Iterator<JsonNode> it = tmp.iterator();

    while(it.hasNext()) {
      JsonNode op = it.next();
      op.get
    }*/

  }
}
