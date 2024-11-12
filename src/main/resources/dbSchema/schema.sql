CREATE TABLE IF NOT EXISTS users (
    username VARCHAR(64) NOT NULL,
    password VARCHAR(64) NOT NULL
);

CREATE TABLE IF NOT EXISTS user_roles (
    username VARCHAR(64) NOT NULL,
    role_name VARCHAR(64) NOT NULL
);

DELETE FROM user_roles WHERE 1;

DELETE FROM users WHERE 1;

INSERT INTO users (username, password)
VALUES ('devuser', 'dc067f8a150df19383bc33d7ac9032f7');

INSERT INTO user_roles (username, role_name)
VALUES ('devuser', 'SIMPLEUSER');