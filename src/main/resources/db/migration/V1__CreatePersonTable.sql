CREATE TABLE IF NOT EXISTS PERSON (
  ID_PERSON INT NOT NULL AUTO_INCREMENT,
  NAME VARCHAR(100) NOT NULL,
  PRIMARY KEY (ID_PERSON)
);

INSERT INTO PERSON (NAME) VALUES ('DEFAULT');