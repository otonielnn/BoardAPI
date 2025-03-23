package br.com.board.dto;

import java.time.OffsetDateTime;

public record CardDetails(Long id,
                          boolean blocked,
                          OffsetDateTime blockedAt,
                          String blockReason,
                          int blocksAmount,
                          Long columnId,
                          String columnName) {
}
