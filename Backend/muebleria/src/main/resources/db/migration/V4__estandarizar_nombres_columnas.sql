
BEGIN;

ALTER TABLE empleado RENAME COLUMN active TO activo;
ALTER TABLE empleado RENAME COLUMN name TO nombre;

COMMIT;
