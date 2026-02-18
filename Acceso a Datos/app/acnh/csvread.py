import csv
import json
import os

nombre_archivo = 'villagers.json'

extension = os.path.splitext(nombre_archivo)[1].lower()

img_url = "https://raw.githubusercontent.com/alexislours/ACNHAPI/refs/heads/master/images/villagers/"

try:
    with open(nombre_archivo, newline='', encoding='utf-8') as archivo:

        # CSV
        if extension == '.csv':
            spamreader = csv.reader(archivo, delimiter=',', quotechar='|')

            for r in spamreader:
                if len(r) >= 3:
                    file_code = r[2]
                    print(f"Nombre: {r[0]}")
                    print(f"Especie: {r[1]}")
                    print(f"Foto: {img_url + file_code + '.png'}")

        # JSON
        elif extension == '.json':
            data = json.load(archivo)

            for r in data:
                file_code = r['file-name']
                print(f"Nombre: {r['name']['name-USen']}")
                print(f"Especie: {r['species']}")
                print(f"Foto: {img_url + file_code + '.png'}")

        else:
            print("Formato no soportado. Usa .csv o .json")

except FileNotFoundError:
    print(f"El archivo '{nombre_archivo}' no existe.")