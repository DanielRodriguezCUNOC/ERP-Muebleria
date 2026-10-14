package com.erp.muebleria.modules.common.infrastructure.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class DatabaseSequenceSyncRunner implements ApplicationRunner {

    private final JdbcTemplate jdbcTemplate;

    private static final List<String> TABLAS = List.of(
            "area", "rol", "permiso", "cliente", "proveedor", "producto",
            "empleado", "inventario", "inventario_log", "compra", "lote",
            "venta", "factura", "configuracion_sistema", "bitacora_operacion"
    );

    @Override
    public void run(ApplicationArguments args) {
        for (String tabla : TABLAS) {
            try {
                String sql = String.format("""
                    DO $$
                    DECLARE
                        seq TEXT;
                    BEGIN
                        seq := pg_get_serial_sequence('%s', 'id');
                        IF seq IS NOT NULL THEN
                            EXECUTE format('SELECT setval(%%L, COALESCE((SELECT MAX(id) FROM %s), 1))', seq);
                        END IF;
                    END $$;
                    """, tabla, tabla);
                jdbcTemplate.execute(sql);
            } catch (Exception e) {
                log.warn("No se pudo sincronizar la secuencia para la tabla {}: {}", tabla, e.getMessage());
            }
        }
        log.info("Secuencias de base de datos sincronizadas correctamente.");
    }
}
