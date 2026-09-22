import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { TokenService } from '../../../../core/services/token.service';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-ventas-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './ventas-dashboard.component.html',
  styleUrls: ['./ventas-dashboard.component.css']
})
export class VentasDashboardComponent {
  private tokenService = inject(TokenService);
  private sanitizer = inject(DomSanitizer);
  
  username = this.tokenService.getUsername();
  authorities = this.tokenService.getAuthorities();

  tiles = [
    { title: 'Registrar venta', desc: 'Nueva factura', link: '/ventas/registrar-venta', permissions: ['VENTAS_GESTIONAR', 'ADMIN'], icon: this.sanitizer.bypassSecurityTrustHtml('<svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 3h2l.4 2M7 13h10l4-8H5.4M7 13L5.4 5M7 13l-2.293 2.293c-.63.63-.184 1.707.707 1.707H17m0 0a2 2 0 100 4 2 2 0 000-4zm-8 2a2 2 0 11-4 0 2 2 0 014 0z"></path></svg>') },
    { title: 'Gestionar clientes', desc: 'Directorio de clientes', link: '/clientes', permissions: ['VENTAS_GESTIONAR', 'VENTAS_VER', 'ADMIN'], icon: this.sanitizer.bypassSecurityTrustHtml('<svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4.354a4 4 0 110 5.292M15 21H3v-1a6 6 0 0112 0v1zm0 0h6v-1a6 6 0 00-9-5.197M13 7a4 4 0 11-8 0 4 4 0 018 0z"></path></svg>') },
    { title: 'Reportes de ventas', desc: 'Métricas e ingresos', link: '/reportes', permissions: ['REPORTES_VER', 'ADMIN'], icon: this.sanitizer.bypassSecurityTrustHtml('<svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z"></path></svg>') },
    { title: 'Anular factura', desc: 'Reversión de ventas', link: null, permissions: ['VENTAS_GESTIONAR', 'ADMIN'], icon: this.sanitizer.bypassSecurityTrustHtml('<svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z"></path></svg>') },
    { title: 'Historial de ventas', desc: 'Consultar facturas', link: null, permissions: ['VENTAS_VER', 'ADMIN'], icon: this.sanitizer.bypassSecurityTrustHtml('<svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"></path></svg>') },
    { title: 'Factura por número', desc: 'Búsqueda rápida', link: null, permissions: ['VENTAS_VER', 'ADMIN'], icon: this.sanitizer.bypassSecurityTrustHtml('<svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"></path></svg>') }
  ];

  hasPermission(permissions: string[]): boolean {
    if (!this.authorities || this.authorities.length === 0) return false;
    return permissions.some(p => this.authorities.includes(p));
  }
}
