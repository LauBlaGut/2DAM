from odoo import models, fields


class BookingEquipment(models.Model):
    _name = 'booking.equipment'
    _description = 'Equipo Informático'

    name = fields.Char(string="Nombre del Equipo", required=True)
    description = fields.Text(string="Descripción Técnica")
    serial_number = fields.Char(string="Número de Serie")
    image = fields.Binary(string="Imagen del Equipo")

    state = fields.Selection([
        ('available', 'Disponible'),
        ('maintenance', 'En Mantenimiento'),
        ('broken', 'Averiado')
    ], string="Estado", default='available')
