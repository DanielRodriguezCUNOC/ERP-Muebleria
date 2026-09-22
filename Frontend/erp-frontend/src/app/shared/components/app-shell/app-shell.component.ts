import { Component } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-shell',
  standalone: true,
  imports: [RouterOutlet, RouterLink],
  template: `
    <div class="min-h-screen bg-slate-100 text-slate-800">
      <header class="border-b border-slate-200 bg-white px-4 py-3 shadow-sm">
        <nav class="flex items-center justify-between gap-3">
          <div class="font-semibold">ERP Muebleria</div>
          <div class="flex gap-3 text-sm">
            <a routerLink="/inventario" class="text-slate-600 hover:text-slate-900">Inventario</a>
            <a routerLink="/compras" class="text-slate-600 hover:text-slate-900">Compras</a>
            <a routerLink="/reportes" class="text-slate-600 hover:text-slate-900">Reportes</a>
            <a routerLink="/login" class="text-slate-600 hover:text-slate-900">Cerrar sesión</a>
          </div>
        </nav>
      </header>
      <main class="p-4">
        <router-outlet />
      </main>
    </div>
  `
})
export class AppShellComponent {}
