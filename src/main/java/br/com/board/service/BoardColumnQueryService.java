package br.com.board.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import br.com.board.persistence.dao.BoardColumnDAO;
import br.com.board.persistence.entity.BoardColumnEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BoardColumnQueryService {
    
    private final Connection connection;
    
    public Optional<BoardColumnEntity> findById(final Long id) throws SQLException{
        BoardColumnDAO dao = new BoardColumnDAO(connection);
        return dao.findById(id);
    }
}
