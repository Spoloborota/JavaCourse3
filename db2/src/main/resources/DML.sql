Пример 1

SELECT
  ID,Name,Salary,

  CASE
    WHEN Salary>=3000 THEN 'ЗП >= 3000'
    WHEN Salary>=2000 THEN '2000 <= ЗП < 3000'
    ELSE 'ЗП < 2000'
  END SalaryTypeWithELSE,

  CASE
    WHEN Salary>=3000 THEN 'ЗП >= 3000'
    WHEN Salary>=2000 THEN '2000 <= ЗП < 3000'
  END SalaryTypeWithoutELSE

FROM Employees

Пример 2

SELECT
  ID,Name,Salary,Department_ID,

  -- для наглядности выведем процент в виде строки
  CASE Department_ID -- проверяемое значение
    WHEN 2 THEN '10%' -- 10% от ЗП выдать Бухгалтерам
    WHEN 3 THEN '15%' -- 15% от ЗП выдать ИТ-шникам
    ELSE '5%' -- всем остальным по 5%
  END NewYearBonusPercent,

  -- построим выражение с использованием CASE, чтобы увидеть сумму бонуса
  Salary/100*
  CASE Department_ID
    WHEN 2 THEN 10 -- 10% от ЗП выдать Бухгалтерам
    WHEN 3 THEN 15 -- 15% от ЗП выдать ИТ-шникам
    ELSE 5 -- всем остальным по 5%
  END BonusAmount

FROM Employees

Пример 3

SELECT
  ID,Name,Salary,Department_ID,

  CASE
    WHEN Department_ID=2 THEN '10%' -- 10% от ЗП выдать Бухгалтерам
    WHEN Department_ID=3 THEN '15%' -- 15% от ЗП выдать ИТ-шникам
    ELSE '5%' -- всем остальным по 5%
  END NewYearBonusPercent,

  -- построим выражение с использованием CASE, чтобы увидеть сумму бонуса
  Salary/100*
  CASE
    WHEN Department_ID=2 THEN 10 -- 10% от ЗП выдать Бухгалтерам
    WHEN Department_ID=3 THEN 15 -- 15% от ЗП выдать ИТ-шникам
    ELSE 5 -- всем остальным по 5%
  END BonusAmount

FROM Employees

Пример 4

SELECT
  ID,Name,Salary,

  CASE
    WHEN Department_ID IN(1,2) THEN 'A'
    WHEN Department_ID=3 THEN
                          CASE Position_ID -- вложенный CASE
                            WHEN 3 THEN 'B-1'
                            WHEN 4 THEN 'B-2'
                          END
    ELSE 'C'
  END Demo1

FROM Employees

Пример 5

SELECT
  ID,Name,Salary,Department_ID,

  CASE
    WHEN Department_ID=2 THEN '10%' -- 10% от ЗП выдать Бухгалтерам
    WHEN Department_ID=3 THEN '15%' -- 15% от ЗП выдать ИТ-шникам
    WHEN Department_ID IS NULL THEN '-' -- внештатникам бонусов не даем (используем IS NULL)
    ELSE '5%' -- всем остальным по 5%
  END NewYearBonusPercent1,

  -- а так проверять на NULL нельзя, вспоминаем что говорилось про NULL во второй части
  CASE Department_ID -- проверяемое значение
    WHEN 2 THEN '10%'
    WHEN 3 THEN '15%'
    WHEN NULL THEN '-' -- !!! в данном случае использование второй формы CASE не подходит
    ELSE '5%'
  END NewYearBonusPercent2

FROM Employees

Пример 6

SELECT
  COUNT(*) "Общее кол-во сотрудников",
  COUNT(DISTINCT Department_ID) "Число уникальных отделов",
  COUNT(DISTINCT Position_ID) "Число уникальных должностей",
  COUNT(BonusPercent) "Кол-во сотрудников у которых указан % бонуса",
  MAX(BonusPercent) "Максимальный процент бонуса",
  MIN(BonusPercent) "Минимальный процент бонуса",
  SUM(Salary/100*BonusPercent) "Сумма всех бонусов",
  AVG(Salary/100*BonusPercent) "Средний размер бонуса",
  AVG(Salary) "Средний размер ЗП"
FROM Employees

Пример 7

SELECT
  COUNT(*) "Общее кол-во сотрудников",
  COUNT(DISTINCT Department_ID) "Число уникальных отделов",
  COUNT(DISTINCT Position_ID) "Число уникальных должностей",
  COUNT(BonusPercent) "Кол-во сотрудников у которых указан % бонуса",
  MAX(BonusPercent) "Максимальный процент бонуса",
  MIN(BonusPercent) "Минимальный процент бонуса",
  SUM(Salary/100*BonusPercent) "Сумма всех бонусов",
  AVG(Salary/100*BonusPercent) "Средний размер бонуса",
  AVG(Salary) "Средний размер ЗП"
FROM Employees
WHERE Department_ID=3 -- учесть только ИТ-отдел

Пример 8

SELECT
  'Администрация' Info,
  COUNT(DISTINCT Position_ID) PositionCount,
  COUNT(*) EmplCount,
  SUM(Salary) SalaryAmount
FROM Employees
WHERE Department_ID=1 -- данные по Администрации

SELECT
  'Бухгалтерия' Info,
  COUNT(DISTINCT Position_ID) PositionCount,
  COUNT(*) EmplCount,
  SUM(Salary) SalaryAmount
FROM Employees
WHERE Department_ID=2 -- данные по Бухгалтерии

SELECT
  'ИТ' Info,
  COUNT(DISTINCT Position_ID) PositionCount,
  COUNT(*) EmplCount,
  SUM(Salary) SalaryAmount
FROM Employees
WHERE Department_ID=3 -- данные по ИТ отделу

SELECT
  'Прочие' Info,
  COUNT(DISTINCT Position_ID) PositionCount,
  COUNT(*) EmplCount,
  SUM(Salary) SalaryAmount
FROM Employees
WHERE Department_ID IS NULL -- и еще не забываем данные по внештатникам

Пример 9

SELECT
  COUNT(*) EmplCount,
  SUM(Salary) SalaryAmount
FROM Employees
WHERE  Department_ID IS NULL AND Position_ID IS NULL

SELECT
  COUNT(*) EmplCount,
  SUM(Salary) SalaryAmount
FROM Employees
WHERE  Department_ID=1 AND Position_ID=2

-- ...

SELECT
  COUNT(*) EmplCount,
  SUM(Salary) SalaryAmount
FROM Employees
WHERE  Department_ID=3 AND Position_ID=4

Пример 10

SELECT
  'Строка константа' Const1, -- константа в виде строки
  1 Const2, -- константа в виде числа

  -- выражение с использованием полей участвуещих в группировке
  CONCAT('Отдел № ',Department_ID) ConstAndGroupField, 
  CONCAT('Отдел № ',Department_ID,', Должность № ',Position_ID) ConstAndGroupFields,

  Department_ID, -- поле из списка полей участвующих в группировке
  -- Position_ID, -- поле учавствующее в группировке, не обязательно дублировать здесь

  COUNT(*) EmplCount, -- кол-во строк в каждой группе

  -- остальные поля можно использовать только с агрегатными функциями: COUNT, SUM, MIN, MAX, …
  SUM(Salary) SalaryAmount,
  MIN(ID) MinID
FROM Employees
GROUP BY Department_ID,Position_ID -- группировка по полям Department_ID,Position_ID

Пример 11

SELECT
  CASE
    WHEN EXTRACT(YEAR FROM Birthday)>=2000 THEN 'от 2000'
    WHEN EXTRACT(YEAR FROM Birthday)>=1990 THEN '1999-1990'
    WHEN EXTRACT(YEAR FROM Birthday)>=1980 THEN '1989-1980'
    WHEN EXTRACT(YEAR FROM Birthday)>=1970 THEN '1979-1970'
    WHEN Birthday IS NOT NULL THEN 'ранее 1970'
    ELSE 'не указано'
  END RangeName,
  COUNT(*) EmplCount
FROM Employees
GROUP BY
  CASE
    WHEN EXTRACT(YEAR FROM Birthday)>=2000 THEN 'от 2000'
    WHEN EXTRACT(YEAR FROM Birthday)>=1990 THEN '1999-1990'
    WHEN EXTRACT(YEAR FROM Birthday)>=1980 THEN '1989-1980'
    WHEN EXTRACT(YEAR FROM Birthday)>=1970 THEN '1979-1970'
    WHEN Birthday IS NOT NULL THEN 'ранее 1970'
    ELSE 'не указано'
  END
  
Пример 12

SELECT
  ID,
  CASE
    WHEN EXTRACT(YEAR FROM Birthday)>=2000 THEN 'от 2000'
    WHEN EXTRACT(YEAR FROM Birthday)>=1990 THEN '1999-1990'
    WHEN EXTRACT(YEAR FROM Birthday)>=1980 THEN '1989-1980'
    WHEN EXTRACT(YEAR FROM Birthday)>=1970 THEN '1979-1970'
    WHEN Birthday IS NOT NULL THEN 'ранее 1970'
    ELSE 'не указано'
  END
FROM Employees

Пример 13

SELECT
  Department_ID,
  CONCAT('Год рождения - ',EXTRACT(YEAR FROM Birthday)) YearOfBirthday,
  COUNT(*) EmplCount
FROM Employees
GROUP BY EXTRACT(YEAR FROM Birthday),Department_ID -- порядок может не совпадать с порядком их использования в блоке SELECT
ORDER BY Department_ID,YearOfBirthday -- напоследок мы можем применить к результату сортировку

Пример 14

SELECT
  CASE Department_ID
    WHEN 1 THEN 'Администрация'
    WHEN 2 THEN 'Бухгалтерия'
    WHEN 3 THEN 'ИТ'
    ELSE 'Прочие'
  END Info,
  COUNT(DISTINCT Position_ID) PositionCount,
  COUNT(*) EmplCount,
  SUM(Salary) SalaryAmount,
  AVG(Salary) SalaryAvg -- плюс выполняем пожелание директора
FROM Employees
GROUP BY Department_ID
ORDER BY Info -- добавим для большего удобства сортировку по колонке Info

Пример 15

SELECT
  COALESCE(dep.Name,'Прочие') DepName,
  COUNT(DISTINCT emp.Position_ID) PositionCount,
  COUNT(*) EmplCount,
  SUM(emp.Salary) SalaryAmount,
  AVG(emp.Salary) SalaryAvg -- плюс выполняем пожелание директора
FROM Employees emp
LEFT JOIN Departments dep ON emp.Department_ID=dep.ID
GROUP BY emp.Department_ID,dep.Name
ORDER BY DepName

Пример 16

SELECT
  Department_ID,
  SUM(CASE WHEN Position_ID=1 THEN Salary END) "Бухгалтера",
  SUM(CASE WHEN Position_ID=2 THEN Salary END) "Директора",
  SUM(CASE WHEN Position_ID=3 THEN Salary END) "Программисты",
  SUM(CASE WHEN Position_ID=4 THEN Salary END) "Старшие программисты",
  SUM(Salary) "Итого по отделу"
FROM Employees
GROUP BY Department_ID
