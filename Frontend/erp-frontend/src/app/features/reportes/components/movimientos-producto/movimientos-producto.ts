import { Component, OnInit, inject, signal, computed } from '@angular/core';
import { CommonModule, DatePipe, DecimalPipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ReportesService } from '../../services/reportes.service';
import { ProductoCatalogoResponseDTO, MovimientoProductoResponseDTO } from '../../models/reportes.model';

@Component({
  selector: 'app-movimientos-producto',
  standalone: true,
  imports: [CommonModule, FormsModule, DatePipe, DecimalPipe],
  templateUrl: './movimientos-producto.html'
})
export class MovimientosProductoComponent implements OnInit {
  private readonly reportesService = inject(ReportesService);

  //* Estados para selección de Producto
  busquedaProducto = signal<string>('');
  productosEncontrados = signal<ProductoCatalogoResponseDTO[]>([]);
  productoSeleccionado = signal<ProductoCatalogoResponseDTO | null>(null);
  isSearchingProductos = signal<boolean>(false);
  showDropdown = signal<boolean>(false);

  //* Estados para Movimientos
  movimientos = signal<MovimientoProductoResponseDTO[]>([]);
  isLoadingMovimientos = signal<boolean>(false);
  errorMessage = signal<string | null>(null);
  isExporting = signal<boolean>(false);

  //* Cálculos dinámicos
  totalEntradas = computed(() =>
    this.movimientos()
      .filter(m => m.cantidadCambio > 0)
      .reduce((acc, curr) => acc + curr.cantidadCambio, 0)
  );

  totalSalidas = computed(() =>
    this.movimientos()
      .filter(m => m.cantidadCambio < 0)
      .reduce((acc, curr) => acc + Math.abs(curr.cantidadCambio), 0)
  );

  ngOnInit(): void {
    this.cargarCatalogoInicial();
  }

  cargarCatalogoInicial(): void {
    this.isSearchingProductos.set(true);
    this.reportesService.buscarProductosCatalogo().subscribe({
      next: (data) => {
        this.productosEncontrados.set(data);
        this.isSearchingProductos.set(false);
      },
      error: () => this.isSearchingProductos.set(false)
    });
  }

  onBuscarProductoInput(): void {
    const query = this.busquedaProducto().trim();
    //* Evita error < 2 caracteres del backend
    if (query.length === 1) return;

    this.isSearchingProductos.set(true);
    this.showDropdown.set(true);

    this.reportesService.buscarProductosCatalogo(query).subscribe({
      next: (data) => {
        this.productosEncontrados.set(data);
        this.isSearchingProductos.set(false);
      },
      error: () => this.isSearchingProductos.set(false)
    });
  }

  seleccionarProducto(producto: ProductoCatalogoResponseDTO): void {
    this.productoSeleccionado.set(producto);
    this.busquedaProducto.set(`${producto.sku} - ${producto.nombre}`);
    this.showDropdown.set(false);
    this.cargarMovimientos(producto.id);
  }

  cargarMovimientos(productoId: number): void {
    this.isLoadingMovimientos.set(true);
    this.errorMessage.set(null);

    this.reportesService.obtenerMovimientosProducto(productoId).subscribe({
      next: (data) => {
        this.movimientos.set(data);
        this.isLoadingMovimientos.set(false);
      },
      error: (err) => {
        this.isLoadingMovimientos.set(false);
        this.errorMessage.set('Error al cargar el historial de movimientos.');
        console.error(err);
      }
    });
  }

  exportarPdf(): void {
    if (!this.productoSeleccionado()) return;

    this.isExporting.set(true);
    setTimeout(() => {
      alert('La exportación en PDF se habilitará al implementar la respuesta en Spring Boot.');
      this.isExporting.set(false);
    }, 600);
  }
}