package br.com.board.service;

import java.sql.Connection;
import java.sql.SQLException;

import br.com.board.persistence.entity.CardEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CardService {
    
    private final Connection connection;

    public CardEntity insert(final CardEntity entity) throws SQLException {
        try  {
            connection.commit();
            return entity;
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        }
    }
}
