
insert into endereco (logradouro, numero, complemento, bairro, cidade, estado, cep)
values ('Rua 1', '123', 'Apto 101', 'Centro', 'Palmas', 'TO', '77000000'),
       ('Rua 2', '456', 'Bloco B', 'Setor Oeste', 'Araguaina', 'TO', '77800000'),
       ('Avenida JK', '789', 'Sala 5', 'Plano Diretor Sul', 'Palmas', 'TO', '77015000'),
       ('Rua das Acácias', '321', 'Casa 2', 'Jardim América', 'Gurupi', 'TO', '77410000');

INSERT INTO produto (nome, fabricante, tipo, sistema, material, preco)
VALUES ('AK-47', 'Izmash', 'Fuzil', 'Automatico', 'Aço', 1200.00),
       ('M4A1', 'Colt', 'Fuzil', 'Automatico', 'Aço', 1500.00);


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


insert into pessoafisica (id, cpf, nascimento)
values (1, '11111111111', '2005-03-09'),
       (2, '22222222222', '2003-09-10');

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
       (3000.00, 'a caminho', 2, '2x M4A1'),
       (2250.00, 'a caminho', 1, '1x M4A1, 1x Luneta Lpvo 1-6x24'),
       (210.00, 'entregue', 2, '2x Pente 30 Rounds 7.62, 1x Bipé Tático');

INSERT INTO acessorio (nome, fabricante, tipo, material, preco, compatibilidade)
VALUES ('Red Dot Sight', 'EOTech', 'Mira', 'Alumínio', 450.00, 'Picatinny Rail'),
       ('Suppressor 5.56', 'SureFire', 'Supressor', 'Titânio', 600.00, 'Rosca M27x1'),
       ('Lpvo 1-6x24', 'Vortex', 'Luneta', 'Alumínio', 750.00, 'Montagem 30mm'),
       ('Bipé Tático', 'Magpul', 'Bipé', 'Polímero', 120.00, 'M-LOK / Picatinny'),
       ('Pente 30 Rounds 7.62', 'Magpul', 'Pente', 'Polímero', 45.00, 'AK-47'),
       ('Carregador de Bateria', 'Hera Arms', 'Carregador', 'Plástico', 80.00, 'Todos os modelos de Li-Po'),
       ('Kit Conversão .22LR', 'CMMG', 'Kit Conversao', 'Aço / Polímero', 350.00, 'Plataforma AR-15'),
       ('Bandoleira 2 Pontas', 'Blue Force Gear', 'Bandoleira', 'Nylon', 55.00, 'Todos os Rifles');
