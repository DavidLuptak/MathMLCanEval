package cz.muni.fi.mir.mathmlcaneval.mappers;

import cz.muni.fi.mir.mathmlcaneval.domain.Revision;
import cz.muni.fi.mir.mathmlcaneval.responses.RevisionResponse;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RevisionMapper {

  RevisionResponse map(Revision revision);

  List<RevisionResponse> map(List<Revision> revision);
}
