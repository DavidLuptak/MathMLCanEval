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
