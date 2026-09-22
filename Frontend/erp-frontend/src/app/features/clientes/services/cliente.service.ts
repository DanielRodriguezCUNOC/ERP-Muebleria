import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { ClienteResponseDTO } from '../models/clientes.model';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = `${environment.apiUrl}/clientes`;

  obtenerPorId(clienteId: number): Observable<ClienteResponseDTO> {
    return this.http.get<ClienteResponseDTO>(`${this.apiUrl}/${clienteId}`);
  }

  obtenerPorNit(nit: string): Observable<ClienteResponseDTO> {
    return this.http.get<ClienteResponseDTO>(`${this.apiUrl}/nit/${nit}`);
  }
}