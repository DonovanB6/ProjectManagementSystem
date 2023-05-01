CREATE TABLE Student (
    sid INTEGER PRIMARY KEY,
    sname VARCHAR(255),
    major VARCHAR(255),
    level VARCHAR(255),
    byear INTEGER
);

CREATE TABLE Faculty (
    fid INTEGER PRIMARY KEY,
    fname VARCHAR(255),
    department VARCHAR(255)
);

CREATE TABLE Project (
    pid INTEGER PRIMARY KEY,
    pname VARCHAR(255),
    sdate DATE,
    edate DATE,
    pi INTEGER,
    copi INTEGER,
    FOREIGN KEY (pi) REFERENCES Faculty(fid),
    FOREIGN KEY (copi) REFERENCES Faculty(fid)
);

CREATE TABLE Assigned (
    sid INTEGER,
    pid INTEGER,
    sdate DATE,
    edate DATE,
    PRIMARY KEY (sid, pid),
    FOREIGN KEY (sid) REFERENCES Student(sid),
    FOREIGN KEY (pid) REFERENCES Project(pid)
);

CREATE TABLE "User" (
 username VARCHAR(255),
 password VARCHAR(255)
);


Insert into [User] VALUES ('wssu','pass')


Select *
From [User]

-- Insert 5 rows into Student table with unique names
INSERT INTO Student (sid, sname, major, level, byear)
VALUES
    (1, 'Donovan Benson', 'Computer Science', 'SR', 2000),
    (2, 'Hector Santiago', 'Computer Science', 'SR', 2002),
    (3, 'Sally Smith', 'Mathematics', 'SR', 2000),
    (4, 'Sarah Evans', 'Chemistry', 'JR', 2001),
    (5, 'Emily Marsh', 'Political Science', 'FR', 2003);

-- Insert 5 rows into Faculty table with unique names
INSERT INTO Faculty (fid, fname, department)
VALUES
    (1, 'Elva Jones', 'Computer Science'),
    (2, 'Mustafa Atay', 'Computer Science'),
    (3, 'John Maguire', 'Mathematics'),
    (4, 'Rachel Elmore', 'Chemistry'),
    (5, 'David Honan', 'Political Science');

-- Insert 5 rows into Project table
INSERT INTO Project (pid, pname, sdate, edate, pi, copi)
VALUES
    (1, 'Face Rec', '2022-01-01', '2022-05-31', 1, 2),
    (2, 'Video Games', '2022-03-15', '2022-07-31', 2, 4),
    (3, 'Cyber Security', '2022-05-01', '2022-10-31', 3, 1),
    (4, 'Data Analytics', '2022-06-01', '2022-12-31', 4, 5),
    (5, 'Robotics', '2022-07-01', '2022-11-30', 5, 3);

-- Insert 5 rows into Assigned table
INSERT INTO Assigned (sid, pid, sdate, edate)
VALUES
    (1, 1, '2022-01-01', '2022-05-31'),
    (2, 1, '2022-01-01', '2022-05-31'),
    (3, 2, '2022-03-15', '2022-07-31'),
    (4, 3, '2022-05-01', '2022-10-31'),
    (5, 4, '2022-06-01', '2022-12-31'),
    (3, 1, '2022-01-01', '2022-05-31');


--2 Stored Procedures(Maybe Insert and Update methods to kill 2 birds with one stone)
        --List all of the Students

CREATE PROCEDURE usp_liststudents

AS 
BEGIN
    SELECT *
    FROM Student
END
 EXEC usp_liststudents

--List All Faculty

CREATE PROCEDURE usp_listfaculty
AS 
BEGIN
    SELECT *
    FROM Faculty
END

EXEC usp_listfaculty

--2 Constraints (Too old for college, Start Date before end Date)

Create TRIGGER tr_student_insert
ON Student
FOR INSERT
AS
BEGIN
	IF (SELECT Count(*) FROM inserted WHERE byear > 2006 ) > 0
	BEGIN
		PRINT 'ALERT! Invalid Age'
		ROLLBACK
	END
	ELSE
		PRINT 'Age is valid.'
END

Create TRIGGER tr_project_insert
ON Project
FOR INSERT
AS
BEGIN
	IF (SELECT Count(*) FROM inserted WHERE sdate > edate ) > 0
	BEGIN
		PRINT 'ALERT! Start Date is after End Date!'
		ROLLBACK
	END
	ELSE
		PRINT 'Dates are valid.'
END

INSERT INTO Student (sid, sname, major, level, byear)
VALUES (10, 'Donovan Benson', 'Computer Science', 'SR', 2010)

INSERT INTO Project (pid, pname, sdate, edate, pi, copi)
VALUES
    (10, 'System Admin', '2023-01-01', '2022-05-31', 1, 2)

Select * 
FROM Student;