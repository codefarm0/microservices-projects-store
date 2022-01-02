create table users(
id varchar(40) not null,
 name varchar(20),
  email varchar(20) unique,
   mobile int(10) unique,
   password varchar(100),
   insert_date date,
   expire_date date,
   primary key(id)
   );