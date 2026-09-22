export interface LoginDTO {
  usuario: string;
  password: string;
}

export interface AuthResponseDTO {
  token: string;
  expiresAt: string;
}

export interface RecuperarContrasenaDTO {
  usuario: string;
  dpi: string;
  newPassword: string;
}

export interface JwtPayload {
  sub: string;
  rol: string;
  permisos: string[];
  iat: number;
  exp: number;
  usuarioId?: number;
}