package cz.muni.fi.mir.mathmlcaneval.scheduling;

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
