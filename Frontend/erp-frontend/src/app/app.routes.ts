import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';
import { AppShellComponent } from './shared/components/app-shell/app-shell.component';
import { RedirectHomeComponent } from './shared/components/redirect-home/redirect-home.component';

export const routes: Routes = [
  {
    path: 'login',
    loadComponent: () => import('./features/auth/login.component/login.component').then((m) => m.LoginComponent)
  },
  {
    path: 'recuperar-contrasena',
    loadComponent: () => import('./features/auth/recuperar-contrasena/recuperar-contrasena').then((m) => m.RecuperarContrasenaComponent)
  },
  {
    path: '',
    component: AppShellComponent,
    canActivate: [authGuard],
    children: [
      { path: '', component: RedirectHomeComponent, pathMatch: 'full' },
      {
        path: 'inventario',
        children: [
          { path: '', loadComponent: () => import('./features/dashboard/inventario/inventario-dashboard.component').then((m) => m.InventarioDashboardComponent) },
          { path: 'movimientos', loadComponent: () => import('./features/reportes/components/movimientos-producto/movimientos-producto').then((m) => m.MovimientosProductoComponent) }
        ]
      },
      {
        path: 'compras',
        children: [
          { path: '', loadComponent: () => import('./features/dashboard/compras/compras-dashboard.component').then((m) => m.ComprasDashboardComponent) },
          { path: 'registrar-compra', loadComponent: () => import('./features/compras/components/components/registrar-compra/registrar-compra').then((m) => m.RegistrarCompraComponent) },
          { path: 'anular-compra', loadComponent: () => import('./features/compras/components/components/anular-compra/anular-compra').then((m) => m.AnularCompraModalComponent) },
          { path: 'historial-compras', loadComponent: () => import('./features/compras/components/components/historial-compras/historial-compras').then((m) => m.HistorialComprasComponent) },
          { path: 'productos-bajo-stock', loadComponent: () => import('./features/compras/components/components/productos-bajo-stock/productos-bajo-stock').then((m) => m.ProductosBajoStockComponent) }
        ]
      },
      {
        path: 'ventas',
        children: [
          { path: '', loadComponent: () => import('./features/dashboard/ventas/ventas-dashboard.component').then((m) => m.VentasDashboardComponent) },
          { path: 'registrar-venta', loadComponent: () => import('./features/ventas/components/registrar-venta/registrar-venta').then((m) => m.RegistrarVentaComponent) }
        ]
      },
      {
        path: 'administracion',
        children: [
          { path: '', loadComponent: () => import('./features/dashboard/administracion/administracion-dashboard.component').then((m) => m.AdministracionDashboardComponent) },
          { path: 'configuracion', loadComponent: () => import('./features/administracion/configuracion/configuracion').then((m) => m.ConfiguracionComponent) }
        ]
      },
      {
        path: 'usuarios',
        children: [
          { path: 'roles-permisos', loadComponent: () => import('./features/usuarios/components/crear-rol/crear-rol').then((m) => m.CrearRolComponent) }
        ]
      },
      {
        path: 'reportes',
        children: [
          { path: '', loadComponent: () => import('./features/reportes/reportes-home.component').then((m) => m.ReportesHomeComponent) },
          { path: 'compras-proveedor', loadComponent: () => import('./features/reportes/components/compras-proveedor/compras-proveedor').then((m) => m.ComprasProveedorComponent) },
          { path: 'compras-rango-fechas', loadComponent: () => import('./features/reportes/components/compras-rango-fechas/compras-rango-fechas').then((m) => m.ComprasRangoFechasComponent) },
          { path: 'movimientos-producto', loadComponent: () => import('./features/reportes/components/movimientos-producto/movimientos-producto').then((m) => m.MovimientosProductoComponent) },
          { path: 'operaciones-empleado', loadComponent: () => import('./features/reportes/components/operaciones-empleado/operaciones-empleado').then((m) => m.OperacionesEmpleadoComponent) },
          { path: 'resumen-ventas-periodo', loadComponent: () => import('./features/reportes/components/resumen-ventas-periodo/resumen-ventas-periodo').then((m) => m.ResumenVentasPeriodoComponent) },
          { path: 'top-clientes', loadComponent: () => import('./features/reportes/components/top-clientes/top-clientes').then((m) => m.TopClientesComponent) },
          { path: 'top-productos-ingresos', loadComponent: () => import('./features/reportes/components/top-productos-ingresos/top-productos-ingresos').then((m) => m.TopProductosIngresosComponent) },
          { path: 'ventas-periodo', loadComponent: () => import('./features/reportes/components/ventas-periodo/ventas-periodo').then((m) => m.VentasPeriodoComponent) }
        ]
      },
      {
        path: 'clientes',
        loadComponent: () => import('./features/clientes/clientes-home.component').then((m) => m.ClientesHomeComponent)
      }
    ]
  },
  {
    path: '**',
    redirectTo: 'login'
  }
];
