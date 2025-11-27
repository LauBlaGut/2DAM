import {CategoriaEvento} from "./categoria-evento.enum";

export interface Evento {
  id?: number;
  titulo: string;
  descripcion: string;
  fecha: string;
  hora: string;
  ubicacion: string;
  foto: string;
  precio: number;
  categoria: CategoriaEvento;
  idOrganizador: number;
}
