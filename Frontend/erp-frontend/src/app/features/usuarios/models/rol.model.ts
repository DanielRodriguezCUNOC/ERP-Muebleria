export interface PermisoResponseDTO {
  id: number;
  codigo: string;
  descripcion: string;
}

export interface CrearRolDTO {
  nombre: string;
  descripcion: string;
  permisosIds: number[];
}

export interface RolResponseDTO {
  id: number;
  rol: string;
}