from odoo import models, fields, api
from odoo.exceptions import ValidationError
from datetime import date


class SaleOrder(models.Model):
    _inherit = 'sale.order'

    # 1. % Comisión (0-20)
    x_porcentaje_comision = fields.Float(string="% Comisión vendedor", default=0.0)

    # 2. Monto calculado
    x_monto_comision = fields.Monetary(
        string="Monto Comisión",
        compute="_compute_monto_comision",
        currency_field='currency_id',
        store=True
    )

    # 3. Estado de pago
    x_pagada_comision = fields.Boolean(string="Comisión pagada", default=False)

    # 4. Fecha de pago
    x_fecha_pago_comision = fields.Date(string="Fecha pago comisión")

    # Cálculo del monto
    @api.depends('amount_total', 'x_porcentaje_comision')
    def _compute_monto_comision(self):
        for order in self:
            order.x_monto_comision = order.amount_total * (order.x_porcentaje_comision / 100)

    # Automatización: Si se marca como pagada, poner fecha de hoy
    @api.onchange('x_pagada_comision')
    def _onchange_pagada_comision(self):
        if self.x_pagada_comision:
            self.x_fecha_pago_comision = date.today()
        else:
            self.x_fecha_pago_comision = False

    # Validaciones técnicas (Python Constraints)
    @api.constrains('x_porcentaje_comision', 'x_pagada_comision', 'x_fecha_pago_comision')
    def _check_commission_rules(self):
        hoy = date.today()
        for order in self:
            # Regla 1: Porcentaje entre 0 y 20
            if order.x_porcentaje_comision < 0 or order.x_porcentaje_comision > 20:
                raise ValidationError("El porcentaje de comisión debe estar entre 0 y 20%.")

            # Regla 2: Si está pagada, fecha obligatoria y no futura
            if order.x_pagada_comision:
                if not order.x_fecha_pago_comision:
                    raise ValidationError("Debe indicar la fecha de pago de la comisión.")
                if order.x_fecha_pago_comision > hoy:
                    raise ValidationError("La fecha de pago no puede ser una fecha futura.")