package com.db.code;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateAndTimeAPI {
    public static void main(String[] args) {
        // 1. Current Date and Time
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        LocalDateTime currentDateTime = LocalDateTime.now();
        ZonedDateTime zonedDateTime = ZonedDateTime.now();

        System.out.println("Current Date      : " + currentDate);
        System.out.println("Current Time      : " + currentTime);
        System.out.println("Current DateTime  : " + currentDateTime);
        System.out.println("Zoned DateTime    : " + zonedDateTime);

        // 2. Create specific date and time
        LocalDate birthDate = LocalDate.of(1990, Month.MARCH, 25);
        LocalTime meetingTime = LocalTime.of(14, 30);
        LocalDateTime eventDateTime = LocalDateTime.of(2024, 12, 31, 23, 59);

        System.out.println("\nBirth Date        : " + birthDate);
        System.out.println("Meeting Time      : " + meetingTime);
        System.out.println("Event DateTime    : " + eventDateTime);

        // 3. Add/Subtract Days, Months, Years
        LocalDate nextWeek = currentDate.plusWeeks(1);
        LocalDate lastMonth = currentDate.minusMonths(1);
        System.out.println("\nNext Week         : " + nextWeek);
        System.out.println("Last Month        : " + lastMonth);

        // 4. Duration and Period
        Period period = Period.between(birthDate, currentDate);
        System.out.println("\nAge: " + period.getYears() + " years, " +
                period.getMonths() + " months, " + period.getDays() + " days");

        Duration duration = Duration.between(LocalTime.NOON, currentTime);
        System.out.println("Duration from NOON: " + duration.toMinutes() + " minutes");

        // 5. Formatting and Parsing
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String formatted = currentDateTime.format(formatter);
        System.out.println("\nFormatted DateTime: " + formatted);

        LocalDateTime parsedDateTime = LocalDateTime.parse("15-08-2023 10:30", formatter);
        System.out.println("Parsed DateTime   : " + parsedDateTime);

        // 6. Time Zones
        ZonedDateTime indiaTime = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));
        ZonedDateTime utcTime = ZonedDateTime.now(ZoneId.of("UTC"));
        System.out.println("\nIndia Time        : " + indiaTime);
        System.out.println("UTC Time          : " + utcTime);

        // 7. Comparing Dates
        if (currentDate.isAfter(birthDate)) {
            System.out.println("\nYou were born before today!");
        }
    }
}
