1
INSERT INTO Departments(ID,Name) VALUES(4,'Маркетинг и реклама');
INSERT INTO Departments(ID,Name) VALUES(5,'Логистика');
INSERT INTO Positions(ID,Name) VALUES(5,'Маркетолог');
INSERT INTO Positions(ID,Name) VALUES(6,'Логист');
INSERT INTO Positions(ID,Name) VALUES(7,'Кладовщик');

2
SELECT emp.ID,emp.Name,emp.Department_ID,dep.ID,dep.Name
FROM Employees emp
JOIN Departments dep ON emp.Department_ID=dep.ID

3
CREATE TABLE LeftTable(
  LCode int,
  LDescr varchar(10)
);

CREATE TABLE RightTable(
  RCode int,
  RDescr varchar(10)
);

INSERT INTO LeftTable(LCode,LDescr)VALUES
(1,'L-1'),
(2,'L-2'),
(3,'L-3'),
(5,'L-5');

INSERT INTO RightTable(RCode,RDescr)VALUES
(2,'B-2'),
(3,'B-3'),
(4,'B-4');

4
SELECT l.*,r.*
FROM LeftTable l
JOIN RightTable r ON l.LCode=r.RCode

5
SELECT l.*,r.*
FROM LeftTable l
LEFT JOIN RightTable r ON l.LCode=r.RCode

6
SELECT l.*,r.*
FROM LeftTable l
RIGHT JOIN RightTable r ON l.LCode=r.RCode

7
SELECT l.*,r.*
FROM LeftTable l
FULL JOIN RightTable r ON l.LCode=r.RCode

8
SELECT l.*,r.*
FROM LeftTable l
CROSS JOIN RightTable r

9
SELECT
  e.ID,
  e.Name EmployeeName,
  p.Name PositionName,
  d.Name DepartmentName
FROM Employees e
LEFT JOIN Departments d ON e.Department_ID=d.ID
LEFT JOIN Positions p ON e.Position_ID=p.ID

10
CREATE TABLE TopTable(
  T1 int,
  T2 varchar(10)
);

CREATE TABLE BottomTable(
  B1 int,
  B2 varchar(10)
);

INSERT INTO TopTable(T1,T2)VALUES
(1,'Text 1'),
(1,'Text 1'),
(2,'Text 2'),
(3,'Text 3'),
(4,'Text 4'),
(5,'Text 5');


INSERT INTO BottomTable(B1,B2)VALUES
(2,'Text 2'),
(3,'Text 3'),
(6,'Text 6'),
(6,'Text 6');

11
SELECT T1 x,T2 y
FROM TopTable

UNION ALL

SELECT B1,B2
FROM BottomTable

12
SELECT T1 x,T2 y
FROM TopTable

UNION

SELECT B1,B2
FROM BottomTable

13
SELECT
  DepartmentID,
  COUNT(DISTINCT PositionID) PositionCount,
  COUNT(*) EmplCount,
  SUM(Salary) SalaryAmount,
  AVG(Salary) SalaryAvg -- плюс выполняем пожелание директора
FROM Employees
GROUP BY DepartmentID

14
SELECT
  /*
    используем подзапрос с параметром
    в данном случае подзапрос должен возвращать одну строку
    и только одно значение
  */
  (SELECT Name FROM Departments dep WHERE dep.ID=emp.Department_ID) DepartmentName,

  COUNT(DISTINCT Position_ID) PositionCount,
  COUNT(*) EmplCount,
  SUM(Salary) SalaryAmount,
  AVG(Salary) SalaryAvg
FROM Employees emp -- задаем псевдоним
GROUP BY Department_ID
ORDER BY DepartmentName

