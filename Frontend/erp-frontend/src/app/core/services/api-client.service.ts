import { Injectable, inject, signal } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, finalize } from 'rxjs/operators';

export type ApiClientOptions = {
  headers?: HttpHeaders | Record<string, string | string[]>;
  params?: HttpParams | Record<string, string | number | boolean | readonly (string | number | boolean)[]>;
  body?: unknown;
  withCredentials?: boolean;
};

@Injectable({
  providedIn: 'root'
})
export class ApiClientService {
  private readonly http = inject(HttpClient);
  readonly isLoading = signal(false);

  private executeRequest<T>(operation: string, url: string, request: () => Observable<T>): Observable<T> {
    this.isLoading.set(true);

    return request().pipe(
      finalize(() => this.isLoading.set(false)),
      catchError((error) => {
        console.error(`[API] ${operation} ${url} falló`, error);
        return throwError(() => error);
      })
    );
  }

  get<T>(url: string, options?: ApiClientOptions): Observable<T> {
    return this.executeRequest('GET', url, () => this.http.get<T>(url, options));
  }

  post<T>(url: string, body: unknown, options?: ApiClientOptions): Observable<T> {
    return this.executeRequest('POST', url, () => this.http.post<T>(url, body, options));
  }

  put<T>(url: string, body: unknown, options?: ApiClientOptions): Observable<T> {
    return this.executeRequest('PUT', url, () => this.http.put<T>(url, body, options));
  }

  patch<T>(url: string, body: unknown, options?: ApiClientOptions): Observable<T> {
    return this.executeRequest('PATCH', url, () => this.http.patch<T>(url, body, options));
  }

  delete<T>(url: string, options?: ApiClientOptions): Observable<T> {
    return this.executeRequest('DELETE', url, () => this.http.delete<T>(url, options));
  }
}
