package cz.muni.fi.mir.mathmlcaneval.repository;

import cz.muni.fi.mir.mathmlcaneval.domain.Revision;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RevisionRepository extends JpaRepository<Revision, Long> {

}
