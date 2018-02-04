DROP ALL OBJECTS;

CREATE TABLE PUBLIC.Accounts (
  id BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
  user_id VARCHAR(100) NOT NULL,
  name VARCHAR(100),
  number VARCHAR(16) UNIQUE NOT NULL,
  amount DECIMAL(16, 2) DEFAULT 0.0,
  status VARCHAR(10) DEFAULT 'WORK' CHECK (status in ('CLOSED', 'BLOCKED', 'WORK'))
);

insert into PUBLIC.Accounts (user_id, name, number, amount, status)
values ('user', 'main', '1234567890123456', 10012.45, 'WORK');
insert into PUBLIC.Accounts (user_id, name, number, amount, status)
values ('user', 'additional', '1234567890123457', 1001.28, 'WORK');
