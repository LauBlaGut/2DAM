from odoo import models, fields, api

class ImportLogisticsWizard(models.TransientModel):
    _name = 'import.logistics.wizard'
    _description = 'Cargar Respuesta Logística'

    file_data = fields.Binary(string='Archivo CSV', required=True)
    file_name = fields.Char(string='Nombre Archivo')

    def process_import(self):
        # Llamamos al método que creamos en sale.order
        return self.env['sale.order'].action_import_logistics_csv(self.file_data)