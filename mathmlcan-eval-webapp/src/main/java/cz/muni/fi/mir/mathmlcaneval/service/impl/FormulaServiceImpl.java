package cz.muni.fi.mir.mathmlcaneval.service.impl;

import cz.muni.fi.mir.mathmlcaneval.mappers.FormulaMapper;
import cz.muni.fi.mir.mathmlcaneval.repository.FormulaRepository;
import cz.muni.fi.mir.mathmlcaneval.responses.FormulaResponse;
import cz.muni.fi.mir.mathmlcaneval.service.FormulaService;
import cz.muni.fi.mir.mathmlcaneval.support.ReadOnly;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class FormulaServiceImpl implements FormulaService {
  private final FormulaRepository formulaRepository;
  private final FormulaMapper formulaMapper;

  @ReadOnly
  @Override
  public List<FormulaResponse> findAll() {
    return formulaMapper.map(formulaRepository.findAll());
  }

  @ReadOnly
  @Override
  public Optional<FormulaResponse> findById(Long id) {
    return formulaRepository.findById(id)
      .map(formulaMapper::map);
  }

  @ReadOnly
  @Override
  public List<FormulaResponse> findAll(Pageable pageable) {
    return formulaMapper.map(formulaRepository.findAll(pageable).getContent());
  }
}
