import { Component, OnInit, inject, signal, computed } from '@angular/core';
import { CommonModule, CurrencyPipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ReportesService } from '../../services/reportes.service';
import { TopClientesResponseDTO } from '../../models/reportes.model';

@Component({
  selector: 'app-top-clientes',
  standalone: true,
  imports: [CommonModule, FormsModule, CurrencyPipe],
  templateUrl: './top-clientes.html'
})
export class TopClientesComponent implements OnInit {
  private readonly reportesService = inject(ReportesService);


  topClientes = signal<TopClientesResponseDTO[]>([]);
  isLoading = signal<boolean>(true);
  errorMessage = signal<string | null>(null);
  filtroBusqueda = signal<string>('');
  isExporting = signal<boolean>(false);

  //* Clientes filtrados dinámicamente por Nombre o NIT
  clientesFiltrados = computed(() => {
    const busqueda = this.filtroBusqueda().toLowerCase().trim();
    if (!busqueda) return this.topClientes();

    return this.topClientes().filter(
      c => c.nombre.toLowerCase().includes(busqueda) || c.nit.toLowerCase().includes(busqueda)
    );
  });

  //* Cliente con el monto total más alto y el monto total acumulado de todos los clientes
  clienteTopStar = computed(() => this.topClientes()[0] ?? null);
  montoTotalAcumulado = computed(() =>
    this.topClientes().reduce((acc, curr) => acc + Number(curr.montoTotal), 0)
  );

  ngOnInit(): void {
    this.cargarReporte();
  }

  cargarReporte(): void {
    this.isLoading.set(true);
    this.errorMessage.set(null);

    this.reportesService.obtenerTopClientes().subscribe({
      next: (data) => {
        this.topClientes.set(data);
        this.isLoading.set(false);
      },
      error: (err) => {
        this.isLoading.set(false);
        this.errorMessage.set('Error al consultar el reporte de top clientes.');
        console.error(err);
      }
    });
  }

  //*  Para el botón de exportación en PDF
  exportarPdf(): void {
    this.isExporting.set(true);

    // TODO: Aqui conectaremos con el backend
    setTimeout(() => {
      alert('La exportación a PDF está preparada en el frontend y se activará al implementar el servicio en Java.');
      this.isExporting.set(false);
    }, 600);
  }
}