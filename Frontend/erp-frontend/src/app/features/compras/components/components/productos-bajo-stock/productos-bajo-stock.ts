import { Component, OnInit, inject, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ComprasService } from '../../../services/compras.service';
import { ProductoBajoStockResponseDTO } from '../../../models/compras.model';

@Component({
  selector: 'app-productos-bajo-stock',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './productos-bajo-stock.html'
})
export class ProductosBajoStockComponent implements OnInit {
  private readonly comprasService = inject(ComprasService);

  // Estados de carga y datos
  productos = signal<ProductoBajoStockResponseDTO[]>([]);
  isLoading = signal<boolean>(false);
  errorMessage = signal<string | null>(null);

  // Filtro de búsqueda rápida local
  busqueda = signal<string>('');

  // Lista filtrada reactiva
  productosFiltrados = computed(() => {
    const query = this.busqueda().toLowerCase().trim();
    if (!query) {
      return this.productos();
    }
    return this.productos().filter(p =>
      p.sku.toLowerCase().includes(query) ||
      p.nombre.toLowerCase().includes(query) ||
      p.categoria.toLowerCase().includes(query)
    );
  });

  // Métricas calculadas para tarjetas de resumen
  totalProductosCriticos = computed(() => this.productos().length);

  totalUnidadesSugeridas = computed(() => {
    return this.productos().reduce((acc, p) => acc + p.cantidadSugeridaReabastecimiento, 0);
  });

  ngOnInit(): void {
    this.cargarProductosBajoStock();
  }

  cargarProductosBajoStock(): void {
    this.isLoading.set(true);
    this.errorMessage.set(null);

    this.comprasService.consultarProductosBajoStock().subscribe({
      next: (data) => {
        this.productos.set(data);
        this.isLoading.set(false);
      },
      error: (err) => {
        this.isLoading.set(false);
        console.error('Error al consultar productos bajo stock:', err);
        this.errorMessage.set('No se pudo cargar la lista de productos con bajo stock. Intenta de nuevo.');
      }
    });
  }
}