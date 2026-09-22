import { Component, inject, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { VentasService } from '../../services/ventas.service';
import { InventarioService } from '../../../inventario/services/inventario.service';
import { ClienteService } from '../../../clientes/services/cliente.service';
import { AuthService } from '../../../../core/services/auth.service';
import { ExistenciaProductoDTO } from '../../../inventario/models/inventario.model';
import { RegistrarVentaRequestDTO, VentaResponseDTO } from '../../models/ventas.model';

export interface ItemDetalleTabla {
  productoId: number;
  sku: string;
  nombre: string;
  cantidad: number;
  precioUnitario: number;
  existenciaDisponible: number;
}

@Component({
  selector: 'app-registrar-venta',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './registrar-venta.html',
  styleUrl: './registrar-venta.css'
})
export class RegistrarVentaComponent {
  private readonly ventasService = inject(VentasService);
  private readonly inventarioService = inject(InventarioService);
  private readonly clienteService = inject(ClienteService);
  private readonly authService = inject(AuthService);

  // Cliente
  clienteId = signal<number | null>(null);
  clienteNit = signal<string>('CF');
  clienteNombre = signal<string>('Consumidor Final');

  // Productos
  busquedaProducto = signal<string>('');
  productosCoincidentes = signal<ExistenciaProductoDTO[]>([]);
  cargandoProductos = signal<boolean>(false);

  // Detalle Venta
  detalles = signal<ItemDetalleTabla[]>([]);

  // Estados
  guardandoVenta = signal<boolean>(false);
  mensajeExito = signal<string | null>(null);
  mensajeError = signal<string | null>(null);

  // Totales
  subTotal = computed(() =>
    this.detalles().reduce((acc, item) => acc + (item.cantidad * item.precioUnitario), 0)
  );
  iva = computed(() => this.subTotal() * 0.12);
  total = computed(() => this.subTotal() + this.iva());

  // Búsqueda en Inventario con Paginación Spring
  onBuscarProducto(query: string): void {
    this.busquedaProducto.set(query);
    if (!query || query.trim().length < 2) {
      this.productosCoincidentes.set([]);
      return;
    }

    this.cargandoProductos.set(true);
    // Invocamos InventarioService y extraemos .content de la Page
    this.inventarioService.consultarExistencias(query, false, 0, 10).subscribe({
      next: (page) => {
        this.productosCoincidentes.set(page.content);
        this.cargandoProductos.set(false);
      },
      error: () => {
        this.productosCoincidentes.set([]);
        this.cargandoProductos.set(false);
      }
    });
  }

  // Búsqueda de Cliente por NIT
  buscarCliente(): void {
    const nitVal = this.clienteNit().trim();
    if (!nitVal || nitVal.toUpperCase() === 'CF') {
      this.clienteNit.set('CF');
      this.clienteNombre.set('Consumidor Final');
      this.clienteId.set(null);
      return;
    }

    this.clienteService.obtenerPorNit(nitVal).subscribe({
      next: (cliente) => {
        if (cliente) {
          this.clienteId.set(cliente.id);
          this.clienteNombre.set(cliente.nombre);
          this.mensajeError.set(null);
        }
      },
      error: (err) => {
        if (err.status === 404) {
          this.mensajeError.set('Cliente no encontrado. Registre al cliente antes de continuar o use CF.');
        } else {
          this.mensajeError.set('Error al consultar el cliente.');
        }
      }
    });
  }

  agregarProducto(prod: ExistenciaProductoDTO): void {
    const actuales = this.detalles();
    const existeIndex = actuales.findIndex(item => item.productoId === prod.productoId);

    if (existeIndex !== -1) {
      const itemExistente = actuales[existeIndex];
      if (itemExistente.cantidad + 1 > prod.existencia) {
        alert(`Stock insuficiente. Disponible: ${prod.existencia}`);
        return;
      }
      const actualizados = [...actuales];
      actualizados[existeIndex] = {
        ...itemExistente,
        cantidad: itemExistente.cantidad + 1
      };
      this.detalles.set(actualizados);
    } else {
      if (prod.existencia < 1) {
        alert('Este producto no cuenta con existencias disponibles.');
        return;
      }
      this.detalles.set([
        ...actuales,
        {
          productoId: prod.productoId,
          sku: prod.sku,
          nombre: prod.nombre,
          cantidad: 1,
          precioUnitario: prod.precio,
          existenciaDisponible: prod.existencia
        }
      ]);
    }

    this.busquedaProducto.set('');
    this.productosCoincidentes.set([]);
  }

  actualizarCantidad(index: number, nuevaCantidad: number): void {
    if (nuevaCantidad <= 0) return;
    const lista = [...this.detalles()];
    const item = lista[index];

    if (nuevaCantidad > item.existenciaDisponible) {
      alert(`La cantidad supera las existencias disponibles (${item.existenciaDisponible}).`);
      return;
    }

    lista[index].cantidad = nuevaCantidad;
    this.detalles.set(lista);
  }

  eliminarDetalle(index: number): void {
    const lista = [...this.detalles()];
    lista.splice(index, 1);
    this.detalles.set(lista);
  }

  procesarVenta(): void {
    this.mensajeError.set(null);
    this.mensajeExito.set(null);

    const empleadoId = this.authService.getUsuarioId();
    if (!empleadoId) {
      this.mensajeError.set('No se pudo determinar el ID del empleado desde el JWT.');
      return;
    }

    if (this.detalles().length === 0) {
      this.mensajeError.set('Debe ingresar al menos un producto.');
      return;
    }

    const payload: RegistrarVentaRequestDTO = {
      empleadoId: empleadoId,
      clienteId: this.clienteId() ?? 1,
      clienteNit: this.clienteNit(),
      clienteNombre: this.clienteNombre(),
      detalles: this.detalles().map(item => ({
        productoId: item.productoId,
        cantidad: item.cantidad,
        precioUnitario: item.precioUnitario
      }))
    };

    this.guardandoVenta.set(true);

    this.ventasService.registrarVenta(payload).subscribe({
      next: (res: VentaResponseDTO) => {
        this.guardandoVenta.set(false);
        this.mensajeExito.set(`Venta registrada exitosamente. Factura: ${res.numeroFactura || res.facturaId}`);
        this.limpiarFormulario();
      },
      error: (err) => {
        this.guardandoVenta.set(false);
        this.mensajeError.set(err.error?.mensaje || 'Error al procesar la venta.');
      }
    });
  }

  limpiarFormulario(): void {
    this.detalles.set([]);
    this.clienteNit.set('CF');
    this.clienteNombre.set('Consumidor Final');
    this.clienteId.set(null);
    this.busquedaProducto.set('');
    this.productosCoincidentes.set([]);
  }
}