CREATE TABLE employee (
    id uuid DEFAULT random_uuid() PRIMARY KEY ,
    name varchar(100) not null,
    salary int not null
);