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
import cz.muni.fi.mir.mathmlcaneval.repository.ApplicationRunRepository;
import cz.muni.fi.mir.mathmlcaneval.repository.CanonicOutputRepository;
import cz.muni.fi.mir.mathmlcaneval.repository.FormulaRepository;
import cz.muni.fi.mir.mathmlcaneval.service.CanonicalizerService;
import cz.muni.fi.mir.mathmlcaneval.service.SimilarityService;
import cz.muni.fi.mir.mathmlcaneval.service.XmlDocumentService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

@Log4j2
@Setter
public class CanonicalizationJob implements Job {

  // these bad boys needs to match fields
  public static final String COLLECTION = "collectionId";
  public static final String APP_RUN = "applicationRunId";

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
  private CanonicOutputRepository canonicOutputRepository;
  @Autowired
  private CanonicalizerService canonicalizerService;
  @Autowired
  private SimilarityService similarityService;
  @Autowired
  private XmlDocumentService xmlDocumentService;

  @Override
  public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    // this will fetch data and release underlying datasource connection
    final var tmp = transactionTemplate.execute(transactionStatus -> {
      final var formulas = formulaRepository.getFormulasInCollection(collectionId);
      final var run = applicationRunRepository.findByIdFetched(applicationRunId).orElseThrow();
      // we need to touch these fields in order to prevent lazyinit exception

      log.info("Application run {} started", run::getId);
      run.setStart(LocalDateTime.now());
      return new CanonicData(formulas, run);
    });

    List<CanonicOutput> result = this.canonicalizerService
      .fireCanonicalizer(tmp.getRun().getRevision().getSha1(),
        tmp.getRun().getInputConfiguration().getContent(), tmp.getFormulas(), tmp.getRun());

    result.forEach(co -> {
      log.debug("Building document for canonic output '{}'", co::getHash);
      final var docXml = this.xmlDocumentService.buildDocument(co);

      log.debug("Preparing similarity output for {}", co::getHash);
      final var sf = similarityService.generateSimilarity(docXml);
      co.setSimilarityForm(sf);

      co.setPretty(this.xmlDocumentService.prettyPrintToString(docXml));
      log.debug("CO {} pretty printed", co::getHash);
    });



    transactionTemplate.execute(new TransactionCallbackWithoutResult() {
      @Override
      protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
        tmp.getRun().setEnd(LocalDateTime.now());
        applicationRunRepository.save(tmp.getRun());
        canonicOutputRepository.saveAll(result);
      }
    });
  }


  @Getter
  @RequiredArgsConstructor
  class CanonicData {

    @NonNull
    private final List<Formula> formulas;
    @NonNull
    private final ApplicationRun run;
  }
}
