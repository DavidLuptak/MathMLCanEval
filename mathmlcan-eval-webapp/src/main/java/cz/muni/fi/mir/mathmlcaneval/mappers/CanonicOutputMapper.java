package cz.muni.fi.mir.mathmlcaneval.mappers;

import cz.muni.fi.mir.mathmlcaneval.domain.CanonicOutput;
import cz.muni.fi.mir.mathmlcaneval.responses.ApplicationRunDetailedResponse.CanonicalizationContainer;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CanonicOutputMapper {
  @Mapping(source = "id", target = "canonicId")
  @Mapping(source = "error", target = "canonicalizationError")
  @Mapping(source = "pretty", target = "canonicXml")
  @Mapping(source = "formula.id", target = "formulaId")
  @Mapping(source = "formula.pretty", target = "formulaXml")
  CanonicalizationContainer map(CanonicOutput canonicOutput);

  Set<CanonicalizationContainer> map(Set<CanonicOutput> outputs);
}
