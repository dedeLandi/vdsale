INSERT INTO CASHBACK (ID_CASHBACK, ID_CATEGORY, WEEKDAY, CASHBACK_VALUE)
  VALUES (1, (SELECT ID_CATEGORY FROM CATEGORY WHERE ID_CATEGORY_SPOTIFY = 'pop' ), 'SUNDAY', 25);
INSERT INTO CASHBACK (ID_CASHBACK, ID_CATEGORY, WEEKDAY, CASHBACK_VALUE)
  VALUES (2, (SELECT ID_CATEGORY FROM CATEGORY WHERE ID_CATEGORY_SPOTIFY = 'pop' ), 'MONDAY', 7);

INSERT INTO CASHBACK (ID_CASHBACK, ID_CATEGORY, WEEKDAY, CASHBACK_VALUE)
  VALUES (3, (SELECT ID_CATEGORY FROM CATEGORY WHERE ID_CATEGORY_SPOTIFY = 'brazilian' ), 'TUESDAY', 10);
INSERT INTO CASHBACK (ID_CASHBACK, ID_CATEGORY, WEEKDAY, CASHBACK_VALUE)
  VALUES (4, (SELECT ID_CATEGORY FROM CATEGORY WHERE ID_CATEGORY_SPOTIFY = 'brazilian' ), 'WEDNESDAY', 15);

INSERT INTO CASHBACK (ID_CASHBACK, ID_CATEGORY, WEEKDAY, CASHBACK_VALUE)
  VALUES (5, (SELECT ID_CATEGORY FROM CATEGORY WHERE ID_CATEGORY_SPOTIFY = 'classical' ), 'THURSDAY', 13);
INSERT INTO CASHBACK (ID_CASHBACK, ID_CATEGORY, WEEKDAY, CASHBACK_VALUE)
  VALUES (6, (SELECT ID_CATEGORY FROM CATEGORY WHERE ID_CATEGORY_SPOTIFY = 'classical' ), 'FRIDAY', 18);

INSERT INTO CASHBACK (ID_CASHBACK, ID_CATEGORY, WEEKDAY, CASHBACK_VALUE)
  VALUES (7, (SELECT ID_CATEGORY FROM CATEGORY WHERE ID_CATEGORY_SPOTIFY = 'rock' ), 'SATURDAY', 40);