import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { ConfiguracionSistema, ActualizarConfiguracionDTO } from '../../../features/administracion/models/configuracion.model';

@Injectable({
  providedIn: 'root'
})
export class ConfiguracionService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = `${environment.apiUrl}/admin/configuracion`;

  obtenerConfiguracion(): Observable<ConfiguracionSistema> {
    return this.http.get<ConfiguracionSistema>(this.apiUrl);
  }

  actualizarConfiguracion(dto: ActualizarConfiguracionDTO): Observable<ConfiguracionSistema> {
    return this.http.put<ConfiguracionSistema>(this.apiUrl, dto);
  }
}