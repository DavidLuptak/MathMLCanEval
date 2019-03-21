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
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Log4j2
public abstract class AbstractRevisionSyncJob implements Job {

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
  private AsyncListenableTaskExecutor taskExecutor;

  private String jobId;

  abstract List<RepositoryCommit> filteredCommitsToImport() throws IOException;

  protected Repository getRepository() throws IOException {
    for (Repository r : new RepositoryService().getRepositories("MIR-MU")) {
      if (r.getName().equals("MathMLCan")) {
        return r;
      }
    }

    throw new RuntimeException();
  }

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    try {
      final var commits = filteredCommitsToImport();
      final var counter = new AtomicInteger(0);

      for (RepositoryCommit rc : filteredCommitsToImport()) {
        taskExecutor.submitListenable(new CheckoutDeployBuildSubTask(rc)).addCallback(
          new ListenableFutureCallback<Object>() {
            @Override
            public void onFailure(Throwable throwable) {
              counter.incrementAndGet();
            }

            @Override
            public void onSuccess(Object o) {
              counter.incrementAndGet();
            }
          });
      }

      while(counter.get() != commits.size()) {
        TimeUnit.MILLISECONDS.sleep(1500L);
      }
    } catch (IOException ex) {
      log.error(ex);
    } catch (InterruptedException ex) {
      Thread.currentThread().interrupt();
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

  protected LocalDateTime commitTime(Date date) {
    return Instant.ofEpochMilli(date.getTime())
      .atZone(ZoneId.systemDefault())
      .toLocalDateTime();
  }
}
