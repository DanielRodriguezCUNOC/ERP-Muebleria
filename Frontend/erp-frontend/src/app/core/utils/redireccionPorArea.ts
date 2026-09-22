export function redireccionPorArea(areaId: number | null | undefined): string {
  switch (areaId) {
    case 1: return '/administracion';
    case 2: return '/compras';
    case 3: return '/inventario';
    case 4: return '/ventas';
    default: return '/login';
  }
}
