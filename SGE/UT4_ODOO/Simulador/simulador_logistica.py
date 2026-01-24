import csv
from datetime import datetime, timedelta


# Este archivo debe ejecutarse donde tengas el CSV exportado de Odoo
def procesar_simulacion(archivo_entrada='pedidos_exportados.csv'):
    archivo_salida = 'respuesta_logistica.csv'
    try:
        with open(archivo_entrada, mode='r', encoding='utf-8') as f_in:
            reader = csv.DictReader(f_in, delimiter=';')
            filas_respuesta = []

            for row in reader:
                # Generamos datos ficticios basados en el pedido
                order_name = row['odoo_order']
                batch_id = row['batch_id']
                external_id = f"EXT-2026-{order_name[-5:]}"
                ship_date = (datetime.now() + timedelta(days=5)).date().isoformat()

                filas_respuesta.append({
                    'odoo_order': order_name,
                    'external_id': external_id,
                    'estimated_ship_date': ship_date,
                    'batch_id': batch_id
                })

        with open(archivo_salida, mode='w', newline='', encoding='utf-8') as f_out:
            writer = csv.DictWriter(f_out, fieldnames=filas_respuesta[0].keys(), delimiter=';')
            writer.writeheader()
            writer.writerows(filas_respuesta)

        print(f"Éxito: Se ha creado '{archivo_salida}' listo para importar en Odoo.")

    except FileNotFoundError:
        print(f"Error: No se encuentra el archivo '{archivo_entrada}'. Primero expórtalo desde Odoo.")


if __name__ == "__main__":
    procesar_simulacion()