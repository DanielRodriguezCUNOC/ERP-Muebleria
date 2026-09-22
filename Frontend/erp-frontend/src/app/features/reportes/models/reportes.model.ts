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

export interface ConsultaComprasRequestDTO {
  fechaInicio: string;
  fechaFin: string;
}

export interface ReporteCompraResponseDTO {
  compraId: number;
  proveedorId: number;
  nombreProveedor: string;
  //* ISO LocalDateTime
  fecha: string;
  total: number;
  estado: string;
}

export interface ProveedorResponseDTO {
  id: number;
  nombre: string;
  direccion?: string;
  telefonoContacto?: string;
  activo: boolean;
}

export interface ReporteCompraResponseDTO {
  compraId: number;
  proveedorId: number;
  nombreProveedor: string;
  fecha: string;
  total: number;
  estado: string;
}

export interface UsuarioResponseDTO {
  id: number;
  name: string;
  usuario: string;
  dpi: string;
  numeroTelefono: string;
  activo: boolean;
  areaId?: number;
  rolId?: number;
  nombreRol?: string;
}

export interface OperacionEmpleadoResponseDTO {
  id: number;
  empleadoId: number;
  accion: string;
  modulo: string;
  detalle: string;
  fecha: string;
}