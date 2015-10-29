CREATE TABLE car (
  id           UUID PRIMARY KEY,
  model        VARCHAR(20) NOT NULL,
  year         INT         NOT NULL,
  modification VARCHAR(20) NOT NULL,
  vin          VARCHAR(17) NULL,
  owner        UUID        NOT NULL
);

CREATE TABLE person (
  id    UUID PRIMARY KEY,
  first VARCHAR(50) NOT NULL,
  last  VARCHAR(50) NOT NULL,
  birth DATE        NOT NULL
);
