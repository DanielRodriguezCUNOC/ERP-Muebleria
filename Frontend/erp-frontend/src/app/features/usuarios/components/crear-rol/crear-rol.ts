import { Component, OnInit, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { RolesService } from '../../services/roles.service';
import { CrearRolDTO, PermisoResponseDTO } from '../../models/rol.model';

@Component({
  selector: 'app-crear-rol',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './crear-rol.html'
})
export class CrearRolComponent implements OnInit {
  private readonly fb = inject(FormBuilder);
  private readonly rolesService = inject(RolesService);
  private readonly router = inject(Router);

  // Estados
  isLoading = signal<boolean>(false);
  cargandoPermisos = signal<boolean>(true);
  errorMessage = signal<string | null>(null);

  permisosDisponibles = signal<PermisoResponseDTO[]>([]);
  permisosSeleccionados = signal<number[]>([]);

  form = this.fb.nonNullable.group({
    nombre: ['', [Validators.required, Validators.minLength(3)]],
    descripcion: ['', [Validators.required, Validators.maxLength(255)]]
  });

  ngOnInit(): void {
    this.cargarPermisos();
  }

  cargarPermisos(): void {
    this.cargandoPermisos.set(true);
    this.rolesService.obtenerPermisos().subscribe({
      next: (permisos) => {
        this.permisosDisponibles.set(permisos);
        this.cargandoPermisos.set(false);
      },
      error: (err) => {
        this.cargandoPermisos.set(false);
        this.errorMessage.set('Error al cargar el listado de permisos.');
      }
    });
  }

  togglePermiso(permisoId: number): void {
    const actuales = this.permisosSeleccionados();
    if (actuales.includes(permisoId)) {
      this.permisosSeleccionados.set(actuales.filter(id => id !== permisoId));
    } else {
      this.permisosSeleccionados.set([...actuales, permisoId]);
    }
  }

  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.isLoading.set(true);
    this.errorMessage.set(null);

    const dto: CrearRolDTO = {
      nombre: this.form.controls.nombre.value,
      descripcion: this.form.controls.descripcion.value,
      permisosIds: this.permisosSeleccionados()
    };

    this.rolesService.crearRol(dto).subscribe({
      next: () => {
        this.isLoading.set(false);
        this.router.navigate(['/usuarios/roles']);
      },
      error: (err) => {
        this.isLoading.set(false);
        this.errorMessage.set(err.error?.message || 'Ocurrió un error al crear el rol.');
      }
    });
  }
}