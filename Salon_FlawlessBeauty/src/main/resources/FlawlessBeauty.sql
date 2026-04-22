
-- 1) CREAR USUARIO
DROP USER IF EXISTS 'flawless_user'@'localhost';
CREATE USER 'flawless_user'@'localhost'
IDENTIFIED BY '123456';

-- 2) CREAR BASE DE DATOS
DROP DATABASE IF EXISTS flawless_beauty;
CREATE DATABASE flawless_beauty;

GRANT ALL PRIVILEGES ON flawless_beauty.*
TO 'flawless_user'@'localhost';

FLUSH PRIVILEGES;

USE flawless_beauty;

-- 3) TABLA CATEGORIA (UNIFICADA)
CREATE TABLE categoria (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    tipo VARCHAR(20) NOT NULL
);

-- 4) TABLA ROL
CREATE TABLE rol (
    id_rol INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
);

-- 5) TABLA USUARIO
CREATE TABLE usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    telefono VARCHAR(20) NOT NULL,
    password VARCHAR(255) NOT NULL,
    activo BOOLEAN DEFAULT TRUE
);

-- 6) TABLA USUARIO_ROL
CREATE TABLE usuario_rol (
    id_usuario INT,
    id_rol INT,
    PRIMARY KEY (id_usuario, id_rol),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario),
    FOREIGN KEY (id_rol) REFERENCES rol(id_rol)
);

-- 7) TABLA SERVICIO
CREATE TABLE servicio (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(8,2) NOT NULL,
    categoria_id INT NOT NULL,
    FOREIGN KEY (categoria_id) REFERENCES categoria(id)
);

-- 8) TABLA PRODUCTO
CREATE TABLE producto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(8,2) NOT NULL,
    stock INT NOT NULL,
    imagen VARCHAR(255),
    categoria_id INT,
    FOREIGN KEY (categoria_id) REFERENCES categoria(id)
);

-- 9) TABLA PROMOCION
CREATE TABLE promocion (
    id           INT AUTO_INCREMENT PRIMARY KEY,
    titulo       VARCHAR(100) NOT NULL,
    descripcion  TEXT,
    descuento    DECIMAL(5,2) NOT NULL,
    imagen       VARCHAR(255) NULL,
    activo       BOOLEAN      NOT NULL DEFAULT TRUE,
    fecha_inicio DATE         NULL,
    fecha_fin    DATE         NULL
);

-- 10) TABLA CITA
CREATE TABLE cita (
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(20) UNIQUE,
    usuario_id INT NOT NULL,
    servicio_id INT NOT NULL,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id_usuario),
    FOREIGN KEY (servicio_id) REFERENCES servicio(id)
);

-- 11) TABLA RESERVA
CREATE TABLE reserva (
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(20) UNIQUE,
    usuario_id INT NOT NULL,
    producto_id INT NOT NULL,
    cantidad INT NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id_usuario),
    FOREIGN KEY (producto_id) REFERENCES producto(id)
);

-- 12) RESET PASSWORD
CREATE TABLE reset_password (
    id INT AUTO_INCREMENT PRIMARY KEY,
    token VARCHAR(255) NOT NULL UNIQUE,
    usuario_id INT NOT NULL UNIQUE,
    fecha_expiracion DATETIME NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id_usuario) ON DELETE CASCADE
);

-- DATOS INICIALES

INSERT INTO categoria (nombre, tipo) VALUES
('Maquillajes', 'SERVICIO'),
('Labiales', 'PRODUCTO');

INSERT INTO rol (nombre) VALUES
('ROLE_ADMIN'),
('ROLE_USER');

INSERT INTO usuario (nombre, correo, telefono, password, activo) VALUES
('Administrador', 'admin@flawless.com', '88888888',
'$2a$10$KIXk1H0nR2fEMM0lP0W5pO8nKXu95syEHCqB6fRwGO6zkRgZNpmjS',
TRUE);

INSERT INTO usuario_rol (id_usuario, id_rol) VALUES
(1, 1);

INSERT INTO usuario (nombre, correo, telefono, password, activo) VALUES
('Cliente Demo', 'cliente@flawless.com', '88888888',
'$2a$10$KIXk1H0nR2fEMM0lP0W5pO8nKXu95syEHCqB6fRwGO6zkRgZNpmjS',
TRUE);

INSERT INTO usuario_rol (id_usuario, id_rol) VALUES
(2, 2);

UPDATE usuario
SET password='$2a$10$izwsyT9s9Z2HCae9NxPSYum0.Zg.QNWRfqkrSSLmsLNI.RWywvbNa' -- 1234 password
WHERE correo='admin@flawless.com';

UPDATE usuario
SET password='$2a$10$izwsyT9s9Z2HCae9NxPSYum0.Zg.QNWRfqkrSSLmsLNI.RWywvbNa' -- 1234 password
WHERE correo='cliente@flawless.com';