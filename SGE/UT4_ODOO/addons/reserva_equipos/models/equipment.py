from odoo import models, fields


class BookingEquipment(models.Model):
    _name = 'booking.equipment'
    _description = 'Equipo Individual'

    name = fields.Char(string="Referencia / Serial", required=True)
    description = fields.Text(string="Descripción Técnica")

    quantity = fields.Integer(string="Cantidad", default=1)

    location = fields.Char(string="Ubicación Física")
    image = fields.Binary(string="Imagen")

    category_id = fields.Many2one('booking.equipment.category', string="Categoría")

    state = fields.Selection([
        ('available', 'Operativo'),
        ('maintenance', 'En Mantenimiento'),
        ('broken', 'Averiado'),
    ], string="Estado Físico", default='available', required=True)

    # --- CAMPOS CALCULADOS (Información visual) ---
    stock_real = fields.Integer(string="Total Flota", related='category_id.total_items', readonly=True)
    stock_available = fields.Integer(string="Disponibles Ahora", related='category_id.available_items', readonly=True)