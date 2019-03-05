package cz.muni.fi.mir.mathmlcaneval.resource;

import java.security.Principal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MeResource {

  @GetMapping("/api/me")
  public Principal getProfile(Principal principal) {
    return principal;
  }
}
