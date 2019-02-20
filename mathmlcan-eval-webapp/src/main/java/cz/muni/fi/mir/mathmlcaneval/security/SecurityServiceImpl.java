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
