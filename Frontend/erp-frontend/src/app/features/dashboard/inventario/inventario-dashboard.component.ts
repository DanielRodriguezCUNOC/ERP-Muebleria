import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { TokenService } from '../../../../core/services/token.service';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-inventario-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './inventario-dashboard.component.html',
  styleUrls: ['./inventario-dashboard.component.css']
})
export class InventarioDashboardComponent {
  private tokenService = inject(TokenService);
  private sanitizer = inject(DomSanitizer);
  
  username = this.tokenService.getUsername();
  authorities = this.tokenService.getAuthorities();

  tiles = [
    { title: 'Movimientos de producto', desc: 'Kárdex', link: '/reportes/movimientos-producto', permissions: ['INVENTARIO_VER', 'ADMIN'], icon: this.sanitizer.bypassSecurityTrustHtml('<svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 7h12m0 0l-4-4m4 4l-4 4m0 6H4m0 0l4 4m-4-4l4-4"></path></svg>') },
    { title: 'Productos bajo stock', desc: 'Alertas de reabastecimiento', link: '/compras/productos-bajo-stock', permissions: ['INVENTARIO_VER', 'ADMIN'], icon: this.sanitizer.bypassSecurityTrustHtml('<svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z"></path></svg>') },
    { title: 'Catálogo/Existencias', desc: 'Inventario actual', link: '/inventario', permissions: ['INVENTARIO_VER', 'ADMIN'], icon: this.sanitizer.bypassSecurityTrustHtml('<svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2H6a2 2 0 01-2-2V6zM14 6a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2h-2a2 2 0 01-2-2V6zM4 16a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2H6a2 2 0 01-2-2v-2zM14 16a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2h-2a2 2 0 01-2-2v-2z"></path></svg>') },
    { title: 'Ajuste manual de inventario', desc: 'Corrección de stock', link: null, permissions: ['INVENTARIO_GESTIONAR', 'ADMIN'], icon: this.sanitizer.bypassSecurityTrustHtml('<svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z"></path></svg>') },
    { title: 'Consultar lotes', desc: 'Trazabilidad', link: null, permissions: ['INVENTARIO_VER', 'ADMIN'], icon: this.sanitizer.bypassSecurityTrustHtml('<svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 7h.01M7 3h5c.512 0 1.024.195 1.414.586l7 7a2 2 0 010 2.828l-7 7a2 2 0 01-2.828 0l-7-7A1.994 1.994 0 013 12V7a4 4 0 014-4z"></path></svg>') },
    { title: 'Reportes', desc: 'Rotación y pérdidas', link: null, permissions: ['REPORTES_VER', 'ADMIN'], icon: this.sanitizer.bypassSecurityTrustHtml('<svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z"></path></svg>') }
  ];

  hasPermission(permissions: string[]): boolean {
    if (!this.authorities || this.authorities.length === 0) return false;
    return permissions.some(p => this.authorities.includes(p));
  }
}
