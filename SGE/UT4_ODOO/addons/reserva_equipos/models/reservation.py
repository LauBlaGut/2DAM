from odoo import models, fields, api
from odoo.exceptions import ValidationError


class BookingReservation(models.Model):
    _name = 'booking.reservation'
    _description = 'Reserva de Equipo'

    category_id = fields.Many2one('booking.equipment.category', string="Modelo Solicitado")
    equipment_id = fields.Many2one('booking.equipment', string="Equipo Asignado")
    user_id = fields.Many2one('res.users', string="Reservado por", default=lambda self: self.env.user)

    start_date = fields.Datetime(string="Fecha Inicio", required=True, default=fields.Datetime.now)
    end_date = fields.Datetime(string="Fecha Fin", required=True)

    duration = fields.Float(string="Duración (Horas)", compute="_compute_duration", store=True)

    state = fields.Selection([
        ('draft', 'Borrador'),
        ('confirmed', 'Confirmado'),
        ('done', 'Finalizado'),
        ('canceled', 'Cancelado')
    ], string="Estado", default='draft')

    @api.depends('start_date', 'end_date')
    def _compute_duration(self):
        for record in self:
            if record.start_date and record.end_date:
                diff = record.end_date - record.start_date
                record.duration = diff.total_seconds() / 3600
            else:
                record.duration = 0

    @api.constrains('start_date', 'end_date')
    def _check_dates(self):
        for record in self:
            if record.end_date < record.start_date:
                raise ValidationError("La fecha de fin no puede ser anterior a la de inicio.")

    # --- LÓGICA DE ASIGNACIÓN AUTOMÁTICA ---
    @api.model_create_multi
    def create(self, vals_list):
        for vals in vals_list:
            if vals.get('category_id') and not vals.get('equipment_id'):
                candidates = self.env['booking.equipment'].search([
                    ('category_id', '=', vals['category_id']),
                    ('state', '=', 'available')
                ])

                start = vals.get('start_date')
                end = vals.get('end_date')

                occupied = self.search([
                    ('equipment_id', 'in', candidates.ids),
                    ('state', 'not in', ['draft', 'canceled']),
                    ('start_date', '<', end),
                    ('end_date', '>', start),
                ]).mapped('equipment_id.id')

                available = candidates.filtered(lambda c: c.id not in occupied)

                if not available:
                    raise ValidationError("No quedan unidades disponibles de este modelo para esas fechas.")

                vals['equipment_id'] = available[0].id

        return super().create(vals_list)

    # Botones básicos
    def action_confirm(self):
        self.state = 'confirmed'

    def action_done(self):
        self.state = 'done'

    def action_cancel(self):
        self.state = 'canceled'