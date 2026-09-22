import { Component, OnInit, inject, signal, computed } from '@angular/core';
import { CommonModule, CurrencyPipe, DatePipe } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ReportesService } from '../../services/reportes.service';
import { VentasPorPeriodoResponseDTO, ConsultaVentasPeriodoRequestDTO } from '../../models/reportes.model';

@Component({
  selector: 'app-ventas-periodo',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, CurrencyPipe, DatePipe],
  templateUrl: './ventas-periodo.html'
})
export class VentasPeriodoComponent implements OnInit {
  private readonly fb = inject(FormBuilder);
  private readonly reportesService = inject(ReportesService);

  reporteData = signal<VentasPorPeriodoResponseDTO[]>([]);
  isLoading = signal<boolean>(false);
  errorMessage = signal<string | null>(null);
  isExporting = signal<boolean>(false);

  //* Formulario con rango inicial por defecto (primer día del mes actual hasta hoy)
  hoy = new Date().toISOString().split('T')[0];
  primerDiaMes = new Date(new Date().getFullYear(), new Date().getMonth(), 1).toISOString().split('T')[0];

  filterForm = this.fb.nonNullable.group({
    fechaInicio: [this.primerDiaMes, [Validators.required]],
    fechaFin: [this.hoy, [Validators.required]]
  });

  //* Métricas agregadas calculadas
  totalIngresosAcumulados = computed(() =>
    this.reporteData().reduce((acc, curr) => acc + Number(curr.totalIngresos), 0)
  );

  totalFacturasEmitidas = computed(() =>
    this.reporteData().reduce((acc, curr) => acc + Number(curr.totalFacturas), 0)
  );

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

    const dto: ConsultaVentasPeriodoRequestDTO = this.filterForm.getRawValue();

    this.reportesService.obtenerVentasPorRangoFecha(dto).subscribe({
      next: (data) => {
        this.reporteData.set(data);
        this.isLoading.set(false);
      },
      error: (err) => {
        this.isLoading.set(false);
        this.errorMessage.set('Error al consultar el reporte de ventas por rango de fechas.');
        console.error(err);
      }
    });
  }

  exportarPdf(): void {
    this.isExporting.set(true);

    // TODO: Conectar con el backend binario
    setTimeout(() => {
      alert('La exportación a PDF se habilitará al integrar el generador en Spring Boot.');
      this.isExporting.set(false);
    }, 600);
  }
}