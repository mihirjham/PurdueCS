rem Query 1
SELECT f.fname, count(e.snum)
from Enrolled e, Faculty f, Class c
where f.fid=c.fid and e.cname=c.cname and (select count(e2.snum) from Enrolled e2 where e2.cname=e.cname) >= all(select count(e3.snum) from Enrolled e3 group by e3.cname) group by f.fname, c.cname;

rem Query 2
SELECT d.deptid, AVG(s.age)
from Department d, Student s
where d.deptid=s.deptid and d.dname<>'Management' group by d.deptid union
select d.deptid, 0
from Department d
where not exists(select * from Student s2 where s2.deptid=d.deptid);

rem Query 3
select s.sname, s.deptid, s.age
from Student s
where (select count(e.cname) from Enrolled e, Class c where e.snum=s.snum and c.cname=e.cname and c.fid in (select f.fid from Faculty f, Department d where f.deptid=d.deptid and d.dname='Computer Sciences')) >= all(select count(e2.snum) from Enrolled e2, Class c2 where e2.snum=s2.snum and c2.cname=e2.cname and c2.fid in (select f2.fid from Faculty f2, Department d2 where f2.deptid=d2.deptid and d2.dname='Computer Sciences')) group by e2.snum;
