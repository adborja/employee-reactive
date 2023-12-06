CREATE TABLE sys_user (
    id UUID NOT NULL PRIMARY KEY,
    login VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(256) NOT NULL UNIQUE,
    secret VARCHAR(50) NOT NULL,
    enabled BOOLEAN NOT NULL
);
INSERT INTO sys_user (id, login, email, secret, enabled) VALUES (random_uuid(), 'dborja', 'david.borja@cedesistemas.edu.co', 'dborja', true);
INSERT INTO sys_user (id, login, email, secret, enabled) VALUES (random_uuid(), 'admin', 'admin@cedesistemas.edu.co', 'admin', true);


CREATE TABLE sys_role (
    id UUID NOT NULL PRIMARY KEY,
    role_name VARCHAR(10) NOT NULL UNIQUE
);
INSERT INTO sys_role (id, role_name) VALUES (random_uuid(), 'USER');
INSERT INTO sys_role (id, role_name) VALUES (random_uuid(), 'ADMIN');
INSERT INTO sys_role (id, role_name) VALUES (random_uuid(), 'WRITER');
INSERT INTO sys_role (id, role_name) VALUES (random_uuid(), 'READER');


CREATE TABLE sys_user_role (
    id UUID NOT NULL PRIMARY KEY,
    login VARCHAR(50) NOT NULL,
    role_name VARCHAR(10)
);
ALTER TABLE sys_user_role ADD FOREIGN KEY (login) REFERENCES sys_user (login) ON DELETE CASCADE;
ALTER TABLE sys_user_role ADD FOREIGN KEY (role_name) REFERENCES sys_role (role_name) ON DELETE CASCADE;

INSERT INTO sys_user_role (id, login, role_name) VALUES (random_uuid(), 'dborja', 'USER');
INSERT INTO sys_user_role (id, login, role_name) VALUES (random_uuid(), 'dborja', 'READER');
INSERT INTO sys_user_role (id, login, role_name) VALUES (random_uuid(), 'admin', 'ADMIN');
INSERT INTO sys_user_role (id, login, role_name) VALUES (random_uuid(), 'admin', 'WRITER');
INSERT INTO sys_user_role (id, login, role_name) VALUES (random_uuid(), 'admin', 'READER');
