package br.com.board.persistence.dao;

import java.sql.Connection;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CardDAO {
    
    private Connection connection;

    public void findById(final Long id) {
        
    }
}
