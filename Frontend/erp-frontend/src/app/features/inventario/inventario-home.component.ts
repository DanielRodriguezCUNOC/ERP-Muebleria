import { Component } from '@angular/core';

@Component({
  selector: 'app-inventario-home',
  standalone: true,
  template: `
    <section class="p-6 bg-white rounded shadow-sm border border-slate-200">
      <h2 class="text-xl font-semibold mb-2">Inventario</h2>
      <p class="text-slate-600">Módulo de inventario activo.</p>
    </section>
  `
})
export class InventarioHomeComponent {}
