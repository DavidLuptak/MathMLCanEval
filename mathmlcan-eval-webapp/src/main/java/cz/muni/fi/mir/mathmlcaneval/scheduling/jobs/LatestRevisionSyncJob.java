package cz.muni.fi.mir.mathmlcaneval.scheduling.jobs;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.service.CommitService;

public class LatestRevisionSyncJob extends AbstractRevisionSyncJob {

  @Override
  List<RepositoryCommit> filteredCommitsToImport() throws IOException {
    CommitService cs = new CommitService();

    return cs.getCommits(getRepository()).stream().limit(1).collect(Collectors.toList());
  }
}
