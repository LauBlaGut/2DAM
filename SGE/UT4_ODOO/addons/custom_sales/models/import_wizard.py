from odoo import models, fields


class SaleImportWizard(models.TransientModel):
    _name = 'sale.import.wizard'

    file_data = fields.Binary(string="Archivo CSV", required=True)

    def import_file(self):
        # Llama al método del modelo principal
        self.env['sale.order'].action_import_external_data(self.file_data)
        return {'type': 'ir.actions.client', 'tag': 'reload'}