package cz.muni.fi.mir.mathmlcaneval.repository;

import cz.muni.fi.mir.mathmlcaneval.domain.Formula;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FormulaRepository extends JpaRepository<Formula, Long> {

  @Query("SELECT fc.formulas FROM FormulaCollection fc WHERE fc.id = :collection")
  List<Formula> getFormulasInCollection(Long collection);
}
