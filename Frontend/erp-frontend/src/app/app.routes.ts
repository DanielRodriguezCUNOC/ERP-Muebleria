import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';
import { AppShellComponent } from './shared/components/app-shell/app-shell.component';

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
      { path: '', redirectTo: 'inventario', pathMatch: 'full' },
      {
        path: 'inventario',
        loadComponent: () => import('./features/inventario/inventario-home.component').then((m) => m.InventarioHomeComponent)
      },
      {
        path: 'compras',
        loadComponent: () => import('./features/compras/compras-home.component').then((m) => m.ComprasHomeComponent)
      },
      {
        path: 'reportes',
        loadComponent: () => import('./features/reportes/reportes-home.component').then((m) => m.ReportesHomeComponent)
      },
      {
        path: 'usuarios',
        loadComponent: () => import('./features/usuarios/components/crear-rol/crear-rol').then((m) => m.CrearRolComponent)
      },
      {
        path: 'clientes',
        loadComponent: () => import('./features/clientes/clientes-home.component').then((m) => m.ClientesHomeComponent)
      },
      {
        path: 'ventas',
        loadComponent: () => import('./features/ventas/components/registrar-venta/registrar-venta').then((m) => m.RegistrarVentaComponent)
      },
      {
        path: 'administracion',
        loadComponent: () => import('./features/administracion/administracion-home.component').then((m) => m.AdministracionHomeComponent)
      }
    ]
  },
  {
    path: '**',
    redirectTo: 'login'
  }
];
