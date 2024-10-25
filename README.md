steps to run the code :- 
1. First steup the android studio with latest version.
2. then create the sql server databse using this command  - :
 ---- First to create the database----
  create database AppEngine;
---- then run below command-----
    use AppEngine;
 -----Table to store rules------
CREATE TABLE rules (
    id INT PRIMARY KEY IDENTITY(1,1),  -- Auto-incremented primary key
    rule_string NVARCHAR(MAX) NOT NULL  -- String to hold the rule definition
);
select * from rules;

3. Open the project in android studio and build the project then run it in emulater(make sure you use emulater of andorid)






   
