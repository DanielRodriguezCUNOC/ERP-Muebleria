import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { TokenService } from '../../../../core/services/token.service';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-compras-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './compras-dashboard.component.html',
  styleUrls: ['./compras-dashboard.component.css']
})
export class ComprasDashboardComponent {
  private tokenService = inject(TokenService);
  private sanitizer = inject(DomSanitizer);
  
  username = this.tokenService.getUsername();
  authorities = this.tokenService.getAuthorities();

  tiles = [
    { title: 'Registrar compra', desc: 'Ingreso de mercancía', link: '/compras/registrar-compra', permissions: ['COMPRAS_GESTIONAR', 'ADMIN'], icon: this.sanitizer.bypassSecurityTrustHtml('<svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 3h2l.4 2M7 13h10l4-8H5.4M7 13L5.4 5M7 13l-2.293 2.293c-.63.63-.184 1.707.707 1.707H17m0 0a2 2 0 100 4 2 2 0 000-4zm-8 2a2 2 0 11-4 0 2 2 0 014 0z"></path></svg>') },
    { title: 'Anular compra', desc: 'Reversión de compras', link: '/compras/anular-compra', permissions: ['COMPRAS_GESTIONAR', 'ADMIN'], icon: this.sanitizer.bypassSecurityTrustHtml('<svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z"></path></svg>') },
    { title: 'Historial de compras', desc: 'Consultar transacciones', link: '/compras/historial-compras', permissions: ['COMPRAS_VER', 'ADMIN'], icon: this.sanitizer.bypassSecurityTrustHtml('<svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"></path></svg>') },
    { title: 'Productos bajo stock', desc: 'Alertas de inventario', link: '/compras/productos-bajo-stock', permissions: ['INVENTARIO_VER', 'ADMIN'], icon: this.sanitizer.bypassSecurityTrustHtml('<svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z"></path></svg>') },
    { title: 'Reportes de compras', desc: 'Análisis de abastecimiento', link: '/reportes', permissions: ['REPORTES_VER', 'ADMIN'], icon: this.sanitizer.bypassSecurityTrustHtml('<svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z"></path></svg>') },
    { title: 'Gestionar proveedores', desc: 'Directorio de proveedores', link: null, permissions: ['COMPRAS_GESTIONAR', 'ADMIN'], icon: this.sanitizer.bypassSecurityTrustHtml('<svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 21V5a2 2 0 00-2-2H7a2 2 0 00-2 2v16m14 0h2m-2 0h-5m-9 0H3m2 0h5M9 7h1m-1 4h1m4-4h1m-1 4h1m-5 10v-5a1 1 0 011-1h2a1 1 0 011 1v5m-4 0h4"></path></svg>') }
  ];

  hasPermission(permissions: string[]): boolean {
    if (!this.authorities || this.authorities.length === 0) return false;
    return permissions.some(p => this.authorities.includes(p));
  }
}
