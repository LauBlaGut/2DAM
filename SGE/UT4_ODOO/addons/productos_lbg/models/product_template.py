import csv
import io
import base64
from datetime import datetime
from odoo import models, fields, _
from odoo.exceptions import UserError


class ProductTemplate(models.Model):
    _inherit = 'product.template'

    # CAMPOS NUEVOS
    num_lote_lbg = fields.Char(
        string='Num. Lote',
        tracking=True,
        readonly=True
    )
    id_externo_lbg = fields.Char(
        string='Id. Externo',
        readonly=True
    )
    fec_val_lbg = fields.Date(
        string='Fecha Validado',
        readonly=True
    )
    est_lote_lbg = fields.Selection([
        ('pendiente', 'Pendiente'),
        ('exportado', 'Exportado'),
        ('importado', 'Importado')
    ], string='Estado Lote', default='pendiente', readonly=True)

    # EXPORTAR CSV
    def action_export_products_csv_lbg(self):
        output = io.StringIO()
        writer = csv.writer(output, delimiter=';')

        writer.writerow(['id', 'name', 'list_price', 'categ_id'])

        for record in self:
            writer.writerow([
                record.id,
                record.name,
                record.list_price,
                record.categ_id.name
            ])
            # Actualización del estado
            record.est_lote_lbg = 'exportado'

        content = base64.b64encode(output.getvalue().encode('utf-8'))
        filename = f'productos_exportados_{datetime.now().strftime("%Y%m%d")}.csv'

        attachment = self.env['ir.attachment'].create({
            'name': filename,
            'datas': content,
            'type': 'binary',
            'res_model': 'product.template',
            'res_id': self.env.user.id,
        })

        return {
            'type': 'ir.actions.act_url',
            'url': f'/web/content/{attachment.id}?download=true',
            'target': 'self',
        }

    # IMPORTAR CSV
    def action_import_external_csv_lbg(self, csv_content):
        decoded_data = base64.b64decode(csv_content).decode("utf-8")
        reader = csv.DictReader(io.StringIO(decoded_data), delimiter=";")

        for row in reader:
            product_id = int(row.get('id', 0))
            product = self.browse(product_id)

            if product.exists():
                product.write({
                    'id_externo_lbg': row.get('external_id'),
                    'fec_val_lbg': row.get('date_processed'),
                    'est_lote_lbg': 'importado'
                })