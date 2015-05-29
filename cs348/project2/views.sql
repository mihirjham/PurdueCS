CREATE VIEW VIEWA AS
SELECT actor_name, nummovies
FROM
(SELECT actor_name, count(*) as nummovies
FROM Cast_Member 
GROUP BY actor_name
UNION
SELECT actor_name, 0
FROM Actor
WHERE actor_name NOT in
(SELECT actor_name
FROM Cast_Member))
ORDER BY actor_name;

CREATE VIEW VIEWB AS
SELECT c.actor_name, n.event_name, n.event_year, n.movie_title, n.release_year, n.category
FROM Nomination n, Cast_Member c 
WHERE (n.category = 'best lead actor' and c.actor_role = 'lead actor')  
AND n.movie_title = c.movie_title AND n.release_year = c.release_year
UNION
SELECT c.actor_name, n.event_name, n.event_year, n.movie_title, n.release_year, n.category
FROM Nomination n, Cast_Member c
WHERE (n.category = 'best supporting actor' and c.actor_role = 'supporting actor')   
AND n.movie_title = c.movie_title AND n.release_year = c.release_year
UNION
SELECT c.actor_name, n.event_name, n.event_year, n.movie_title, n.release_year, n.category
FROM Nomination n, Cast_Member c
WHERE (n.category = 'best lead actress' and c.actor_role = 'lead actress')   
AND n.movie_title = c.movie_title AND n.release_year = c.release_year
UNION
SELECT c.actor_name, n.event_name, n.event_year, n.movie_title, n.release_year, n.category
FROM Nomination n, Cast_Member c
WHERE (n.category = 'best supporting actress' and c.actor_role = 'supporting actress')   
AND n.movie_title = c.movie_title AND n.release_year = c.release_year
ORDER BY actor_name;

SELECT * FROM VIEWA;
SELECT * FROM VIEWB;
