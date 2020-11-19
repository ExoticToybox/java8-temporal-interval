package com.example.core.temporal;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

public class LocalTimeIntervalTest {
    private final LocalTime originFrom = LocalTime.now();
    private final LocalTime originTo = originFrom.plusMinutes(30L);
    private final LocalTimeInterval origin = new LocalTimeInterval(originFrom, originTo);

    @Nested
    class Contains {
        /**
         * <pre>
         *         @
         * |----origin----|
         * </pre>
         */
        @Test
        public void test_contains_between_shouldBeTrue() {
            boolean result = origin.contains(originFrom.plusMinutes(15L));
            assertThat(result).isTrue();
        }

        /**
         * <pre>
         * @
         * |----origin----|
         * </pre>
         */
        @Test
        public void test_contains_equalFrom_shouldBeTrue() {
            boolean result = origin.contains(originFrom);
            assertThat(result).isTrue();
        }

        /**
         * <pre>
         *                @
         * |----origin----|
         * </pre>
         */
        @Test
        public void test_contains_equalTo_shouldBeTrue() {
            boolean result = origin.contains(originTo);
            assertThat(result).isTrue();
        }

        /**
         * <pre>
         * @
         *   |----origin----|
         * </pre>
         */
        @Test
        public void test_contains_beforeFrom_shouldBeFalse() {
            boolean result = origin.contains(originFrom.minusMinutes(1L));
            assertThat(result).isFalse();
        }

        /**
         * <pre>
         *                  @
         * |----origin----|
         * </pre>
         */
        @Test
        public void test_contains_afterTo_shouldBeFalse() {
            boolean result = origin.contains(originTo.plusMinutes(1L));
            assertThat(result).isFalse();
        }
    }

    @Nested
    class Equals {
        /**
         * <pre>
         * |-----origin-----|
         * |----interval----|
         * </pre>
         */
        @Test
        public void test_equals_equal_shouldBeTrue() {
            LocalTimeInterval interval = new LocalTimeInterval(originFrom, originTo);
            boolean result = origin.equals(interval);
            assertThat(result).isTrue();
        }

        /**
         * <pre>
         *     |-----origin-----|
         * |--------interval--------|
         * </pre>
         */
        @Test
        public void test_equals_interval_containsOrigin_shouldBeFalse() {
            LocalTimeInterval interval = new LocalTimeInterval(originFrom.minusMinutes(15L), originTo.minusMinutes(15L));
            boolean result = origin.equals(interval);
            assertThat(result).isFalse();
        }

        /**
         * <pre>
         * |----------origin----------|
         *     |-----interval-----|
         * </pre>
         */
        @Test
        public void test_equals_origin_containsInterval_shouldBeFalse() {
            LocalTimeInterval interval = new LocalTimeInterval(originFrom.minusMinutes(15L), originTo.minusMinutes(15L));
            boolean result = origin.equals(interval);
            assertThat(result).isFalse();
        }

        /**
         * <pre>
         *                    |-----origin-----|
         * |----interval----|
         * </pre>
         */
        @Test
        public void test_equals_intervalTo_beforeOriginFrom_shouldBeFalse() {
            LocalTimeInterval interval = new LocalTimeInterval(originFrom.minusMinutes(15L), originFrom.minusMinutes(1L));
            boolean result = origin.equals(interval);
            assertThat(result).isFalse();
        }

        /**
         * <pre>
         *                  |-----origin-----|
         * |----interval----|
         * </pre>
         */
        @Test
        public void test_equals_intervalTo_equalOriginFrom_shouldBeFalse() {
            LocalTimeInterval interval = new LocalTimeInterval(originFrom.minusMinutes(15L), originFrom);
            boolean result = origin.equals(interval);
            assertThat(result).isFalse();
        }

        /**
         * <pre>
         *           |-----origin-----|
         * |----interval----|
         * </pre>
         */
        @Test
        public void test_equals_intervalTo_betweenOrigin_shouldBeFalse() {
            LocalTimeInterval interval = new LocalTimeInterval(originFrom.minusMinutes(15L), originTo.minusMinutes(15L));
            boolean result = origin.equals(interval);
            assertThat(result).isFalse();
        }

        /**
         * <pre>
         * |-----origin-----|
         *         |----interval----|
         * </pre>
         */
        @Test
        public void test_equals_intervalFrom_betweenOrigin_shouldBeFalse() {
            LocalTimeInterval interval = new LocalTimeInterval(originFrom.plusMinutes(15L), originTo.plusMinutes(15L));
            boolean result = origin.equals(interval);
            assertThat(result).isFalse();
        }

        /**
         * <pre>
         * |-----origin-----|
         *                  |----interval----|
         * </pre>
         */
        @Test
        public void test_equals_intervalFrom_equalOriginTo_shouldBeFalse() {
            LocalTimeInterval interval = new LocalTimeInterval(originTo, originTo.plusMinutes(15L));
            boolean result = origin.equals(interval);
            assertThat(result).isFalse();
        }

        /**
         * <pre>
         * |-----origin-----|
         *                    |----interval----|
         * </pre>
         */
        @Test
        public void test_equals_intervalFrom_afterOriginTo_shouldBeFalse() {
            LocalTimeInterval interval = new LocalTimeInterval(originTo.plusMinutes(1L), originTo.plusMinutes(15L));
            boolean result = origin.equals(interval);
            assertThat(result).isFalse();
        }
    }

    @Nested
    class OverlapsAsOpen {

        /**
         * <pre>
         * |-----origin-----|
         * |----interval----|
         * </pre>
         */
        @Test
        public void test_overlapsAsOpen_equal_shouldBeTrue() {
            LocalTimeInterval interval = new LocalTimeInterval(originFrom, originTo);
            boolean result = origin.overlapsAsOpen(interval);
            assertThat(result).isTrue();
        }

        /**
         * <pre>
         *     |-----origin-----|
         * |--------interval--------|
         * </pre>
         */
        @Test
        public void test_overlapsAsOpen_interval_containsOrigin_shouldBeTrue() {
            LocalTimeInterval interval = new LocalTimeInterval(originFrom.minusMinutes(15L), originTo.minusMinutes(15L));
            boolean result = origin.overlapsAsOpen(interval);
            assertThat(result).isTrue();
        }

        /**
         * <pre>
         * |----------origin----------|
         *     |-----interval-----|
         * </pre>
         */
        @Test
        public void test_overlapsAsOpen_origin_containsInterval_shouldBeTrue() {
            LocalTimeInterval interval = new LocalTimeInterval(originFrom.minusMinutes(15L), originTo.minusMinutes(15L));
            boolean result = origin.overlapsAsOpen(interval);
            assertThat(result).isTrue();
        }

        /**
         * <pre>
         *                    |-----origin-----|
         * |----interval----|
         * </pre>
         */
        @Test
        public void test_overlapsAsOpen_intervalTo_beforeOriginFrom_shouldBeFalse() {
            LocalTimeInterval interval = new LocalTimeInterval(originFrom.minusMinutes(15L), originFrom.minusMinutes(1L));
            boolean result = origin.overlapsAsOpen(interval);
            assertThat(result).isFalse();
        }

        /**
         * <pre>
         *                  |-----origin-----|
         * |----interval----|
         * </pre>
         */
        @Test
        public void test_overlapsAsOpen_intervalTo_equalOriginFrom_shouldBeFalse() {
            LocalTimeInterval interval = new LocalTimeInterval(originFrom.minusMinutes(15L), originFrom);
            boolean result = origin.overlapsAsOpen(interval);
            assertThat(result).isFalse();
        }

        /**
         * <pre>
         *           |-----origin-----|
         * |----interval----|
         * </pre>
         */
        @Test
        public void test_overlapsAsOpen_intervalTo_betweenOrigin_shouldBeTrue() {
            LocalTimeInterval interval = new LocalTimeInterval(originFrom.minusMinutes(15L), originTo.minusMinutes(15L));
            boolean result = origin.overlapsAsOpen(interval);
            assertThat(result).isTrue();
        }

        /**
         * <pre>
         * |-----origin-----|
         *         |----interval----|
         * </pre>
         */
        @Test
        public void test_overlapsAsOpen_intervalFrom_betweenOrigin_shouldBeTrue() {
            LocalTimeInterval interval = new LocalTimeInterval(originFrom.plusMinutes(15L), originTo.plusMinutes(15L));
            boolean result = origin.overlapsAsOpen(interval);
            assertThat(result).isTrue();
        }

        /**
         * <pre>
         * |-----origin-----|
         *                  |----interval----|
         * </pre>
         */
        @Test
        public void test_overlapsAsOpen_intervalFrom_equalOriginTo_shouldBeFalse() {
            LocalTimeInterval interval = new LocalTimeInterval(originTo, originTo.plusMinutes(15L));
            boolean result = origin.overlapsAsOpen(interval);
            assertThat(result).isFalse();
        }

        /**
         * <pre>
         * |-----origin-----|
         *                    |----interval----|
         * </pre>
         */
        @Test
        public void test_overlapsAsOpen_intervalFrom_afterOriginTo_shouldBeFalse() {
            LocalTimeInterval interval = new LocalTimeInterval(originTo.plusMinutes(1L), originTo.plusMinutes(15L));
            boolean result = origin.overlapsAsOpen(interval);
            assertThat(result).isFalse();
        }
    }

    @Nested
    class OverlapsAsClosed {
        /**
         * <pre>
         * |-----origin-----|
         * |----interval----|
         * </pre>
         */
        @Test
        public void test_overlapsAsClosed_equal_shouldBeTrue() {
            LocalTimeInterval interval = new LocalTimeInterval(originFrom, originTo);
            boolean result = origin.overlapsAsClosed(interval);
            assertThat(result).isTrue();
        }

        /**
         * <pre>
         *     |-----origin-----|
         * |--------interval--------|
         * </pre>
         */
        @Test
        public void test_overlapsAsClosed_interval_containsOrigin_shouldBeTrue() {
            LocalTimeInterval interval = new LocalTimeInterval(originFrom.minusMinutes(15L), originTo.minusMinutes(15L));
            boolean result = origin.overlapsAsClosed(interval);
            assertThat(result).isTrue();
        }

        /**
         * <pre>
         * |----------origin----------|
         *     |-----interval-----|
         * </pre>
         */
        @Test
        public void test_overlapsAsClosed_origin_containsInterval_shouldBeTrue() {
            LocalTimeInterval interval = new LocalTimeInterval(originFrom.minusMinutes(15L), originTo.minusMinutes(15L));
            boolean result = origin.overlapsAsClosed(interval);
            assertThat(result).isTrue();
        }

        /**
         * <pre>
         *                    |-----origin-----|
         * |----interval----|
         * </pre>
         */
        @Test
        public void test_overlapsAsClosed_intervalTo_beforeOriginFrom_shouldBeFalse() {
            LocalTimeInterval interval = new LocalTimeInterval(originFrom.minusMinutes(15L), originFrom.minusMinutes(1L));
            boolean result = origin.overlapsAsClosed(interval);
            assertThat(result).isFalse();
        }

        /**
         * <pre>
         *                  |-----origin-----|
         * |----interval----|
         * </pre>
         */
        @Test
        public void test_overlapsAsClosed_intervalTo_equalOriginFrom_shouldBeTrue() {
            LocalTimeInterval interval = new LocalTimeInterval(originFrom.minusMinutes(15L), originFrom);
            boolean result = origin.overlapsAsClosed(interval);
            assertThat(result).isTrue();
        }

        /**
         * <pre>
         *           |-----origin-----|
         * |----interval----|
         * </pre>
         */
        @Test
        public void test_overlapsAsClosed_intervalTo_betweenOrigin_shouldBeTrue() {
            LocalTimeInterval interval = new LocalTimeInterval(originFrom.minusMinutes(15L), originTo.minusMinutes(15L));
            boolean result = origin.overlapsAsClosed(interval);
            assertThat(result).isTrue();
        }

        /**
         * <pre>
         * |-----origin-----|
         *         |----interval----|
         * </pre>
         */
        @Test
        public void test_overlapsAsClosed_intervalFrom_betweenOrigin_shouldBeTrue() {
            LocalTimeInterval interval = new LocalTimeInterval(originFrom.plusMinutes(15L), originTo.plusMinutes(15L));
            boolean result = origin.overlapsAsClosed(interval);
            assertThat(result).isTrue();
        }

        /**
         * <pre>
         * |-----origin-----|
         *                  |----interval----|
         * </pre>
         */
        @Test
        public void test_overlapsAsClosed_intervalFrom_equalOriginTo_shouldBeTrue() {
            LocalTimeInterval interval = new LocalTimeInterval(originTo, originTo.plusMinutes(15L));
            boolean result = origin.overlapsAsClosed(interval);
            assertThat(result).isTrue();
        }

        /**
         * <pre>
         * |-----origin-----|
         *                    |----interval----|
         * </pre>
         */
        @Test
        public void test_overlapsAsClosed_intervalFrom_afterOriginTo_shouldBeFalse() {
            LocalTimeInterval interval = new LocalTimeInterval(originTo.plusMinutes(1L), originTo.plusMinutes(15L));
            boolean result = origin.overlapsAsClosed(interval);
            assertThat(result).isFalse();
        }
    }
}
