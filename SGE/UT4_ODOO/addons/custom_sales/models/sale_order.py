from odoo import models, fields, api
from datetime import date

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
        store=False, # No se guarda en BD al ser un cálculo de tiempo real
        help='Days elapsed since order creation'
    )

    x_first_product_image = fields.Binary(
        string="Imagen del producto",
        compute="_compute_first_product_image",
        store=False, # Campo calculado de tiempo real
        help='Muestra la imagen del primer producto del pedido'
    )

    @api.depends('date_order')
    def _compute_order_duration(self):
        """Calcula días desde creación del pedido"""
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
        """Calcula la imagen del primer producto del pedido"""
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