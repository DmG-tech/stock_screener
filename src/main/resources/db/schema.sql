DROP TABLE IF EXISTS indicators;
DROP TABLE IF EXISTS companies;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE companies
(
    id       INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name     VARCHAR NOT NULL,
    industry VARCHAR NOT NULL,
    ticker   VARCHAR NOT NULL
);

CREATE TABLE indicators
(
    id         INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    company_id INTEGER   NOT NULL,
    name       VARCHAR   NOT NULL,
    value      REAL,
    unit       VARCHAR,
    date       TIMESTAMP NOT NULL,
    FOREIGN KEY (company_id) REFERENCES companies (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX indicator_unique_company_name_idx ON indicators (company_id, name);