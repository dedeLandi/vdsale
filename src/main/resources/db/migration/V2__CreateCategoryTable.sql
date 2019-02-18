CREATE TABLE IF NOT EXISTS CATEGORY (
  ID_CATEGORY INT NOT NULL AUTO_INCREMENT,
  ID_CATEGORY_SPOTIFY VARCHAR(100) NOT NULL,
  NAME VARCHAR(100) NOT NULL,
  PRIMARY KEY (ID_CATEGORY)
);

INSERT INTO CATEGORY (ID_CATEGORY_SPOTIFY, NAME) VALUES ('pop', 'Pop');
INSERT INTO CATEGORY (ID_CATEGORY_SPOTIFY, NAME) VALUES ('brazilian', 'Brazilian music');
INSERT INTO CATEGORY (ID_CATEGORY_SPOTIFY, NAME) VALUES ('classical', 'Classical');
INSERT INTO CATEGORY (ID_CATEGORY_SPOTIFY, NAME) VALUES ('rock', 'Rock');