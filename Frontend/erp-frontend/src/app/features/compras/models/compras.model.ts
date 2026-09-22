export interface DetalleCompraRequestDTO {
  productoId: number | null;
  proveedorId: number | null;
  cantidad: number;
  precioUnitario: number;
}

export interface RegistrarCompraRequestDTO {
  empleadoId: number;
  detalles: DetalleCompraRequestDTO[];
}

export interface CompraResponseDTO {
  id: number;
  proveedorIds: number[];
  empleadoId: number;
  fechaCompra: string;
  mensaje: string;
}

export interface ProveedorResponseDTO {
  id: number;
  nombre: string;
  nit?: string;
  telefono?: string;
  activo?: boolean;
}

export interface ProductoOptionDTO {
  id: number;
  nombre: string;
  codigo?: string;
  precioVenta?: number;
  stockActual?: number;
}

export interface FiltroHistorialCompraDTO {
  fechaInicio?: string | null;
  fechaFin?: string | null;
  proveedorId?: number | null;
  empleadoId?: number | null;
}

export interface HistorialComprasResponseDTO {
  id: number;
  fechaCompra: string;
  empleadoId: number;
  totalProductos: number;
  costoTotal: number;
  proveedores: string;
}

export interface AnularCompraRequestDTO {
  compraId?: number;
  empleadoId: number;
  motivo: string;
}

export interface CompraResponseDTO {
  id: number;
  proveedorIds: number[];
  empleadoId: number;
  fechaCompra: string;
  mensaje: string;
}

export interface ProductoBajoStockResponseDTO {
  productoId: number;
  sku: string;
  nombre: string;
  categoria: string;
  existenciaActual: number;
  existenciaMinima: number;
  cantidadSugeridaReabastecimiento: number;
}