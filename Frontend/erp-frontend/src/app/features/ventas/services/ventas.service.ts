import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { RegistrarVentaRequestDTO, VentaResponseDTO } from '../models/ventas.model';

@Injectable({
  providedIn: 'root'
})
export class VentasService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = `${environment.apiUrl}/ventas`;

  registrarVenta(request: RegistrarVentaRequestDTO): Observable<VentaResponseDTO> {
    return this.http.post<VentaResponseDTO>(this.apiUrl, request);
  }
}