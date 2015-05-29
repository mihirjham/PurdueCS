create table Actor(actor_name varchar(30), gender varchar(6), date_of_birth date, primary key(actor_name));

create table Movie(movie_title varchar(60), release_year integer, genre varchar(20), movie_length integer, primary key(movie_title, release_year));

create table Cast_Member(actor_name varchar(30), movie_title varchar(60), release_year integer, actor_role varchar(60), primary key(actor_name, movie_title, release_year), foreign key(actor_name) references Actor(actor_name), foreign key(movie_title, release_year) references Movie(movie_title, release_year));

create table Awards_Event(event_name varchar(60), event_year integer, venue varchar(60), primary key(event_name, event_year));

create table Nomination(event_name varchar(60), event_year integer, movie_title varchar(60), release_year integer, category varchar(60), won varchar(3), primary key(event_name, event_year, movie_title, release_year, category), foreign key(event_name, event_year) references Awards_Event(event_name, event_year), foreign key(movie_title, release_year) references Movie(movie_title, release_year));

create table Db_User(userid varchar(30), primary key(userid));

create table Movie_Rating(userid varchar(30), movie_title varchar(60), release_year integer, rating number, primary key(userid, movie_title, release_year), foreign key(userid) references Db_User(userid), foreign key(movie_title, release_year) references Movie(movie_title, release_year));
