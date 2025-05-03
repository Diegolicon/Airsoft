-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(1, 'field-1');
-- insert into myentity (id, field) values(2, 'field-2');
-- insert into myentity (id, field) values(3, 'field-3');
-- alter sequence myentity_seq restart with 4;

insert into endereco (logradouro, numero, complemento, bairro, cidade, estado, cep)
values ('Rua 1', '123', 'Apto 101', 'Centro', 'Palmas', 'TO', '77000000'),
       ('Rua 2', '456', 'Bloco B', 'Setor Oeste', 'Araguaina', 'TO', '77800000'),
       ('Avenida JK', '789', 'Sala 5', 'Plano Diretor Sul', 'Palmas', 'TO', '77015000'),
       ('Rua das Acácias', '321', 'Casa 2', 'Jardim América', 'Gurupi', 'TO', '77410000');

-- Inserir dados na tabela produto
INSERT INTO produto (nome, fabricante, tipo, sistema, material, preco)
VALUES ('AK-47', 'Izmash', 'Fuzil', 'Automatico', 'Aço', 1200.00),
       ('M4A1', 'Colt', 'Fuzil', 'Automatico', 'Aço', 1500.00);


-- Inserir dados na tabela telefone (relacionando com pessoas)
insert into telefone (ddd, numero)
values ('63', '99991000'), -- Ana Silva
       ('62', '88882000'), -- João Pereira
       ('71', '77773000'), -- CONECOM
       ('81', '66664000'); -- C&A

insert into pessoa (nome, email,  id_endereco, id_telefone)
values ('Ana Silva', 'ana.silva@example.com', 1,1),
       ('João Pereira', 'joao.pereira@example.com', 2,2),
       ('CONECOM', 'contato@conecom.com.br', 3,3),
       ('C&A', 'atendimento@cea.com.br', 4,4),
       ('Julianne', 'Julianne@example.com', 1, NULL),
       ('Diego', 'Diego@example.com', 2, NULL);


-- Inserir dados na tabela pessoafisica
insert into pessoafisica (id, cpf, nascimento)
values (1, '11111111111', '2005-03-09'),
       (2, '22222222222', '2003-09-10');

-- Inserir dados na tabela pessoajuridica
insert into pessoajuridica (id, cnpj)
values (3, '12345678000199'),
       (4, '98765432000188');

insert into cliente(saldo, vip, observacoes, pessoa_id)
values (2000,TRUE,'Empresa de Segurança',3),
       (750,TRUE,'Empresa de Roupa',4);

insert into pedido (valortotal, status, cliente_id, quantidade)
values (2700.00, 'a caminho', 1, '1x AK-47, 1x M4A1'),
       (1500.00, 'entregue', 2, '1x M4A1'),
       (2400.00, 'separando', 1, '2x AK-47'),
       (3000.00, 'a caminho', 2, '2x M4A1');
