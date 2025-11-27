export interface Inscripcion {
  id?: number;
  idUsuario: number;
  idEvento: number;
  pagoRealizado: boolean;
  metodoPago: number;
  tieneCoste: boolean;
}
