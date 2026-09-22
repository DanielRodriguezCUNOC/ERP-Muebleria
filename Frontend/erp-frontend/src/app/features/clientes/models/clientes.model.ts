export interface ClienteResponseDTO {
  id: number;
  nombre: string;
  nit: string;
  direccion?: string;
  telefono?: string;
  activo: boolean;
}

export interface RegistrarClienteRequestDTO {
  nombre: string;
  nit: string;
  direccion?: string;
  telefono?: string;
}

export interface ModificarClienteRequestDTO {
  nombre: string;
  nit: string;
  direccion?: string;
  telefono?: string;
}

export interface CambiarEstadoClienteRequestDTO {
  activo: boolean;
}