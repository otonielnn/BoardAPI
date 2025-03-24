package br.com.board.persistence.converter;

import java.sql.Timestamp;
import java.time.OffsetDateTime;

import lombok.NoArgsConstructor;

import static java.time.ZoneOffset.UTC;
import static java.util.Objects.nonNull;
import static lombok.AccessLevel.PRIVATE;


@NoArgsConstructor(access = PRIVATE)
public final class OffsetDateTimeConverter {

    public static OffsetDateTime toOffsetDateTime(final Timestamp value) {
        return OffsetDateTime.ofInstant(value.toInstant(), UTC);
    }

    public static Timestamp toTimestamp(final OffsetDateTime value) {
        return nonNull(value) ? Timestamp.from(value.toInstant()) : null;
    }
}