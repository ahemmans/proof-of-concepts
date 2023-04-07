-- HSQLDB create script

DROP TABLE IF EXISTS counties;

CREATE TABLE counties(
	id INT NOT NULL,
	county VARCHAR(40),
	state VARCHAR(20),
	st_abbr VARCHAR(2),
	population BIGINT,
	filename VARCHAR(50),
	mimetype VARCHAR(50),
	filedata LONGVARBINARY,
	filingseason VARCHAR(8),
	PRIMARY KEY (id)
);
COMMIT;

INSERT INTO counties(id, county,state,st_abbr,population) VALUES (24033, 'Prince George''s County', 'Maryland', 'MD',0);
INSERT INTO counties(id, county,state,st_abbr,population) VALUES (34023, 'Middlesex County', 'New Jersey', 'NJ',0);
INSERT INTO counties(id, county,state,st_abbr,population) VALUES (75000, '', 'Maryland', 'MD',0);
INSERT INTO counties(id, county,state,st_abbr,population) VALUES (75001, '', 'Maryland', 'MD',0);
INSERT INTO counties(id, county,state,st_abbr,population) VALUES (75002, '', 'Maryland', 'MD',0);
INSERT INTO counties(id, county,state,st_abbr,population) VALUES (75003, '', 'Maryland', 'MD',0);
INSERT INTO counties(id, county,state,st_abbr,population) VALUES (75004, '', 'Maryland', 'MD',0);
COMMIT;


