package com.bairesdev.logreader;

import com.bairesdev.logreader.dto.LogEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.varia.LogFilePatternReceiver;

public class LogReader extends LogFilePatternReceiver {

  private final List<LoggingEvent> events = new ArrayList<>();

  @Override
  public void doPost(LoggingEvent event) {
    events.add(event);
  }

  public Stream<LogEvent> parse()  throws IOException {
    InputStream file = getClass()
        .getClassLoader().getResourceAsStream("TIMESTAMP LEVEL CLASS MESSAGE.log");
    return parse("TIMESTAMP LEVEL CLASS MESSAGE", file);
  }

  public Stream<LogEvent> parse(String pattern, InputStream stream) throws IOException{
    events.clear();
    this.setLogFormat(pattern);
    this.setHost("");
    this.setPath("file");
    this.setTimestampFormat("yyyy-MM-d HH:mm:ss.SSS");
    this.initialize();
    this.createPattern();
    this.process(new BufferedReader(new InputStreamReader(stream)));
    return events.stream()
        .map(log -> LogEvent
        .builder()
        .level(log.getLevel().toString())
        .timestamp(log.getTimeStamp())
        .message(log.getMessage().toString())
        .file(log.getLocationInformation().getFileName())
        .clazz(log.getLocationInformation().getClassName())
        .line(log.getLocationInformation().getLineNumber())
        .method(log.getLocationInformation().getMethodName())
        .build());
  }

  public static void main(String[] arg) throws IOException{
    (new LogReader()).parse()
      .forEach(System.err::println);
  }
}
