-- SCRIPT INITIAL - Controller App

-- Creación de la base de datos
CREATE DATABASE db_tdc;

-- Creación de un usuario para esa BD
CREATE USER 'user_tdc'@'localhost' IDENTIFIED BY 'password';

-- Otorgar premisos al usuario para esa base y origen
GRANT ALL PRIVILEGES ON db_tdc.* TO 'user_tdc'@'localhost';

-- Actualizar permisos
FLUSH PRIVILEGES;

USE db_tdc;

-- Creación de roles
INSERT INTO tb_role(id, name, created_by, created_on) VALUES(1,'ROLE_USER', 1, now());
INSERT INTO tb_role(id, name, created_by, created_on) VALUES(2,'ROLE_MODERATOR', 1, now());
INSERT INTO tb_role(id, name, created_by, created_on) VALUES(3,'ROLE_ADMIN', 1, now());

-- Creación de admin user
INSERT INTO tb_usuario(id_usuario, nombre, apellido, email, username, password) VALUES(1, 'admin', 'admin', 'admin', 'admin', sha2('admin',256));

-- Creación de relation role user
INSERT INTO user_roles(user_id, role_id) VALUES(1,3);