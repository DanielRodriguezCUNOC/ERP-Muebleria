import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { TokenService } from '../../../core/services/token.service';

@Component({
  selector: 'app-ventas-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './ventas-dashboard.component.html',
  styleUrls: ['./ventas-dashboard.component.css']
})
export class VentasDashboardComponent {
  private tokenService = inject(TokenService);

  username = this.tokenService.getUsername();
  authorities = this.tokenService.getAuthorities();

  tiles = [
    { title: 'Registrar venta', desc: 'Nueva transacción', link: '/ventas/registrar-venta', permissions: ['VENTAS_GESTIONAR'], icon: '🛒' },
    { title: 'Gestionar clientes', desc: 'Directorio de clientes', link: '/clientes', permissions: ['VENTAS_VER'], icon: '👥' },
    { title: 'Reportes de ventas', desc: 'Análisis por período', link: '/reportes', permissions: ['REPORTES_VER'], icon: '📊' },
    { title: 'Anular factura', desc: 'Reversión de ventas', link: null, permissions: ['VENTAS_GESTIONAR'], icon: '↩️' },
    { title: 'Historial de ventas', desc: 'Consultar transacciones', link: null, permissions: ['VENTAS_VER'], icon: '📋' },
    { title: 'Factura por número', desc: 'Buscar comprobante', link: null, permissions: ['VENTAS_VER'], icon: '🔎' }
  ];

  hasPermission(permissions: string[]): boolean {
    if (!this.authorities || this.authorities.length === 0) return false;
    return permissions.some(p => this.authorities.includes(p));
  }
}
