create table if not exists blogdb;

-- drop user if exists 'blogger'@'localhost'
create user if not exists 'blogger'@'localhost' identitied by '#Whale123';
grant all privileges  on blogdb.* to 'blogger'@'localhost';
flush privileges;