INSERT INTO department (dept_number, dept_name) VALUES ('D001', 'Accounting');
INSERT INTO department (dept_number, dept_name) VALUES ('D002', 'Engineering');
INSERT INTO department (dept_number, dept_name) VALUES ('D003', 'Human Resources');
INSERT INTO department (dept_number, dept_name) VALUES ('D004', 'General Management');

INSERT INTO department_employee (id, employee_id, dept_number, from_date, to_date) VALUES (random_uuid(), '15fc23f6-7dfc-4526-bb40-eba11cad292c', 'D001', '2020-10-23', '9999-12-31');
INSERT INTO department_employee (id, employee_id, dept_number, from_date, to_date) VALUES (random_uuid(), '8484dbee-a0a2-4098-a93e-a013bac9e8f6', 'D002', '2020-12-25', '9999-12-31');
INSERT INTO department_employee (id, employee_id, dept_number, from_date, to_date) VALUES (random_uuid(), 'a849d53b-65f7-45e3-976c-b1338e5b7611', 'D003', '2020-10-22', '9999-12-31');
INSERT INTO department_employee (id, employee_id, dept_number, from_date, to_date) VALUES (random_uuid(), '49e5497e-5914-472e-aae4-1dbf5c9d049e', 'D004', '2020-11-05', '9999-12-31');
INSERT INTO department_employee (id, employee_id, dept_number, from_date, to_date) VALUES (random_uuid(), '1561f0f0-f43b-4beb-aba2-c529ea6a1cce', 'D002', '2021-01-22', '9999-12-31');
INSERT INTO department_employee (id, employee_id, dept_number, from_date, to_date) VALUES (random_uuid(), '956ce5e2-2fa0-47d9-928e-48b021227449', 'D002', '2021-07-08', '9999-12-31');
INSERT INTO department_employee (id, employee_id, dept_number, from_date, to_date) VALUES (random_uuid(), '5d2fd144-fa70-4877-b600-0b125680f84c', 'D002', '2021-06-16', '9999-12-31');
INSERT INTO department_employee (id, employee_id, dept_number, from_date, to_date) VALUES (random_uuid(), '95c3316d-fb0d-467c-a218-b8aebe6e3e78', 'D002', '2021-05-19', '9999-12-31');
INSERT INTO department_employee (id, employee_id, dept_number, from_date, to_date) VALUES (random_uuid(), 'e109e17b-bfc6-4f4d-a8b2-0b7e214e5d9b', 'D002', '2021-11-24', '9999-12-31');
INSERT INTO department_employee (id, employee_id, dept_number, from_date, to_date) VALUES (random_uuid(), 'de52aab2-8143-445a-ad17-8d26e8305200', 'D002', '2021-01-06', '9999-12-31');

INSERT INTO department_manager (id, dept_number, employee_id, from_date, to_date) VALUES (random_uuid(), 'D001', '15fc23f6-7dfc-4526-bb40-eba11cad292c', '2020-10-23', '9999-12-31');
INSERT INTO department_manager (id, dept_number, employee_id, from_date, to_date) VALUES (random_uuid(), 'D002', '8484dbee-a0a2-4098-a93e-a013bac9e8f6', '2020-12-25', '9999-12-31');
INSERT INTO department_manager (id, dept_number, employee_id, from_date, to_date) VALUES (random_uuid(), 'D003', 'a849d53b-65f7-45e3-976c-b1338e5b7611', '2020-10-22', '9999-12-31');
INSERT INTO department_manager (id, dept_number, employee_id, from_date, to_date) VALUES (random_uuid(), 'D004', '49e5497e-5914-472e-aae4-1dbf5c9d049e', '2020-11-05', '9999-12-31');