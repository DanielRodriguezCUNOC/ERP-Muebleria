-- ============================================================
-- MUEBLERIA ERP - Índices compuestos para consultas frecuentes
-- ============================================================

BEGIN;

CREATE INDEX IF NOT EXISTS idx_compra_empleado_fecha
    ON compra (empleado_id, fecha_compra);

CREATE INDEX IF NOT EXISTS idx_compra_proveedor_fecha
    ON compra_proveedor (proveedor_id, compra_id);

CREATE INDEX IF NOT EXISTS idx_detalle_compra_producto_fecha
    ON detalle_compra (producto_id, compra_id);

CREATE INDEX IF NOT EXISTS idx_venta_empleado_fecha
    ON venta (empleado_id, fecha_venta);

CREATE INDEX IF NOT EXISTS idx_venta_cliente_fecha
    ON venta (cliente_id, fecha_venta);

CREATE INDEX IF NOT EXISTS idx_factura_estado_fecha
    ON factura (estado, fecha_emision);

CREATE INDEX IF NOT EXISTS idx_inventario_log_inventario_fecha
    ON inventario_log (inventario_id, creado_en);

CREATE INDEX IF NOT EXISTS idx_bitacora_empleado_fecha
    ON bitacora_operacion (empleado_id, fecha);

COMMIT;
