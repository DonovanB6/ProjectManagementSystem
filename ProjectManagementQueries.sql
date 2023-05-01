--Question 1
SELECT Project.pname
FROM Assigned, Project
WHERE Assigned.pid =Project.pid AND Assigned.sid = 3; -- Replace 1 with the specified student id



--Question 2
SELECT DISTINCT Student.sname, Faculty.fname
FROM Assigned, Project, Student, Faculty
WHERE Assigned.pid = Project.pid
  AND Assigned.sid = Student.sid
  AND (Project.pi = Faculty.fid OR Project.copi = Faculty.fid)
  AND Assigned.pid = 1; -- Replace 1 with the specified project id


--Question 3

SELECT Project.pname
FROM Project
WHERE Project.pi = 1 -- Replace 1 with the specified faculty id
AND Project.sdate >= '2022-01-01' -- Replace with the start date of the time range
AND Project.edate <= '2022-07-31'; -- Replace with the end date of the time range
