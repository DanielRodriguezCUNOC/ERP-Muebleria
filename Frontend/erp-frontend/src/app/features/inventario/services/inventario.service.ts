import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { ExistenciaProductoDTO } from '../models/inventario.model';
import { Page } from '../../../shared/models/page.model';

@Injectable({
  providedIn: 'root'
})
export class InventarioService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = `${environment.apiUrl}/inventario`;

  consultarExistencias(
    busqueda?: string,
    soloBajoStock: boolean = false,
    page: number = 0,
    size: number = 20
  ): Observable<Page<ExistenciaProductoDTO>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('soloBajoStock', soloBajoStock.toString());

    if (busqueda && busqueda.trim()) {
      params = params.set('busqueda', busqueda.trim());
    }

    return this.http.get<Page<ExistenciaProductoDTO>>(`${this.apiUrl}/existencias`, { params });
  }
}