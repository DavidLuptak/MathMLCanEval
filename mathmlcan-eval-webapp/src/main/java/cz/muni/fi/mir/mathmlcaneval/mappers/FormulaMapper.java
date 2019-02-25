package cz.muni.fi.mir.mathmlcaneval.mappers;

import cz.muni.fi.mir.mathmlcaneval.domain.Formula;
import cz.muni.fi.mir.mathmlcaneval.responses.FormulaResponse;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FormulaMapper {

  FormulaResponse map(Formula formula);

  List<FormulaResponse> map(List<Formula> formulas);
}
