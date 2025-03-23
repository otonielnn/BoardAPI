package br.com.board.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.mysql.cj.jdbc.StatementImpl;

import br.com.board.dto.BoardColumnDTO;
import br.com.board.persistence.entity.BoardColumnEntity;
import br.com.board.persistence.entity.BoardColumnKindEnum;
import br.com.board.persistence.entity.CardEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BoardColumnDAO {
    
    private final Connection connection;

    public BoardColumnEntity insert(final BoardColumnEntity entity) throws SQLException {
        String sql = "INSERT INTO BOARDS_COLUMNS (name, `order`, kind, board_id) VALUES (?, ?, ?, ?);";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            int i = 1;
            statement.setString(i ++, entity.getName());
            statement.setInt(i ++, entity.getOrder());
            statement.setString(i ++, entity.getKind().name());
            statement.setLong(i, entity.getBoard().getId());
            statement.executeUpdate();
            if (statement instanceof StatementImpl impl) {
                entity.setId(impl.getLastInsertID());
            }
            return entity;
        }
    }

    public List<BoardColumnEntity> findById(final Long boardId) throws SQLException {
        List<BoardColumnEntity> columns = new ArrayList<>();
        String sql = """
            SELECT bc.id,
                   bc.name,
                   bc.kind,
                   c.id AS card_id,
                   c.title AS card_title,
                   c.description AS card_description
            FROM BOARDS_COLUMNS bc
            LEFT JOIN CARDS c
                   ON c.board_column_id = bc.id
            WHERE bc.board_id = ?;
            """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, boardId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    BoardColumnEntity column = new BoardColumnEntity();
                    column.setId(resultSet.getLong("bc.id"));
                    column.setName(resultSet.getString("bc.name"));
                    column.setKind(BoardColumnKindEnum.findByName(resultSet.getString("bc.kind")));

                    // Adicionar cartões à coluna
                    CardEntity card = new CardEntity();
                    card.setId(resultSet.getLong("card_id"));
                    card.setTitle(resultSet.getString("card_title"));
                    card.setDescription(resultSet.getString("card_description"));
                    column.getCards().add(card);

                    columns.add(column);
                }
            }
        }
        return columns;
    }

    public List<BoardColumnDTO> findByBoardIdWithDetails(final Long boardId) throws SQLException{
        List<BoardColumnDTO> dtos = new ArrayList<>();
        String sql = """
        SELECT COUNT(id) from boards_columns
        SELECT bc.id,
              bc.name,
              bc.kind
              COUNT(SELECT c.id
                    FROM CARDS c
                    WHERE c.board_column_id = bc.id) cards_amount
        FROM BOARDS_COLUMNS
        WHERE board_id = ?
        ORDER BY `order`;
        """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, boardId);
            statement.executeQuery();
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                BoardColumnDTO dto = new BoardColumnDTO(
                    resultSet.getLong("bc.id"),
                    resultSet.getString("bc.name"),
                    BoardColumnKindEnum.findByName(resultSet.getString("bc.kind")),
                    resultSet.getInt("bc.cards_amount")
                );
                dtos.add(dto);
            }
        }

        return dtos;
    }
}
