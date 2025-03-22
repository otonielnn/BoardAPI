package br.com.board.ui;

import br.com.board.persistence.entity.BoardEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BoardMenu {
    private final BoardEntity entity;

    public void execute() {}
}
