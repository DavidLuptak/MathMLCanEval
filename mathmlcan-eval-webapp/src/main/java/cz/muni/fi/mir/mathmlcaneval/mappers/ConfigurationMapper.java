package cz.muni.fi.mir.mathmlcaneval.mappers;

import cz.muni.fi.mir.mathmlcaneval.domain.InputConfiguration;
import cz.muni.fi.mir.mathmlcaneval.requests.CreateConfigurationRequest;
import cz.muni.fi.mir.mathmlcaneval.responses.ConfigurationResponse;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConfigurationMapper {

  ConfigurationResponse map(InputConfiguration configuration);

  List<ConfigurationResponse> map(List<InputConfiguration> configuration);

  InputConfiguration map(CreateConfigurationRequest request);
}
