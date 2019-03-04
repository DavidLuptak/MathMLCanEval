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

import cz.muni.fi.mir.mathmlcaneval.domain.Revision;
import cz.muni.fi.mir.mathmlcaneval.repository.RevisionRepository;
import cz.muni.fi.mir.mathmlcaneval.service.DeployService;
import cz.muni.fi.mir.mathmlcaneval.service.MavenService;
import cz.muni.fi.mir.mathmlcaneval.service.RemoteRepositoryService;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.service.CommitService;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

@Log4j2
@Setter
public class RevisionSyncJob implements Job {

  public static final String REVISION = "revisionId";
  public static final String DATE_FROM = "dateFrom";
  public static final String DATE_TO = "dateTo";

  private String revisionId;
  private LocalDateTime dateFrom;
  private LocalDateTime dateTo;


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
  @Autowired
  private AsyncTaskExecutor taskExecutor; // todo maybe use quartz here ?

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
            // todo exception handling
            List<RepositoryCommit> data;
            if (!StringUtils.isEmpty(this.revisionId)) {
             data = commitService
                .getCommits(r).stream()
                .filter(rc -> rc.getSha().equals(revisionId))
                .collect(Collectors.toList());
            } else {
              data = commitService
                .getCommits(r)
                .stream()
                .filter(rc -> nsIsAfter(rc.getCommit().getCommitter().getDate()))
                .filter(rc -> nsIsBefore(rc.getCommit().getCommitter().getDate()))
                .collect(Collectors.toList());
            }

            executeJob(data);

          } catch (Exception e) {
            log.fatal(e);
          }
        });

    } catch (IOException ex) {
      log.error(ex);
    }
  }

  private void executeJob(List<RepositoryCommit> commits)  {
    for (RepositoryCommit rc : commits) {
      taskExecutor.submit(new CheckoutDeployBuildSubTask(rc));
    }
  }

  @RequiredArgsConstructor
  class CheckoutDeployBuildSubTask implements Runnable {

    private final RepositoryCommit repositoryCommit;

    @Override
    public void run() {
      try {
        final var revision = remoteRepositoryService.cloneAndCheckout(repositoryCommit.getSha());

        final var artifact = mavenService.invokeMavenBuild(revision);
        deployService.deploy(artifact, repositoryCommit.getSha());

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
          @Override
          protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
            final var db = new Revision();
            db.setSha1(repositoryCommit.getSha());
            db.setCommitTime(commitTime(repositoryCommit.getCommit().getCommitter().getDate()));
            db.setSyncTime(LocalDateTime.now());
            db.setName(repositoryCommit.getSha());

            revisionRepository.save(db);
          }
        });
      } catch (Exception e) {
        log.warn(e);
      }
    }
  }

  private boolean nsIsAfter(Date from) {
    return dateFrom == null || commitTime(from).isAfter(dateFrom);
  }

  private boolean nsIsBefore(Date to) {
    return dateTo == null || commitTime(to).isBefore(dateTo);
  }

  private LocalDateTime commitTime(Date date) {
    return Instant.ofEpochMilli(date.getTime())
      .atZone(ZoneId.systemDefault())
      .toLocalDateTime();
  }
}
