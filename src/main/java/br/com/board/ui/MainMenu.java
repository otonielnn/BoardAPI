package br.com.board.ui;

import static br.com.board.persistence.config.ConnectionConfig.getConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import br.com.board.persistence.entity.BoardColumnEntity;
import br.com.board.persistence.entity.BoardColumnKindEnum;
import br.com.board.persistence.entity.BoardEntity;
import br.com.board.service.BoardQueryService;
import br.com.board.service.BoardService;

public class MainMenu {
    
    private final Scanner scanner = new Scanner(System.in);

    public void execute() throws SQLException{
        System.out.println("Seja bem-vindo ao Gerenciador de Board, escolha a opção desejada:");
        int option = -1;
        
        while (true) {
            System.out.println("1 - Criar novo Board");
            System.out.println("2 - Selecionar um board existente");
            System.out.println("3 - Excluir um Board");
            System.out.println("4 - Sair");

            option = scanner.nextInt();
            
            switch (option) {
                case 1 -> createBoard();
                case 2 -> selectBoard();
                case 3 -> deleteBoard();
                case 4 -> System.exit(0);
                default -> System.out.println("Opção inválida, informe uma opção do menu.");
            }
        }
    }

    private void createBoard() throws SQLException{
        var entity = new BoardEntity();
        System.out.println("Informe o nome do seu Board:");
        entity.setName(scanner.next());

        System.out.println("Seu board terá colunas além das 3 padrões? Se sim informe quantas, senão digite 0:");
        int additionalColumns = scanner.nextInt();

        List<BoardColumnEntity> columns = new ArrayList<>();

        System.out.println("Informe o nome da coluna inicial do board: ");
        String initialColumnName = scanner.next();
        BoardColumnEntity initialColumn = createColumn(initialColumnName, BoardColumnKindEnum.INITIAL, 0);
        columns.add(initialColumn);

        for (int i = 0; i < additionalColumns; i++) {
            System.out.println("Informe o nome da coluna de tarefa pendente: ");
            String pendingColumnName = scanner.next();
            BoardColumnEntity pendingColumn = createColumn(pendingColumnName, BoardColumnKindEnum.PENDING, i + 1);
            columns.add(pendingColumn);
        }

        System.out.println("Informe o nome da coluna final: ");
        String finalColumnName = scanner.next();
        BoardColumnEntity finalColumn = createColumn(finalColumnName, BoardColumnKindEnum.FINAL, additionalColumns + 1);
        columns.add(finalColumn);

        System.out.println("Informe o nome da coluna de cancelamento do board: ");
        String cancelColumnName = scanner.next();
        BoardColumnEntity cancelColumn = createColumn(cancelColumnName, BoardColumnKindEnum.CANCEL, additionalColumns + 1);
        columns.add(cancelColumn);

        entity.setBoardcolumns(columns);
        try (Connection connection = getConnection()) {
            BoardService service = new BoardService(connection);
            service.insert(entity);
        }
    };

    private void selectBoard() throws SQLException{
        System.out.println("Informe o id do board que deseja selecionar: ");
        Long id = scanner.nextLong();
        try (Connection connection = getConnection()) {
            BoardQueryService queryService = new BoardQueryService(connection);
            Optional<BoardEntity> optional = queryService.findById(id);
            optional.ifPresentOrElse(
                b -> new BoardMenu(optional.get()),
                () -> System.out.printf("Não foi encontrado o board com id %s\n", id)
                );
        }
    };

    private void deleteBoard() throws SQLException{
        System.out.println("Informe o ID do Board que deseja excluir:");
        Long id = scanner.nextLong();
        try (Connection connection = getConnection()){
            BoardService service = new BoardService(connection);
            service.delete(id);
            if (service.delete(id)) {
                System.out.printf("O Board %s foi excluido\n", id);
            } else {
                System.out.printf("Não foi encontrado o board com id %s\n", id);
            }
        }
    };
    
    private BoardColumnEntity createColumn(final String name, final BoardColumnKindEnum kind, final int order) {
        BoardColumnEntity boardColumn = new BoardColumnEntity();
        boardColumn.setName(name);
        boardColumn.setKind(kind);
        boardColumn.setOrder(order);
        return boardColumn;
    }
}
