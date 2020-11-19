package com.example.core.temporal;

import lombok.NonNull;

import java.time.LocalDate;

/**
 * <p>
 * {@link java.time.LocalDate}の期間を表す。
 * </p>
 * <p>
 * LocalDateTimeInterval is an interval between two {@link java.time.LocalDate}s.
 * </p>
 */
public class LocalDateInterval extends AbstractTemporalInterval<LocalDate, LocalDateInterval> {
    public LocalDateInterval(LocalDate from, LocalDate to) {
        super(from, to);
    }

    @Override
    protected long toEpoch(@NonNull LocalDate date) {
        return date.toEpochDay();
    }
}
