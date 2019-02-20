package cz.muni.fi.mir.mathmlcaneval.service;

import java.util.List;
import java.util.Optional;

public interface BaseService<R,C> {

  List<R> findAll();

  Optional<R> findById(Long id);

  R save(C create);
}
