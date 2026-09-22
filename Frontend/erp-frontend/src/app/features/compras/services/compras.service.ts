import { Injectable, inject } from '@angular/core';
import { HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { ApiClientService } from '../../../core/services/api-client.service';
import {
  RegistrarCompraRequestDTO,
  CompraResponseDTO,
  ProveedorResponseDTO,
  ProductoOptionDTO,
  HistorialComprasResponseDTO,
  FiltroHistorialCompraDTO,
  AnularCompraRequestDTO,
  ProductoBajoStockResponseDTO
} from '../models/compras.model';

@Injectable({
  providedIn: 'root'
})
export class ComprasService {
  private readonly apiClient = inject(ApiClientService);
  private readonly baseUrl = `${environment.apiUrl}/compras`;
  private readonly inventarioBaseUrl = `${environment.apiUrl}/inventario`;

  registrarCompra(request: RegistrarCompraRequestDTO): Observable<CompraResponseDTO> {
    return this.apiClient.post<CompraResponseDTO>(this.baseUrl, request);
  }

  consultarProveedores(nombre?: string): Observable<ProveedorResponseDTO[]> {
    let params = new HttpParams();
    if (nombre && nombre.trim().length > 0) {
      params = params.set('nombre', nombre.trim());
    }
    return this.apiClient.get<ProveedorResponseDTO[]>(`${this.baseUrl}/proveedores`, { params });
  }

  consultarProductos(nombre?: string): Observable<ProductoOptionDTO[]> {
    let params = new HttpParams();
    if (nombre && nombre.trim().length > 0) {
      params = params.set('nombre', nombre.trim());
    }
    return this.apiClient.get<ProductoOptionDTO[]>(`${this.inventarioBaseUrl}/productos`, { params });
  }

  consultarHistorial(filtro: FiltroHistorialCompraDTO): Observable<HistorialComprasResponseDTO[]> {
    let params = new HttpParams();

    if (filtro.fechaInicio) {
      params = params.set('fechaInicio', filtro.fechaInicio);
    }
    if (filtro.fechaFin) {
      params = params.set('fechaFin', filtro.fechaFin);
    }
    if (filtro.proveedorId !== null && filtro.proveedorId !== undefined) {
      params = params.set('proveedorId', filtro.proveedorId.toString());
    }
    if (filtro.empleadoId !== null && filtro.empleadoId !== undefined) {
      params = params.set('empleadoId', filtro.empleadoId.toString());
    }

    return this.apiClient.get<HistorialComprasResponseDTO[]>(this.baseUrl, { params });
  }

  anularCompra(request: AnularCompraRequestDTO): Observable<CompraResponseDTO> {
    const compraId = request.compraId ?? 0;
    return this.apiClient.delete<CompraResponseDTO>(`${this.baseUrl}/${compraId}`, {
      body: request
    });
  }

  consultarProductosBajoStock(): Observable<ProductoBajoStockResponseDTO[]> {
    return this.apiClient.get<ProductoBajoStockResponseDTO[]>(`${this.baseUrl}/productos-bajo-stock`);
  }
}