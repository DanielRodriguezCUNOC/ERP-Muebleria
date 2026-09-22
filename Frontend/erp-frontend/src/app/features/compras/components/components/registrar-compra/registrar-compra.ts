import { Component, OnInit, inject, signal, computed } from '@angular/core';
import { CommonModule, CurrencyPipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ComprasService } from '../../../services/compras.service';
import { AuthService } from '../../../../../core/services/auth.service';
import {
  DetalleCompraRequestDTO,
  ProveedorResponseDTO,
  ProductoOptionDTO,
  CompraResponseDTO
} from '../../../models/compras.model';

//* Extensión local para control visual por fila
interface DetalleFilaUI extends DetalleCompraRequestDTO {
  uuid: string;
  subtotal: number;
}

@Component({
  selector: 'app-registrar-compra',
  standalone: true,
  imports: [CommonModule, FormsModule, CurrencyPipe],
  templateUrl: './registrar-compra.html'
})
export class RegistrarCompraComponent implements OnInit {
  private readonly comprasService = inject(ComprasService);
  //* Obtiene el empleado logueado
  private readonly authService = inject(AuthService);

  //* ID del Empleado obtenido del token
  empleadoId = signal<number>(this.authService.getUsuarioId() || 1);

  //* Listas de referencias para los selectores
  proveedores = signal<ProveedorResponseDTO[]>([]);
  productos = signal<ProductoOptionDTO[]>([]);

  isLoadingCatalogos = signal<boolean>(false);
  isSubmitting = signal<boolean>(false);

  //* Mensajes de estado
  mensajeExito = signal<string | null>(null);
  mensajeError = signal<string | null>(null);
  compraResultado = signal<CompraResponseDTO | null>(null);

  //* Listado dinámico de renglones de la compra
  detalles = signal<DetalleFilaUI[]>([]);

  //* Total calculado automáticamente de la compra
  totalCompra = computed(() => {
    return this.detalles().reduce((acc, item) => acc + (item.cantidad * item.precioUnitario || 0), 0);
  });

  //* Conteo total de items/unidades
  totalUnidades = computed(() => {
    return this.detalles().reduce((acc, item) => acc + (Number(item.cantidad) || 0), 0);
  });

  ngOnInit(): void {
    this.cargarCatalogos();
    this.agregarFila();
  }

  cargarCatalogos(): void {
    this.isLoadingCatalogos.set(true);

    //* Carga de proveedores activos
    this.comprasService.consultarProveedores().subscribe({
      next: (data) => this.proveedores.set(data.filter(p => p.activo !== false)),
      error: (err) => console.error('Error al cargar proveedores:', err)
    });

    //* Carga de productos del catálogo
    this.comprasService.consultarProductos().subscribe({
      next: (data) => {
        this.productos.set(data);
        this.isLoadingCatalogos.set(false);
      },
      error: (err) => {
        this.isLoadingCatalogos.set(false);
        console.error('Error al cargar productos:', err);
      }
    });
  }

  agregarFila(): void {
    const nuevaFila: DetalleFilaUI = {
      uuid: crypto.randomUUID(),
      productoId: null,
      proveedorId: null,
      cantidad: 1,
      precioUnitario: 0,
      subtotal: 0
    };
    this.detalles.update(lista => [...lista, nuevaFila]);
  }

  eliminarFila(uuid: string): void {
    if (this.detalles().length === 1) {
      alert('Debe incluir al menos un detalle en la compra.');
      return;
    }
    this.detalles.update(lista => lista.filter(item => item.uuid !== uuid));
  }

  actualizarSubtotal(index: number): void {
    this.detalles.update(lista => {
      const copia = [...lista];
      const item = copia[index];
      item.subtotal = (item.cantidad || 0) * (item.precioUnitario || 0);
      return copia;
    });
  }

  //* Pre-llenar precio si el producto tiene sugerencia
  onProductoSeleccionado(index: number, productoId: number | null): void {
    if (!productoId) return;
    const prod = this.productos().find(p => p.id === Number(productoId));
    if (prod && prod.precioVenta) {
      this.detalles.update(lista => {
        const copia = [...lista];
        if (copia[index].precioUnitario === 0) {
          copia[index].precioUnitario = prod.precioVenta || 0;
          copia[index].subtotal = copia[index].cantidad * copia[index].precioUnitario;
        }
        return copia;
      });
    }
  }

  validarFormulario(): boolean {
    if (!this.empleadoId()) {
      this.mensajeError.set('No se ha detectado el ID del empleado.');
      return false;
    }

    if (this.detalles().length === 0) {
      this.mensajeError.set('Debe agregar al menos un producto a la compra.');
      return false;
    }

    for (let i = 0; i < this.detalles().length; i++) {
      const d = this.detalles()[i];
      if (!d.productoId) {
        this.mensajeError.set(`Fila ${i + 1}: Seleccione un producto.`);
        return false;
      }
      if (!d.proveedorId) {
        this.mensajeError.set(`Fila ${i + 1}: Seleccione el proveedor correspondiente.`);
        return false;
      }
      if (!d.cantidad || d.cantidad <= 0) {
        this.mensajeError.set(`Fila ${i + 1}: La cantidad debe ser mayor a 0.`);
        return false;
      }
      if (d.precioUnitario == null || d.precioUnitario < 0) {
        this.mensajeError.set(`Fila ${i + 1}: Ingrese un precio unitario válido.`);
        return false;
      }
    }

    this.mensajeError.set(null);
    return true;
  }

  guardarCompra(): void {
    if (!this.validarFormulario()) return;

    this.isSubmitting.set(true);
    this.mensajeExito.set(null);
    this.mensajeError.set(null);

    //* Preparar Payload
    const payload = {
      empleadoId: Number(this.empleadoId()),
      detalles: this.detalles().map(d => ({
        productoId: Number(d.productoId),
        proveedorId: Number(d.proveedorId),
        cantidad: Number(d.cantidad),
        precioUnitario: Number(d.precioUnitario)
      }))
    };

    this.comprasService.registrarCompra(payload).subscribe({
      next: (res) => {
        this.isSubmitting.set(false);
        this.compraResultado.set(res);
        this.mensajeExito.set(res.mensaje || `¡Compra #${res.id} registrada exitosamente!`);
        this.resetearFormulario();
      },
      error: (err) => {
        this.isSubmitting.set(false);
        console.error('Error al registrar compra:', err);
        this.mensajeError.set(err.error?.message || 'Ocurrió un error al procesar el registro de la compra.');
      }
    });
  }

  resetearFormulario(): void {
    this.detalles.set([]);
    this.agregarFila();
  }
}