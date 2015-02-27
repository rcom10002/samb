------------------------------------------------------------------------------------------------
DROP TABLE soPrivilege;

CREATE TABLE soPrivilege
(
id       INTEGER      NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
name        VARCHAR(100) NOT NULL,
source      VARCHAR(100) NOT NULL,
description VARCHAR(1000) NOT NULL,
control     VARCHAR(100) NOT NULL,
status      VARCHAR(100) NOT NULL,
createAt TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
createBy VARCHAR(100) NOT NULL DEFAULT 'soSys',
updateAt TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
updateBy VARCHAR(100) NOT NULL DEFAULT 'soSys'
);

------------------------------------------------------------------------------------------------
DROP TABLE soRole;

CREATE TABLE soRole
(
id       INTEGER      NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
roleName   VARCHAR(100) NOT NULL,
roleDesc   VARCHAR(500) NOT NULL,
roleStatus VARCHAR(100) NOT NULL,
createAt TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
createBy VARCHAR(100) NOT NULL DEFAULT 'soSys',
updateAt TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
updateBy VARCHAR(100) NOT NULL DEFAULT 'soSys'
);


------------------------------------------------------------------------------------------------

DROP TABLE soRoleToPrivilege;

CREATE TABLE soRoleToPrivilege
(
id       INTEGER      NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
roleId      INTEGER NOT NULL,
privilegeId INTEGER NOT NULL,
createAt TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
createBy VARCHAR(100) NOT NULL DEFAULT 'soSys',
updateAt TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
updateBy VARCHAR(100) NOT NULL DEFAULT 'soSys'
);


------------------------------------------------------------------------------------------------
DROP TABLE soRequirement;

CREATE TABLE soRequirement
(
id       INTEGER      NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
corp     VARCHAR(100),
location VARCHAR(100),
target   VARCHAR(100),
manager  VARCHAR(100),
position VARCHAR(100),
quantity INTEGER,
status   VARCHAR(100),
hr       VARCHAR(100),
comment  VARCHAR(100),
createAt TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
createBy VARCHAR(100) NOT NULL DEFAULT 'soSys',
updateAt TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
updateBy VARCHAR(100) NOT NULL DEFAULT 'soSys'
);

SELECT * FROM soRequirement;

------------------------------------------------------------------------------------------------
DROP TABLE soLookup;

CREATE TABLE soLookup
(
id       INTEGER      NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
category VARCHAR(100),
name     VARCHAR(100),
val      VARCHAR(100),
weight   INTEGER,
createAt TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
createBy VARCHAR(100) NOT NULL DEFAULT 'soSys',
updateAt TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
updateBy VARCHAR(100) NOT NULL DEFAULT 'soSys'
);

------------------------------------------------------------------------------------------------
DROP VIEW soLinked;
CREATE VIEW soLinked(entity, name, val, weight) AS                                                             
  SELECT 'member' AS entity, id, username AS val, username AS weight FROM soUser 
   UNION
  SELECT 'requirement' AS entity,                                                                              
         id,                                                                                                   
         (corp || ', ' || cast(createAt as date) || ', ' || target || ', ' || position) AS val, corp AS weight 
    FROM soRequirement                                                                                         
   WHERE status = 'OPEN'                                                                                       
   UNION                                                                                                       
  SELECT 'unit' AS entity, id, val, weight FROM                                                                
  (                                                                                                            
  SELECT id, name AS val, parentUnit || '/' AS weight                                                          
    FROM soUnit                                                                                                
   WHERE status = 'ACTIVE'                                                                                     
   UNION VALUES (0, '/', '')                                                                                   
   ORDER BY 1, 3                                                                                               
   ) T (id, val, weight)                                                                                       
   ORDER BY 1, 4

------------------------------------------------------------------------------------------------
DROP TABLE soUser;

CREATE TABLE soUser
(
id       INTEGER      NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
username VARCHAR(100) NOT NULL,
pwd VARCHAR(100) NOT NULL,
pwdCreateAt TIMESTAMP NOT NULL,
pwdExpDays INTEGER NOT NULL,
status VARCHAR(100) NOT NULL,
role VARCHAR(100) NOT NULL,
birth DATE NOT NULL,
gender VARCHAR(100) NOT NULL,
idCard VARCHAR(100) NOT NULL,
createAt TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
createBy VARCHAR(100) NOT NULL DEFAULT 'soSys',
updateAt TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
updateBy VARCHAR(100) NOT NULL DEFAULT 'soSys'
);

------------------------------------------------------------------------------------------------

DROP TABLE soUnit;

CREATE TABLE soUnit
(
id       INTEGER      NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
name     VARCHAR(100) NOT NULL,
status   VARCHAR(100) NOT NULL,
category VARCHAR(100) NOT NULL,
remark   VARCHAR(100) NOT NULL,
createAt TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
createBy VARCHAR(100) NOT NULL DEFAULT 'soSys',
updateAt TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
updateBy VARCHAR(100) NOT NULL DEFAULT 'soSys'
);

ALTER TABLE soUnit ADD COLUMN parentUnit VARCHAR(2000) NOT NULL DEFAULT '/';
ALTER TABLE soUnit ADD COLUMN parentUnitId INTEGER NOT NULL DEFAULT 0;
ALTER TABLE soUnit DROP COLUMN remark;
ALTER TABLE soUnit ADD COLUMN remark VARCHAR(100);

------------------------------------------------------------------------------------------------

DROP TABLE soUnitToMember;

CREATE TABLE soUnitToMember
(
id       INTEGER      NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
unitId   INTEGER NOT NULL,
memberId INTEGER NOT NULL,
createAt TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
createBy VARCHAR(100) NOT NULL DEFAULT 'soSys',
updateAt TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
updateBy VARCHAR(100) NOT NULL DEFAULT 'soSys'
);

------------------------------------------------------------------------------------------------

DROP TABLE soNews;

CREATE TABLE soNews
(
id       INTEGER      NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
title       VARCHAR(100) NOT NULL,
category    VARCHAR(100) NOT NULL,
author      VARCHAR(100) NOT NULL,
mainBody    VARCHAR(2000) NOT NULL,
dateOfIssue DATE NOT NULL,
createAt TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
createBy VARCHAR(100) NOT NULL DEFAULT 'soSys',
updateAt TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
updateBy VARCHAR(100) NOT NULL DEFAULT 'soSys'
);

------------------------------------------------------------------------------------------------

DROP TABLE soEvent;

CREATE TABLE soEvent
(
id       INTEGER      NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
subject     VARCHAR(100) NOT NULL,
category    VARCHAR(100) NOT NULL,
host        VARCHAR(100) NOT NULL,
participant VARCHAR(200) NOT NULL,
content     VARCHAR(2000) NOT NULL,
startAt     TIMESTAMP NOT NULL,
endAt       TIMESTAMP NOT NULL,
createAt TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
createBy VARCHAR(100) NOT NULL DEFAULT 'soSys',
updateAt TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
updateBy VARCHAR(100) NOT NULL DEFAULT 'soSys'
);

------------------------------------------------------------------------------------------------

DROP TABLE soBook;

CREATE TABLE soBook
(
id       INTEGER      NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
name           VARCHAR(100) NOT NULL,
category       VARCHAR(100) NOT NULL,
dateOfIssue    DATE NOT NULL,
summary        VARCHAR(2000) NOT NULL,
dateOfPurchase DATE NOT NULL,
createAt TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
createBy VARCHAR(100) NOT NULL DEFAULT 'soSys',
updateAt TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
updateBy VARCHAR(100) NOT NULL DEFAULT 'soSys'
);

------------------------------------------------------------------------------------------------

DROP TABLE soTracking;

CREATE TABLE soTracking
(
id               INTEGER      NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
candidate        VARCHAR(100),
phone            VARCHAR(100),
hr               VARCHAR(100) NOT NULL,
workLocation     VARCHAR(100),
skill            VARCHAR(100),
experience       INTEGER      NOT NULL,
language         VARCHAR(100),
employer         VARCHAR(100),
level            VARCHAR(100),
remarks          VARCHAR(100),
toSubmitResumeAt TIMESTAMP    NOT NULL,
interviewResult  VARCHAR(100),
interviewTime    TIMESTAMP,
expectedSalary   INTEGER      NOT NULL,
createAt         TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
createBy         VARCHAR(100) NOT NULL DEFAULT 'soSys',
updateAt         TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
updateBy         VARCHAR(100) NOT NULL DEFAULT 'soSys'
);

ALTER TABLE soTracking ADD COLUMN toRequirement INTEGER NOT NULL DEFAULT 0;
ALTER TABLE soTracking ADD COLUMN files VARCHAR(2000);

------------------------------------------------------------------------------------------------

DROP TABLE soCost;

CREATE TABLE soCost
(
id               INTEGER        NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
category         VARCHAR(100)   NOT NULL,
name             VARCHAR(100)   NOT NULL,
applicant        VARCHAR(100)   NOT NULL,
status           VARCHAR(100)   NOT NULL,
amount           DECIMAL(18, 2) NOT NULL,
detail           VARCHAR(1000)          ,
memo             VARCHAR(1000)          ,
mailEvidence     VARCHAR(100)           ,
createAt         TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
createBy         VARCHAR(100)   NOT NULL DEFAULT 'soSys',
updateAt         TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
updateBy         VARCHAR(100)   NOT NULL DEFAULT 'soSys'
);

------------------------------------------------------------------------------------------------

DROP TABLE soReport;

CREATE TABLE soReport
(
id               INTEGER        NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
name             VARCHAR(100)   NOT NULL,
sql              VARCHAR(2000)  NOT NULL,
labels           VARCHAR(1000)  NOT NULL,
filter           VARCHAR(1000)  NOT NULL,
createAt         TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
createBy         VARCHAR(100)   NOT NULL DEFAULT 'soSys',
updateAt         TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
updateBy         VARCHAR(100)   NOT NULL DEFAULT 'soSys'
);

------------------------------------------------------------------------------------------------
DROP TABLE soMytest;

CREATE TABLE soMytest
(
id               INTEGER        NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
name             VARCHAR(100)   NOT NULL,
test             VARCHAR(200)   NOT NULL,
createAt         TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
createBy         VARCHAR(100)   NOT NULL DEFAULT 'soSys',
updateAt         TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
updateBy         VARCHAR(100)   NOT NULL DEFAULT 'soSys'
);

------------------------------------------------------------------------------------------------

DROP TABLE soEmployee;

CREATE TABLE soEmployee
(
id               INTEGER      NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
name             VARCHAR(100) NOT NULL,
gender           VARCHAR(100) NOT NULL,
phone            VARCHAR(100) NOT NULL,
workLocation     VARCHAR(100) NOT NULL,
employer         VARCHAR(100) NOT NULL,
level            VARCHAR(100) NOT NULL,
hireDate         DATE         NOT NULL,
idCard           VARCHAR(100),
email            VARCHAR(100),
remarks          VARCHAR(100),
createAt         TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
createBy         VARCHAR(100) NOT NULL DEFAULT 'soSys',
updateAt         TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
updateBy         VARCHAR(100) NOT NULL DEFAULT 'soSys'
);

------------------------------------------------------------------------------------------------

DROP FUNCTION TO_LONG;

CREATE FUNCTION TO_LONG(TS TIMESTAMP) RETURNS BIGINT
PARAMETER STYLE JAVA NO SQL LANGUAGE JAVA
EXTERNAL NAME 'info.woody.so.db.DerbyFunctionExt.getTimeOfLongType';

DROP FUNCTION REPLACE;

CREATE FUNCTION REPLACE(S1 VARCHAR(1000), S2 VARCHAR(1000), S3 VARCHAR(1000)) RETURNS VARCHAR(1000)
PARAMETER STYLE JAVA NO SQL LANGUAGE JAVA
EXTERNAL NAME 'info.woody.so.db.DerbyFunctionExt.replace';

DROP FUNCTION REGEX_REPLACE;

CREATE FUNCTION REGEX_REPLACE(S1 VARCHAR(1000), S2 VARCHAR(1000), S3 VARCHAR(1000)) RETURNS VARCHAR(1000)
PARAMETER STYLE JAVA NO SQL LANGUAGE JAVA
EXTERNAL NAME 'info.woody.so.db.DerbyFunctionExt.regex_replace';

------------------------------------------------------------------------------------------------



CREATE VIEW soLinked(entity, name, val, weight) AS                                                      
  SELECT 'requirement' AS entity,                                                                         
         id,                                                                                              
         (corp || ', ' || cast(createAt as date) || ', ' || target || ', ' || position) AS val, corp AS weight
    FROM soRequirement                                                                                    
   WHERE status = 'OPEN'                                                                                  
   UNION                                                                                                  
  SELECT 'unit' AS entity, id, val, weight FROM
  (
  SELECT id, name AS val, parentUnit || '/' AS weight
    FROM soUnit
   WHERE status = 'ACTIVE'
   UNION VALUES (0, '/', '')
   ORDER BY 1, 3
   ) T (id, val, weight)
   ORDER BY 1, 4                                                                                          




		    SELECT id, val, weight FROM
		    (
			SELECT id, name AS val, 0 AS weight
			  FROM soUnit
			 WHERE status = 'ACTIVE'
			 UNION VALUES (0, '/', 0)
			 ORDER BY 1, 3
			 ) T (id, val, weight)

			 VALUES (0, '/', 0)

			 SELECT COUNT(1) + 1
			  FROM soUnit
			 WHERE status = 'ACTIVE'