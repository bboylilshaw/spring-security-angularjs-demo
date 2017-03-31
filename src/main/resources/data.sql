INSERT INTO t_user (username, password, first_name, last_name, email, role) VALUES
  ('jason', '$2a$10$DkofAgO37J3iZD4DdaYXmuq4nL0iDsFhfG8vKwgOP5e661GGn2Jgu', 'Jason', 'Xiao', 'jason@test.com', 0),
  ('john', '$2a$10$DS7cOLocWx1Pi308jCnCPe2rapE5P47pwaYHkH5nJatMWpC8.LC8e', 'John', 'Doe', 'john@test.com', 1);

INSERT INTO t_book (name, author) VALUES
  ('Effective Java', 'Joshua Bloch'),
  ('Introduction to Algorithms', 'Tomas H. Cormen');
