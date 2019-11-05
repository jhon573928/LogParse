package com.bairesdev.logreader;


import org.junit.jupiter.api.Assertions;

import com.bairesdev.logreader.dto.LogEvent;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public class LogReaderTest {

  private final LogReader logReader = new LogReader();

  @Test
  public void testPatternTIMESTAMP_LEVEL_CLASS_MESSAGE() throws IOException {
    InputStream file = getClass()
        .getClassLoader().getResourceAsStream("TIMESTAMP LEVEL CLASS MESSAGE.log");
    Stream<LogEvent> stream =  logReader.parse("TIMESTAMP LEVEL CLASS MESSAGE", file);
    List<LogEvent> list = stream.collect(Collectors.toList());
    Assertions.assertEquals(1, list.size());
    Assertions.assertEquals("DEBUG", list.get(0).getLevel());
    Assertions.assertEquals("o.s.b.a.b.JobLauncherCommandLineRunner", list.get(0).getClazz());
    Assertions.assertEquals("No job found in registry for job name ThisWillPreventAllJobsFromBeingExecutedOnStartup", list.get(0).getMessage());
    Assertions.assertEquals(1572011293211L, list.get(0).getTimestamp().longValue());
  }

  @Test
  public void testLOGGER_LEVEL_TIMESTAMP_THREAD_FILE_LINE_MESSAGE() throws IOException {
    InputStream file = getClass()
        .getClassLoader().getResourceAsStream("LOGGER_LEVEL_TIMESTAMP_THREAD_FILE_LINE_MESSAGE.log");
    Stream<LogEvent> stream =  logReader.parse("LOGGER - LEVEL [TIMESTAMP] THREAD [FILE:LINE] - MESSAGE", file);
    List<LogEvent> list = stream.collect(Collectors.toList());
    Assertions.assertEquals(1, list.size());
    Assertions.assertEquals("DEBUG", list.get(0).getLevel());
    Assertions.assertEquals("?", list.get(0).getClazz());
    Assertions.assertEquals("o.s.b.a.b.JobLauncherCommandLineRunner", list.get(0).getFile());
    Assertions.assertEquals("12", list.get(0).getLine());
    Assertions.assertEquals("No job found in registry for job name ThisWillPreventAllJobsFromBeingExecutedOnStartup.", list.get(0).getMessage());
    Assertions.assertEquals(1572011293211L, list.get(0).getTimestamp().longValue());
  }
}
