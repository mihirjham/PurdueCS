rem Query 1
SELECT c.movie_title, c.release_year, COUNT(c.actor_name)
FROM Cast_Member c, Actor a
WHERE c.actor_name = a.actor_name AND a.gender = 'Female'
GROUP BY c.movie_title, c.release_year
UNION
SELECT m.movie_title, m.release_year, 0
FROM Movie m
WHERE NOT EXISTS
(SELECT *
FROM Cast_Member c, Actor a
WHERE c.actor_name = a.actor_name AND a.gender = 'Female' AND m.movie_title = c.movie_title AND m.release_year = c.release_year);

rem Query 2
SELECT a.actor_name, a.date_of_birth
FROM Actor a
WHERE
(SELECT COUNT(a1.actor_name)
FROM Actor a1, Nomination n, Cast_Member c
WHERE (n.category = 'best lead actress' AND c.movie_title = n.movie_title AND c.release_year = n.release_year AND c.actor_role = 'lead actress' AND a1.actor_name = c.actor_name AND a.gender = 'Female') OR (n.category = 'best supporting actress' AND c.movie_title = n.movie_title AND c.release_year = n.release_year AND c.actor_role = 'supporting actress' AND a1.actor_name = c.actor_name AND a1.gender = 'Female' AND a.actor_name = a1.actor_name)) >= ALL
(SELECT COUNT(a2.actor_name) FROM Actor a2, Nomination n1, Cast_Member c1
WHERE (n1.category = 'best lead actress' AND c1.movie_title = n1.movie_title AND c1.release_year = n1.release_year AND c1.actor_role = 'lead actress' AND a2.actor_name = c1.actor_name AND a2.gender = 'Female') OR (n1.category = 'best supporting actress' AND c1.movie_title = n1.movie_title AND c1.release_year = n1.release_year AND c1.actor_role = 'supporting actress' AND a2.actor_name = c1.actor_name AND a2.gender = 'Female'));

rem Query 3
SELECT m.movie_title, m.release_year, COUNT(*)
FROM Movie m, Nomination n
WHERE
(SELECT COUNT(n1.movie_title)
FROM Movie m1, Nomination n1
WHERE m1.genre = 'Comedy' AND n1.movie_title = m1.movie_title AND n1.release_year = m1.release_year and m.movie_title = m1.movie_title and m.release_year = m1.release_year and n.movie_title = n1.movie_title and n.release_year = n1.release_year
GROUP BY m1.movie_title, m1.release_year) >= ALL
(SELECT COUNT(n2.movie_title)
FROM Movie m2, Nomination n2
WHERE m2.genre = 'Comedy' AND n2.movie_title = m2.movie_title AND n2.release_year = m2.release_year
GROUP BY m2.movie_title, m2.release_year)
GROUP BY m.movie_title, m.release_year;

rem Query 4
SELECT actor_name
FROM(SELECT DISTINCT c.actor_name, a1.venue
FROM Cast_Member c, Nomination n, Awards_Event a1
WHERE c.movie_title = n.movie_title AND c.release_year = n.release_year AND n.event_name = a1.event_name AND n.event_year = a1.event_year)
GROUP BY actor_name
HAVING COUNT(actor_name) = (SELECT COUNT(DISTINCT venue) FROM AWARDS_EVENT);

rem Query 5
SELECT movie_title, release_year
FROM
(SELECT n.movie_title, n.release_year, COUNT(n.won)
FROM Nomination n
WHERE n.won = 'Yes'
GROUP BY n.movie_title, n.release_year
HAVING COUNT(n.won) > 2
MINUS
SELECT c.movie_title, c.release_year, COUNT(*)
FROM Cast_Member c
GROUP BY c.movie_title, c.release_year
HAVING COUNT(*) > 2);

rem Query 6
SELECT userid, movie_title, release_year, rating
FROM
(SELECT * FROM MOVIE_RATING NATURAL JOIN
(SELECT movie_title, release_year
FROM
(SELECT movie_title, release_year, AVG(rating) as avg_rating
FROM Movie_Rating
GROUP BY movie_title, release_year)
WHERE avg_rating =
(SELECT MIN(avg_rating)
FROM
(SELECT movie_title, release_year, AVG(rating) as avg_rating
FROM Movie_Rating
GROUP BY movie_title, release_year))))
WHERE rating =
(SELECT MAX(rating) as rating
FROM
(SELECT * FROM MOVIE_RATING NATURAL JOIN
(SELECT movie_title, release_year
FROM
(SELECT movie_title, release_year, AVG(rating) as avg_rating
FROM Movie_Rating
GROUP BY movie_title, release_year)
WHERE avg_rating = 
(SELECT MIN(avg_rating)
FROM
(SELECT movie_title, release_year, AVG(rating) as avg_rating
FROM Movie_Rating
GROUP BY movie_title, release_year)))));

rem Query 7
SELECT genre, movie_title, release_year, movie_length
FROM Movie NATURAL JOIN
(SELECT genre, MAX(movie_length) as movie_length
FROM Movie
GROUP BY genre)
ORDER BY movie_length;
