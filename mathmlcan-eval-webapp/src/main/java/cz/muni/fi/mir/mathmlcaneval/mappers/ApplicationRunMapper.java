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
package cz.muni.fi.mir.mathmlcaneval.mappers;

import cz.muni.fi.mir.mathmlcaneval.domain.ApplicationRun;
import cz.muni.fi.mir.mathmlcaneval.domain.User;
import cz.muni.fi.mir.mathmlcaneval.events.CanonicalizationEvent;
import cz.muni.fi.mir.mathmlcaneval.requests.CanonicalizationRequest;
import cz.muni.fi.mir.mathmlcaneval.responses.ApplicationRunDetailedResponse;
import cz.muni.fi.mir.mathmlcaneval.responses.ApplicationRunResponse;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Qualifier;

@Mapper(componentModel = "spring")
public interface ApplicationRunMapper {

  @Mapping(source = "inputConfiguration.id", target = "configurationId")
  @Mapping(source = "revision.id", target = "revisionId")
  @Mapping(source = "ownedBy.id", target = "ownedById")
  @Mapping(source = "ownedBy.name", target = "ownedByName")
  @RunCollectionMapper
  ApplicationRunResponse map(ApplicationRun run);

  @IterableMapping(qualifiedBy = RunCollectionMapper.class)
  List<ApplicationRunResponse> mapList(List<ApplicationRun> runs);

  @Mapping(source = "configurationId", target = "inputConfiguration.id")
  @Mapping(source = "revisionId", target = "revision.id")
  ApplicationRun mapRequest(CanonicalizationRequest request);


  @Mapping(source = "inputConfiguration.id", target = "configurationId")
  @Mapping(source = "inputConfiguration.content", target = "configurationXml")
  @Mapping(source = "revision.id", target = "revisionId")
  @Mapping(source = "revision.sha1", target = "revisionHash")
  @Mapping(source = "ownedBy.id", target = "ownedById")
  @Mapping(source = "ownedBy.name", target = "ownedByName")
  ApplicationRunDetailedResponse mapDetail(ApplicationRun run);

  default ApplicationRun map(CanonicalizationRequest request, User ownedBy) {
    final var run = mapRequest(request);
    run.setOwnedBy(ownedBy);

    return run;
  }

  default CanonicalizationEvent map(Object source, ApplicationRun run,
    CanonicalizationRequest request) {
    return new CanonicalizationEvent(source,
      run.getId(),
      request.getCollectionId(),
      request.getPostProcessors()
    );
  }


  @Qualifier
  @Target(ElementType.METHOD)
  @Retention(RetentionPolicy.CLASS)
  @interface RunCollectionMapper {

  }
}
