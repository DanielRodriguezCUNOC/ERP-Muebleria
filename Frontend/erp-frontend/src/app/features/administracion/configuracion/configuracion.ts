import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ConfiguracionService } from '../../../features/administracion/services/configuracion.service';
import { ActualizarConfiguracionDTO, ConfiguracionSistema } from '../../administracion/models/configuracion.model';

@Component({
  selector: 'app-configuracion',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './configuracion.html'
})
export class ConfiguracionComponent implements OnInit {
  private readonly fb = inject(FormBuilder);
  private readonly configService = inject(ConfiguracionService);

  //* Estados locales
  isLoading = signal<boolean>(true);
  isSaving = signal<boolean>(false);
  errorMessage = signal<string | null>(null);
  successMessage = signal<string | null>(null);

  configForm = this.fb.nonNullable.group({
    tasaIva: [0.12, [Validators.required, Validators.min(0), Validators.max(1)]],
    metodoValoracion: ['PEPS' as 'PEPS' | 'UEPS', [Validators.required]],
    resolucionFactura: ['', [Validators.required]],
    serieFacturas: ['', [Validators.required]],
    correlativoSiguiente: [1, [Validators.required, Validators.min(1)]]
  });

  ngOnInit(): void {
    this.cargarConfiguracion();
  }

  cargarConfiguracion(): void {
    this.isLoading.set(true);
    this.errorMessage.set(null);

    this.configService.obtenerConfiguracion().subscribe({
      next: (config: ConfiguracionSistema) => {
        this.configForm.patchValue({
          tasaIva: config.tasaIva,
          metodoValoracion: config.metodoValoracion,
          resolucionFactura: config.resolucionFacturas,
          serieFacturas: config.serieFacturas,
          correlativoSiguiente: config.correlativoSiguiente
        });
        this.isLoading.set(false);
      },
      error: (err) => {
        this.isLoading.set(false);
        this.errorMessage.set('Error al cargar la configuración del sistema.');
        console.error(err);
      }
    });
  }

  onSubmit(): void {
    if (this.configForm.invalid) {
      this.configForm.markAllAsTouched();
      return;
    }

    this.isSaving.set(true);
    this.errorMessage.set(null);
    this.successMessage.set(null);

    const dto: ActualizarConfiguracionDTO = this.configForm.getRawValue();

    this.configService.actualizarConfiguracion(dto).subscribe({
      next: () => {
        this.isSaving.set(false);
        this.successMessage.set('Parámetros del sistema actualizados correctamente.');
        setTimeout(() => this.successMessage.set(null), 4000);
      },
      error: (err) => {
        this.isSaving.set(false);
        const msg = typeof err.error === 'string' ? err.error : (err.error?.message || 'Error al guardar la configuración.');
        this.errorMessage.set(msg);
      }
    });
  }
}