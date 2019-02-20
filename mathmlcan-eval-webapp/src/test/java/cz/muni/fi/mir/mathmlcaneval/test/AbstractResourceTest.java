package cz.muni.fi.mir.mathmlcaneval.test;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

public abstract class AbstractResourceTest {

  @Autowired
  protected ResourceLoader resourceLoader;
  @Autowired
  protected MockMvc mockMvc;
  @Autowired
  protected ObjectMapper objectMapper;

  protected ResultMatcher matchesJson(String resource) throws Exception {
    return content()
      .json(IOUtils.toString(resourceLoader
        .getResource(String.format("classpath:/responses/%s.json", resource)).getURL(), "UTF-8"));
  }

  protected String readJsonAsString(String location) throws Exception {
    return IOUtils
      .toString(resourceLoader.getResource(String.format("classpath:/%s", location)).getURL(), "UTF-8");
  }

  protected LoginResponse login(String username, String password) throws Exception {
    return this.objectMapper.readValue(loginRaw(username, password)
      .andExpect(status().isOk())
      .andExpect(content().contentType("application/json;charset=UTF-8"))
      .andReturn().getResponse()
      .getContentAsByteArray(), LoginResponse.class);
  }

  protected ResultActions loginRaw(String username, String password) throws Exception {
    return testLoginRaw(username, password, "test", "test");
  }

  protected ResultActions testLoginRaw(String username, String password, String id, String secret)
    throws Exception {
    return mockMvc
      .perform(post("/api/oauth/token")
        .param("grant_type", "password")
        .param("username", username)
        .param("password", password)
        .with(httpBasic(id, secret)));
  }

  protected void testNotLoggedIn(MockHttpServletRequestBuilder request) throws Exception {
    this.mockMvc
      .perform(request)
      .andExpect(status().isUnauthorized())
      .andExpect(jsonPath("$.error", is("unauthorized")));
  }

  protected void isDenied(MockHttpServletRequestBuilder request) throws Exception {
    this.mockMvc
      .perform(request)
      .andExpect(status().isForbidden())
      .andExpect(jsonPath("$.title", is("Forbidden")))
      .andExpect(jsonPath("$.status", is(403)))
      .andExpect(jsonPath("$.detail", is("Access is denied")));
  }

  @Data
  protected static class LoginResponse {

    @JsonProperty("access_token")
    private String accessToken;

    public String tokenAsBearer() {
      return "Bearer " + accessToken;
    }
  }
}
