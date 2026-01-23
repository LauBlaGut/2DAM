from odoo import models, fields, api
from datetime import date
import base64
import io
import csv

class SaleOrder(models.Model):
    _inherit = 'sale.order'

    # Cambiamos a x_custom_note por convención de campos nuevos (Ejercicio 1)
    x_custom_note = fields.Char(
        string='Nota Personalizada',
        tracking=True,
        help='Nota personalizada para este pedido de venta'
    )

    # Añadimos x_ al campo calculado también
    x_order_duration = fields.Integer(
        string='Days since creation',
        compute='_compute_order_duration',
        store=False,
        help='Days elapsed since order creation'
    )

    x_first_product_image = fields.Binary(
        string="Imagen del producto",
        compute="_compute_first_product_image",
        store=False,
        help='Muestra la imagen del primer producto del pedido'
    )

    @api.depends('date_order')
    def _compute_order_duration(self):
        today = date.today()
        for order in self:
            if order.date_order:
                # Extraemos solo la fecha (sin hora) para restar
                order_date = order.date_order.date()
                delta = (today - order_date).days
                order.x_order_duration = max(0, delta)
            else:
                order.x_order_duration = 0
    
    @api.depends('order_line.product_id.image_128')
    def _compute_first_product_image(self):
        for order in self:
            # Aseguramos que solo tomamos la imagen si hay líneas y el producto tiene una imagen
            if order.order_line and order.order_line[0].product_id:
                # Odoo tiene campos predefinidos para miniaturas, como image_128
                product = order.order_line[0].product_id
                order.x_first_product_image = product.image_128
            else:
                order.x_first_product_image = False

    #Para agrupar el buscador por fecha
    order_year_month = fields.Char(
        string="Año-Mes",
        compute="_compute_order_year_month",
        store=True,
        index=True,
    )

    @api.depends('date_order')
    def _compute_order_year_month(self):
        for order in self:
            if order.date_order:
                # date_order es datetime; nos quedamos con YYYY-MM
                order.order_year_month = fields.Datetime.to_string(order.date_order)[:7]
            else:
                order.order_year_month = False

    # --- SOLUCIÓN EJERCICIO 3: Campo Año-Semestre ---
    order_year_semester = fields.Char(
        string="Año-Semestre",
        compute="_compute_order_year_semester",
        store=True,
        index=True
    )

    @api.depends("date_order")
    def _compute_order_year_semester(self):
        for order in self:
            if order.date_order:
                # Obtenemos año y mes
                dt = order.date_order
                year = dt.year
                month = dt.month

                # Calculamos semestre
                semester = "S1" if month <= 6 else "S2"

                # Formato final: "2025-S1"
                order.order_year_semester = f"{year}-{semester}"
            else:
                order.order_year_semester = False

    x_external_id = fields.Char(string="ID externo")
    x_external_status = fields.Selection([
        ("pending", "Pendiente"),
        ("exported", "Exportado"),
        ("imported", "Importado"),
        ("error", "Error"),
    ], default="pending", string="Estado integración")
    x_estimated_ship_date = fields.Date(string="Fecha estimada envío")
    x_last_export = fields.Datetime(string="Última exportación", readonly=True)
    x_last_import = fields.Datetime(string="Última importación", readonly=True)
    x_integration_error = fields.Text(string="Último error integración",
                                      readonly=True)
    x_export_batch = fields.Char(string="Lote de exportación", readonly=True)

    def action_export_orders_csv(self):
        orders = self.search([
            ("state", "=", "sale"),
            ("x_external_id", "=", False),
            ("x_export_batch", "=", False),
        ])

        if not orders:
            return False
        batch_id = f"BATCH-{fields.Datetime.now().strftime('%Y%m%d_%H%M%S')}"
        output = io.StringIO()
        writer = csv.writer(output, delimiter=";")
        writer.writerow([
            "odoo_order",
            "customer",
            "date_order",
            "amount_total",
            "company_id",
            "batch_id",
        ])
        for so in orders:
            writer.writerow([
                so.name,
                so.partner_id.name or "",
                so.date_order.date().isoformat() if so.date_order else "",
                f"{so.amount_total:.2f}",
                so.company_id.id,
                batch_id,
            ])
        content = output.getvalue().encode("utf-8")
        output.close()
        attachment = self.env["ir.attachment"].create({
            "name": f"export_orders_{batch_id}.csv",
            "type": "binary",
            "datas": base64.b64encode(content),
            "mimetype": "text/csv",
        })
        orders.write({
            "x_external_status": "exported",
            "x_export_batch": batch_id,
            "x_last_export": fields.Datetime.now(),
            "x_integration_error": False,
        })
        return {
            "type": "ir.actions.act_url",
            "url": f"/web/content/{attachment.id}?download=true",
            "target": "self",
        }

    def action_import_logistics_csv(self, file_content):
        import base64
        import io
        import csv

        data = base64.b64decode(file_content).decode('utf-8')
        reader = csv.DictReader(io.StringIO(data), delimiter=';')

        for row in reader:
            order = self.search([('name', '=', row['odoo_order'])], limit=1)
            if order:
                order.write({
                    'x_external_id': row['external_id'],
                    'x_estimated_ship_date': row['estimated_ship_date'],
                    'x_external_status': 'imported',  # Marcamos como importado
                    'x_last_import': fields.Datetime.now(),
                })
        return True
