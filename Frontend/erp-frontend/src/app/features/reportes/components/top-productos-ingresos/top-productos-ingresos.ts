import { Component, OnInit, inject, signal, computed } from '@angular/core';
import { CommonModule, CurrencyPipe, DecimalPipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ReportesService } from '../../services/reportes.service';
import { TopProductosMasIngresosResponseDTO } from '../../models/reportes.model';

@Component({
  selector: 'app-top-productos-ingresos',
  standalone: true,
  imports: [CommonModule, FormsModule, CurrencyPipe, DecimalPipe],
  templateUrl: './top-productos-ingresos.html'
})
export class TopProductosIngresosComponent implements OnInit {
  private readonly reportesService = inject(ReportesService);

  // Estados reactivos con Signals
  topProductos = signal<TopProductosMasIngresosResponseDTO[]>([]);
  isLoading = signal<boolean>(true);
  errorMessage = signal<string | null>(null);
  filtroBusqueda = signal<string>('');
  isExporting = signal<boolean>(false);

  // Productos filtrados dinámicamente por Nombre
  productosFiltrados = computed(() => {
    const busqueda = this.filtroBusqueda().toLowerCase().trim();
    if (!busqueda) return this.topProductos();

    return this.topProductos().filter(
      p => p.nombre.toLowerCase().includes(busqueda)
    );
  });

  // Métricas destacadas
  productoMasRentable = computed(() => this.topProductos()[0] ?? null);

  ingresosTotalesTop = computed(() =>
    this.topProductos().reduce((acc, curr) => acc + Number(curr.totalIngresos), 0)
  );

  totalUnidadesVendidas = computed(() =>
    this.topProductos().reduce((acc, curr) => acc + Number(curr.cantidadVendida), 0)
  );

  ngOnInit(): void {
    this.cargarReporte();
  }

  cargarReporte(): void {
    this.isLoading.set(true);
    this.errorMessage.set(null);

    this.reportesService.obtenerTopProductosMasIngresos().subscribe({
      next: (data) => {
        this.topProductos.set(data);
        this.isLoading.set(false);
      },
      error: (err) => {
        this.isLoading.set(false);
        this.errorMessage.set('Error al consultar el reporte de productos por ingresos.');
        console.error(err);
      }
    });
  }

  exportarPdf(): void {
    this.isExporting.set(true);

    // TODO: Conectar con el backend binario
    setTimeout(() => {
      alert('La exportación a PDF está lista para conectarse al servicio en Spring Boot.');
      this.isExporting.set(false);
    }, 600);
  }
}