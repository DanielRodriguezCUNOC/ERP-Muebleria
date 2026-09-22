import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './login.component.html'
})
export class LoginComponent {
  private readonly fb = inject(FormBuilder);
  private readonly authService = inject(AuthService);
  private readonly router = inject(Router);

  //* Estados locales con Signals
  isLoading = signal<boolean>(false);
  errorMessage = signal<string | null>(null);

  loginForm = this.fb.nonNullable.group({
    usuario: ['', [Validators.required]],
    password: ['', [Validators.required]]
  });

  onSubmit(): void {
    if (this.loginForm.invalid) {
      this.loginForm.markAllAsTouched();
      return;
    }

    this.isLoading.set(true);
    this.errorMessage.set(null);

    const credentials = this.loginForm.getRawValue();

    this.authService.login(credentials).subscribe({
      next: () => {
        this.isLoading.set(false);
        this.router.navigate(['/inventario']);
      },
      error: (err) => {
        this.isLoading.set(false);
        if (err.status === 401 || err.status === 400) {
          this.errorMessage.set('Credenciales inválidas. Verifica tu usuario y contraseña.');
        } else if (err.error && typeof err.error === 'string') {
          this.errorMessage.set(err.error);
        } else {
          this.errorMessage.set('Ocurrió un error al intentar iniciar sesión. Intente de nuevo.');
        }
      }
    });
  }
}