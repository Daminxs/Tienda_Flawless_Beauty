
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

-- 3) TABLA CATEGORIA (Servicios)
CREATE TABLE categoria_servicio (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

-- 4) TABLA CATEGORIA (Productos)
CREATE TABLE categoria_producto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

-- 5) TABLA SERVICIO
CREATE TABLE servicio (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(8,2) NOT NULL,
    categoria_id INT NOT NULL,
    FOREIGN KEY (categoria_id) REFERENCES categoria_servicio(id)
);

-- 6) TABLA PRODUCTO
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

-- 7) TABLA PROMOCION
CREATE TABLE promocion (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    descripcion TEXT,
    descuento DECIMAL(5,2) NOT NULL
);

-- 8) TABLA RESERVA SERVICIO
CREATE TABLE reserva_servicio (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_cliente VARCHAR(100) NOT NULL,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    servicio_id INT NOT NULL,
    FOREIGN KEY (servicio_id) REFERENCES servicio(id)
);

-- 9) TABLA RESERVA PRODUCTO
CREATE TABLE reserva_producto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_cliente VARCHAR(100) NOT NULL,
    producto_id INT NOT NULL,
    cantidad INT NOT NULL,
    FOREIGN KEY (producto_id) REFERENCES producto(id)
);

-- 10) TABLA ROL
CREATE TABLE rol (
    id_rol INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
);

-- 11) TABLA USUARIO
CREATE TABLE usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    activo BOOLEAN DEFAULT TRUE
);

-- 12) TABLA USUARIO_ROL
CREATE TABLE usuario_rol (
    id_usuario INT,
    id_rol INT,
    PRIMARY KEY (id_usuario, id_rol),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario),
    FOREIGN KEY (id_rol) REFERENCES rol(id_rol)
);

-- Roles por defecto
INSERT INTO rol (nombre) VALUES
('ROLE_ADMIN'),
('ROLE_USER');

-- Datos de prueba

INSERT INTO categoria_servicio (nombre) VALUES
('Uñas'),
('Pestañas'),
('Cejas'),
('Maquillaje');

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

-- Servicios
INSERT INTO servicio (nombre, descripcion, precio, categoria_id) VALUES
('Manicure Tradicional', 'Limpieza y esmalte básico', 6000.00, 1),
('Extensión de Pestañas', 'Efecto natural', 25000.00, 2),
('Diseño de Cejas', 'Perfilado profesional', 8000.00, 3),
('Maquillaje Profesional', 'Para eventos especiales', 30000.00, 4);

-- Productos
INSERT INTO producto (nombre, descripcion, precio, stock, categoria_id) VALUES
('Labial Matte Rojo', 'Color rojo intenso', 5000, 20, 1),
('Collar Dorado', 'Collar elegante', 15000, 10, 2),
('Anillo Plata', 'Anillo minimalista', 12000, 15, 3),
('Mascarilla Facial', 'Mascarilla hidratante', 7000, 30, 4);

-- Promociones
INSERT INTO promocion (titulo, descripcion, descuento) VALUES
('Promo Manicure', '10% de descuento en manicure', 10.00),
('Combo Maquillaje', '15% en maquillaje profesional', 15.00);

-- Reservas de servicio
INSERT INTO reserva_servicio (nombre_cliente, fecha, hora, servicio_id) VALUES
('Ana Rodríguez', '2026-03-10', '14:00:00', 1);

-- Reservas de producto
INSERT INTO reserva_producto (nombre_cliente, producto_id, cantidad) VALUES
('María López', 1, 2);