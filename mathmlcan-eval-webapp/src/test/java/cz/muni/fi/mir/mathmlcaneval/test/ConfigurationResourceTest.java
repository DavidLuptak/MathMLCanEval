package cz.muni.fi.mir.mathmlcaneval.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@MathTest
public class ConfigurationResourceTest extends AbstractResourceTest {

  @Test
  void testGetall() throws Exception {
    this.mockMvc
      .perform(get("/api/configurations"))
      .andExpect(status().isOk())
      .andExpect(matchesJson("api-configurations"));
  }

  @Test
  void testCreateConfigDenied() throws Exception {
    //super.isDenied(post("/api/configurations").);
  }
}
