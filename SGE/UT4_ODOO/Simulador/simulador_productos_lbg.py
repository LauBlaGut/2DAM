import csv
from datetime import datetime

# Archivo que se descarga de Odoo
input_file = 'productos_exportados.csv'
# Archivo que se va a subir a Odoo
output_file = 'productos_respuesta.csv'

try:
    with open(input_file, mode='r', encoding='utf-8') as infile:
        reader = csv.DictReader(infile, delimiter=';')
        rows = []

        for row in reader:
            # Creación de los nuevos datos requeridos
            new_row = {
                'id': row['id'],
                'external_id': f"EXT-LBG-{row['id']}",
                'date_processed': datetime.now().date().isoformat()
            }
            rows.append(new_row)

    # Archivo de respuesta
    with open(output_file, mode='w', newline='', encoding='utf-8') as outfile:
        fieldnames = ['id', 'external_id', 'date_processed']
        writer = csv.DictWriter(outfile, fieldnames=fieldnames, delimiter=';')
        writer.writeheader()
        writer.writerows(rows)

    print(f"Archivo '{output_file}' generado.")

except FileNotFoundError:
    print("Error: No encuentro el archivo 'productos_exportados.csv'")