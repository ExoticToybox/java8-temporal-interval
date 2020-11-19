package com.example.core.temporal;

import lombok.NonNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;

/**
 * <p>
 * {@link java.time.LocalTime}の期間を表す。
 * </p>
 * <p>
 * LocalDateTimeInterval is an interval between two {@link java.time.LocalTime}s.
 * </p>
 */
public class LocalTimeInterval extends AbstractTemporalInterval<LocalTime, LocalTimeInterval> {
    public LocalTimeInterval(LocalTime from, LocalTime to) {
        super(from, to);
    }

    @Override
    protected long toEpoch(@NonNull LocalTime time) {
        return time.toEpochSecond(LocalDate.EPOCH, ZoneOffset.UTC);
    }
}
