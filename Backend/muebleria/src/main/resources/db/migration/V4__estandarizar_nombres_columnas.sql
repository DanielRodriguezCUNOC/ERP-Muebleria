
BEGIN;

ALTER TABLE empleado RENAME COLUMN active TO activo;
ALTER TABLE empleado RENAME COLUMN name TO nombre;
ALTER TABLE inventario_log RENAME COLUMN catidad_cambio TO cantidad_cambio;

COMMIT;
