package cz.muni.fi.mir.mathmlcaneval.repository;

import cz.muni.fi.mir.mathmlcaneval.domain.MathmlElement;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MathmlElementRepository extends JpaRepository<MathmlElement, Long> {

  @Query("SELECT e.vectorPosition FROM MathmlElement e WHERE e.element = :element")
  Optional<Integer> getPositionForElement(String element);

  @Override
  long count();
}
