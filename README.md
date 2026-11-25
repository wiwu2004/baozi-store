# Baozi Store

API REST acadêmica para cadastrar e consultar clientes, produtos e pedidos.

## Executar

Use o JDK 21. No Windows, abra a pasta que contém o `pom.xml` no IntelliJ, carregue o Maven e execute `BaoziStoreApplication`. Também é possível executar no PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

A API fica em `http://localhost:8080`. O banco H2 em memória é iniciado automaticamente e os dados são limpos a cada reinício.

## Endpoints

| Operação | Clientes | Produtos | Pedidos | Sucesso |
| --- | --- | --- | --- | --- |
| Cadastrar (POST) | /clientes | /produtos | /pedidos | 201 |
| Listar (GET) | /clientes | /produtos | /pedidos | 200 |
| Consultar (GET) | /clientes/{id} | /produtos/{id} | /pedidos/{id} | 200 |
| Excluir (DELETE) | /clientes/{id} | /produtos/{id} | /pedidos/{id} | 204 |

Um ID inexistente retorna 404 na consulta ou exclusão. Excluir um cliente ou produto associado a um pedido retorna 409. Exclua o pedido primeiro.

## Exemplos de cadastro

```json
{"nome":"Cliente de exemplo","clienteDesde":"2026-11-25"}
```

```json
{"nome":"Baozi de legumes","preco":8.50,"estoque":true}
```

```json
{"clienteId":1,"produtoId":1,"quantidade":3}
```

Use os IDs devolvidos pelos cadastros de cliente e produto. Pedido com quantidade inválida, cliente ou produto inexistente ou produto indisponível retorna 400.

## Organização

Os controllers tratam HTTP. Os services executam as operações e validam pedidos. Os repositories fornecem acesso ao H2 com Spring Data JPA. O pacote model contém as entidades e os relacionamentos.
