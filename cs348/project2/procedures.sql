set serveroutput on size 32000

create or replace procedure pro_actor_stats as

actor_name Actor.actor_name%TYPE;
num_actors integer;
num_movie number;
max_movie number;
min_movie number;
num_bins integer;
increments integer;
mod_value integer;


CURSOR actor_cur is SELECT actor_name, nummovies FROM (SELECT actor_name, count(*) as nummovies FROM Cast_Member GROUP BY actor_name UNION SELECT actor_name, 0 FROM Actor WHERE actor_name NOT in (SELECT actor_name FROM Cast_Member)) ORDER BY actor_name;

actor_rec actor_cur%ROWTYPE;

begin
	SELECT MAX(nummovies) into max_movie FROM (SELECT actor_name, nummovies FROM (SELECT actor_name, count(*) as nummovies FROM Cast_Member GROUP BY actor_name UNION SELECT actor_name, 0 FROM Actor WHERE actor_name NOT in (SELECT actor_name FROM Cast_Member)) ORDER BY actor_name);
	SELECT MIN(nummovies) into min_movie FROM (SELECT actor_name, nummovies FROM (SELECT actor_name, count(*) as nummovies FROM Cast_Member GROUP BY actor_name UNION SELECT actor_name, 0 FROM Actor WHERE actor_name NOT in (SELECT actor_name FROM Cast_Member)) ORDER BY actor_name);
	SELECT COUNT(*) into num_actors FROM (SELECT actor_name, nummovies FROM (SELECT actor_name, count(*) as nummovies FROM Cast_Member GROUP BY actor_name UNION SELECT actor_name, 0 FROM Actor WHERE actor_name NOT in (SELECT actor_name FROM Cast_Member)) ORDER BY actor_name);

	num_bins := 1 + 3;
	
	if MOD(max_movie-min_movie,3) > 0 then
		num_bins := num_bins + 1;
	end if;

	increments := floor((max_movie-min_movie)/3);
	mod_value := MOD(max_movie-min_movie,3);
	
	if num_bins = 5 then
		dbms_output.put(rpad('Actor name', 30));
		dbms_output.put(rpad('Movies', 10));
		dbms_output.put(rpad(min_movie, 10));
		dbms_output.put(rpad('>'||min_movie||', <=',6));
		dbms_output.put(rpad(min_movie+increments, 10));
		dbms_output.put(rpad('>',1));
		dbms_output.put(rpad(min_movie+increments,1));
		dbms_output.put(rpad(', <=', 4));
		dbms_output.put(rpad(min_movie+increments*2,10));
		dbms_output.put(rpad('>',1));
		dbms_output.put(rpad(min_movie+increments*2,1));
		dbms_output.put(rpad(', <=', 4));
		dbms_output.put(rpad(min_movie+increments*3,10));
		dbms_output.put(rpad('>',1));
		dbms_output.put(rpad(min_movie+increments*3,1));
		dbms_output.put_line('');
		dbms_output.put(rpad(' ', 101, '-'));
		dbms_output.put_line('');
	end if;
	
	if num_bins = 4 then
		dbms_output.put(rpad('Actor name', 30));
		dbms_output.put(rpad('Movies', 10));
		dbms_output.put(rpad(min_movie, 10));
		dbms_output.put(rpad('>'||min_movie||', <=',6));
		dbms_output.put(rpad(min_movie+increments, 10));
		dbms_output.put(rpad('>',1));
		dbms_output.put(rpad(min_movie+increments,1));
		dbms_output.put(rpad(', <=', 4));
		dbms_output.put(rpad(min_movie+increments*2,10));
		dbms_output.put(rpad('>',1));
		dbms_output.put(rpad(min_movie+increments*2,1));
		dbms_output.put(rpad(', <=', 4));
		dbms_output.put(rpad(min_movie+increments*3,10));
		dbms_output.put_line('');
		dbms_output.put(rpad(' ', 90, '-'));
		dbms_output.put_line('');
	end if;

	for actor_rec in actor_cur loop
		actor_name := actor_rec.actor_name;
		num_movie := actor_rec.nummovies;
		dbms_output.put(rpad(actor_name, 40));
		if num_bins = 5 then
			if num_movie = min_movie then
				dbms_output.put(lpad('X', 1));
			elsif num_movie>min_movie and num_movie<=(min_movie+increments) then
				dbms_output.put(lpad('X', 14));
			elsif num_movie>(min_movie+increments) and num_movie<=(min_movie+increments*2) then
				dbms_output.put(lpad('X', 30));
			elsif num_movie>(min_movie+(increments*2)) and num_movie<=(min_movie+increments*3) then
				dbms_output.put(lpad('X', 46));
			elsif num_movie>(min_movie+increments*3) then
				dbms_output.put(lpad('X', 59));
			end if;
		end if;
		
		if num_bins = 4 then
			if num_movie = min_movie then
				dbms_output.put(lpad('X', 1));
			elsif num_movie>min_movie and num_movie<=(min_movie+increments) then
				dbms_output.put(lpad('X', 14));
			elsif num_movie>(min_movie+increments) and num_movie<=(min_movie+increments*2) then
				dbms_output.put(lpad('X', 30));
			elsif num_movie>(min_movie+(increments*2)) and num_movie<=(min_movie+increments*3) then
				dbms_output.put(lpad('X', 46));
			end if;
		end if;
		dbms_output.put_line('');
	end loop;
end pro_actor_stats;
/

begin
	pro_actor_stats;
end;
/

create or replace procedure pro_histogram as

num_movies integer;
max_nominations integer;
counter integer;
counter_mode integer;
is_odd integer;
median_value number;
mod_temp number;
median_printed integer;

cursor nom_cursor is select movie_title, release_year, count(*) as numnomination from Nomination group by movie_title, release_year union select m.movie_title, m.release_year, 0 from Movie m where not exists(select * from Nomination n where n.movie_title = m.movie_title and n.release_year = m.release_year) order by numnomination;
nom_rec nom_cursor%ROWTYPE;

begin
	select max(numnomination) into max_nominations from (select movie_title, release_year, count(*) as numnomination from Nomination group by movie_title, release_year);
	select count(*) into num_movies from Movie;
	
	is_odd := MOD(num_movies,2);
	select median(numnomination) into median_value from (select movie_title, release_year, count(*) as numnomination from Nomination group by movie_title, release_year union select m.movie_title, m.release_year, 0 from Movie m where not exists(select * from Nomination n where n.movie_title = m.movie_title and n.release_year = m.release_year) order by numnomination);
	
	dbms_output.put_line('#nominations | #movies');
	counter := 0;
	median_printed := 0;
	while counter <= max_nominations loop
		counter_mode := 0;
		dbms_output.put(counter);
		dbms_output.put('|');
		open nom_cursor;
		loop
			fetch nom_cursor into nom_rec;
			exit when nom_cursor%NOTFOUND;

			if nom_rec.numnomination = counter then
				counter_mode := counter_mode + 1;
			end if;
		end loop;
		close nom_cursor;
		
		if median_printed = 0 then
			if is_odd = 1 then
				if median_value = counter then
					dbms_output.put(counter_mode);
					dbms_output.put_line(' <--- median');
					median_printed := 1;
				else
					dbms_output.put_line(counter_mode);
				end if;
			end if;
		
			if is_odd = 0 then
				mod_temp := MOD(median_value, 1);
				if mod_temp = 0 and counter = median_value and counter_mode > 0 then	
					dbms_output.put(counter_mode);
					dbms_output.put_line(' <--- median');
					median_printed := 1;
				else
					dbms_output.put_line(counter_mode);
				end if;
			end if;
		else
			dbms_output.put_line(counter_mode);
		end if;
		counter := counter + 1;
	end loop;
end pro_histogram;
/

begin
	pro_histogram;
end;
/

create or replace procedure pro_add_rating(userid_in IN Movie_Rating.userid%TYPE, movie_title_in IN Movie_Rating.movie_title%TYPE, release_year_in in Movie_Rating.release_year%TYPE, rating_in IN Movie_Rating.rating%TYPE) as
begin
	INSERT INTO Movie_Rating VALUES(userid_in, movie_title_in, release_year_in, rating_in);
end pro_add_rating;
/

Select * from Movie_Rating;

begin
	pro_add_rating('user1', 'American Hustle', 2013, 7);
end;
/

Select * from Movie_Rating;
