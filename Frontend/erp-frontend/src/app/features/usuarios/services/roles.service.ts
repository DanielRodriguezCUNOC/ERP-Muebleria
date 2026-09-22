import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { CrearRolDTO, PermisoResponseDTO, RolResponseDTO } from '../models/rol.model';

@Injectable({
  providedIn: 'root'
})
export class RolesService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = `${environment.apiUrl}/admin/roles`;

  crearRol(dto: CrearRolDTO): Observable<void> {
    return this.http.post<void>(this.apiUrl, dto);
  }

  obtenerPermisos(): Observable<PermisoResponseDTO[]> {
    return this.http.get<PermisoResponseDTO[]>(this.apiUrl + '/permisos');
  }

  asignarPermiso(rolId: number, permisoId: number): Observable<void> {
    return this.http.patch<void>(`${this.apiUrl}/${rolId}/permisos/${permisoId}`, {});
  }

  obtenerTodosLosRoles(): Observable<RolResponseDTO[]> {
    return this.http.get<RolResponseDTO[]>(this.apiUrl);
  }
}