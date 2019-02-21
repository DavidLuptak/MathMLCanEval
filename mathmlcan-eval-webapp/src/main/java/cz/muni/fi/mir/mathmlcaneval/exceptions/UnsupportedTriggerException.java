package cz.muni.fi.mir.mathmlcaneval.exceptions;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UnsupportedTriggerException extends RuntimeException {

  private final String cron;
}
