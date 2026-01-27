import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {ComentarioEvento} from "../modelos/comentario-evento.model";

@Injectable({ providedIn: 'root' })
export class ComentarioEventoService {
  private http = inject(HttpClient);
  private baseUrl = '/api/comentarioevento';

  getAll(): Observable<ComentarioEvento[]> {
    return this.http.get<ComentarioEvento[]>(`${this.baseUrl}/all`);
  }

  getByUsuario(id: number): Observable<ComentarioEvento[]> {
    return this.http.get<ComentarioEvento[]>(`${this.baseUrl}/usuario/${id}`);
  }

  getByEvento(id: number): Observable<ComentarioEvento[]> {
    return this.http.get<ComentarioEvento[]>(`${this.baseUrl}/evento/${id}`);
  }

  guardar(dto: ComentarioEvento): Observable<ComentarioEvento> {
    return this.http.post<ComentarioEvento>(`${this.baseUrl}/guardar`, dto);
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/eliminar/${id}`);
  }
}
