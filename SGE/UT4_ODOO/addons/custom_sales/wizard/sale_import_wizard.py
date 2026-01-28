from odoo import models, fields, _
from odoo.exceptions import UserError
import base64


class SaleImportWizard(models.TransientModel):
    _name = 'sale.import.wizard'
    _description = 'Asistente para importar respuesta logística'

    file_data = fields.Binary(string="Archivo de respuesta (CSV)", required=True)
    file_name = fields.Char(string="Nombre del archivo")

    def action_import(self):
        """Llama al método del modelo sale.order para procesar el archivo"""
        if not self.file_data:
            raise UserError(_("Por favor, sube un archivo."))

        # Llamamos a la función que creaste en sale.order (Paso 3)
        # Nota: Asegúrate de que el método en sale.order se llame 'action_import_external_csv'
        self.env['sale.order'].action_import_external_csv(self.file_data)

        return {
            'type': 'ir.actions.client',
            'tag': 'display_notification',
            'params': {
                'title': _('Éxito'),
                'message': _('Datos logísticos importados correctamente.'),
                'type': 'success',
                'sticky': False,
                'next': {'type': 'ir.actions.client', 'tag': 'reload'},
            }
        }