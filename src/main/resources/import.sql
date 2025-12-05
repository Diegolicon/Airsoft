

------------------------------------------------------------------------------------------------------------------------

-- 2. USUARIOS (ACESSOS)
-- Usuário 1 (ADM) e Usuário 2 (USER)
-- A senha '123456' está aqui representada por um HASH de exemplo.
INSERT INTO Usuario (ID, NOME, LOGIN, SENHA, PERFIL)
VALUES (101, 'Administrador Master', 'admin', '$2a$10$ExemploDeSenhaHashedAdmin123', 1);

INSERT INTO Usuario (ID, NOME, LOGIN, SENHA, PERFIL)
VALUES (102, 'Cliente Comum', 'usuario', '$2a$10$ExemploDeSenhaHashedUser4567', 2);

------------------------------------------------------------------------------------------------------------------------

-- 3. PRODUTOS
INSERT INTO Produto (ID, NOME, FABRICANTE, TIPO, SISTEMA, MATERIAL, PRECO, ESTOQUE)
VALUES (101, 'Pistola Tática X', 'Glock', 'pistola', 'semi-automatico', 'polimero', 1500.00, 50);

INSERT INTO Produto (ID, NOME, FABRICANTE, TIPO, SISTEMA, MATERIAL, PRECO, ESTOQUE)
VALUES (102, 'Fuzil de Assalto Alfa', 'Colt', 'fuzil', 'automatico', 'metal', 4500.50, 20);

INSERT INTO Produto (ID, NOME, FABRICANTE, TIPO, SISTEMA, MATERIAL, PRECO, ESTOQUE)
VALUES (103, 'Carabina de Pressão 3.0', 'CBC', 'carabina', 'spring', 'madeira', 850.99, 100);

------------------------------------------------------------------------------------------------------------------------

-- 4. PESSOAS / CLIENTES (TABLE_PER_CLASS)

-- Pessoa Física (ID 100)
INSERT INTO PessoaFisica (ID, EMAIL, NOME, CPF, DATANASCIMENTO)
VALUES (100, 'joao.silva@email.com', 'João da Silva', '12345678900', '1985-10-20');

-- Pessoa Jurídica (ID 101)
INSERT INTO PessoaJuridica (ID, EMAIL, RAZAOSOCIAL, CNPJ)
VALUES (101, 'techcorp@email.com', 'Tech Corp Soluções Ltda', '00112233000145');

------------------------------------------------------------------------------------------------------------------------

-- 5. TELEFONES (FK: PESSOA_ID)
-- Telefone para João da Silva (ID 100)
INSERT INTO Telefone (ID, DDD, NUMERO, TIPO, PESSOA_ID)
VALUES (101, '63', '988887777', 'CELULAR', 100);

-- Telefone para Tech Corp (ID 101)
INSERT INTO Telefone (ID, DDD, NUMERO, TIPO, PESSOA_ID)
VALUES (102, '11', '30304040', 'COMERCIAL', 101);

------------------------------------------------------------------------------------------------------------------------

-- 6. ENDEREÇOS (FK: PESSOA_ID)
-- Endereço para João da Silva (ID 100)
INSERT INTO Endereco (ID, CEP, LOGRADOURO, NUMERO, COMPLEMENTO, BAIRRO, CIDADE, ESTADO, PESSOA_ID)
VALUES (101, '77000000', 'Quadra 104 Sul', '15', 'AP 101', 'Plano Diretor Sul', 'Palmas', 'TO', 100);

------------------------------------------------------------------------------------------------------------------------

-- 7. PEDIDOS (FK: PESSOA_ID, USUARIO_ID)
-- Pedido 1: Feito por João (ID Pessoa 100), efetuado pelo Usuario (ID 2)
INSERT INTO Pedido (ID, DATAPEDIDO, STATUS, PESSOA_ID, USUARIO_ID)
VALUES (101, '2025-12-02', 'PROCESSANDO', 100, 102);

-- Pedido 2: Feito por Tech Corp (ID Pessoa 101), efetuado pelo Admin (ID 1)
INSERT INTO Pedido (ID, DATAPEDIDO, STATUS, PESSOA_ID, USUARIO_ID)
VALUES (102, '2025-11-20', 'ENVIADO', 101, 101);

------------------------------------------------------------------------------------------------------------------------

-- 8. ITENS DO PEDIDO (FK: PEDIDO_ID, PRODUTO_ID)

-- Itens para Pedido 1
-- Item 1: 2 unidades da Pistola X (ID Produto 1) pelo preço salvo de 1500.00
INSERT INTO ItemPedido (ID, QUANTIDADE, PRECO, PEDIDO_ID, PRODUTO_ID)
VALUES (101, 2, 1500.00, 101, 101);
-- Item 2: 1 unidade do Fuzil Alfa (ID Produto 2) pelo preço salvo de 4500.50
INSERT INTO ItemPedido (ID, QUANTIDADE, PRECO, PEDIDO_ID, PRODUTO_ID)
VALUES (102, 1, 4500.50, 101, 102);

-- Item para Pedido 2
-- Item 3: 5 unidades da Carabina (ID Produto 3)
INSERT INTO ItemPedido (ID, QUANTIDADE, PRECO, PEDIDO_ID, PRODUTO_ID)
VALUES (103, 5, 850.99, 102, 103);