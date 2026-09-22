import { Component, inject, signal, input, output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ComprasService } from '../../../services/compras.service';
import { AuthService } from '../../../../../core/services/auth.service';
import { AnularCompraRequestDTO, CompraResponseDTO } from '../../../models/compras.model';

@Component({
  selector: 'app-anular-compra-modal',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './anular-compra.html'
})
export class AnularCompraModalComponent {
  private readonly comprasService = inject(ComprasService);
  private readonly authService = inject(AuthService);

  // Recibe la compra seleccionada desde el Historial
  compraId = input.required<number>();
  onClose = output<void>();
  onExito = output<CompraResponseDTO>();

  motivo = signal<string>('');
  isLoading = signal<boolean>(false);
  errorMessage = signal<string | null>(null);

  confirmarAnulacion(): void {
    const textoMotivo = this.motivo().trim();

    if (!textoMotivo) {
      this.errorMessage.set('Debes ingresar el motivo de la anulación.');
      return;
    }

    if (textoMotivo.length < 10) {
      this.errorMessage.set('El motivo debe ser más descriptivo (mínimo 10 caracteres).');
      return;
    }

    // Obtener el ID del empleado autenticado desde el token JWT
    const empleadoId = this.authService.getUsuarioId();
    if (!empleadoId) {
      this.errorMessage.set('No se pudo determinar la sesión del usuario. Intenta reiniciar sesión.');
      return;
    }

    this.isLoading.set(true);
    this.errorMessage.set(null);

    // Construcción del DTO completo
    const payload: AnularCompraRequestDTO = {
      compraId: this.compraId(),
      empleadoId: empleadoId,
      motivo: textoMotivo
    };

    this.comprasService.anularCompra(payload).subscribe({
      next: (response) => {
        this.isLoading.set(false);
        this.onExito.emit(response);
      },
      error: (err) => {
        this.isLoading.set(false);
        console.error('Error al anular la compra:', err);
        const mensajeApi = err.error?.mensaje || 'Ocurrió un error al intentar anular la compra.';
        this.errorMessage.set(mensajeApi);
      }
    });
  }

  cerrar(): void {
    if (!this.isLoading()) {
      this.onClose.emit();
    }
  }
}