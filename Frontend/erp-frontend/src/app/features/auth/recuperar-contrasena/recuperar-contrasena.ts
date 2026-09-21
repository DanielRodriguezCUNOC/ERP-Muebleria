import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators, AbstractControl, ValidationErrors } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { RecuperarContrasenaDTO } from '../../../core/models/auth.model';

@Component({
  selector: 'app-recuperar-contrasena',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './recuperar-contrasena.html'
})
export class RecuperarContrasenaComponent {
  private readonly fb = inject(FormBuilder);
  private readonly authService = inject(AuthService);
  private readonly router = inject(Router);

  // * Estados locales con Signals
  isLoading = signal<boolean>(false);
  errorMessage = signal<string | null>(null);
  successMessage = signal<string | null>(null);

  recoveryForm = this.fb.nonNullable.group(
    {
      usuario: ['', [Validators.required]],
      dpi: ['', [Validators.required, Validators.pattern(/^[0-9]{13}$/)]],
      newPassword: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', [Validators.required]]
    },
    { validators: [this.passwordsMatchValidator] }
  );

  //* Validador para que ambas contraseñas coincidan
  private passwordsMatchValidator(group: AbstractControl): ValidationErrors | null {
    const newPassword = group.get('newPassword')?.value;
    const confirmPassword = group.get('confirmPassword')?.value;
    return newPassword === confirmPassword ? null : { passwordMismatch: true };
  }

  onSubmit(): void {
    if (this.recoveryForm.invalid) {
      this.recoveryForm.markAllAsTouched();
      return;
    }

    this.isLoading.set(true);
    this.errorMessage.set(null);
    this.successMessage.set(null);

    const formVal = this.recoveryForm.getRawValue();
    const payload: RecuperarContrasenaDTO = {
      usuario: formVal.usuario.trim(),
      dpi: formVal.dpi.trim(),
      newPassword: formVal.newPassword
    };

    this.authService.recuperarContrasena(payload).subscribe({
      next: () => {
        this.isLoading.set(false);
        this.successMessage.set('¡Contraseña actualizada exitosamente! Redirigiendo al inicio de sesión...');

        setTimeout(() => {
          this.router.navigate(['/login']);
        }, 2500);
      },
      error: (err) => {
        this.isLoading.set(false);
        if (err.status === 400 || err.status === 404 || err.status === 500) {
          //* Captura mensajes de excepción lanzados por el backend 
          const msg = typeof err.error === 'string' ? err.error : (err.error?.message || 'Usuario o DPI no coinciden.');
          this.errorMessage.set(msg);
        } else {
          this.errorMessage.set('Error de conexión al intentar restablecer la contraseña.');
        }
      }
    });
  }
}