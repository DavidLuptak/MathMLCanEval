package cz.muni.fi.mir.mathmlcaneval.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;

public interface BaseService<R, C> {

  List<R> findAll();

  default List<R> findAll(Pageable pageable) {
    return findAll();
  }

  Optional<R> findById(Long id);

  default R save(C create) {
    throw new UnsupportedOperationException();
  }

}
