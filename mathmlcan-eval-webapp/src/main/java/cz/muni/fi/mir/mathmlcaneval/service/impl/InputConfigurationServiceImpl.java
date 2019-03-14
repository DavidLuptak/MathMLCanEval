/*
 * Copyright © 2013 the original author or authors (webmias@fi.muni.cz)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cz.muni.fi.mir.mathmlcaneval.service.impl;

import static cz.muni.fi.mir.mathmlcaneval.repository.specs.ConfigurationSpecification.publicOrMine;

import cz.muni.fi.mir.mathmlcaneval.domain.User;
import cz.muni.fi.mir.mathmlcaneval.mappers.ConfigurationMapper;
import cz.muni.fi.mir.mathmlcaneval.repository.InputConfigurationRepository;
import cz.muni.fi.mir.mathmlcaneval.requests.CreateConfigurationRequest;
import cz.muni.fi.mir.mathmlcaneval.responses.ConfigurationResponse;
import cz.muni.fi.mir.mathmlcaneval.security.SecurityService;
import cz.muni.fi.mir.mathmlcaneval.service.InputConfigurationService;
import cz.muni.fi.mir.mathmlcaneval.support.ReadOnly;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InputConfigurationServiceImpl implements InputConfigurationService {

  private final ConfigurationMapper configurationMapper;
  private final InputConfigurationRepository inputConfigurationRepository;
  private final SecurityService securityService;

  @Override
  @ReadOnly
  public Page<ConfigurationResponse> findAll(Pageable pageable) {
    return inputConfigurationRepository
      .findAll(publicOrMine(securityService.getCurrentUserId(false)), pageable)
      .map(configurationMapper::map);
  }

  @ReadOnly
  @Override
  public Optional<ConfigurationResponse> findById(Long id) {
    return inputConfigurationRepository
      .findById(id)
      .map(configurationMapper::map);
  }

  @Transactional
  @Override
  public ConfigurationResponse save(CreateConfigurationRequest create) {
    final var preSave = configurationMapper.map(create);
    preSave.setOwnedBy(new User(securityService.getCurrentUserId(false)));

    final var saved = inputConfigurationRepository.save(preSave);

    return configurationMapper.map(saved);
  }
}
