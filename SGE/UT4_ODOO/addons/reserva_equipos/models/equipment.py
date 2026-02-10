from odoo import models, fields


class BookingEquipment(models.Model):
    _name = 'booking.equipment'
    _description = 'Equipo Individual'

    name = fields.Char(string="Referencia / Serial", required=True)
    description = fields.Text(string="Descripción Técnica")

    image = fields.Binary(string="Foto")

    category_id = fields.Many2one('booking.equipment.category', string="Categoría", required=True)

    location = fields.Char(string="Ubicación", related='category_id.location', store=True, readonly=True)

    state = fields.Selection([
        ('available', 'Operativo'),
        ('maintenance', 'En Mantenimiento'),
        ('broken', 'Averiado'),
    ], string="Estado Físico", default='available', required=True)

    quantity = fields.Integer(string="Cantidad", default=1)
