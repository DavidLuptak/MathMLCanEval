package cz.muni.fi.mir.mathmlcaneval.repository;

import cz.muni.fi.mir.mathmlcaneval.domain.FormulaCollection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FormulaCollectionRepository extends JpaRepository<FormulaCollection, Long> {

  @Query("SELECT fc FROM FormulaCollection fc join fc.formulas fcf WHERE fcf.id = :formulaId")
  List<FormulaCollection> getCollectionsWithFormula(Long formulaId);
}
