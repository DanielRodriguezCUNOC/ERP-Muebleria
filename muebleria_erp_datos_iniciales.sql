-- ============================================================
-- MUEBLERIA ERP - DATOS INICIALES
-- PostgreSQL
--
-- Requisitos:
--   4 empleados (uno por cada area)
--   30 productos
--   8 proveedores
--   20 clientes
--   15 compras, cada una con >= 1 detalle
--   25 ventas, cada una con >= 1 detalle
-- ============================================================

BEGIN;

-- ============================================================
-- 0. CONFIGURACIÓN GLOBAL
-- ============================================================
INSERT INTO configuracion_sistema (id, tasa_iva, metodo_valoracion, resolucion_facturas, serie_facturas, correlativo_siguiente) 
VALUES (1, 0.1200, 'PEPS', 'RES-2026-001', 'A', 26)
ON CONFLICT (id) DO NOTHING;

-- ============================================================
-- 1. AREAS
-- ============================================================

INSERT INTO area (id, nombre) VALUES
    (1, 'Administración'),
    (2, 'Compras'),
    (3, 'Inventario'),
    (4, 'Ventas')
ON CONFLICT (id) DO NOTHING;

-- ============================================================
-- 2. ROLES
-- ============================================================

INSERT INTO rol (id, rol) VALUES
    (1, 'Administrador'),
    (2, 'Comprador'),
    (3, 'Encargado de Inventario'),
    (4, 'Vendedor')
ON CONFLICT (id) DO NOTHING;

-- ============================================================
-- 3. PERMISOS
-- ============================================================

INSERT INTO permiso (id, codigo, descripcion) VALUES
    (1, 'USUARIOS_VER', 'Consultar usuarios y empleados'),
    (2, 'USUARIOS_GESTIONAR', 'Crear y modificar usuarios y empleados'),
    (3, 'COMPRAS_VER', 'Consultar compras'),
    (4, 'COMPRAS_GESTIONAR', 'Registrar y gestionar compras'),
    (5, 'INVENTARIO_VER', 'Consultar existencias y movimientos'),
    (6, 'INVENTARIO_GESTIONAR', 'Gestionar inventario y movimientos'),
    (7, 'VENTAS_VER', 'Consultar ventas'),
    (8, 'VENTAS_GESTIONAR', 'Registrar y gestionar ventas'),
    (9, 'REPORTES_VER', 'Consultar reportes del sistema'),
    (10, 'ROLES_GESTIONAR', 'Registrar y gestionar roles'),
    (11, 'PERMISOS_GESTIONAR', 'Registrar y gestionar permisos a roles'),
    (12, 'CONFIGURACION_GESTIONAR', 'Gestión de las configuraciones del sistema')
ON CONFLICT (id) DO NOTHING;

-- ============================================================
-- 4. RELACION ROL - PERMISO
-- ============================================================

INSERT INTO rol_permiso (rol_id, permiso_id) VALUES
    -- Administrador
    (1, 1), (1, 2), (1, 3), (1, 4), (1, 5),
    (1, 6), (1, 7), (1, 8), (1, 9), (1, 10),
    (1, 11), (1, 12),

    -- Comprador
    (2, 3), (2, 4), (2, 9),

    -- Inventario
    (3, 5), (3, 6), (3, 9),

    -- Vendedor
    (4, 7), (4, 8), (4, 9)
ON CONFLICT (rol_id, permiso_id) DO NOTHING;

-- ============================================================
-- 5. EMPLEADOS / USUARIOS
-- ============================================================

INSERT INTO empleado
    (id, nombre, usuario, rol_id, password, dpi, numero_telefono, activo, area_id)
VALUES
    (1, 'Ana Martinez',    'admin',      1, '$2a$10$tRyqqLMT0lcY4IX.OHzTbOqMwJYQKTZSnnXwuCICVITflRmrpky3W',  '1111111110101', '5551-0001', TRUE, 1),
    (2, 'Carlos Lopez',    'compras',    2, '$2a$10$vLbgwaOq2mMtXpahljpM9eH5v.shg3cldJECYiLwOgx4SdLpZ7782', '2222222220102', '5551-0002', TRUE, 2),
    (3, 'Maria Gonzalez',  'inventario', 3, '$2a$10$YwkGrFPr.N4vLzorR5o5c.C.TUgoQcMnpSRJeDbQILdL4HH7B9Uya', '3333333330103', '5551-0003', TRUE, 3),
    (4, 'Juan Ramirez',    'ventas',     4, '$2a$10$U.MiPRm1XRG.OggjGaNc9elgJQparLct636F4YfuC75Kp0V7DzkNa',  '4444444440104', '5551-0004', TRUE, 4)
ON CONFLICT (id) DO NOTHING;

-- ============================================================
-- 6. PROVEEDORES (8)
-- ============================================================

INSERT INTO proveedor
    (id, nombre, telefono_contacto, direccion, activo)
VALUES
    (1, 'Maderas del Pacifico',       '5552-1001', 'Zona 12, Ciudad de Guatemala', TRUE),
    (2, 'Herrajes y Accesorios GT',   '5552-1002', 'Zona 7, Ciudad de Guatemala', TRUE),
    (3, 'Tapizados Centroamericanos', '5552-1003', 'Mixco, Guatemala', TRUE),
    (4, 'Maderas Selectas',            '5552-1004', 'Villa Nueva, Guatemala', TRUE),
    (5, 'Distribuidora El Roble',     '5552-1005', 'Zona 11, Ciudad de Guatemala', TRUE),
    (6, 'Importadora Hogar',           '5552-1006', 'Zona 9, Ciudad de Guatemala', TRUE),
    (7, 'Acabados y Barnices GT',      '5552-1007', 'Zona 3, Ciudad de Guatemala', TRUE),
    (8, 'Cristales y Complementos',   '5552-1008', 'Zona 18, Ciudad de Guatemala', TRUE)
ON CONFLICT (id) DO NOTHING;

-- ============================================================
-- 7. PRODUCTOS (30)
-- ============================================================

INSERT INTO producto
    (id, nombre, categoria, precio, activo, sku, existencia_minima)
VALUES
    (1,  'Mesa de comedor Oslo',        'Mesas',        2450.00, TRUE, 'MES-001', 5),
    (2,  'Mesa de centro Roma',         'Mesas',        1250.00, TRUE, 'MES-002', 5),
    (3,  'Escritorio Ejecutivo',        'Escritorios',  3200.00, TRUE, 'ESC-001', 3),
    (4,  'Escritorio Compacto',         'Escritorios',  1750.00, TRUE, 'ESC-002', 3),
    (5,  'Silla Ejecutiva',             'Sillas',       1350.00, TRUE, 'SIL-001', 8),
    (6,  'Silla de Comedor',            'Sillas',        650.00, TRUE, 'SIL-002', 10),
    (7,  'Sillon Reclinable',           'Sillones',     2850.00, TRUE, 'SIL-003', 4),
    (8,  'Sofa 3 Plazas',               'Sofas',        4950.00, TRUE, 'SOF-001', 4),
    (9,  'Sofa 2 Plazas',               'Sofas',        3750.00, TRUE, 'SOF-002', 4),
    (10, 'Librera Alta',                'Libreras',     2100.00, TRUE, 'LIB-001', 3),
    (11, 'Librera Baja',                'Libreras',     1450.00, TRUE, 'LIB-002', 3),
    (12, 'Ropero de 3 Puertas',         'Roperos',      3850.00, TRUE, 'ROP-001', 3),
    (13, 'Ropero de 2 Puertas',         'Roperos',      2950.00, TRUE, 'ROP-002', 3),
    (14, 'Comoda 6 Gavetas',            'Comodas',      2250.00, TRUE, 'COM-001', 4),
    (15, 'Comoda 4 Gavetas',            'Comodas',      1750.00, TRUE, 'COM-002', 4),
    (16, 'Cabecera King',               'Dormitorio',   1850.00, TRUE, 'DOR-001', 3),
    (17, 'Cabecera Queen',              'Dormitorio',   1650.00, TRUE, 'DOR-002', 3),
    (18, 'Cama King',                   'Dormitorio',   5200.00, TRUE, 'DOR-003', 2),
    (19, 'Cama Queen',                  'Dormitorio',   4650.00, TRUE, 'DOR-004', 2),
    (20, 'Mesa de Noche',               'Dormitorio',   1050.00, TRUE, 'DOR-005', 5),
    (21, 'Banco de Madera',             'Bancos',        850.00, TRUE, 'BAN-001', 5),
    (22, 'Banco Tapizado',              'Bancos',       1150.00, TRUE, 'BAN-002', 5),
    (23, 'Vitrina de Vidrio',           'Vitrinas',     3350.00, TRUE, 'VIT-001', 2),
    (24, 'Mueble para TV',              'Entretenimiento', 2850.00, TRUE, 'TV-001', 3),
    (25, 'Centro de Entretenimiento',   'Entretenimiento', 4100.00, TRUE, 'TV-002', 2),
    (26, 'Gabinete de Cocina',          'Cocina',       2400.00, TRUE, 'COC-001', 4),
    (27, 'Isla de Cocina',              'Cocina',       3600.00, TRUE, 'COC-002', 2),
    (28, 'Zapatera',                    'Organizacion',  1450.00, TRUE, 'ORG-001', 3),
    (29, 'Perchero de Madera',          'Organizacion',   750.00, TRUE, 'ORG-002', 5),
    (30, 'Espejo Decorativo',           'Decoracion',   1200.00, TRUE, 'DEC-001', 4)
ON CONFLICT (id) DO NOTHING;

-- ============================================================
-- 8. CLIENTES (20)
-- ============================================================

INSERT INTO cliente
    (id, nombre, nit, direccion, telefono, activo)
VALUES
    (1,  'Roberto Castillo',    '10000001-1', 'Zona 1, Ciudad de Guatemala',  '5553-0001', TRUE),
    (2,  'Laura Mendoza',       '10000002-2', 'Zona 10, Ciudad de Guatemala', '5553-0002', TRUE),
    (3,  'Pedro Alvarado',      '10000003-3', 'Zona 15, Ciudad de Guatemala', '5553-0003', TRUE),
    (4,  'Sofia Herrera',       '10000004-4', 'Mixco, Guatemala',              '5553-0004', TRUE),
    (5,  'Miguel Torres',       '10000005-5', 'Villa Nueva, Guatemala',         '5553-0005', TRUE),
    (6,  'Andrea Morales',      '10000006-6', 'Zona 11, Ciudad de Guatemala',  '5553-0006', TRUE),
    (7,  'Luis Perez',          '10000007-7', 'Zona 7, Ciudad de Guatemala',   '5553-0007', TRUE),
    (8,  'Gabriela Rojas',      '10000008-8', 'Zona 12, Ciudad de Guatemala',  '5553-0008', TRUE),
    (9,  'Fernando Diaz',       '10000009-9', 'Zona 5, Ciudad de Guatemala',   '5553-0009', TRUE),
    (10, 'Valeria Ortiz',       '10000010-0', 'Zona 14, Ciudad de Guatemala',  '5553-0010', TRUE),
    (11, 'Jorge Vasquez',       '10000011-1', 'Zona 6, Ciudad de Guatemala',   '5553-0011', TRUE),
    (12, 'Paola Reyes',         '10000012-2', 'Zona 16, Ciudad de Guatemala',  '5553-0012', TRUE),
    (13, 'Diego Navarro',       '10000013-3', 'Fraijanes, Guatemala',           '5553-0013', TRUE),
    (14, 'Camila Fuentes',      '10000014-4', 'Santa Catarina Pinula',          '5553-0014', TRUE),
    (15, 'Oscar Ramirez',       '10000015-5', 'Zona 4, Ciudad de Guatemala',   '5553-0015', TRUE),
    (16, 'Natalia Cabrera',     '10000016-6', 'Zona 13, Ciudad de Guatemala',  '5553-0016', TRUE),
    (17, 'Ricardo Salazar',     '10000017-7', 'San Miguel Petapa, Guatemala',   '5553-0017', TRUE),
    (18, 'Daniela Pineda',      '10000018-8', 'Zona 8, Ciudad de Guatemala',   '5553-0018', TRUE),
    (19, 'Esteban Molina',      '10000019-9', 'Amatitlan, Guatemala',            '5553-0019', TRUE),
    (20, 'Mariana Soto',        '10000020-0', 'Zona 17, Ciudad de Guatemala',  '5553-0020', TRUE)
ON CONFLICT (id) DO NOTHING;

-- ============================================================
-- 9. COMPRAS (15)
-- ============================================================

INSERT INTO compra
    (id, fecha_compra, empleado_id)
VALUES
    (1,  '2026-01-05 09:00:00', 2),
    (2,  '2026-01-12 10:00:00', 2),
    (3,  '2026-01-20 09:30:00', 2),
    (4,  '2026-02-03 11:00:00', 2),
    (5,  '2026-02-14 09:15:00', 2),
    (6,  '2026-02-25 10:30:00', 2),
    (7,  '2026-03-04 08:45:00', 2),
    (8,  '2026-03-16 11:15:00', 2),
    (9,  '2026-03-28 09:00:00', 2),
    (10, '2026-04-08 10:00:00', 2),
    (11, '2026-04-20 09:45:00', 2),
    (12, '2026-05-05 11:00:00', 2),
    (13, '2026-05-18 10:15:00', 2),
    (14, '2026-06-02 09:30:00', 2),
    (15, '2026-06-15 10:45:00', 2)
ON CONFLICT (id) DO NOTHING;

INSERT INTO compra_proveedor (compra_id, proveedor_id) VALUES
    (1, 1), (2, 2), (3, 3), (4, 4), (5, 5),
    (6, 6), (7, 7), (8, 8), (9, 1), (10, 2),
    (11, 3), (12, 4), (13, 5), (14, 6), (15, 7)
ON CONFLICT (compra_id, proveedor_id) DO NOTHING;

-- Cada compra tiene uno o mas productos.
-- Se agregan precios/costos para que los datos sean utiles
-- para reportes de compras y costos de inventario.
INSERT INTO detalle_compra
    (compra_id, producto_id, proveedor_id, cantidad, precio_unitario, costo_total)
VALUES
    (1,  1, 1,  8, 1500.00, 12000.00),
    (1,  2, 1, 10,  700.00, 7000.00),
    (2,  3, 2,  6, 2100.00, 12600.00),
    (2,  4, 2,  8, 1100.00, 8800.00),
    (3,  5, 3, 12,  800.00, 9600.00),
    (3,  6, 3, 20,  380.00, 7600.00),
    (4,  7, 4,  6, 1750.00, 10500.00),
    (4,  8, 4,  5, 3200.00, 16000.00),
    (5,  9, 5,  6, 2400.00, 14400.00),
    (5, 10, 5,  8, 1350.00, 10800.00),
    (6, 11, 6, 10,  900.00, 9000.00),
    (6, 12, 6,  5, 2500.00, 12500.00),
    (7, 13, 7,  6, 1900.00, 11400.00),
    (7, 14, 7,  8, 1400.00, 11200.00),
    (8, 15, 8, 10, 1050.00, 10500.00),
    (8, 16, 8,  6, 1150.00, 6900.00),
    (9, 17, 1,  8, 1000.00, 8000.00),
    (9, 18, 1,  4, 3300.00, 13200.00),
    (10, 19, 2, 5, 3000.00, 15000.00),
    (10, 20, 2, 10, 650.00, 6500.00),
    (11, 21, 3, 12, 500.00, 6000.00),
    (11, 22, 3, 10, 700.00, 7000.00),
    (12, 23, 4, 5, 2200.00, 11000.00),
    (12, 24, 4, 6, 1700.00, 10200.00),
    (13, 25, 5, 4, 2600.00, 10400.00),
    (13, 26, 5, 8, 1450.00, 11600.00),
    (14, 27, 6, 5, 2200.00, 11000.00),
    (14, 28, 6, 8, 850.00, 6800.00),
    (15, 29, 7, 12, 450.00, 5400.00),
    (15, 30, 7, 8, 700.00, 5600.00)
ON CONFLICT (compra_id, producto_id) DO NOTHING;

-- ============================================================
-- 10. LOTES
-- ============================================================
-- Se crean lotes a partir de las compras para disponer de una
-- base de datos que permita demostrar UEPS/PEPS.
--
-- Nota: detalle_compra_id se conserva como el valor de la compra
-- de origen. El esquema original no tiene una PK simple en
-- detalle_compra para poder declarar FK directa.

INSERT INTO lote
    (id, detalle_compra_id, producto_id, creado_en, costo_unitario,
     cantidad_disponible, cantidad_inicial)
VALUES
    (1,  1,  1,  '2026-01-05 09:10:00', 1500.00, 8, 8),
    (2,  2,  2,  '2026-01-05 09:10:00',  700.00, 10, 10),
    (3,  3,  3,  '2026-01-12 10:10:00', 2100.00, 6, 6),
    (4,  4,  4,  '2026-01-12 10:10:00', 1100.00, 8, 8),
    (5,  5,  5,  '2026-01-20 09:40:00',  800.00, 12, 12),
    (6,  6,  6,  '2026-01-20 09:40:00',  380.00, 20, 20),
    (7,  7,  7,  '2026-02-03 11:10:00', 1750.00, 6, 6),
    (8,  8,  8,  '2026-02-03 11:10:00', 3200.00, 5, 5),
    (9,  9,  9,  '2026-02-14 09:25:00', 2400.00, 6, 6),
    (10, 10, 10, '2026-02-14 09:25:00', 1350.00, 8, 8),
    (11, 11, 11, '2026-02-25 10:40:00',  900.00, 10, 10),
    (12, 12, 12, '2026-02-25 10:40:00', 2500.00, 5, 5),
    (13, 13, 13, '2026-03-04 08:55:00', 1900.00, 6, 6),
    (14, 14, 14, '2026-03-04 08:55:00', 1400.00, 8, 8),
    (15, 15, 15, '2026-03-16 11:25:00', 1050.00, 10, 10),
    (16, 16, 16, '2026-03-16 11:25:00', 1150.00, 6, 6),
    (17, 17, 17, '2026-03-28 09:10:00', 1000.00, 8, 8),
    (18, 18, 18, '2026-03-28 09:10:00', 3300.00, 4, 4),
    (19, 19, 19, '2026-04-08 10:10:00', 3000.00, 5, 5),
    (20, 20, 20, '2026-04-08 10:10:00',  650.00, 10, 10),
    (21, 21, 21, '2026-04-20 09:55:00',  500.00, 12, 12),
    (22, 22, 22, '2026-04-20 09:55:00',  700.00, 10, 10),
    (23, 23, 23, '2026-05-05 11:10:00', 2200.00, 5, 5),
    (24, 24, 24, '2026-05-05 11:10:00', 1700.00, 6, 6),
    (25, 25, 25, '2026-05-18 10:25:00', 2600.00, 4, 4),
    (26, 26, 26, '2026-05-18 10:25:00', 1450.00, 8, 8),
    (27, 27, 27, '2026-06-02 09:40:00', 2200.00, 5, 5),
    (28, 28, 28, '2026-06-02 09:40:00',  850.00, 8, 8),
    (29, 29, 29, '2026-06-15 10:55:00',  450.00, 12, 12),
    (30, 30, 30, '2026-06-15 10:55:00',  700.00, 8, 8)
ON CONFLICT (id) DO NOTHING;

-- ============================================================
-- 11. INVENTARIO
-- ============================================================

INSERT INTO inventario (producto_id, existencia)
SELECT
    p.id,
    COALESCE(SUM(l.cantidad_disponible), 0)::INTEGER
FROM producto p
LEFT JOIN lote l ON l.producto_id = p.id
GROUP BY p.id
ON CONFLICT (producto_id) DO NOTHING;

-- ============================================================
-- 12. VENTAS (25)
-- ============================================================
-- IVA utilizado: 12%, correspondiente a Guatemala.
-- Los totales se calculan sobre los detalles.

INSERT INTO venta
    (id, empleado_id, cliente_id, fecha_venta, sub_total, iva, total)
VALUES
    (1,  4,  1, '2026-06-20 09:00:00', 2450.00, 294.00, 2744.00),
    (2,  4,  2, '2026-06-20 10:30:00', 1250.00, 150.00, 1400.00),
    (3,  4,  3, '2026-06-21 11:00:00', 3200.00, 384.00, 3584.00),
    (4,  4,  4, '2026-06-21 14:00:00', 1750.00, 210.00, 1960.00),
    (5,  4,  5, '2026-06-22 09:30:00', 1350.00, 162.00, 1512.00),
    (6,  4,  6, '2026-06-22 13:00:00', 650.00, 78.00, 728.00),
    (7,  4,  7, '2026-06-23 10:00:00', 2850.00, 342.00, 3192.00),
    (8,  4,  8, '2026-06-23 15:30:00', 4950.00, 594.00, 5544.00),
    (9,  4,  9, '2026-06-24 09:15:00', 3750.00, 450.00, 4200.00),
    (10, 4, 10, '2026-06-24 12:00:00', 2100.00, 252.00, 2352.00),
    (11, 4, 11, '2026-06-25 10:15:00', 1450.00, 174.00, 1624.00),
    (12, 4, 12, '2026-06-25 16:00:00', 3850.00, 462.00, 4312.00),
    (13, 4, 13, '2026-06-26 09:45:00', 2950.00, 354.00, 3304.00),
    (14, 4, 14, '2026-06-26 13:30:00', 2250.00, 270.00, 2520.00),
    (15, 4, 15, '2026-06-27 10:00:00', 1750.00, 210.00, 1960.00),
    (16, 4, 16, '2026-06-27 14:30:00', 1850.00, 222.00, 2072.00),
    (17, 4, 17, '2026-06-28 09:30:00', 1650.00, 198.00, 1848.00),
    (18, 4, 18, '2026-06-28 12:30:00', 5200.00, 624.00, 5824.00),
    (19, 4, 19, '2026-06-29 10:45:00', 4650.00, 558.00, 5208.00),
    (20, 4, 20, '2026-06-29 15:00:00', 1050.00, 126.00, 1176.00),
    (21, 4, 1, '2026-06-30 09:00:00', 850.00, 102.00, 952.00),
    (22, 4, 2, '2026-06-30 11:30:00', 1150.00, 138.00, 1288.00),
    (23, 4, 3, '2026-07-01 10:00:00', 3350.00, 402.00, 3752.00),
    (24, 4, 4, '2026-07-01 13:00:00', 2850.00, 342.00, 3192.00),
    (25, 4, 5, '2026-07-02 10:30:00', 4100.00, 492.00, 4592.00)
ON CONFLICT (id) DO NOTHING;

INSERT INTO detalle_venta
    (venta_id, producto_id, cantidad, precio_unitario)
VALUES
    (1,  1,  1, 2450.00),
    (2,  2,  1, 1250.00),
    (3,  3,  1, 3200.00),
    (4,  4,  1, 1750.00),
    (5,  5,  1, 1350.00),
    (6,  6,  1,  650.00),
    (7,  7,  1, 2850.00),
    (8,  8,  1, 4950.00),
    (9,  9,  1, 3750.00),
    (10, 10, 1, 2100.00),
    (11, 11, 1, 1450.00),
    (12, 12, 1, 3850.00),
    (13, 13, 1, 2950.00),
    (14, 14, 1, 2250.00),
    (15, 15, 1, 1750.00),
    (16, 16, 1, 1850.00),
    (17, 17, 1, 1650.00),
    (18, 18, 1, 5200.00),
    (19, 19, 1, 4650.00),
    (20, 20, 1, 1050.00),
    (21, 21, 1, 850.00),
    (22, 22, 1, 1150.00),
    (23, 23, 1, 3350.00),
    (24, 24, 1, 2850.00),
    (25, 25, 1, 4100.00)
ON CONFLICT (venta_id, producto_id) DO NOTHING;

-- ============================================================
-- 13. ACTUALIZAR INVENTARIO DESPUES DE LAS VENTAS
-- ============================================================

UPDATE inventario i
SET existencia = GREATEST(
    0,
    (
        SELECT COALESCE(SUM(l.cantidad_disponible), 0)
        FROM lote l
        WHERE l.producto_id = i.producto_id
    )
    - COALESCE(
        (
            SELECT SUM(dv.cantidad)
            FROM detalle_venta dv
            WHERE dv.producto_id = i.producto_id
        ),
        0
    )
)::INTEGER;

-- ============================================================
-- 14. LOGS DE INVENTARIO
-- ============================================================
-- Se registra el resultado final de las existencias despues
-- de las operaciones de carga inicial.

INSERT INTO inventario_log
    (inventario_id, cantidad_cambio, existencia_resultante,
     tipo_movimiento, creado_en, origen_tipo, origen_id)
SELECT
    i.id,
    0,
    i.existencia,
    'CARGA_INICIAL',
    CURRENT_TIMESTAMP,
    'CARGA',
    NULL
FROM inventario i;

-- ============================================================
-- 15. ACTUALIZAR DISPONIBILIDAD EN LOTES TRAS VENTAS
-- ============================================================

UPDATE lote l
SET cantidad_disponible = GREATEST(
    0,
    l.cantidad_inicial - COALESCE(
        (SELECT SUM(dv.cantidad) FROM detalle_venta dv WHERE dv.producto_id = l.producto_id),
        0
    )
);

-- ============================================================
-- 16. SINCRONIZAR SECUENCIAS IDENTITY (PREVENIR DUPLICADOS)
-- ============================================================

SELECT setval(pg_get_serial_sequence('configuracion_sistema', 'id'), COALESCE((SELECT MAX(id) FROM configuracion_sistema), 1));
SELECT setval(pg_get_serial_sequence('area', 'id'), COALESCE((SELECT MAX(id) FROM area), 1));
SELECT setval(pg_get_serial_sequence('rol', 'id'), COALESCE((SELECT MAX(id) FROM rol), 1));
SELECT setval(pg_get_serial_sequence('permiso', 'id'), COALESCE((SELECT MAX(id) FROM permiso), 1));
SELECT setval(pg_get_serial_sequence('empleado', 'id'), COALESCE((SELECT MAX(id) FROM empleado), 1));
SELECT setval(pg_get_serial_sequence('proveedor', 'id'), COALESCE((SELECT MAX(id) FROM proveedor), 1));
SELECT setval(pg_get_serial_sequence('producto', 'id'), COALESCE((SELECT MAX(id) FROM producto), 1));
SELECT setval(pg_get_serial_sequence('cliente', 'id'), COALESCE((SELECT MAX(id) FROM cliente), 1));
SELECT setval(pg_get_serial_sequence('compra', 'id'), COALESCE((SELECT MAX(id) FROM compra), 1));
SELECT setval(pg_get_serial_sequence('lote', 'id'), COALESCE((SELECT MAX(id) FROM lote), 1));
SELECT setval(pg_get_serial_sequence('inventario', 'id'), COALESCE((SELECT MAX(id) FROM inventario), 1));
SELECT setval(pg_get_serial_sequence('inventario_log', 'id'), COALESCE((SELECT MAX(id) FROM inventario_log), 1));
SELECT setval(pg_get_serial_sequence('venta', 'id'), COALESCE((SELECT MAX(id) FROM venta), 1));

COMMIT;
