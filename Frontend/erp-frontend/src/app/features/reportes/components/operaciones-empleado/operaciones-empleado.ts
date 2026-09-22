import { Component, OnInit, inject, signal, computed } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ReportesService } from '../../services/reportes.service';
import { UsuarioResponseDTO, OperacionEmpleadoResponseDTO } from '../../models/reportes.model';

@Component({
  selector: 'app-operaciones-empleado',
  standalone: true,
  imports: [CommonModule, FormsModule, DatePipe],
  templateUrl: './operaciones-empleado.html'
})
export class OperacionesEmpleadoComponent implements OnInit {
  private readonly reportesService = inject(ReportesService);

  //* Estados de Selección de Empleado
  busquedaEmpleado = signal<string>('');
  empleados = signal<UsuarioResponseDTO[]>([]);
  empleadoSeleccionado = signal<UsuarioResponseDTO | null>(null);
  isLoadingEmpleados = signal<boolean>(false);
  showDropdown = signal<boolean>(false);

  //* Estados de la Bitácora
  operaciones = signal<OperacionEmpleadoResponseDTO[]>([]);
  isLoadingOperaciones = signal<boolean>(false);
  errorMessage = signal<string | null>(null);
  filtroBusqueda = signal<string>('');
  isExporting = signal<boolean>(false);

  //* Empleados filtrados dinámicamente en el selector
  empleadosFiltrados = computed(() => {
    const q = this.busquedaEmpleado().toLowerCase().trim();
    if (!q) return this.empleados();

    return this.empleados().filter(e =>
      e.name.toLowerCase().includes(q) ||
      e.usuario.toLowerCase().includes(q) ||
      (e.nombreRol && e.nombreRol.toLowerCase().includes(q))
    );
  });

  // Operaciones filtradas localmente por palabra clave
  operacionesFiltradas = computed(() => {
    const query = this.filtroBusqueda().toLowerCase().trim();
    if (!query) return this.operaciones();

    return this.operaciones().filter(op =>
      op.accion.toLowerCase().includes(query) ||
      op.modulo.toLowerCase().includes(query) ||
      op.detalle.toLowerCase().includes(query)
    );
  });

  // Métricas
  totalOperaciones = computed(() => this.operaciones().length);

  modulosUnicos = computed(() => {
    const modulos = this.operaciones().map(o => o.modulo);
    return new Set(modulos).size;
  });

  ngOnInit(): void {
    this.cargarEmpleados();
  }

  cargarEmpleados(): void {
    this.isLoadingEmpleados.set(true);
    this.reportesService.obtenerTodosLosEmpleados().subscribe({
      next: (data) => {
        this.empleados.set(data);
        this.isLoadingEmpleados.set(false);
      },
      error: (err) => {
        this.isLoadingEmpleados.set(false);
        console.error('Error al cargar catálogo de empleados:', err);
      }
    });
  }

  seleccionarEmpleado(empleado: UsuarioResponseDTO): void {
    this.empleadoSeleccionado.set(empleado);
    this.busquedaEmpleado.set(`${empleado.name} (@${empleado.usuario})`);
    this.showDropdown.set(false);
    this.cargarBitacora(empleado.id);
  }

  cargarBitacora(empleadoId: number): void {
    this.isLoadingOperaciones.set(true);
    this.errorMessage.set(null);

    this.reportesService.obtenerOperacionesPorEmpleado(empleadoId).subscribe({
      next: (data) => {
        this.operaciones.set(data);
        this.isLoadingOperaciones.set(false);
      },
      error: (err) => {
        this.isLoadingOperaciones.set(false);
        this.errorMessage.set('Error al cargar el historial de operaciones del empleado.');
        console.error(err);
      }
    });
  }

  exportarPdf(): void {
    if (!this.empleadoSeleccionado()) return;

    this.isExporting.set(true);
    setTimeout(() => {
      alert('La exportación a PDF del historial de operaciones está lista para conectarse al backend.');
      this.isExporting.set(false);
    }, 600);
  }

  //* Estilos según tipo de acción
  getBadgeColorClass(accion: string): string {
    const act = accion.toUpperCase();
    if (act.includes('CREA') || act.includes('INSERT') || act.includes('GUARDAR')) {
      return 'bg-emerald-100 text-emerald-800 border-emerald-200';
    }
    if (act.includes('ACTUALIZA') || act.includes('EDIT') || act.includes('MODIFICA')) {
      return 'bg-blue-100 text-blue-800 border-blue-200';
    }
    if (act.includes('ELIMINA') || act.includes('BORRA') || act.includes('CANCEL')) {
      return 'bg-rose-100 text-rose-800 border-rose-200';
    }
    if (act.includes('LOGIN') || act.includes('ACCESO') || act.includes('SESION')) {
      return 'bg-purple-100 text-purple-800 border-purple-200';
    }
    return 'bg-slate-100 text-slate-700 border-slate-200';
  }
}