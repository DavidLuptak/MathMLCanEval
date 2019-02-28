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
package cz.muni.fi.mir.mathmlcaneval.scheduling.support;

import static java.time.ZoneId.systemDefault;
import static org.quartz.CronExpression.isValidExpression;
import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.springframework.util.StringUtils.isEmpty;

import cz.muni.fi.mir.mathmlcaneval.exceptions.InvalidCronTriggerException;
import cz.muni.fi.mir.mathmlcaneval.exceptions.UnsupportedTriggerException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.quartz.CronTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

@Log4j2
@RequiredArgsConstructor
public class TriggerCreator {
  @Getter
  private final String cron;
  private final boolean delay;
  private String name;

  public String getName() {
    if (name == null) {
      name = UUID.randomUUID().toString();
    }

    return name;
  }


  public Trigger buildTrigger() {
    log.info("Building trigger '{}' with delay set to: {}", cron, delay);
    if (!isEmpty(cron)) {
      if (!isValidExpression(cron)) {
        throw new InvalidCronTriggerException(cron);
      }

      TriggerBuilder<CronTrigger> builder = newTrigger()
        .withIdentity(getName())
        .withSchedule(cronSchedule(cron)
          .withMisfireHandlingInstructionFireAndProceed()
          .inTimeZone(TimeZone.getTimeZone(systemDefault())));
      if (delay) {
        Date delayDate = java.sql.Date.from(LocalDate.now().plusDays(1L)
          .atStartOfDay(ZoneId.systemDefault()).toInstant());

        log.info("This trigger will be delayed until: {}", delayDate);
        builder.startAt(delayDate);
      }
      return
        builder
          .usingJobData("cron", cron)
          .build();
    }

    throw new UnsupportedTriggerException(cron);
  }
}
