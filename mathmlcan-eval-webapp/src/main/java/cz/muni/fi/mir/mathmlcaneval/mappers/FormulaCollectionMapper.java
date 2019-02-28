package cz.muni.fi.mir.mathmlcaneval.mappers;

import cz.muni.fi.mir.mathmlcaneval.domain.Formula;
import cz.muni.fi.mir.mathmlcaneval.domain.FormulaCollection;
import cz.muni.fi.mir.mathmlcaneval.requests.FormulaCollectionRequest;
import cz.muni.fi.mir.mathmlcaneval.responses.FormulaCollectionResponse;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FormulaCollectionMapper {

  @Mapping(source = "creator.id", target = "creatorId")
  FormulaCollectionResponse map(FormulaCollection formulaCollection);

  FormulaCollection map(FormulaCollectionRequest request);

  List<FormulaCollectionResponse> map(List<FormulaCollection> formulaCollection);

  default Formula map(Long id) {
    Formula f = new Formula();
    f.setId(id);

    return f;
  }
}
