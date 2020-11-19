package com.example.core.temporal;

import lombok.NonNull;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * <p>
 * {@link java.time.LocalDateTime}の期間を表す。
 * </p>
 * <p>
 * LocalDateTimeInterval is an interval between two {@link java.time.LocalDateTime}s.
 * </p>
 */
public class LocalDateTimeInterval extends AbstractTemporalInterval<LocalDateTime, LocalDateTimeInterval> {
    public LocalDateTimeInterval(LocalDateTime from, LocalDateTime to) {
        super(from, to);
    }

    @Override
    protected long toEpoch(@NonNull LocalDateTime dateTime) {
        return dateTime.toEpochSecond(ZoneOffset.UTC);
    }
}
