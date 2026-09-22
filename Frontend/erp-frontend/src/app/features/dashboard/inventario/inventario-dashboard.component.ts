import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { TokenService } from '../../../core/services/token.service';

@Component({
  selector: 'app-inventario-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './inventario-dashboard.component.html',
  styleUrls: ['./inventario-dashboard.component.css']
})
export class InventarioDashboardComponent {
  private tokenService = inject(TokenService);

  username = this.tokenService.getUsername();
  authorities = this.tokenService.getAuthorities();

  tiles = [
    { title: 'Catálogo de productos', desc: 'Ver productos disponibles', link: null, permissions: ['INVENTARIO_VER'], icon: '📦' },
    { title: 'Existencias actuales', desc: 'Stock en tiempo real', link: null, permissions: ['INVENTARIO_VER'], icon: '📊' },
    { title: 'Movimientos de producto', desc: 'Historial de entradas/salidas', link: '/inventario/movimientos', permissions: ['INVENTARIO_VER', 'REPORTES_VER'], icon: '🔄' },
    { title: 'Productos bajo stock', desc: 'Alertas de reabastecimiento', link: '/compras/productos-bajo-stock', permissions: ['INVENTARIO_VER'], icon: '⚠️' },
    { title: 'Ajuste manual', desc: 'Entradas/salidas manuales', link: null, permissions: ['INVENTARIO_GESTIONAR'], icon: '✏️' },
    { title: 'Consultar lotes', desc: 'Trazabilidad por lote', link: null, permissions: ['INVENTARIO_VER'], icon: '🏷️' },
    { title: 'Reportes de inventario', desc: 'Rotación, valoración, antigüedad', link: null, permissions: ['REPORTES_VER'], icon: '📈' }
  ];

  hasPermission(permissions: string[]): boolean {
    if (!this.authorities || this.authorities.length === 0) return false;
    return permissions.some(p => this.authorities.includes(p));
  }
}
