-- =====================================
-- 1) CREAR USUARIO
-- =====================================
DROP USER IF EXISTS 'flawless_user'@'localhost';
CREATE USER 'flawless_user'@'localhost'
IDENTIFIED BY '123456';

-- =====================================
-- 2) CREAR BASE DE DATOS
-- =====================================
DROP DATABASE IF EXISTS flawless_beauty;
CREATE DATABASE flawless_beauty;
GRANT ALL PRIVILEGES ON flawless_beauty.*
TO 'flawless_user'@'localhost';

FLUSH PRIVILEGES;

USE flawless_beauty;

-- =====================================
-- 3) TABLA CATEGORIA (Servicios)
-- =====================================
CREATE TABLE categoria (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

-- =====================================
-- 4) TABLA SERVICIO
-- =====================================
CREATE TABLE servicio (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(8,2) NOT NULL,
    categoria_id INT NOT NULL,
    FOREIGN KEY (categoria_id) REFERENCES categoria(id)
);

-- =====================================
-- 5) TABLA PRODUCTO
-- =====================================
CREATE TABLE producto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(8,2) NOT NULL,
    stock INT NOT NULL
);

-- =====================================
-- 6) TABLA PROMOCION
-- =====================================
CREATE TABLE promocion (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    descripcion TEXT,
    descuento DECIMAL(5,2) NOT NULL
);

-- =====================================
-- 7) TABLA RESERVA SERVICIO
-- =====================================
CREATE TABLE reserva_servicio (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_cliente VARCHAR(100) NOT NULL,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    servicio_id INT NOT NULL,
    FOREIGN KEY (servicio_id) REFERENCES servicio(id)
);

-- =====================================
-- 8) TABLA RESERVA PRODUCTO
-- =====================================
CREATE TABLE reserva_producto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_cliente VARCHAR(100) NOT NULL,
    producto_id INT NOT NULL,
    cantidad INT NOT NULL,
    FOREIGN KEY (producto_id) REFERENCES producto(id)
);

-- Datos de prueba para la tienda de Flawless Beauty

INSERT INTO categoria (nombre) VALUES
('Uñas'),
('Pestañas'),
('Cejas'),
('Maquillaje');

-- Servicios
INSERT INTO servicio (nombre, descripcion, precio, categoria_id) VALUES
('Manicure Tradicional', 'Limpieza y esmalte básico', 6000.00, 1),
('Extensión de Pestañas', 'Efecto natural', 25000.00, 2),
('Diseño de Cejas', 'Perfilado profesional', 8000.00, 3),
('Maquillaje Profesional', 'Para eventos especiales', 30000.00, 4);

-- Productos
INSERT INTO producto (nombre, descripcion, precio, stock) VALUES
('Labial Matte', 'Color rojo intenso', 5000.00, 20),
('Collar Dorado', 'Joyería elegante', 15000.00, 10);

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