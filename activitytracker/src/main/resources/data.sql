insert into users (id, email, password) values (0,'raman@example.com','$2y$10$M4BudkzmLl.Hkneg87aAxuM2vnHifzoEmJ3IFFLpRKPnHWX.Vyqwa');
insert into users (id, email, password) values (99,'namar@example.com','$2y$10$M4BudkzmLl.Hkneg87aAxuM2vnHifzoEmJ3IFFLpRKPnHWX.Vyqwa');

insert into activity (id, name,start_time_stamp,end_time_stamp,owner_id) values ( 90, 'coding','08:00','09:00',0);
insert into activity (id, name,start_time_stamp,end_time_stamp,owner_id) values ( 91, 'coding','12:30','13:30',0);
insert into activity (id, name,start_time_stamp,end_time_stamp,owner_id) values ( 92, 'watching youtube','10:30','12:00',0);
insert into activity (id, name,start_time_stamp,end_time_stamp,owner_id) values ( 93, 'exercising','18:30','20:30',99);
insert into activity (id, name,start_time_stamp,end_time_stamp,owner_id) values ( 94, 'watching youtube','16:00','18:00',99);
insert into activity (id, name,start_time_stamp,end_time_stamp,owner_id) values ( 95, 'watching baseball','18:00','18:30',99);