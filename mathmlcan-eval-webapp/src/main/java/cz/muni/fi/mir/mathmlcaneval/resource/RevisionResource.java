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
package cz.muni.fi.mir.mathmlcaneval.resource;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonpatch.JsonPatch;
import cz.muni.fi.mir.mathmlcaneval.requests.SyncRevisionRequest;
import cz.muni.fi.mir.mathmlcaneval.responses.RevisionResponse;
import cz.muni.fi.mir.mathmlcaneval.service.RevisionService;
import cz.muni.fi.mir.mathmlcaneval.service.support.JsonPatchParser;
import cz.muni.fi.mir.mathmlcaneval.support.Response;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/revisions")
public class RevisionResource {

  private final RevisionService revisionService;


  @GetMapping
  public Page<RevisionResponse> findAll(Pageable pageable) {
    return revisionService.findAll(pageable);
  }

  @PostMapping
  public ResponseEntity<Response> submitSync(
    @Valid @RequestBody SyncRevisionRequest syncRevisionRequest) {

    revisionService.syncRevisions(syncRevisionRequest);

    return Response.ACCEPTED;
  }

  @PostMapping("/latest")
  public ResponseEntity<Response> submitSyncLatest(){
    revisionService.syncRevisions(null);

    return Response.ACCEPTED;
  }

  @PatchMapping("/{id}")
  public ResponseEntity<RevisionResponse> patch(@PathVariable Long id, @RequestBody JsonNode input) {
    JsonPatch patch = JsonPatchParser.validateInput(input, null, null);

    return ResponseEntity.ok(revisionService.update(id, patch));
  }
}
