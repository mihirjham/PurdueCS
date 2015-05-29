create table Users(username varchar(30), password varchar(30));

create table Roles(roleName varchar(30), encryptionKey integer);

create table UserRoles(username varchar(30), roleName varchar(30));

create table RolePrivileges(roleName varchar(30), tableName varchar(30), privName varchar(30));

insert into Users values('admin', 'pass');

insert into UserRoles values('admin', 'ADMIN');

insert into Roles values('ADMIN', 1);

create table Movie(movie_title varchar(30), genre varchar(10), encryptedColumn integer, owner varchar(30));

create table Awards_Event(event_name varchar(30), venue varchar(30), encryptedColumn integer, owner varchar(30));

create table Nomination(event_name varchar(30), movie_title varchar(30), category varchar(30), won varchar(5), encryptedColumn integer, owner varchar(30));

