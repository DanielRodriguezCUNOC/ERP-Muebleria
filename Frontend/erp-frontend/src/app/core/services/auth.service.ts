import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { catchError, finalize, Observable, of, tap } from 'rxjs';
import { environment } from '../../../enviroments/environment';
import { LoginDTO, RecuperarContrasenaDTO } from '../models/auth.model';
import { TokenService } from './token.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly http = inject(HttpClient);
  private readonly tokenService = inject(TokenService);
  private readonly router = inject(Router);
  private readonly apiUrl = `${environment.apiUrl}/auth`;

  login(dto: LoginDTO): Observable<string> {
    return this.http.post(`${this.apiUrl}/login`, dto, { responseType: 'text' }).pipe(
      tap((token) => {
        this.tokenService.setToken(token);
      })
    );
  }

  logout(): void {

    if (!this.tokenService.getToken()) {
      this.router.navigate(['/login']);
      return;
    }

    //* Se activa authInterceptor para que se ejecute el logout en el backend
    this.http.post<void>(`${this.apiUrl}/logout`, {}).pipe(
      catchError((error) => {
        //* Atrapar el error proveniente del backend
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
    return !!this.tokenService.getToken();
  }

  //* Metodo para recuperación de contraseña
  recuperarContrasena(dto: RecuperarContrasenaDTO): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/recuperar-contrasena`, dto);
  }
}