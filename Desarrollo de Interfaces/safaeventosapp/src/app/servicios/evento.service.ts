import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {Evento} from "../modelos/evento.model";
import {CategoriaEvento} from "../modelos/categoria-evento.enum";
import {EventoCrear} from "../modelos/EventoCrear";

@Injectable({ providedIn: 'root' })
export class EventoService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/eventos';

  getAll(): Observable<Evento[]> {
    return this.http.get<Evento[]>(`${this.apiUrl}/all`);
  }

  getById(id: number): Observable<Evento> {
    return this.http.get<Evento>(`${this.apiUrl}/${id}`);
  }

  getProximos(): Observable<Evento[]> {
    return this.http.get<Evento[]>(`${this.apiUrl}/descubre`);
  }

  getByCategoria(cat: string): Observable<Evento[]> {
    const encodedCat = encodeURIComponent(cat);
    return this.http.get<Evento[]>(`${this.apiUrl}?categoria=${encodedCat}`);
  }

  crearEvento(evento: any): Observable<any> {
    return this.http.post<Evento>(`${this.apiUrl}`, evento);
  }

  actualizarEvento(id: number,evento: EventoCrear) {
    return this.http.put(`${this.apiUrl}/`+id, evento);
  }

  eliminarEvento(id: number){
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
