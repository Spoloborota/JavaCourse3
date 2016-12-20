Part 1

CREATE TABLE Positions(
  ID INTEGER PRIMARY KEY,
  Name VARCHAR NOT NULL
);

CREATE TABLE Departments(
  ID INTEGER PRIMARY KEY,
  Name VARCHAR NOT NULL
);

CREATE TABLE Employees(
  ID INTEGER PRIMARY KEY,
  Name VARCHAR,
  Birthday date,
  Email VARCHAR,
  Position_ID INTEGER,
  Department_ID INTEGER,
  Hire_Date date NOT NULL,
  Manager_ID INTEGER,
CONSTRAINT FK_Employees_DepartmentID FOREIGN KEY(Department_ID) REFERENCES Departments(ID),
CONSTRAINT FK_Employees_PositionID FOREIGN KEY(Position_ID) REFERENCES Positions(ID),
CONSTRAINT FK_Employees_ManagerID FOREIGN KEY (Manager_ID) REFERENCES Employees(ID),
CONSTRAINT UQ_Employees_Email UNIQUE(Email),
UNIQUE(Name)
);

INSERT INTO Departments(Id,Name)VALUES
('1','Администрация'),
('2','Бухгалтерия'),
('3','ИТ');

INSERT INTO Positions(Id,Name)VALUES
('1','Бухгалтер'),
('2','Директор'),
('3','Программист'),
('4','Старший программист');

INSERT INTO Employees (id, Name,Birthday,Email,Position_ID,Department_ID,Hire_Date,Manager_ID)VALUES
('1000','Иванов И.И.','19550219','i.ivanov@test.tt','2','1','20100115',NULL),
('1001','Петров П.П.','19831203','p.petrov@test.tt','3','3','20130116','1003'),
('1002','Сидоров С.С.','19760607','s.sidorov@test.tt','1','2','20140117','1000'),
('1003','Андреев А.А.','19820417','a.andreev@test.tt','4','3','20160118','1000');


Part 2

ALTER TABLE Employees
ADD LastName VARCHAR,
ADD FirstName VARCHAR,
ADD MiddleName VARCHAR,
ADD Salary float,
ADD BonusPercent float;

UPDATE Employees
SET
  LastName='Иванов',FirstName='Иван',MiddleName='Иванович',
  Salary=5000,BonusPercent= 50
WHERE name='Иванов И.И.';

UPDATE Employees
SET
  LastName='Петров',FirstName='Петр',MiddleName='Петрович',
  Salary=1500,BonusPercent= 15
WHERE name='Петров П.П.';

UPDATE Employees
SET
  LastName='Сидоров',FirstName='Сидор',MiddleName=NULL,
  Salary=2500,BonusPercent=NULL
WHERE name='Сидоров С.С.';

UPDATE Employees
SET
  LastName='Андреев',FirstName='Андрей',MiddleName=NULL,
  Salary=2000,BonusPercent= 30
WHERE name='Андреев А.А.';

INSERT INTO public.employees(
            id, name, birthday, email, position_id, department_id, hire_date, 
            manager_id, lastname, firstname, middlename, salary)
    VALUES (1004, 'Иванов Д.М.', '19740201', 'd.ivanov@test.tt', 3, 3, '20160229', 
            1003, 'Иванов', 'Дмитрий', 'Михайлович', 500);

INSERT INTO Employees(ID,Name,Email,Position_ID,Department_ID,hire_date,Manager_ID,Salary)
VALUES(1004,'Николаев Н.Н.','n.nikolayev@test.tt',3,3,'20141209',1003,1500);

INSERT INTO Employees(ID,Name,Email,Position_ID,Department_ID,hire_date,Manager_ID,Salary)
VALUES(1005,'Александров А.А.','a.alexandrov@test.tt',NULL,NULL,'20141209',1000,2000);



1.
SELECT
  LastName || ' ' || FirstName || ' ' || MiddleName AS ФИО,
  HireDate AS "Дата приема",
  Birthday AS "Дата рождения",
  Salary ZP
FROM Employees;

2.
SELECT
  ID,Name,
  Salary/100*BonusPercent AS Result1,
  Salary/100*(CASE WHEN BonusPercent IS NULL THEN 0 ELSE BonusPercent END) AS Result2,
  Salary/100*COALESCE(BonusPercent,0) AS Result3 
FROM Employees;