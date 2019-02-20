package cz.muni.fi.mir.mathmlcaneval.mappers;

import cz.muni.fi.mir.mathmlcaneval.domain.InputConfiguration;
import cz.muni.fi.mir.mathmlcaneval.requests.CreateConfigurationRequest;
import cz.muni.fi.mir.mathmlcaneval.responses.ConfigurationResponse;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ConfigurationMapper {

  @Mapping(source = "user.id", target = "userId")
  ConfigurationResponse map(InputConfiguration configuration);

  List<ConfigurationResponse> map(List<InputConfiguration> configuration);

  InputConfiguration map(CreateConfigurationRequest request);
}
