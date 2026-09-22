export interface DetalleVentaRequestDTO {
  productoId: number;
  cantidad: number;
  precioUnitario: number;
}

export interface RegistrarVentaRequestDTO {
  empleadoId: number;
  clienteId: number;
  clienteNit: string;
  clienteNombre: string;
  detalles: DetalleVentaRequestDTO[];
}

export interface VentaResponseDTO {
  ventaId: number;
  facturaId: number;
  numeroFactura: string;
  subTotal: number;
  iva: number;
  total: number;
  fechaVenta: string;
  mensaje: string;
}