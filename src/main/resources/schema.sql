CREATE TABLE IF NOT EXISTS user ( 
 id varchar(40) primary key,
 passwd varchar(40) not null
);

CREATE TABLE IF NOT EXISTS notice ( 
 seq integer IDENTITY,
 title varchar(100) not null,
 content varchar(300) not null,
 userId varchar(40) not null
);