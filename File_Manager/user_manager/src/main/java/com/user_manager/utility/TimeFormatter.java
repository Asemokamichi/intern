package com.user_manager.utility;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TimeFormatter {
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    public static ZonedDateTime now = ZonedDateTime.now();
}
