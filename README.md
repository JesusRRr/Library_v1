# Library_v1

user for database 

CREATE USER IF NOT EXISTS 'user_library'@'localhost' IDENTIFIED BY '1234';
GRANT ALL PRIVILEGES ON library.* TO 'user_library'@'localhost';
FLUSH PRIVILEGES;
