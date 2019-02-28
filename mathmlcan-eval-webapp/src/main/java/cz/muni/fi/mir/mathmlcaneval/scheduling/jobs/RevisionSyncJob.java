/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cz.muni.fi.mir.mathmlcaneval.scheduling.jobs;

import cz.muni.fi.mir.mathmlcaneval.domain.Revision;
import cz.muni.fi.mir.mathmlcaneval.repository.RevisionRepository;
import cz.muni.fi.mir.mathmlcaneval.service.DeployService;
import cz.muni.fi.mir.mathmlcaneval.service.MavenService;
import cz.muni.fi.mir.mathmlcaneval.service.RemoteRepositoryService;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.service.CommitService;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

@Log4j2
@Data
public class RevisionSyncJob implements Job {

  private String jobId;
  @Autowired
  private TransactionTemplate transactionTemplate;
  @Autowired
  private RevisionRepository revisionRepository;
  @Autowired
  private RemoteRepositoryService remoteRepositoryService;
  @Autowired
  private MavenService mavenService;
  @Autowired
  private DeployService deployService;

  @Override
  public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    RepositoryService repositoryService = new RepositoryService();
    CommitService commitService = new CommitService();

    try {
      repositoryService.getRepositories("MIR-MU")
        .stream()
        .filter(r -> r.getName().equals("MathMLCan"))
        .findFirst()
        .ifPresent(r -> {
          try {
            for (RepositoryCommit rc : commitService.getCommits(r).subList(0, 2)) {
              Path revision = remoteRepositoryService.cloneAndCheckout(rc.getSha());

              InputStream artifact = mavenService.invokeMavenBuild(revision);
              deployService.deploy(artifact, rc.getSha());

              transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                  final var db = new Revision();
                  db.setSha1(rc.getSha());
                  db.setCommitTime(commitTime(rc.getCommit().getCommitter().getDate()));
                  db.setSyncTime(LocalDateTime.now());
                  db.setName(rc.getSha());

                  revisionRepository.save(db);
                }
              });
            }
          } catch (Exception e) {
            log.fatal(e);
          }
        });

    } catch (IOException ex) {
      log.error(ex);
    }
  }

  private LocalDateTime commitTime(Date date) {
    return Instant.ofEpochMilli(date.getTime())
      .atZone(ZoneId.systemDefault())
      .toLocalDateTime();
  }
}
