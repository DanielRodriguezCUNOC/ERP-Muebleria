import { Injectable, inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, finalize, Observable, of, tap } from 'rxjs';
import { environment } from '../../../environments/environment';
import { AuthResponseDTO, LoginDTO, RecuperarContrasenaDTO } from '../models/auth.model';
import { ApiClientService } from './api-client.service';
import { TokenService } from './token.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly apiClient = inject(ApiClientService);
  private readonly tokenService = inject(TokenService);
  private readonly router = inject(Router);
  private readonly apiUrl = `${environment.apiUrl}/auth`;

  login(dto: LoginDTO): Observable<AuthResponseDTO> {
    return this.apiClient.post<AuthResponseDTO>(`${this.apiUrl}/login`, dto).pipe(
      tap((response) => {
        this.tokenService.setToken(response.token);
      })
    );
  }

  logout(): void {
    const token = this.tokenService.getToken();

    if (!token) {
      this.router.navigate(['/login']);
      return;
    }

    this.apiClient.post<void>(`${this.apiUrl}/logout`, {}).pipe(
      catchError((error) => {
        console.error('Error del servidor al intentar cerrar sesión:', error);
        return of(null);
      }),
      finalize(() => {
        this.tokenService.removeToken();
        this.router.navigate(['/login']);
      })
    ).subscribe();
  }

  hasAuthority(requiredAuthority: string): boolean {
    const permisos = this.tokenService.getAuthorities();
    return permisos.includes(requiredAuthority);
  }

  isAuthenticated(): boolean {
    return this.tokenService.isTokenValid();
  }

  recuperarContrasena(dto: RecuperarContrasenaDTO): Observable<void> {
    return this.apiClient.post<void>(`${this.apiUrl}/recuperar-contrasena`, dto);
  }

  getUsuarioId(): number | null {
    const payload = this.tokenService.getDecodedToken();
    return payload?.usuarioId ?? null;
  }
}