--PostgresSQL create script

-- CREATE DATABASE counties;

CREATE ROLE counties_user WITH LOGIN PASSWORD 'counties_pwd';
GRANT ALL PRIVILEGES ON DATABASE counties TO counties_user;
GRANT USAGE ON SCHEMA public to counties_user;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO counties_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO counties_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON TABLES TO counties_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON SEQUENCES TO counties_user;

CREATE TABLE counties (
    id INT,
    county VARCHAR(40),
    state VARCHAR(20),
    st_abbr VARCHAR(2),
    population BIGINT,
    filename VARCHAR(50),
    mimetype VARCHAR(50),
    filedata bytea,
    filingseason VARCHAR(8)
    PRIMARY KEY (id)
);

CREATE INDEX state_idx on counties(st_abbr);