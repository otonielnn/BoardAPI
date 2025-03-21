package br.com.board.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import br.com.board.persistence.dao.BoardColumnDAO;
import br.com.board.persistence.dao.BoardDAO;
import br.com.board.persistence.entity.BoardEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BoardQueryService {
    
    private final Connection connection;

    public Optional<BoardEntity> findById(final Long id) throws SQLException {
        BoardDAO dao = new BoardDAO(connection);
        BoardColumnDAO boardColumnDAO = new BoardColumnDAO(connection);
        Optional<BoardEntity> optional = dao.findById(id);
        if (optional.isPresent()) {
            BoardEntity entity = optional.get();
            entity.setBoardcolumns(boardColumnDAO.findByBoardId(entity.getId()));
            return Optional.of(entity);
        }
        return Optional.empty();
    }
}
