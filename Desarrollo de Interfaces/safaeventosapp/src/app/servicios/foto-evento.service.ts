import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {FotoEvento} from "../modelos/foto-evento.model";

@Injectable({ providedIn: 'root' })
export class FotoEventoService {
  private http = inject(HttpClient);
  private baseUrl = 'https://twodam.onrender.com/fotoevento';

  getAll(): Observable<FotoEvento[]> {
    return this.http.get<FotoEvento[]>(`${this.baseUrl}/all`);
  }

  getByUsuario(id: number): Observable<FotoEvento[]> {
    return this.http.get<FotoEvento[]>(`${this.baseUrl}/usuario/${id}`);
  }

  getByEvento(id: number): Observable<FotoEvento[]> {
    return this.http.get<FotoEvento[]>(`${this.baseUrl}/eventos/${id}`);
  }

  guardar(dto: FotoEvento): Observable<FotoEvento> {
    return this.http.post<FotoEvento>(`${this.baseUrl}/guardar`, dto);
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/eliminar/${id}`);
  }

  subirFoto(eventoId: number, usuarioId: number, data: any): Observable<any> {
    const url = `${this.baseUrl}/eventos/${eventoId}/usuario/${usuarioId}`;
    return this.http.post(url, data);
  }
}


