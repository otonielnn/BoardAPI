package br.com.board.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import br.com.board.dto.CardDetailsDTO;
import br.com.board.persistence.dao.CardDAO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CardQueryService {
    
    private final Connection connection;

    public Optional<CardDetailsDTO> findById(final Long id) throws SQLException {
        CardDAO dao = new CardDAO(connection);
        return dao.findById(id);
    }
}
