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

import cz.muni.fi.mir.mathmlcaneval.domain.ApplicationRun;
import cz.muni.fi.mir.mathmlcaneval.domain.CanonicOutput;
import cz.muni.fi.mir.mathmlcaneval.domain.Formula;
import cz.muni.fi.mir.mathmlcaneval.domain.InputConfiguration;
import cz.muni.fi.mir.mathmlcaneval.domain.User;
import cz.muni.fi.mir.mathmlcaneval.repository.ApplicationRunRepository;
import cz.muni.fi.mir.mathmlcaneval.repository.CanonicOutputRepository;
import cz.muni.fi.mir.mathmlcaneval.repository.FormulaRepository;
import cz.muni.fi.mir.mathmlcaneval.repository.InputConfigurationRepository;
import cz.muni.fi.mir.mathmlcaneval.repository.RevisionRepository;
import cz.muni.fi.mir.mathmlcaneval.service.CanonicalizerService;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

@Setter
public class CanonicalizationJob implements Job {

  // these bad boys needs to match fields
  public static final String CONFIG = "configurationId";
  public static final String REVISION = "revisionId";
  public static final String COLLECTION = "collectionId";
  public static final String APP_RUN = "applicationRunId";

  private Long configurationId;
  private Long revisionId;
  private Long collectionId;
  private Long applicationRunId;
  private String jobId;


  @Autowired
  private TransactionTemplate transactionTemplate;
  @Autowired
  private FormulaRepository formulaRepository;
  @Autowired
  private ApplicationRunRepository applicationRunRepository;
  @Autowired
  private RevisionRepository revisionRepository;
  @Autowired
  private CanonicOutputRepository canonicOutputRepository;
  @Autowired
  private InputConfigurationRepository inputConfigurationRepository;

  @Autowired
  private CanonicalizerService canonicalizerService;

  @Override
  public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    // this will fetch data and release underlying datasource connection
    final var tmp = transactionTemplate.execute(transactionStatus -> {
      final var formulas = formulaRepository.getFormulasInCollection(collectionId);
      final var config = inputConfigurationRepository.findById(configurationId)
        .orElseThrow();

      final var run = new ApplicationRun();
      run.setInputConfiguration(config);
      run.setRevision(revisionRepository.findById(revisionId).orElseThrow());
      run.setStartedBy(new User(2L));

      return new CanonicData(formulas, config, applicationRunRepository.save(run));
    });

    List<CanonicOutput> result = this.canonicalizerService
      .fireCanonicalizer(tmp.getRun().getRevision().getSha1(),
        tmp.getInputConfiguration().getContent(), tmp.getFormulas(), tmp.getRun());

    transactionTemplate.execute(new TransactionCallbackWithoutResult() {
      @Override
      protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
        canonicOutputRepository.saveAll(result);
      }
    });
  }


  @Getter
  @RequiredArgsConstructor
  class CanonicData {

    private final List<Formula> formulas;
    private final InputConfiguration inputConfiguration;
    private final ApplicationRun run;
  }
}
