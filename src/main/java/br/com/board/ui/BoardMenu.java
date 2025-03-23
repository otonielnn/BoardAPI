package br.com.board.ui;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import br.com.board.dto.BoardDetailsDTO;
import br.com.board.persistence.entity.BoardColumnEntity;
import br.com.board.persistence.entity.BoardEntity;
import br.com.board.service.BoardColumnQueryService;
import br.com.board.service.BoardQueryService;
import lombok.AllArgsConstructor;
import static br.com.board.persistence.config.ConnectionConfig.getConnection;

@AllArgsConstructor
public class BoardMenu {
    private final BoardEntity entity;
    private Scanner scanner = new Scanner(System.in);

    public void execute() throws SQLException{
        try {
        System.out.printf("Bem vindo ao ao board %s, selecione a operação desejada\n", entity.getId());

        int option = -1;
        
        while (option != 9) {
            System.out.println("1 - Criar um card");
            System.out.println("2 - Mover um card");
            System.out.println("3 - Bloquear um card");
            System.out.println("4 - Desbloquear um card");
            System.out.println("5 - Cancelar um card");
            System.out.println("6 - Visualizar colunas");
            System.out.println("7 - Visualizar colunas com cards");
            System.out.println("8 - Visualizar card");
            System.out.println("6 - Voltar para o menu anterior");
            System.out.println("9 - Sair");

            option = scanner.nextInt();
            
            switch (option) {
                case 1 -> createCard();
                case 2 -> moveCardToNextColumn();
                case 3 -> blockCard();
                case 4 -> unblockCard();
                case 5 -> cancelCard();
                case 6 -> showBoard();
                case 7 -> showColumn();
                case 8 -> showCard();
                case 9 -> System.out.println("Voltando para o menu anterior");
                case 10 -> System.exit(0);
                default -> System.out.println("Opção inválida, informe uma opção do menu.");
            }
        }
    } catch(SQLException e) {
        e.printStackTrace();
        System.exit(0);
    }

}

    private void createCard() {
    }

    private void moveCardToNextColumn() {
    }

    private void blockCard() {
    }

    private void unblockCard() {
    }

    private void cancelCard() {
    }

    private void showBoard() throws SQLException {
        try (Connection connection = getConnection()) {
            Optional<BoardDetailsDTO> optional = new BoardQueryService(connection).showBoardDetails(entity.getId());
            optional.ifPresent(b -> {
                System.out.printf("Board [%s,%s]\n", b.id(), b.name());
                b.columns().forEach(c -> {
                    System.out.printf("Coluna [%s] tipo: [%s] tem %s cards\n", c.name(), c.kind(), c.cardsAmount());
                });
            });
        }
    }

    private void showColumn() throws SQLException{
        System.out.printf("Escolha uma coluna do board %s\n", entity.getName());
        List<Long> columnsIds = entity.getBoardcolumns().stream().map(BoardColumnEntity::getId).toList();
        Long selectedColumn = -1L;
        while (!columnsIds.contains(selectedColumn)) {
            System.out.printf("Escolha uma coluna do board %s\n", entity.getName());
            entity.getBoardcolumns().forEach(c -> System.out.printf("%s - %s [%s]\n", c.getId(), c.getName(), c.getKind()));
            selectedColumn = scanner.nextLong();
        }
        try (Connection connection = getConnection()) {
            Optional<BoardColumnEntity> column = new BoardColumnQueryService(connection).findById(selectedColumn);
            column.ifPresent(co -> {
                System.out.printf("Coluna %s tipo %s\n", co.getName(), co.getKind());
                co.getCards().forEach(ca -> System.out.printf("Card %s - %s.\nDescrição: %s",
                 ca.getId(), ca.getTitle(), ca.getDescription()));
            });
        }
    }

    private void showCard() {
    }
}
