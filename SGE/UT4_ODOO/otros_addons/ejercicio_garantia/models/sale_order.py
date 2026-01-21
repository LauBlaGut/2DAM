from odoo import models, fields, api
from odoo.exceptions import ValidationError
from dateutil.relativedelta import relativedelta
from datetime import date


class SaleOrder(models.Model):
    _inherit = 'sale.order'

    # 1. Campos base
    x_tiene_garantia = fields.Boolean(string="Incluye garantía extendida")
    x_meses_garantia = fields.Integer(string="Meses de garantía", default=1)

    # 2. Campo fecha fin (calculado)
    x_fecha_fin_garantia = fields.Date(
        string="Fecha fin de garantía",
        compute="_compute_fechas_garantia",
        store=True
    )

    # 3. Campo días restantes (calculado)
    x_dias_restantes_garantia = fields.Integer(
        string="Días restantes",
        compute="_compute_fechas_garantia"
    )

    @api.depends('date_order', 'x_meses_garantia', 'x_tiene_garantia')
    def _compute_fechas_garantia(self):
        hoy = date.today()
        for order in self:
            if order.x_tiene_garantia and order.date_order:
                # Calculamos fecha fin: fecha pedido + meses
                # date_order es datetime, usamos .date() para convertir a fecha
                fecha_fin = order.date_order.date() + relativedelta(months=order.x_meses_garantia)
                order.x_fecha_fin_garantia = fecha_fin

                # Calculamos días restantes
                delta = (fecha_fin - hoy).days
                order.x_dias_restantes_garantia = max(0, delta)
            else:
                order.x_fecha_fin_garantia = False
                order.x_dias_restantes_garantia = 0

    # 4. Validaciones
    @api.constrains('x_tiene_garantia', 'x_meses_garantia', 'x_fecha_fin_garantia')
    def _check_garantia_rules(self):
        for order in self:
            if order.x_tiene_garantia:
                if order.x_meses_garantia < 1 or order.x_meses_garantia > 24:
                    raise ValidationError("La garantía debe ser de entre 1 y 24 meses.")

                if order.x_fecha_fin_garantia and order.x_fecha_fin_garantia < date.today():
                    raise ValidationError("La fecha de fin de garantía no puede estar en el pasado.")
