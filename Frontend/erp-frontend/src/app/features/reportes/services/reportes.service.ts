import { Injectable, inject } from '@angular/core';
import { HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { ApiClientService } from '../../../core/services/api-client.service';
import {
  ConsultaComprasRequestDTO,
  ConsultaResumenVentasRequestDTO,
  ConsultaVentasPeriodoRequestDTO,
  MovimientoProductoResponseDTO,
  OperacionEmpleadoResponseDTO,
  ProductoCatalogoResponseDTO,
  ProveedorResponseDTO,
  ReporteCompraResponseDTO,
  ResumenVentasPeriodoResponseDTO,
  TopClientesResponseDTO,
  TopProductosMasIngresosResponseDTO,
  UsuarioResponseDTO,
  VentasPorPeriodoResponseDTO
} from '../models/reportes.model';

@Injectable({
  providedIn: 'root'
})
export class ReportesService {
  private readonly apiClient = inject(ApiClientService);
  private readonly baseUrl = `${environment.apiUrl}/reportes`;
  private readonly comprasBaseUrl = `${environment.apiUrl}/compras`;
  private readonly inventarioBaseUrl = `${environment.apiUrl}/inventario`;
  private readonly usuariosBaseUrl = `${environment.apiUrl}/usuarios`;

  obtenerTopClientes(): Observable<TopClientesResponseDTO[]> {
    return this.apiClient.get<TopClientesResponseDTO[]>(`${this.baseUrl}/top-clientes`);
  }

  obtenerVentasPorRangoFecha(dto: ConsultaVentasPeriodoRequestDTO): Observable<VentasPorPeriodoResponseDTO[]> {
    let params = new HttpParams();
    if (dto.fechaInicio) params = params.set('fechaInicio', dto.fechaInicio);
    if (dto.fechaFin) params = params.set('fechaFin', dto.fechaFin);

    return this.apiClient.get<VentasPorPeriodoResponseDTO[]>(`${this.baseUrl}/ventas-por-rango-de-fechas`, { params });
  }

  obtenerTopProductosMasIngresos(): Observable<TopProductosMasIngresosResponseDTO[]> {
    return this.apiClient.get<TopProductosMasIngresosResponseDTO[]>(`${this.baseUrl}/top-productos-mas-ingresos`);
  }

  obtenerResumenVentasPorPeriodo(dto: ConsultaResumenVentasRequestDTO): Observable<ResumenVentasPeriodoResponseDTO[]> {
    let params = new HttpParams();
    if (dto.fechaInicio) params = params.set('fechaInicio', dto.fechaInicio);
    if (dto.fechaFin) params = params.set('fechaFin', dto.fechaFin);
    if (dto.agrupacion) params = params.set('agrupacion', dto.agrupacion);

    return this.apiClient.get<ResumenVentasPeriodoResponseDTO[]>(`${this.baseUrl}/resumen-ventas-por-periodo`, { params });
  }

  buscarProductosCatalogo(nombre?: string): Observable<ProductoCatalogoResponseDTO[]> {
    let params = new HttpParams();
    if (nombre && nombre.trim().length >= 2) {
      params = params.set('nombre', nombre.trim());
    }
    return this.apiClient.get<ProductoCatalogoResponseDTO[]>(`${this.inventarioBaseUrl}/productos`, { params });
  }

  obtenerMovimientosProducto(productoId: number): Observable<MovimientoProductoResponseDTO[]> {
    return this.apiClient.get<MovimientoProductoResponseDTO[]>(`${this.baseUrl}/productos/${productoId}/movimientos`);
  }

  obtenerComprasPorRangoDeFechas(dto: ConsultaComprasRequestDTO): Observable<ReporteCompraResponseDTO[]> {
    let params = new HttpParams();
    if (dto.fechaInicio) params = params.set('fechaInicio', dto.fechaInicio);
    if (dto.fechaFin) params = params.set('fechaFin', dto.fechaFin);

    return this.apiClient.get<ReporteCompraResponseDTO[]>(`${this.baseUrl}/compras-por-rango-de-fechas`, { params });
  }

  buscarProveedores(nombre?: string): Observable<ProveedorResponseDTO[]> {
    let params = new HttpParams();
    if (nombre && nombre.trim().length > 0) {
      params = params.set('nombre', nombre.trim());
    }
    return this.apiClient.get<ProveedorResponseDTO[]>(`${this.comprasBaseUrl}/proveedores`, { params });
  }

  obtenerComprasPorProveedor(proveedorId: number): Observable<ReporteCompraResponseDTO[]> {
    return this.apiClient.get<ReporteCompraResponseDTO[]>(`${this.baseUrl}/proveedores/${proveedorId}/compras`);
  }

  obtenerTodosLosEmpleados(): Observable<UsuarioResponseDTO[]> {
    return this.apiClient.get<UsuarioResponseDTO[]>(this.usuariosBaseUrl);
  }

  obtenerOperacionesPorEmpleado(empleadoId: number): Observable<OperacionEmpleadoResponseDTO[]> {
    return this.apiClient.get<OperacionEmpleadoResponseDTO[]>(`${this.baseUrl}/empleados/${empleadoId}/operaciones`);
  }
}