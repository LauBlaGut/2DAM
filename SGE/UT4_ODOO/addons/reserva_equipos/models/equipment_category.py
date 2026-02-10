from odoo import models, fields, api


class BookingEquipmentCategory(models.Model):
    _name = 'booking.equipment.category'
    _description = 'Catálogo de Modelos'

    name = fields.Char(string="Modelo / Nombre", required=True)
    description = fields.Text(string="Descripción")

    image = fields.Binary(string="Imagen del Modelo")

    location = fields.Char(string="Ubicación Habitual", default="Almacén General")

    equipment_ids = fields.One2many('booking.equipment', 'category_id', string="Equipos Individuales")

    total_items = fields.Integer(string="Total Unidades", compute='_compute_stock', store=True)
    available_items = fields.Integer(string="Disponibles", compute='_compute_stock', store=True)

    @api.depends('equipment_ids', 'equipment_ids.state', 'equipment_ids.quantity')
    def _compute_stock(self):
        for record in self:
            record.total_items = sum(record.equipment_ids.mapped('quantity'))
            available_recs = record.equipment_ids.filtered(lambda x: x.state == 'available')
            record.available_items = sum(available_recs.mapped('quantity'))