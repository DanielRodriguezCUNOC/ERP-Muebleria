import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { TokenService } from '../../../core/services/token.service';

@Component({
  selector: 'app-administracion-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './administracion-dashboard.component.html',
  styleUrls: ['./administracion-dashboard.component.css']
})
export class AdministracionDashboardComponent {
  private tokenService = inject(TokenService);

  username = this.tokenService.getUsername();
  authorities = this.tokenService.getAuthorities();

  tiles = [
    { title: 'Configuración del sistema', desc: 'Ajustes generales', link: '/administracion/configuracion', permissions: ['CONFIGURACION_GESTIONAR', 'ADMIN'], icon: '⚙️' },
    { title: 'Roles y permisos', desc: 'Gestión de accesos', link: '/usuarios/roles-permisos', permissions: ['ROLES_GESTIONAR', 'PERMISOS_GESTIONAR', 'USUARIOS_VER'], icon: '🔐' },
    { title: 'Reportes gerenciales', desc: 'Métricas clave', link: '/reportes', permissions: ['REPORTES_VER'], icon: '📊' },
    { title: 'Gestionar empleados', desc: 'Directorio del personal', link: null, permissions: ['USUARIOS_VER'], icon: '👥' },
    { title: 'Ver bitácora', desc: 'Registro de auditoría', link: null, permissions: ['CONFIGURACION_GESTIONAR', 'ADMIN'], icon: '📋' }
  ];

  hasPermission(permissions: string[]): boolean {
    if (!this.authorities || this.authorities.length === 0) return false;
    return permissions.some(p => this.authorities.includes(p));
  }
}
