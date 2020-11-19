package com.example.core.temporal;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class LocalDateTimeIntervalTest {
    private final LocalDateTime originFrom = LocalDateTime.now();
    private final LocalDateTime originTo = originFrom.plusDays(30L);
    private final LocalDateTimeInterval origin = new LocalDateTimeInterval(originFrom, originTo);

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
            boolean result = origin.contains(originFrom.plusDays(15L));
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
            boolean result = origin.contains(originFrom.minusDays(1L));
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
            boolean result = origin.contains(originTo.plusDays(1L));
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
            LocalDateTimeInterval interval = new LocalDateTimeInterval(originFrom, originTo);
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
            LocalDateTimeInterval interval = new LocalDateTimeInterval(originFrom.minusDays(15L), originTo.minusDays(15L));
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
            LocalDateTimeInterval interval = new LocalDateTimeInterval(originFrom.minusDays(15L), originTo.minusDays(15L));
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
            LocalDateTimeInterval interval = new LocalDateTimeInterval(originFrom.minusDays(15L), originFrom.minusDays(1L));
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
            LocalDateTimeInterval interval = new LocalDateTimeInterval(originFrom.minusDays(15L), originFrom);
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
            LocalDateTimeInterval interval = new LocalDateTimeInterval(originFrom.minusDays(15L), originTo.minusDays(15L));
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
            LocalDateTimeInterval interval = new LocalDateTimeInterval(originFrom.plusDays(15L), originTo.plusDays(15L));
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
            LocalDateTimeInterval interval = new LocalDateTimeInterval(originTo, originTo.plusDays(15L));
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
            LocalDateTimeInterval interval = new LocalDateTimeInterval(originTo.plusDays(1L), originTo.plusDays(15L));
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
            LocalDateTimeInterval interval = new LocalDateTimeInterval(originFrom, originTo);
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
            LocalDateTimeInterval interval = new LocalDateTimeInterval(originFrom.minusDays(15L), originTo.minusDays(15L));
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
            LocalDateTimeInterval interval = new LocalDateTimeInterval(originFrom.minusDays(15L), originTo.minusDays(15L));
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
            LocalDateTimeInterval interval = new LocalDateTimeInterval(originFrom.minusDays(15L), originFrom.minusDays(1L));
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
            LocalDateTimeInterval interval = new LocalDateTimeInterval(originFrom.minusDays(15L), originFrom);
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
            LocalDateTimeInterval interval = new LocalDateTimeInterval(originFrom.minusDays(15L), originTo.minusDays(15L));
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
            LocalDateTimeInterval interval = new LocalDateTimeInterval(originFrom.plusDays(15L), originTo.plusDays(15L));
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
            LocalDateTimeInterval interval = new LocalDateTimeInterval(originTo, originTo.plusDays(15L));
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
            LocalDateTimeInterval interval = new LocalDateTimeInterval(originTo.plusDays(1L), originTo.plusDays(15L));
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
            LocalDateTimeInterval interval = new LocalDateTimeInterval(originFrom, originTo);
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
            LocalDateTimeInterval interval = new LocalDateTimeInterval(originFrom.minusDays(15L), originTo.minusDays(15L));
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
            LocalDateTimeInterval interval = new LocalDateTimeInterval(originFrom.minusDays(15L), originTo.minusDays(15L));
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
            LocalDateTimeInterval interval = new LocalDateTimeInterval(originFrom.minusDays(15L), originFrom.minusDays(1L));
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
            LocalDateTimeInterval interval = new LocalDateTimeInterval(originFrom.minusDays(15L), originFrom);
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
            LocalDateTimeInterval interval = new LocalDateTimeInterval(originFrom.minusDays(15L), originTo.minusDays(15L));
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
            LocalDateTimeInterval interval = new LocalDateTimeInterval(originFrom.plusDays(15L), originTo.plusDays(15L));
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
            LocalDateTimeInterval interval = new LocalDateTimeInterval(originTo, originTo.plusDays(15L));
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
            LocalDateTimeInterval interval = new LocalDateTimeInterval(originTo.plusDays(1L), originTo.plusDays(15L));
            boolean result = origin.overlapsAsClosed(interval);
            assertThat(result).isFalse();
        }
    }
}
