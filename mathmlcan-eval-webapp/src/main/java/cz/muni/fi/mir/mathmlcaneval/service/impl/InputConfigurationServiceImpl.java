package cz.muni.fi.mir.mathmlcaneval.service.impl;

import cz.muni.fi.mir.mathmlcaneval.domain.User;
import cz.muni.fi.mir.mathmlcaneval.mappers.ConfigurationMapper;
import cz.muni.fi.mir.mathmlcaneval.repository.InputConfigurationRepository;
import cz.muni.fi.mir.mathmlcaneval.requests.CreateConfigurationRequest;
import cz.muni.fi.mir.mathmlcaneval.responses.ConfigurationResponse;
import cz.muni.fi.mir.mathmlcaneval.security.SecurityService;
import cz.muni.fi.mir.mathmlcaneval.service.InputConfigurationService;
import cz.muni.fi.mir.mathmlcaneval.support.ReadOnly;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InputConfigurationServiceImpl implements InputConfigurationService {
  private final ConfigurationMapper configurationMapper;
  private final InputConfigurationRepository inputConfigurationRepository;
  private final SecurityService securityService;

  @ReadOnly
  @Override
  public List<ConfigurationResponse> findAll() {
    return configurationMapper.map(inputConfigurationRepository.findAll());
  }

  @ReadOnly
  @Override
  public Optional<ConfigurationResponse> findById(Long id) {
    return Optional.empty();
  }

  @Override
  public ConfigurationResponse save(CreateConfigurationRequest create) {
    final var preSave = configurationMapper.map(create);
    preSave.setUser(new User(securityService.getCurrentUserId()));

    final var saved = inputConfigurationRepository.save(preSave);

    return configurationMapper.map(saved);
  }
}
