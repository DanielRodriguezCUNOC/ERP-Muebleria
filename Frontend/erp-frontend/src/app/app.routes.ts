import { Routes } from '@angular/router';

export const routes: Routes = [

  {
    path: 'login',
    loadComponent: () => import('./features/auth/login.component/login.component').then(m => m.LoginComponent)
  },
  {
    path: 'recuperar-contrasena',
    loadComponent: () => import('./features/auth/recuperar-contrasena/recuperar-contrasena').then(m => m.RecuperarContrasenaComponent)
  },
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full'
  }



];
