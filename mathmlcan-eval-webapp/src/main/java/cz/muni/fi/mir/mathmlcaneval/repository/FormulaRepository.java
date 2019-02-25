package cz.muni.fi.mir.mathmlcaneval.repository;

import cz.muni.fi.mir.mathmlcaneval.domain.Formula;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormulaRepository extends JpaRepository<Formula, Long> {

}
