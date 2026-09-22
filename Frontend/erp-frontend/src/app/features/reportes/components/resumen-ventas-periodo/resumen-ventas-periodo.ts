import { Component, OnInit, inject, signal, computed } from '@angular/core';
import { CommonModule, CurrencyPipe, DecimalPipe } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ReportesService } from '../../services/reportes.service';
import { ResumenVentasPeriodoResponseDTO, ConsultaResumenVentasRequestDTO, TipoAgrupacion } from '../../models/reportes.model';

@Component({
  selector: 'app-resumen-ventas-periodo',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, CurrencyPipe, DecimalPipe],
  templateUrl: './resumen-ventas-periodo.html'
})
export class ResumenVentasPeriodoComponent implements OnInit {
  private readonly fb = inject(FormBuilder);
  private readonly reportesService = inject(ReportesService);

  // Estados reactivos
  reporteData = signal<ResumenVentasPeriodoResponseDTO[]>([]);
  isLoading = signal<boolean>(false);
  errorMessage = signal<string | null>(null);
  isExporting = signal<boolean>(false);

  // Fechas iniciales por defecto (Mes actual)
  hoyStr = new Date().toISOString().split('T')[0];
  primerDiaMesStr = new Date(new Date().getFullYear(), new Date().getMonth(), 1).toISOString().split('T')[0];

  filterForm = this.fb.nonNullable.group({
    fechaInicio: [this.primerDiaMesStr, [Validators.required]],
    fechaFin: [this.hoyStr, [Validators.required]],
    agrupacion: ['DIA' as TipoAgrupacion, [Validators.required]]
  });

  // Métricas calculadas
  totalIngresos = computed(() =>
    this.reporteData().reduce((acc, curr) => acc + Number(curr.totalIngresos), 0)
  );

  totalFacturas = computed(() =>
    this.reporteData().reduce((acc, curr) => acc + Number(curr.totalFacturas), 0)
  );

  promedioIngresoPorPeriodo = computed(() => {
    const total = this.reporteData().length;
    return total > 0 ? this.totalIngresos() / total : 0;
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

    const dto: ConsultaResumenVentasRequestDTO = {
      fechaInicio: `${raw.fechaInicio}T00:00:00`,
      fechaFin: `${raw.fechaFin}T23:59:59`,
      agrupacion: raw.agrupacion
    };

    this.reportesService.obtenerResumenVentasPorPeriodo(dto).subscribe({
      next: (data) => {
        this.reporteData.set(data);
        this.isLoading.set(false);
      },
      error: (err) => {
        this.isLoading.set(false);
        this.errorMessage.set('Error al consultar el resumen de ventas agrupado.');
        console.error(err);
      }
    });
  }

  exportarPdf(): void {
    this.isExporting.set(true);

    // TODO: Conectar con el backend
    setTimeout(() => {
      alert('La exportación a PDF del resumen agrupado está lista para integrarse.');
      this.isExporting.set(false);
    }, 600);
  }
}