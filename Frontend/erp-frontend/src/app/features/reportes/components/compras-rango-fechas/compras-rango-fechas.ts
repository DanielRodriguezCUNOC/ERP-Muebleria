import { Component, OnInit, inject, signal, computed } from '@angular/core';
import { CommonModule, CurrencyPipe, DatePipe, DecimalPipe } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, FormsModule, Validators } from '@angular/forms';
import { ReportesService } from '../../services/reportes.service';
import { ReporteCompraResponseDTO, ConsultaComprasRequestDTO } from '../../models/reportes.model';

@Component({
  selector: 'app-compras-rango-fechas',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, FormsModule, CurrencyPipe, DatePipe, DecimalPipe],
  templateUrl: './compras-rango-fechas.html'
})
export class ComprasRangoFechasComponent implements OnInit {
  private readonly fb = inject(FormBuilder);
  private readonly reportesService = inject(ReportesService);

  // Estados reactivos
  compras = signal<ReporteCompraResponseDTO[]>([]);
  isLoading = signal<boolean>(false);
  errorMessage = signal<string | null>(null);
  isExporting = signal<boolean>(false);
  filtroBusqueda = signal<string>('');

  // Fechas iniciales por defecto (Mes actual)
  hoyStr = new Date().toISOString().split('T')[0];
  primerDiaMesStr = new Date(new Date().getFullYear(), new Date().getMonth(), 1).toISOString().split('T')[0];

  filterForm = this.fb.nonNullable.group({
    fechaInicio: [this.primerDiaMesStr, [Validators.required]],
    fechaFin: [this.hoyStr, [Validators.required]]
  });

  // Filtrado local por Proveedor o ID de Compra
  comprasFiltradas = computed(() => {
    const busqueda = this.filtroBusqueda().toLowerCase().trim();
    if (!busqueda) return this.compras();

    return this.compras().filter(c =>
      c.nombreProveedor.toLowerCase().includes(busqueda) ||
      c.compraId.toString().includes(busqueda) ||
      c.estado.toLowerCase().includes(busqueda)
    );
  });

  // Métricas calculadas
  montoTotalGastado = computed(() =>
    this.comprasFiltradas().reduce((acc, curr) => acc + Number(curr.total), 0)
  );

  totalOrdenesCompra = computed(() => this.comprasFiltradas().length);

  promedioPorCompra = computed(() => {
    const total = this.totalOrdenesCompra();
    return total > 0 ? this.montoTotalGastado() / total : 0;
  });

  ngOnInit(): void {
    this.consultarReporte();
  }

  consultarReporte(): void {
    if (this.filterForm.invalid) {
      this.filterForm.markAllAsTouched();
      return;
    }

    this.isLoading.set(true);
    this.errorMessage.set(null);

    const raw = this.filterForm.getRawValue();

    const dto: ConsultaComprasRequestDTO = {
      fechaInicio: `${raw.fechaInicio}T00:00:00`,
      fechaFin: `${raw.fechaFin}T23:59:59`
    };

    this.reportesService.obtenerComprasPorRangoDeFechas(dto).subscribe({
      next: (data) => {
        this.compras.set(data);
        this.isLoading.set(false);
      },
      error: (err) => {
        this.isLoading.set(false);
        this.errorMessage.set('Error al consultar el reporte de compras por rango de fechas.');
        console.error(err);
      }
    });
  }

  exportarPdf(): void {
    if (this.filterForm.invalid) return;

    this.isExporting.set(true);
    setTimeout(() => {
      alert('La exportación a PDF del reporte de compras se integrará con el endpoint binario.');
      this.isExporting.set(false);
    }, 600);
  }
}