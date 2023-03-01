--create database java_app;
--\c app_investimentos
--set datestyle to 'ISO,DMY';

DROP TABLE IF EXISTS investidor CASCADE;
DROP TABLE IF EXISTS investimento CASCADE;
DROP TABLE IF EXISTS oportunidade CASCADE;
DROP TABLE IF EXISTS participacao CASCADE;
DROP TABLE IF EXISTS interesse CASCADE;


CREATE TABLE IF NOT EXISTS public.investidor (
  id_investidor serial NOT NULL,
  nome text NOT NULL,
  email text NOT NULL,
  senha text NOT NULL,
  nivacesso smallint NOT NULL,
  telefone text NOT NULL,
  CONSTRAINT investidor_pk PRIMARY KEY(id_investidor),
  CONSTRAINT email_uk UNIQUE(email),
  CONSTRAINT telefone_uk UNIQUE(telefone)
);

CREATE TABLE IF NOT EXISTS public.investimento (
  id serial NOT NULL,
  nome text NOT NULL,
  descr text NOT NULL,
  cnpj text NOT NULL,
  vinit bigint NOT NULL,
  vatual bigint NOT NULL,
  cidade text NOT NULL,
  CONSTRAINT investimento_pk PRIMARY KEY(id),
  CONSTRAINT cnpj_uk UNIQUE(cnpj)
);


CREATE TABLE IF NOT EXISTS public.oportunidade (
  id serial NOT NULL,
  nome text NOT NULL,
  descr text NOT NULL,
  CONSTRAINT oportunidade_pk PRIMARY KEY(id)
);


CREATE TABLE IF NOT EXISTS public.participacao (
  id_investidor serial NOT NULL,
  id_investimento serial NOT NULL,
  vpinit bigint NOT NULL,
  vpago bigint NOT NULL,
  qnt_participacao integer NOT NULL,
  
  CONSTRAINT participacao_pk PRIMARY KEY(id_investidor, id_investimento),
  CONSTRAINT investidor_fk FOREIGN KEY (id_investidor)
    REFERENCES investidor(id_investidor) ON DELETE CASCADE,
  CONSTRAINT investimento_fk FOREIGN KEY (id_investimento)
    REFERENCES investimento(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS public.interesse (
  id_investidor serial NOT NULL,
  id_oportunidade serial NOT NULL,

  CONSTRAINT interesse_pk PRIMARY KEY(id_investidor, id_oportunidade),
  CONSTRAINT investidor_fk FOREIGN KEY (id_investidor)
    REFERENCES investidor(id_investidor) ON DELETE CASCADE,
  CONSTRAINT investimento_fk FOREIGN KEY (id_oportunidade)
    REFERENCES oportunidade(id) ON DELETE CASCADE
);

GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA "public" to aplicacao_user;
GRANT SELECT, USAGE, UPDATE ON ALL SEQUENCES IN SCHEMA "public" to aplicacao_user;
