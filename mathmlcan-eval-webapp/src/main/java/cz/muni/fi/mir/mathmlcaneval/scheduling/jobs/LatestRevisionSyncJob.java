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
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.service.CommitService;

@Log4j2
public class LatestRevisionSyncJob extends AbstractRevisionSyncJob {

  @Override
  List<RepositoryCommit> filteredCommitsToImport() throws IOException {
    CommitService cs = new CommitService();
    log.info("About to filter all revisions to latest one.");

    return cs.getCommits(getRepository()).stream().limit(1).collect(Collectors.toList());
  }
}
