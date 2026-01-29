import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {MeInteresa} from "../modelos/me-interesa.model";

@Injectable({ providedIn: 'root' })
export class MeInteresaService {
  private http = inject(HttpClient);
  private baseUrl = 'https://safaeventos-springboot.onrender.com/meinteresa';

  getAll(): Observable<MeInteresa[]> {
    return this.http.get<MeInteresa[]>(`${this.baseUrl}/all`);
  }

  getByUsuario(id: number): Observable<MeInteresa[]> {
    return this.http.get<MeInteresa[]>(`${this.baseUrl}/usuario/${id}`);
  }

  guardar(dto: MeInteresa): Observable<MeInteresa> {
    return this.http.post<MeInteresa>(`${this.baseUrl}/guardar`, dto);
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/eliminar/${id}`);
  }
}
