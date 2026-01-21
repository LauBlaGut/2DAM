from odoo import models, fields, api


class SaleOrder(models.Model):
    _inherit = 'sale.order'

    # 1. Campos de Selección
    x_segmento_cliente = fields.Selection([
        ('nuevo', 'Nuevo'),
        ('recurrente', 'Recurrente'),
        ('corporate', 'Corporate'),
        ('vip', 'VIP')
    ], string="Segmento de Cliente")

    x_valoracion_dificultad = fields.Selection([
        ('baja', 'Baja'),
        ('media', 'Media'),
        ('alta', 'Alta')
    ], string="Dificultad de Venta")

    # 2. Campos Calculados
    x_puntuacion_venta = fields.Integer(string="Puntuación", compute="_compute_analisis_venta", store=True)
    x_categoria_venta = fields.Selection([
        ('bronce', 'Bronce'),
        ('plata', 'Plata'),
        ('oro', 'Oro')
    ], string="Categoría de Venta", compute="_compute_analisis_venta", store=True)

    @api.depends('x_segmento_cliente', 'x_valoracion_dificultad')
    def _compute_analisis_venta(self):
        # Diccionarios de puntos
        puntos_segmento = {'nuevo': 10, 'recurrente': 5, 'corporate': 3, 'vip': 15}
        puntos_dificultad = {'baja': 5, 'media': 0, 'alta': -5}

        for order in self:
            puntos = 0
            if order.x_segmento_cliente:
                puntos += puntos_segmento.get(order.x_segmento_cliente, 0)
            if order.x_valoracion_dificultad:
                puntos += puntos_dificultad.get(order.x_valoracion_dificultad, 0)

            order.x_puntuacion_venta = puntos

            # Asignación de categoría
            if puntos < 10:
                order.x_categoria_venta = 'bronce'
            elif 10 <= puntos <= 15:
                order.x_categoria_venta = 'plata'
            else:
                order.x_categoria_venta = 'oro'