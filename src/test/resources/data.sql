INSERT INTO student (id,name, email) VALUES
  (1, 'Benjamin', 'benjamin@email.com'),
  (2, 'Zoey', 'zoey@email.com'),
  (3, 'Peggy', 'peggy@email.com'),
  (4, 'Peggy', 'peggy@email.com');

insert into course (id, title) values (1, 'Spring');
insert into course (id, title) values (2, 'TDC talks');

insert into enrollment (course_id, student_id) values
    (1, 1),(1, 2),(2, 3),(2, 4);