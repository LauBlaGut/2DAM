from odoo import models, fields, _
from odoo.exceptions import UserError

class ProductImportWizard(models.TransientModel):
    _name = 'product.import.wizard'
    _description = 'Asistente Importación Productos LBG'

    file_data = fields.Binary(string="Archivo CSV", required=True)
    file_name = fields.Char(string="Nombre Archivo")

    def action_import(self):
        # Llamamos al método del modelo product.template
        self.env['product.template'].action_import_external_csv_lbg(self.file_data)
        return {'type': 'ir.actions.client', 'tag': 'reload'}