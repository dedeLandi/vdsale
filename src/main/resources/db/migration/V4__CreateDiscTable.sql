CREATE TABLE IF NOT EXISTS ARTIST (
  ID_ARTIST INT NOT NULL AUTO_INCREMENT,
  ID_ARTIST_SPOTIFY VARCHAR(100) NOT NULL,
  NAME VARCHAR(255) NOT NULL,
  PRIMARY KEY (ID_ARTIST)
);

CREATE TABLE IF NOT EXISTS DISC (
  ID_DISC INT NOT NULL AUTO_INCREMENT,
  ID_DISC_SPOTIFY VARCHAR(100) NOT NULL,
  TYPE VARCHAR(15) NOT NULL,
  ID_CATEGORY INT NOT NULL,
  ID_ARTIST INT NOT NULL,
  NAME VARCHAR(255) NOT NULL,
  PRICE DECIMAL(5,2) NOT NULL,
  PRIMARY KEY (ID_DISC),
  CONSTRAINT ID_CATEGORY_DISC FOREIGN KEY (ID_CATEGORY) REFERENCES CATEGORY (ID_CATEGORY),
  CONSTRAINT ID_ARTIST_DISC FOREIGN KEY (ID_ARTIST) REFERENCES ARTIST (ID_ARTIST)
);

INSERT INTO ARTIST (ID_ARTIST_SPOTIFY, NAME) VALUES ('0GNq4xh8uFCyihPurnunf7', 'Engenheiros Do Hawaii');

INSERT INTO DISC (ID_DISC_SPOTIFY, TYPE,
  ID_CATEGORY,
  ID_ARTIST,
  NAME, PRICE)
    VALUES ('2kiDkXNxuQME25DEUWiNkw', 'ALBUM',
        (SELECT ID_CATEGORY FROM CATEGORY WHERE ID_CATEGORY_SPOTIFY = 'pop' ),
        (SELECT ID_ARTIST FROM ARTIST WHERE ID_ARTIST_SPOTIFY = '0GNq4xh8uFCyihPurnunf7' ),
        'O Papa É Pop', 25.5);