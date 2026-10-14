-- ============================================================
-- Sincronización de secuencias PostgreSQL e inicialización de configuración
-- ============================================================

BEGIN;

-- Inicializar configuración del sistema si no existe
INSERT INTO configuracion_sistema (id, tasa_iva, metodo_valoracion, resolucion_facturas, serie_facturas, correlativo_siguiente)
VALUES (1, 0.1200, 'PEPS', 'RES-2026-001', 'A', 1)
ON CONFLICT (id) DO NOTHING;

-- Sincronizar secuencias para evitar violación de clave primaria en inserciones posteriores
DO $$
DECLARE
    t TEXT;
    seq TEXT;
BEGIN
    FOR t IN 
        SELECT unnest(ARRAY[
            'area', 'rol', 'permiso', 'cliente', 'proveedor', 'producto',
            'empleado', 'inventario', 'inventario_log', 'compra', 'lote',
            'venta', 'factura', 'configuracion_sistema', 'bitacora_operacion'
        ])
    LOOP
        seq := pg_get_serial_sequence(t, 'id');
        IF seq IS NOT NULL THEN
            EXECUTE format(
                'SELECT setval(%L, COALESCE((SELECT MAX(id) FROM %I), 1))',
                seq, t
            );
        END IF;
    END LOOP;
END $$;

COMMIT;
