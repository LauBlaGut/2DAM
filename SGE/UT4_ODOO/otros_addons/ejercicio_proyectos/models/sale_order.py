from odoo import models, fields, api
from odoo.exceptions import ValidationError


class SaleOrder(models.Model):
    _inherit = 'sale.order'

    # 1. Relación con el proyecto
    x_proyecto_asociado_id = fields.Many2one('project.project', string="Proyecto asociado")

    # 2. Campos relacionados (Se llenan solos al elegir proyecto)
    # Suponiendo que el campo en project.project es 'code'. Si no existe, usa 'name'.
    x_codigo_proyecto = fields.Char(related="x_proyecto_asociado_id.name", string="Código del proyecto", readonly=True)

    # Suponiendo que has añadido un campo de presupuesto al modelo de proyecto
    # Si el campo no existe en el proyecto base, devolverá 0 o dará error.
    x_presupuesto_proyecto = fields.Monetary(related="x_proyecto_asociado_id.x_presupuesto",
                                             string="Presupuesto Proyecto", readonly=True)

    # 3. Campo calculado: Porcentaje del presupuesto consumido
    x_porcentaje_presupuesto = fields.Float(string="Uso de presupuesto", compute="_compute_porcentaje_presupuesto")

    @api.depends('amount_total', 'x_presupuesto_proyecto')
    def _compute_porcentaje_presupuesto(self):
        for order in self:
            if order.x_presupuesto_proyecto > 0:
                order.x_porcentaje_presupuesto = (order.amount_total / order.x_presupuesto_proyecto) * 100
            else:
                order.x_porcentaje_presupuesto = 0

    # 4. Validación al confirmar
    def action_confirm(self):
        if self.x_porcentaje_presupuesto > 100:
            raise ValidationError("No puede confirmar el pedido: El total supera el presupuesto del proyecto.")
        return super(SaleOrder, self).action_confirm()