export interface Notificacion {
  id?: number;
  idUsuario: number;
  mensaje: string;
  fechaEnvio: string;
  leido: boolean;
}
