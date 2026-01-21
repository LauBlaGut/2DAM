from odoo import models, fields, api
from odoo.exceptions import ValidationError


class SaleOrder(models.Model):
    _inherit = 'sale.order'

    # 1. Número de guía
    x_numero_guia = fields.Char(string="Número de guía de transporte")

    # 2. Transportista (Relación a res.partner filtrando por categoría)
    x_transportista_id = fields.Many2one(
        'res.partner',
        string="Transportista",
        domain=[('category_id.name', 'ilike', 'transportista')]
    )

    # 3. Costo de envío
    # 'currency_id' es el campo estándar de Odoo que indica la moneda del pedido
    x_costo_envio = fields.Monetary(string="Costo de envío adicional", currency_field='currency_id')

    # 4. Total calculado
    x_total_con_envio = fields.Monetary(
        string="Total con envío",
        compute="_compute_total_con_envio",
        currency_field='currency_id',
        store=True  # Lo guardamos para poder usarlo en filtros y reportes
    )

    # Lógica del cálculo
    @api.depends('amount_total', 'x_costo_envio')
    def _compute_total_con_envio(self):
        for order in self:
            order.x_total_con_envio = order.amount_total + order.x_costo_envio

    # Validaciones técnicas
    @api.constrains('x_numero_guia', 'x_transportista_id', 'x_costo_envio')
    def _check_logistics_rules(self):
        for order in self:
            # Regla: Si hay número de guía, el transportista es obligatorio
            if order.x_numero_guia and not order.x_transportista_id:
                raise ValidationError("Si ingresa un número de guía, debe seleccionar un transportista.")

            # Regla: El costo de envío no puede ser negativo
            if order.x_costo_envio < 0:
                raise ValidationError("El costo de envío no puede ser una cantidad negativa.")
