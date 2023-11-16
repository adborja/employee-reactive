CREATE TABLE department (
     dept_number VARCHAR(4)  NOT NULL PRIMARY KEY,
     dept_name   VARCHAR(40) NOT NULL UNIQUE
);

CREATE TABLE department_employee (
    employee_id UUID       NOT NULL,
    dept_number VARCHAR(4) NOT NULL,
    from_date   DATE       NOT NULL,
    to_date     DATE       NOT NULL
);
ALTER TABLE department_employee ADD PRIMARY KEY (employee_id, dept_number);
ALTER TABLE department_employee ADD FOREIGN KEY (employee_id) REFERENCES employee (id) ON DELETE CASCADE;
ALTER TABLE department_employee ADD FOREIGN KEY (dept_number) REFERENCES department (dept_number) ON DELETE CASCADE;


CREATE TABLE department_manager (
  dept_number  VARCHAR(4)  NOT NULL,
  employee_id  UUID      NOT NULL,
  from_date    DATE     NOT NULL,
  to_date      DATE     NOT NULL
);
ALTER TABLE department_manager ADD PRIMARY KEY (employee_id, dept_number);
ALTER TABLE department_manager ADD FOREIGN KEY (dept_number) REFERENCES department (dept_number) ON DELETE CASCADE;
ALTER TABLE department_manager ADD FOREIGN KEY (employee_id) REFERENCES employee (id) ON DELETE CASCADE;
