import { Injectable, signal } from '@angular/core';
import { JwtPayload } from '../models/auth.model';

@Injectable({
  providedIn: 'root'
})
export class TokenService {
  private readonly TOKEN_KEY = 'jwt_token';

  // Signal reactiva para almacenar el payload decodificado
  currentUserPayload = signal<JwtPayload | null>(this.getDecodedToken());

  setToken(token: string): void {
    localStorage.setItem(this.TOKEN_KEY, token);
    this.currentUserPayload.set(this.decodeToken(token));
  }

  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  removeToken(): void {
    localStorage.removeItem(this.TOKEN_KEY);
    this.currentUserPayload.set(null);
  }

  getDecodedToken(): JwtPayload | null {
    const token = this.getToken();
    return token ? this.decodeToken(token) : null;
  }

  getAuthorities(): string[] {
    return this.currentUserPayload()?.permisos ?? [];
  }

  getRole(): string {
    return this.currentUserPayload()?.rol ?? 'SIN_ROL';
  }

  getUsername(): string {
    return this.currentUserPayload()?.sub ?? '';
  }

  private decodeToken(token: string): JwtPayload | null {
    try {
      const base64Url = token.split('.')[1];
      const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
      const jsonPayload = decodeURIComponent(
        atob(base64)
          .split('')
          .map((c) => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2))
          .join('')
      );
      return JSON.parse(jsonPayload);
    } catch (e) {
      console.error('Error al decodificar JWT', e);
      return null;
    }
  }
}