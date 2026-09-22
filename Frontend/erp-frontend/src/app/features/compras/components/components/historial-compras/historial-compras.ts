import { Component, OnInit, inject, signal, computed } from '@angular/core';
import { CommonModule, CurrencyPipe, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ComprasService } from '../../../services/compras.service';
import {
  FiltroHistorialCompraDTO,
  HistorialComprasResponseDTO,
  ProveedorResponseDTO
} from '../../../models/compras.model';

@Component({
  selector: 'app-historial-compras',
  standalone: true,
  imports: [CommonModule, FormsModule, CurrencyPipe, DatePipe],
  templateUrl: './historial-compras.html'
})
export class HistorialComprasComponent implements OnInit {
  private readonly comprasService = inject(ComprasService);

  // Catalogo para el filtro
  proveedores = signal<ProveedorResponseDTO[]>([]);

  // Estado del listado y carga
  historial = signal<HistorialComprasResponseDTO[]>([]);
  isLoading = signal<boolean>(false);
  mensajeError = signal<string | null>(null);

  // Signals para los filtros dinámicos
  fechaInicio = signal<string>('');
  fechaFin = signal<string>('');
  proveedorId = signal<number | null>(null);
  empleadoId = signal<number | null>(null);

  // Métricas acumuladas en tiempo real
  montoTotalAcumulado = computed(() => {
    return this.historial().reduce((acc, item) => acc + (Number(item.costoTotal) || 0), 0);
  });

  totalProductosAcumulados = computed(() => {
    return this.historial().reduce((acc, item) => acc + (Number(item.totalProductos) || 0), 0);
  });

  ngOnInit(): void {
    this.cargarProveedores();
    this.consultar();
  }

  cargarProveedores(): void {
    this.comprasService.consultarProveedores().subscribe({
      next: (data) => this.proveedores.set(data),
      error: (err) => console.error('Error al obtener catálogo de proveedores:', err)
    });
  }

  consultar(): void {
    this.isLoading.set(true);
    this.mensajeError.set(null);

    const filtro: FiltroHistorialCompraDTO = {
      fechaInicio: this.fechaInicio() || null,
      fechaFin: this.fechaFin() || null,
      proveedorId: this.proveedorId() ? Number(this.proveedorId()) : null,
      empleadoId: this.empleadoId() ? Number(this.empleadoId()) : null
    };

    this.comprasService.consultarHistorial(filtro).subscribe({
      next: (data) => {
        this.historial.set(data);
        this.isLoading.set(false);
      },
      error: (err) => {
        this.isLoading.set(false);
        console.error('Error al consultar historial de compras:', err);
        this.mensajeError.set('No se pudo cargar el historial de compras. Verifica los filtros seleccionados.');
      }
    });
  }

  limpiarFiltros(): void {
    this.fechaInicio.set('');
    this.fechaFin.set('');
    this.proveedorId.set(null);
    this.empleadoId.set(null);
    this.consultar();
  }
}