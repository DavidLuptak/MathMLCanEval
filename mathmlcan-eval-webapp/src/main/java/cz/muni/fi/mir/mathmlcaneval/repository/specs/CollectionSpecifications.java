package cz.muni.fi.mir.mathmlcaneval.repository.specs;

import cz.muni.fi.mir.mathmlcaneval.domain.FormulaCollection;
import cz.muni.fi.mir.mathmlcaneval.domain.FormulaCollection_;
import cz.muni.fi.mir.mathmlcaneval.domain.User_;
import org.springframework.data.jpa.domain.Specification;

public class CollectionSpecifications {
  public static Specification<FormulaCollection> publicOrMine(final Long userId) {
    return (Specification<FormulaCollection>) (root, query, criteriaBuilder) -> criteriaBuilder.or(
      criteriaBuilder.equal(root.join(FormulaCollection_.creator).get(User_.id), userId),
      criteriaBuilder.equal(root.get(FormulaCollection_.visibleToPublic), true)
    );
  }
}
