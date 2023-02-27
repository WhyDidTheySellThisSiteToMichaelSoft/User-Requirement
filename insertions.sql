INSERT INTO investidor (
  id_investidor,
  nome,
  email,
  senha,
  nivacesso,
  telefone
) VALUES
(0, 'Paulo', 'paulo@gmail.com', 'senha_forte', 0, '(49)-2637-3578'),
(1, 'Alice', 'alice@gmail.com', 'aniversario', 1, '(49)-6825-1035'),
(2, 'Diego', 'diego@gmail.com', '1234', 1, '(49)-3524-9753'),
(3,'Leonardo','leo@gmail.com', 'esposa', 0,'(49)-9975-5532'),
(4,'Joel','joel@gmail.com', 'marca de carro', 0,'(49)-0863-3513'),
(5,'Luciana','luciana@hotmaill.com', 'serie de tv', 0,'(49)-8779-4133'),
(6,'Mushi','mushi@gmail.com', 'site', 0,'(49)-9752-3414'),
(7,'Shirotomo','Shirotomo@gmail.com', 'ator', 0,'(49)-5821-7532'),
(8,'Alexandre','alexandria@hotmaill.com', 'fato historico', 0,'(49)-5721-8643'),
(9,'Fabio','fabio@gmail.com', 'nome do filho', 1,'(49)-1259-1353');


INSERT INTO investimento (
  id,
  nome,
  descr,
  cnpj,
  vinit,
  vatual,
  cidade
) VALUES 
(0, 'Louvre', 'A mais alta classe de apartamentos', '46.226.627/0001-66', 5000000, 10000000, 'Chapeco'),
(1, 'Panteão', 'Uma das maiores metragens da região e vista ao mar', '61.065.846/0001-02', 6000000, 9000000, 'Florianópolis'),
(2, 'Campo Grama Verde', 'reosrt esportivo para toda a famila', '32.883.573/0001-05', 15000000, 17000000, 'Cascavel'),
(3, 'Mercados Curral', '30 anos de rede no Rio Grande', '97.716.884/0001-17', 5000000, 6000000, 'Porto Alegre'),
(4, 'Casas Marcel', 'O melhor valor de mobilia', '46.757.627/0001-92', 400000, 700000, 'Blumenau'),
(5, 'filler', 'filler', 'filler5', 0, 0, 'filler'),
(6, 'filler', 'filler', 'filler6', 0, 0, 'filler'),
(7, 'filler', 'filler', 'filler7', 0, 0, 'filler'),
(8, 'filler', 'filler', 'filler8', 0, 0, 'filler'),
(9, 'filler', 'filler', 'filler9', 0, 0, 'filler');


INSERT INTO oportunidade (
  id,
  nome,
  descr
) VALUES
(0,'Moradias com painéis solares','Painéis solares aumentam o valor de um casa em 8% á um custo fixo.'),
(1, 'Pet Beach House', 'Pet House de alto trafico em Balneário, valor de aquisição (600 mil) abaixo do projetado em 5 anos.'),
(2, 'Shopping Fernandez Madeiro ', 'Grupo responsavel interessados na expansão do complexo de lojas.'),
(3,'Madeireira Acopollos', 'Liquidação de assets após falencia'),
(4, 'Distribuidora Cara Preta', 'Familia buscando financiamento para expansão em Curitiba'),
(5, 'filler', 'filler'),
(6, 'filler', 'filler'),
(7, 'filler', 'filler'),
(8, 'filler', 'filler'),
(9, 'filler', 'filler');
;

INSERT INTO participacao (
  id_investidor,
  id_investimento,
  vpinit,
  vpago,
  qnt_participacao
) VALUES
(0, 0, 700000, 500000, 15),
(3, 0, 400000, 350000, 8),
(7, 0, 2000000, 1300000, 40),
(9, 0, 1900000, 130000, 37),
(2, 1, 3000000, 700000, 50),
(6, 1, 3000000, 1300000, 50),
(8, 3, 4000000, 2900000, 30),
(4, 3, 1000000, 400000, 10),
(5, 3, 3000000, 1200000, 20),
(1, 3, 9000000, 4000000, 40);

INSERT INTO interesse (
  id_investidor,
  id_oportunidade
) VALUES
(1,0),
(2,0),
(1,4),
(5,2),
(3,4),
(1,1),
(4,3),
(2,4),
(2,1),
(5,3);
