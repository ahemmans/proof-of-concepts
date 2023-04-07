COPY counties(id,county,state,population,st_abbr) 
FROM 'C:\temp\US_Counties_by_State_2013.csv' DELIMITER ',' CSV HEADER;