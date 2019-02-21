package cz.muni.fi.mir.mathmlcaneval.resource;

import cz.muni.fi.mir.mathmlcaneval.requests.SyncRevisionRequest;
import cz.muni.fi.mir.mathmlcaneval.responses.RevisionResponse;
import cz.muni.fi.mir.mathmlcaneval.service.RevisionService;
import cz.muni.fi.mir.mathmlcaneval.support.Response;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
  public List<RevisionResponse> findAll() {
    return revisionService.findAll();
  }

  @PostMapping
  public ResponseEntity<Response> submitSync(
    @Valid @RequestBody SyncRevisionRequest syncRevisionRequest) {

    revisionService.syncRevisions(syncRevisionRequest);

    return ResponseEntity.accepted().body(Response.OK);
  }
}
