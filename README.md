## Diagrama de Classes (Domínio da API)

```mermaid
classDiagram
  direction LR

  class Board {
    -Long id
    -name String
  }

  class BoardColumn {
    -Long id
    -String name
    -String kind
    -int order
  }

  class Card {
    -Long id
    -String title
    -String description
    -OffsetDateTime createdAt
  }

  class Block {
    -Long id
    -String blockCause
    -OffsetDateTime blockIn
    -String unblockCause
    -OffsetDateTime unblockIn
  }


  Board "1" *--* "N" BoardColumn
  BoardColumn "1" *--* "N" Card
  Card "N" *--* "1" Block
```
