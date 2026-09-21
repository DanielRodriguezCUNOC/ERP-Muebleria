export interface TopClientesResponseDTO {
  clienteId: number;
  nombre: string;
  nit: string;
  montoTotal: number;
}

export interface ConsultaVentasPeriodoRequestDTO {
  //* Formato ISO o YYYY-MM-DD
  fechaInicio: string;
  fechaFin: string;
}

export interface VentasPorPeriodoResponseDTO {
  fechaInicio: string;
  fechaFin: string;
  totalIngresos: number;
  totalFacturas: number;
}

export interface TopProductosMasIngresosResponseDTO {
  productoId: number;
  nombre: string;
  cantidadVendida: number;
  totalIngresos: number;
}

export type TipoAgrupacion = 'DIA' | 'MES' | 'AÑO';


export interface ConsultaResumenVentasRequestDTO {
  fechaInicio: string;
  fechaFin: string;
  agrupacion?: TipoAgrupacion;
}

export interface ResumenVentasPeriodoResponseDTO {
  periodo: string;
  totalIngresos: number;
  totalFacturas: number;
}

export interface ProductoCatalogoResponseDTO {
  id: number;
  sku: string;
  nombre: string;
  descripcion: string;
  categoria: string;
  precioVenta: number;
  existenciaTotal: number;
  activo: boolean;
}

export interface ProductoCatalogoResponseDTO {
  id: number;
  sku: string;
  nombre: string;
  descripcion: string;
  categoria: string;
  precioVenta: number;
  existenciaTotal: number;
  activo: boolean;
}

export interface MovimientoProductoResponseDTO {
  id: number;
  productoId: number;
  cantidadCambio: number;
  existenciaResultante: number;
  tipoMovimiento: string;
  origenTipo: string;
  origenId: number;
  fecha: string;
}