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
package cz.muni.fi.mir.mathmlcaneval.scheduling.jobs;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.service.CommitService;

@Log4j2
@Setter
public class RevisionSyncJob extends AbstractRevisionSyncJob {

  public static final String REVISION = "revisionId";
  public static final String DATE_FROM = "dateFrom";
  public static final String DATE_TO = "dateTo";

  private String revisionId;
  private LocalDateTime dateFrom;
  private LocalDateTime dateTo;

  @Override
  List<RepositoryCommit> filteredCommitsToImport() throws IOException {
    CommitService commitService = new CommitService();

    Repository repository = getRepository();

    List<RepositoryCommit> data;
    if (!StringUtils.isEmpty(this.revisionId)) {
      data = commitService
        .getCommits(repository)
        .stream()
        .filter(rc -> rc.getSha().equals(revisionId))
        .collect(Collectors.toList());
    } else {
      data = commitService
        .getCommits(repository)
        .stream()
        .filter(rc -> nsIsAfter(rc.getCommit().getCommitter().getDate()))
        .filter(rc -> nsIsBefore(rc.getCommit().getCommitter().getDate()))
        .collect(Collectors.toList());
    }

    return data;
  }

  private boolean nsIsAfter(Date from) {
    return dateFrom == null || commitTime(from).isAfter(dateFrom);
  }

  private boolean nsIsBefore(Date to) {
    return dateTo == null || commitTime(to).isBefore(dateTo);
  }
}
