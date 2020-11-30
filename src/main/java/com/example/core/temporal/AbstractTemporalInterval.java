package com.example.core.temporal;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;

import java.time.temporal.Temporal;

/**
 * <p>
 * {@link java.time.temporal.Temporal}の実装クラスの期間を表す。
 * </p>
 * <p>
 * AbstractTemporalInterval provides the common behaviour for interval of implementation of {@link java.time.temporal.Temporal}
 * </p>
 *
 * @param <T> {@link java.time.temporal.Temporal}の実装クラス - the implementation of {@link java.time.temporal.Temporal}
 * @param <I> {@link com.example.core.temporal.AbstractTemporalInterval}のサブクラス - the subclass of {@link com.example.core.temporal.AbstractTemporalInterval}
 */
@Getter(AccessLevel.PROTECTED)
public abstract class AbstractTemporalInterval<T extends Temporal, I extends AbstractTemporalInterval<T, I>> {
    @NonNull protected final T from;
    @NonNull protected final T to;

    /**
     * Constructor
     *
     * @param from 開始時点 - the point of start, must be before to, not null
     * @param to   終了時点 - the point of end, must be after before, not null
     */
    protected AbstractTemporalInterval(@NonNull T from, @NonNull T to) {
        if (toEpoch(from) >= toEpoch(to)) {
            throw new IllegalArgumentException("from must be before to");
        }
        this.from = from;
        this.to = to;
    }

    /**
     * <p>
     * temporalが期間に含まれるかどうかを判定する。<br>
     * temporalがfromまたはtoと同一である場合、trueとなる。
     * </p>
     * <p>
     * Checks if this interval contains the specified temporal.<br>
     * It returns true if temporal is equal to from or to.
     * </p>
     *
     * @param temporal 比較対象日時/日付/時刻 - the temporal to compare to, not null
     * @return temporalが期間に含まれればtrue - true if this interval contains the specified temporal
     */
    public final boolean contains(@NonNull T temporal) {
        return toEpoch(from) <= toEpoch(temporal) && toEpoch(temporal) <= toEpoch(to);
    }

    /**
     * <p>
     * otherと同一の期間であるかどうかを判定する。
     * </p>
     * <p>
     * Checks if this interval is equal to another interval.
     * </p>
     *
     * @param other 比較対象期間 - the other interval to compare to, not null
     * @return otherと同一の期間であればtrue - true if this is equal to other interval
     */
    public final boolean equals(@NonNull I other) {
        return toEpoch(from) == toEpoch(other.getFrom()) && toEpoch(to) == toEpoch(other.getTo());
    }

    /**
     * <p>
     * 双方を開区間とみなし、otherと重複する期間があるかどうかを判定する。<br>
     * 以下の場合falseとなる。
     * </p>
     * <p>
     * Checks if this interval overlaps with specified interval, regarding both as Open interval.<br>
     * The case as below, it will return false.
     * </p>
     *
     * <pre>
     * |----this----|
     *              |----other----|
     * </pre>
     * <pre>
     *               |----this----|
     * |----other----|
     * </pre>
     *
     * @param other 比較対象期間 - the other interval to compare to, not null
     * @return otherと重複する期間があればtrue - true if this overlaps with other interval
     */
    public final boolean overlapsAsOpen(@NonNull I other) {
        return toEpoch(from) < toEpoch(other.getTo()) && toEpoch(other.getFrom()) < toEpoch(to);
    }

    /**
     * <p>
     * 双方を閉区間とみなし、otherと重複する期間があるかどうかを判定する。<br>
     * 以下の場合trueとなる。
     * </p>
     * <p>
     * Checks if this interval overlaps with specified interval, regarding both as Closed interval.<br>
     * The case as below, it will return true.
     * </p>
     * <pre>
     * |----this----|
     *              |----other----|
     * </pre>
     * <pre>
     *               |----this----|
     * |----other----|
     * </pre>
     *
     * @param other 比較対象期間 - the other interval to compare to, not null
     * @return otherと重複する期間があればtrue - true if this overlaps with other interval
     */
    public final boolean overlapsAsClosed(@NonNull I other) {
        return toEpoch(from) <= toEpoch(other.getTo()) && toEpoch(other.getFrom()) <= toEpoch(to);
    }

    /**
     * <p>
     * 1970年01月01日00時00分00秒を基準としたエポック数に変換する。
     * </p>
     * <p>
     * Converts to the number since the epoch of 1970-01-01T00:00:00Z.
     * </p>
     *
     * @param temporal 日時/日付/時刻 - the temporal
     * @return エポック数 - the number since the epoch
     */
    protected abstract long toEpoch(@NonNull T temporal);
}
