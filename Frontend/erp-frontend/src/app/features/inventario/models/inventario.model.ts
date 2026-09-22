export interface ExistenciaProductoDTO {
  productoId: number;
  sku: string;
  nombre: string;
  categoria: string;
  existencia: number;
  existenciaMinima: number;
  precio: number;
  estadoStock: 'NORMAL' | 'BAJO_STOCK' | 'AGOTADO';
}