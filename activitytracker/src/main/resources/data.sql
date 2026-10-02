insert into users (id, email, password) values (0,'raman@example.com','$2y$10$M4BudkzmLl.Hkneg87aAxuM2vnHifzoEmJ3IFFLpRKPnHWX.Vyqwa');
insert into users (id, email, password) values (99,'namar@example.com','$2y$10$M4BudkzmLl.Hkneg87aAxuM2vnHifzoEmJ3IFFLpRKPnHWX.Vyqwa');

insert into activity (id, name,start_time_stamp,end_time_stamp,owner_id) values ( 90, 'coding','8am','9am',0);
insert into activity (id, name,start_time_stamp,end_time_stamp,owner_id) values ( 91, 'coding','1230pm','130pm',0);
insert into activity (id, name,start_time_stamp,end_time_stamp,owner_id) values ( 92, 'watching youtube','1030am','12pm',0);
insert into activity (id, name,start_time_stamp,end_time_stamp,owner_id) values ( 93, 'exercising','630pm','830pm',99);
insert into activity (id, name,start_time_stamp,end_time_stamp,owner_id) values ( 94, 'watching youtube','4pm','6pm',99);
insert into activity (id, name,start_time_stamp,end_time_stamp,owner_id) values ( 95, 'watching baseball','6pm','630pm',99);