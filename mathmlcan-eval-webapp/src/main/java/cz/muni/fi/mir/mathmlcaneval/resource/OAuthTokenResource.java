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
