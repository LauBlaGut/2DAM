import csv
from datetime import datetime, timedelta


def simulate_external_processing(input_file, output_file):
    with open(input_file, mode='r', encoding='utf-8') as f:
        # Usamos el delimitador ';' definido en el ejercicio [cite: 745]
        reader = csv.DictReader(f, delimiter=';')
        results = []

        for row in reader:
            # Simulamos datos externos
            order_name = row['odoo_order']
            batch_id = row['batch_id']
            external_id = f"EXT-2026-{order_name.replace('S', '')}"
            # Fecha estimada: hoy + 5 días
            ship_date = (datetime.now() + timedelta(days=5)).date().isoformat()

            results.append({
                'odoo_order': order_name,
                'external_id': external_id,
                'estimated_ship_date': ship_date,
                'batch_id': batch_id
            })

    # Escribimos el CSV de respuesta [cite: 754]
    keys = ['odoo_order', 'external_id', 'estimated_ship_date', 'batch_id']
    with open(output_file, mode='w', newline='', encoding='utf-8') as f:
        writer = csv.DictWriter(f, fieldnames=keys, delimiter=';')
        writer.writeheader()
        writer.writerows(results)
    print(f"Simulación finalizada. Archivo generado: {output_file}")

# Uso: simulate_external_processing('export_orders.csv', 'response_orders.csv')