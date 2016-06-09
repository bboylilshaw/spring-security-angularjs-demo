INSERT INTO t_user (id, username, password, email, role) VALUES
  (null, 'jason', '$2a$10$DkofAgO37J3iZD4DdaYXmuq4nL0iDsFhfG8vKwgOP5e661GGn2Jgu', 'jason@test.com', 0),
  (null, 'john', '$2a$10$DS7cOLocWx1Pi308jCnCPe2rapE5P47pwaYHkH5nJatMWpC8.LC8e', 'john@test.com', 1);

INSERT INTO t_book (id, name, author) VALUES
  (null, 'Effective Java', 'Joshua Bloch'),
  (null, 'Introduction to Algorithms', 'Tomas H. Cormen');
