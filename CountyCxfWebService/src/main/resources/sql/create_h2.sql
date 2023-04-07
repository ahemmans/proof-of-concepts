--H2 db create script

DROP TABLE IF EXISTS counties;

CREATE TABLE counties(
	id IDENTITY,
	county VARCHAR(40),
	state VARCHAR(20),
	st_abbr VARCHAR(2),
	population BIGINT,
	filename VARCHAR(50),
	mimetype VARCHAR(50),
	filedata BINARY,
	filingseason VARCHAR(8),
);
