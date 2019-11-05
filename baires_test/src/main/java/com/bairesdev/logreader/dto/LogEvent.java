package com.bairesdev.logreader.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class LogEvent {
  private String logger;
  private String message;
  private Long timestamp;
  private String level;
  private String thread;
  private String clazz;
  private String file;
  private String line;
  private String method;
  private String stackTrace;

  public void appendStackTrace(String trace) {
    if (stackTrace == null) {
      stackTrace = trace;
    } else {
      stackTrace+=trace;
    }
  }
}
