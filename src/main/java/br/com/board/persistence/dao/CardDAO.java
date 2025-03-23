package br.com.board.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import br.com.board.dto.CardDetailsDTO;
import br.com.board.converter.OffsetDateTimeConverter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CardDAO {
    
    private Connection connection;

    public Optional<CardDetailsDTO> findById(final Long id) throws SQLException{
        String sql = """
                SELECT c.id,
                       c.title,
                       c.description,
                       c.blocked_at,
                       c.block_reason,
                       c.board_column_id,
                       bc.name,
                       COUNT(SELECT sub_b.id
                             FROM BLOCKS sub_b
                             WHERE sub_b.card_id = c.id) blocks_amount
                FROM CARDS c
                LEFT JOIN BLOCKS b
                    ON c.id = b.card_id
                    AND b.unblocked_at IS NOT NULL
                INNER JOIN BOARD_COLUMNS bc
                    ON bc.id = c.board_column_id
                WHERE c.id = ?
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeQuery();
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                CardDetailsDTO dto = new CardDetailsDTO(
                    resultSet.getLong("c.id"),
                    resultSet.getString("c.title"),
                    resultSet.getString("c.description"),
                    resultSet.getString("b.block_reason").isEmpty(),
                    OffsetDateTimeConverter.toOffsetDateTime(resultSet.getTimestamp("b.blocked_at")),
                    resultSet.getString("b.block_reason"),
                    resultSet.getInt("blocks_amount"),
                    resultSet.getLong("c.board_column_id"),
                    resultSet.getString("bc.name")
                );
            }
        }
        return null;
    }
}
