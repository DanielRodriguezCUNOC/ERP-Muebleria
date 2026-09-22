import { Component, OnInit, inject, signal, computed } from '@angular/core';
import { CommonModule, CurrencyPipe, DatePipe, DecimalPipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ReportesService } from '../../services/reportes.service';
import { ProveedorResponseDTO, ReporteCompraResponseDTO } from '../../models/reportes.model';

@Component({
  selector: 'app-compras-proveedor',
  standalone: true,
  imports: [CommonModule, FormsModule, CurrencyPipe, DatePipe, DecimalPipe],
  templateUrl: './compras-proveedor.html'
})
export class ComprasProveedorComponent implements OnInit {
  private readonly reportesService = inject(ReportesService);

  // Estados de Búsqueda de Proveedor
  busquedaProveedor = signal<string>('');
  proveedoresEncontrados = signal<ProveedorResponseDTO[]>([]);
  proveedorSeleccionado = signal<ProveedorResponseDTO | null>(null);
  isSearchingProveedores = signal<boolean>(false);
  showDropdown = signal<boolean>(false);

  // Estados del Reporte
  compras = signal<ReporteCompraResponseDTO[]>([]);
  isLoadingCompras = signal<boolean>(false);
  errorMessage = signal<string | null>(null);
  isExporting = signal<boolean>(false);

  // Métricas Calculadas
  montoTotalInvertido = computed(() =>
    this.compras().reduce((acc, curr) => acc + Number(curr.total), 0)
  );

  totalComprasRealizadas = computed(() => this.compras().length);

  promedioPorCompra = computed(() => {
    const total = this.totalComprasRealizadas();
    return total > 0 ? this.montoTotalInvertido() / total : 0;
  });

  ngOnInit(): void {
    this.cargarProveedoresIniciales();
  }

  cargarProveedoresIniciales(): void {
    this.isSearchingProveedores.set(true);
    this.reportesService.buscarProveedores().subscribe({
      next: (data) => {
        this.proveedoresEncontrados.set(data);
        this.isSearchingProveedores.set(false);
      },
      error: () => this.isSearchingProveedores.set(false)
    });
  }

  onBuscarProveedorInput(): void {
    const query = this.busquedaProveedor().trim();
    this.isSearchingProveedores.set(true);
    this.showDropdown.set(true);

    this.reportesService.buscarProveedores(query).subscribe({
      next: (data) => {
        this.proveedoresEncontrados.set(data);
        this.isSearchingProveedores.set(false);
      },
      error: () => this.isSearchingProveedores.set(false)
    });
  }

  seleccionarProveedor(proveedor: ProveedorResponseDTO): void {
    this.proveedorSeleccionado.set(proveedor);
    this.busquedaProveedor.set(proveedor.nombre);
    this.showDropdown.set(false);
    this.cargarComprasProveedor(proveedor.id);
  }

  cargarComprasProveedor(proveedorId: number): void {
    this.isLoadingCompras.set(true);
    this.errorMessage.set(null);

    this.reportesService.obtenerComprasPorProveedor(proveedorId).subscribe({
      next: (data) => {
        this.compras.set(data);
        this.isLoadingCompras.set(false);
      },
      error: (err) => {
        this.isLoadingCompras.set(false);
        this.errorMessage.set('Error al cargar el historial de compras del proveedor.');
        console.error(err);
      }
    });
  }

  exportarPdf(): void {
    if (!this.proveedorSeleccionado()) return;

    this.isExporting.set(true);
    setTimeout(() => {
      alert('La exportación en PDF del reporte por proveedor está lista para conectarse al backend.');
      this.isExporting.set(false);
    }, 600);
  }
}