import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { TokenService } from '../../../core/services/token.service';

@Component({
  selector: 'app-compras-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './compras-dashboard.component.html',
  styleUrls: ['./compras-dashboard.component.css']
})
export class ComprasDashboardComponent {
  private tokenService = inject(TokenService);

  username = this.tokenService.getUsername();
  authorities = this.tokenService.getAuthorities();

  tiles = [
    { title: 'Registrar compra', desc: 'Ingreso de mercancía', link: '/compras/registrar-compra', permissions: ['COMPRAS_GESTIONAR', 'ADMIN'], icon: '🛒' },
    { title: 'Anular compra', desc: 'Reversión de compras', link: '/compras/anular-compra', permissions: ['COMPRAS_GESTIONAR', 'ADMIN'], icon: '↩️' },
    { title: 'Historial de compras', desc: 'Consultar transacciones', link: '/compras/historial-compras', permissions: ['COMPRAS_VER', 'ADMIN'], icon: '📋' },
    { title: 'Productos bajo stock', desc: 'Alertas de inventario', link: '/compras/productos-bajo-stock', permissions: ['INVENTARIO_VER', 'ADMIN'], icon: '⚠️' },
    { title: 'Reportes de compras', desc: 'Análisis de abastecimiento', link: '/reportes', permissions: ['REPORTES_VER', 'ADMIN'], icon: '📊' },
    { title: 'Gestionar proveedores', desc: 'Directorio de proveedores', link: null, permissions: ['COMPRAS_GESTIONAR', 'ADMIN'], icon: '🏢' }
  ];

  hasPermission(permissions: string[]): boolean {
    if (!this.authorities || this.authorities.length === 0) return false;
    return permissions.some(p => this.authorities.includes(p));
  }
}
