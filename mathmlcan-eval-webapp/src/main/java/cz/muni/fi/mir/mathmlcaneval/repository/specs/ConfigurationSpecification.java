package cz.muni.fi.mir.mathmlcaneval.repository.specs;

import cz.muni.fi.mir.mathmlcaneval.domain.InputConfiguration;
import cz.muni.fi.mir.mathmlcaneval.domain.InputConfiguration_;
import cz.muni.fi.mir.mathmlcaneval.domain.User_;
import org.springframework.data.jpa.domain.Specification;

public class ConfigurationSpecification {

  public static Specification<InputConfiguration> publicOrMine(final Long userId){
    return (Specification<InputConfiguration>) (root,query, cb) -> cb.or(
      cb.equal(root.join(InputConfiguration_.user).get(User_.id), userId),
      cb.equal(root.get(InputConfiguration_.visibleToPublic), true)
    );
  }
}
