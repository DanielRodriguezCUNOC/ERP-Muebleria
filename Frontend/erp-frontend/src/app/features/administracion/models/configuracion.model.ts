export type MetodoValoracion = 'PEPS' | 'UEPS';

export interface ConfiguracionSistema {
  id: number;
  tasaIva: number;
  metodoValoracion: MetodoValoracion;
  resolucionFacturas: string;
  serieFacturas: string;
  correlativoSiguiente: number;
}

export interface ActualizarConfiguracionDTO {
  tasaIva?: number;
  metodoValoracion?: MetodoValoracion;
  resolucionFactura?: string;
  serieFacturas?: string;
  correlativoSiguiente?: number;
}