package cz.muni.fi.mir.mathmlcaneval.service.impl;

import cz.muni.fi.mir.mathmlcaneval.domain.FormulaCollection;
import cz.muni.fi.mir.mathmlcaneval.domain.User;
import cz.muni.fi.mir.mathmlcaneval.mappers.FormulaCollectionMapper;
import cz.muni.fi.mir.mathmlcaneval.repository.FormulaCollectionRepository;
import cz.muni.fi.mir.mathmlcaneval.requests.FormulaCollectionRequest;
import cz.muni.fi.mir.mathmlcaneval.responses.FormulaCollectionResponse;
import cz.muni.fi.mir.mathmlcaneval.security.SecurityService;
import cz.muni.fi.mir.mathmlcaneval.service.FormulaCollectionService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FormulaCollectionServiceImpl implements FormulaCollectionService {

  private final FormulaCollectionRepository formulaCollectionRepository;
  private final FormulaCollectionMapper formulaCollectionMapper;
  private final SecurityService securityService;

  @Override
  public FormulaCollectionResponse save(FormulaCollectionRequest create) {
    FormulaCollection collection = formulaCollectionMapper.map(create);
    collection.setCreator(new User(securityService.getCurrentUserId()));

    formulaCollectionRepository.save(collection);

    return formulaCollectionMapper.map(collection);
  }

  @Override
  public List<FormulaCollectionResponse> findAll() {
    return formulaCollectionMapper.map(formulaCollectionRepository.findAll());
  }

  @Override
  public Optional<FormulaCollectionResponse> findById(Long id) {
    return formulaCollectionRepository
      .findById(id)
      .map(formulaCollectionMapper::map);
  }

  @Override
  public List<FormulaCollectionResponse> collectionsWithFormula(Long formulaId) {
    return formulaCollectionMapper.map(formulaCollectionRepository.getCollectionsWithFormula(formulaId));
  }
}
