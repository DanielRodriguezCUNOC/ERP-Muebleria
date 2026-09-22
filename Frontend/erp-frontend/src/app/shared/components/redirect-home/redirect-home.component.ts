import { Component, inject, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TokenService } from '../../../core/services/token.service';
import { redireccionPorArea } from '../../../core/utils/redireccionPorArea';

@Component({
  selector: 'app-redirect-home',
  standalone: true,
  template: ''
})
export class RedirectHomeComponent implements OnInit {
  private readonly tokenService = inject(TokenService);
  private readonly router = inject(Router);

  ngOnInit(): void {
    const payload = this.tokenService.getDecodedToken();
    this.router.navigate([redireccionPorArea(payload?.areaId)]);
  }
}
