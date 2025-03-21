package br.com.board.persistence.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class BoardEntity {
    private Long id;
    private String name;
    private List<BoardColumnEntity> boardcolumns = new ArrayList<>();
}
