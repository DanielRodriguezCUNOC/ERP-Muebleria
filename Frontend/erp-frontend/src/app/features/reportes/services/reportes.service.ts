import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../enviroments/environment';
import { ConsultaResumenVentasRequestDTO, ConsultaVentasPeriodoRequestDTO, MovimientoProductoResponseDTO, ProductoCatalogoResponseDTO, ResumenVentasPeriodoResponseDTO, TopClientesResponseDTO, TopProductosMasIngresosResponseDTO, VentasPorPeriodoResponseDTO } from '../models/reportes.model';

@Injectable({
  providedIn: 'root'
})
export class ReportesService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = `${environment.apiUrl}/reportes`;

  obtenerTopClientes(): Observable<TopClientesResponseDTO[]> {
    return this.http.get<TopClientesResponseDTO[]>(`${this.apiUrl}/top-clientes`);
  }

  //? Metodo para exportar el reporte de top clientes en formato PDF desde el backend.
  exportarTopClientesPdf(): Observable<Blob> {
    return this.http.get(`${this.apiUrl}/top-clientes/pdf`, {
      responseType: 'blob'
    });
  }

  obtenerVentasPorRangoFecha(dto: ConsultaVentasPeriodoRequestDTO): Observable<VentasPorPeriodoResponseDTO[]> {
    let params = new HttpParams();
    if (dto.fechaInicio) params = params.set('fechaInicio', dto.fechaInicio);
    if (dto.fechaFin) params = params.set('fechaFin', dto.fechaFin);

    return this.http.get<VentasPorPeriodoResponseDTO[]>(`${this.apiUrl}/ventas-por-rango-de-fechas`, { params });
  }

  exportarVentasPorPeriodoPdf(dto: ConsultaVentasPeriodoRequestDTO): Observable<Blob> {
    let params = new HttpParams();
    if (dto.fechaInicio) params = params.set('fechaInicio', dto.fechaInicio);
    if (dto.fechaFin) params = params.set('fechaFin', dto.fechaFin);

    return this.http.get(`${this.apiUrl}/ventas-por-rango-de-fechas/pdf`, {
      params,
      responseType: 'blob'
    });
  }

  obtenerTopProductosMasIngresos(): Observable<TopProductosMasIngresosResponseDTO[]> {
    return this.http.get<TopProductosMasIngresosResponseDTO[]>(`${this.apiUrl}/top-productos-mas-ingresos`);
  }

  exportarTopProductosMasIngresosPdf(): Observable<Blob> {
    return this.http.get(`${this.apiUrl}/top-productos-mas-ingresos/pdf`, {
      responseType: 'blob'
    });
  }

  obtenerResumenVentasPorPeriodo(dto: ConsultaResumenVentasRequestDTO): Observable<ResumenVentasPeriodoResponseDTO[]> {
    let params = new HttpParams();
    if (dto.fechaInicio) params = params.set('fechaInicio', dto.fechaInicio);
    if (dto.fechaFin) params = params.set('fechaFin', dto.fechaFin);
    if (dto.agrupacion) params = params.set('agrupacion', dto.agrupacion);

    return this.http.get<ResumenVentasPeriodoResponseDTO[]>(`${this.apiUrl}/resumen-ventas-por-periodo`, { params });
  }

  exportarResumenVentasPorPeriodoPdf(dto: ConsultaResumenVentasRequestDTO): Observable<Blob> {
    let params = new HttpParams();
    if (dto.fechaInicio) params = params.set('fechaInicio', dto.fechaInicio);
    if (dto.fechaFin) params = params.set('fechaFin', dto.fechaFin);
    if (dto.agrupacion) params = params.set('agrupacion', dto.agrupacion);

    return this.http.get(`${this.apiUrl}/resumen-ventas-por-periodo/pdf`, {
      params,
      responseType: 'blob'
    });
  }

  //* Buscar catálogo de productos
  buscarProductosCatalogo(nombre?: string): Observable<ProductoCatalogoResponseDTO[]> {
    let params = new HttpParams();
    if (nombre && nombre.trim().length >= 2) {
      params = params.set('nombre', nombre.trim());
    }
    return this.http.get<ProductoCatalogoResponseDTO[]>(`${this.apiUrl}/productos`, { params });
  }

  //* Obtener historial de movimientos de un producto
  obtenerMovimientosProducto(productoId: number): Observable<MovimientoProductoResponseDTO[]> {
    return this.http.get<MovimientoProductoResponseDTO[]>(`${this.apiUrl}/reportes/productos/${productoId}/movimientos`);
  }

  //* Descargar el historial de movimientos 
  exportarMovimientosProductoPdf(productoId: number): Observable<Blob> {
    return this.http.get(`${this.apiUrl}/reportes/productos/${productoId}/movimientos/pdf`, {
      responseType: 'blob'
    });
  }
}