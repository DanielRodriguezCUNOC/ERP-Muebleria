import { Component, OnInit, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { forkJoin } from 'rxjs';
import { RolesService } from '../../services/roles.service';
import { RolResponseDTO, PermisoResponseDTO } from '../../models/rol.model';

@Component({
  selector: 'app-asignar-permiso',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './asignar-permiso.html'
})
export class AsignarPermisoComponent implements OnInit {
  private readonly fb = inject(FormBuilder);
  private readonly rolesService = inject(RolesService);
  private readonly router = inject(Router);

  roles = signal<RolResponseDTO[]>([]);
  permisos = signal<PermisoResponseDTO[]>([]);
  cargandoDatos = signal<boolean>(true);
  isSubmitting = signal<boolean>(false);

  mensajeExito = signal<string | null>(null);
  mensajeError = signal<string | null>(null);

  form = this.fb.group({
    rolId: [null as number | null, [Validators.required]],
    permisoId: [null as number | null, [Validators.required]]
  });

  ngOnInit(): void {
    this.cargarCatalogos();
  }

  cargarCatalogos(): void {
    this.cargandoDatos.set(true);
    this.mensajeError.set(null);

    forkJoin({
      roles: this.rolesService.obtenerTodosLosRoles(),
      permisos: this.rolesService.obtenerPermisos()
    }).subscribe({
      next: ({ roles, permisos }) => {
        this.roles.set(roles);
        this.permisos.set(permisos);
        this.cargandoDatos.set(false);
      },
      error: () => {
        this.cargandoDatos.set(false);
        this.mensajeError.set('Error al cargar la lista de roles y permisos desde el servidor.');
      }
    });
  }

  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const rolId = this.form.value.rolId;
    const permisoId = this.form.value.permisoId;

    if (!rolId || !permisoId) return;

    this.isSubmitting.set(true);
    this.mensajeError.set(null);
    this.mensajeExito.set(null);

    this.rolesService.asignarPermiso(rolId, permisoId).subscribe({
      next: () => {
        this.isSubmitting.set(false);
        this.mensajeExito.set('El permiso fue asignado exitosamente al rol.');
        this.form.reset();
      },
      error: (err) => {
        this.isSubmitting.set(false);
        this.mensajeError.set(err.error?.message || 'Ocurrió un error al asignar el permiso al rol.');
      }
    });
  }
}