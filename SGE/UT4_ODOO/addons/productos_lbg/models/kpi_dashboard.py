from odoo import models, fields, api


class ProductKpiLbg(models.Model):
    _name = 'product.kpi.lbg'
    _description = 'KPIs de Productos'

    name = fields.Char(string="KPI")
    value = fields.Char(string="Valor", compute="_compute_kpi_value")
    color = fields.Integer(string="Color Index")

    def _compute_kpi_value(self):
        self.env.cr.execute("SELECT * FROM products_kpis_lbg()")
        res = self.env.cr.fetchone()


        for record in self:
            if record.name == 'Total Productos':
                record.value = str(res[0])
            elif record.name == 'Precio Medio':
                record.value = "{:.2f} €".format(res[1]) if res[1] else "0.00 €"
            elif record.name == 'Productos Activos':
                record.value = str(res[2])
            else:
                record.value = "0"

    def action_open_related_view(self):
        self.ensure_one()
        action = self.env.ref('product.product_template_action').read()[0]

        if self.name == 'Total Productos':
            action['domain'] = []  # Muestra todos
        elif self.name == 'Productos Activos':
            action['domain'] = [('active', '=', True)]
        return action