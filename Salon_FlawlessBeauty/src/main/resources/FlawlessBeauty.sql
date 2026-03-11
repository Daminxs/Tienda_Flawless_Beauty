
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

-- 3) TABLA CATEGORIA SERVICIO
CREATE TABLE categoria_servicio (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

-- 4) TABLA CATEGORIA PRODUCTO
CREATE TABLE categoria_producto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

-- 5) TABLA ROL
CREATE TABLE rol (
    id_rol INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
);

-- 6) TABLA USUARIO
CREATE TABLE usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    telefono VARCHAR(20) NOT NULL,
    password VARCHAR(255) NOT NULL,
    activo BOOLEAN DEFAULT TRUE
);

-- 7) TABLA USUARIO_ROL
CREATE TABLE usuario_rol (
    id_usuario INT,
    id_rol INT,
    PRIMARY KEY (id_usuario, id_rol),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario),
    FOREIGN KEY (id_rol) REFERENCES rol(id_rol)
);

-- 8) TABLA SERVICIO
CREATE TABLE servicio (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(8,2) NOT NULL,
    categoria_id INT NOT NULL,
    FOREIGN KEY (categoria_id) REFERENCES categoria_servicio(id)
);

-- 9) TABLA PRODUCTO
CREATE TABLE producto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(8,2) NOT NULL,
    stock INT NOT NULL,
    imagen VARCHAR(255),
    categoria_id INT,
    FOREIGN KEY (categoria_id) REFERENCES categoria_producto(id)
);

-- 10) TABLA PROMOCION
CREATE TABLE promocion (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    descripcion TEXT,
    descuento DECIMAL(5,2) NOT NULL
);

-- 11) TABLA CITA
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

-- 12) TABLA RESERVA
CREATE TABLE reserva (
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(20) UNIQUE,
    usuario_id INT NOT NULL,
    producto_id INT NOT NULL,
    cantidad INT NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id_usuario),
    FOREIGN KEY (producto_id) REFERENCES producto(id)
);

-- ROLES POR DEFECTO
INSERT INTO rol (nombre) VALUES
('ROLE_ADMIN'),
('ROLE_USER');

-- USUARIO ADMIN
INSERT INTO usuario (nombre, correo, telefono, password, activo) VALUES
('Administrador', 'admin@flawless.com', '88888888', '1234', TRUE);

INSERT INTO usuario_rol (id_usuario, id_rol) VALUES
(1, 1);

-- USUARIO CLIENTE
INSERT INTO usuario (nombre, correo, telefono, password, activo) VALUES
('Cliente Demo', 'cliente@flawless.com', '88888888', '1234', TRUE);

INSERT INTO usuario_rol (id_usuario, id_rol) VALUES
(2, 2);

-- CATEGORIAS SERVICIO
INSERT INTO categoria_servicio (nombre) VALUES
('Uñas'),
('Pestañas'),
('Cejas'),
('Maquillaje');

-- CATEGORIAS PRODUCTO
INSERT INTO categoria_producto (nombre) VALUES
('Labiales'),
('Collares'),
('Anillos'),
('Mascarillas'),
('Aretes'),
('Pulseras'),
('Bases'),
('Correctores'),
('Delineadores'),
('Rubores');

-- SERVICIOS
INSERT INTO servicio (nombre, descripcion, precio, categoria_id) VALUES
('Manicure Tradicional', 'Limpieza y esmalte basico', 6000.00, 1),
('Extension de Pestañas', 'Efecto natural', 25000.00, 2),
('Diseño de Cejas', 'Perfilado profesional', 8000.00, 3),
('Maquillaje Profesional', 'Para eventos especiales', 30000.00, 4);

-- PRODUCTOS
INSERT INTO producto (nombre, descripcion, precio, stock, categoria_id) VALUES
('Labial Matte Rojo', 'Color rojo intenso', 5000, 20, 1),
('Collar Dorado', 'Collar elegante', 15000, 10, 2),
('Anillo Plata', 'Anillo minimalista', 12000, 15, 3),
('Mascarilla Facial', 'Mascarilla hidratante', 7000, 30, 4);

-- PROMOCIONES
INSERT INTO promocion (titulo, descripcion, descuento) VALUES
('Promo Manicure', '10% de descuento en manicure', 10.00),
('Combo Maquillaje', '15% en maquillaje profesional', 15.00);

-- CITAS DE PRUEBA
INSERT INTO cita (codigo, usuario_id, servicio_id, fecha, hora) VALUES
('CITA-00001', 2, 1, '2026-03-10', '14:00:00');

-- RESERVAS DE PRODUCTOS
INSERT INTO reserva (codigo, usuario_id, producto_id, cantidad) VALUES
('RES-00001', 2, 1, 2);