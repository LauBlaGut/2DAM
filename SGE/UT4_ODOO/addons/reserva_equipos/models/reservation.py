from odoo import models, fields, api
from odoo.exceptions import ValidationError


class BookingReservation(models.Model):
    _name = 'booking.reservation'
    _description = 'Reserva de Equipo'

    equipment_id = fields.Many2one('booking.equipment', string="Equipo", required=True)
    user_id = fields.Many2one('res.users', string="Reservado por", default=lambda self: self.env.user)

    start_date = fields.Datetime(string="Fecha Inicio", required=True, default=fields.Datetime.now)
    end_date = fields.Datetime(string="Fecha Fin", required=True)

    duration = fields.Float(string="Duración (Horas)", compute="_compute_duration", store=True)

    state = fields.Selection([
        ('draft', 'Borrador'),
        ('confirmed', 'Confirmado'),
        ('done', 'Finalizado'),
        ('canceled', 'Cancelado')  # Añadimos estado cancelado
    ], string="Estado", default='draft')

    @api.depends('start_date', 'end_date')
    def _compute_duration(self):
        for record in self:
            if record.start_date and record.end_date:
                diff = record.end_date - record.start_date
                record.duration = diff.total_seconds() / 3600
            else:
                record.duration = 0

    # VALIDACIÓN 1: FECHAS COHERENTES
    @api.constrains('start_date', 'end_date')
    def _check_dates(self):
        for record in self:
            if record.start_date and record.end_date:
                if record.end_date < record.start_date:
                    raise ValidationError("La fecha de fin no puede ser anterior a la de inicio.")

    # VALIDACIÓN 2: SOLAPAMIENTO DE RESERVAS
    @api.constrains('equipment_id', 'start_date', 'end_date', 'state')
    def _check_overlap(self):
        for record in self:
            if record.state != 'canceled':
                domain = [
                    ('id', '!=', record.id),
                    ('equipment_id', '=', record.equipment_id.id),
                    ('state', 'not in', ['draft', 'canceled']),
                    ('start_date', '<', record.end_date),
                    ('end_date', '>', record.start_date),
                ]
                if self.search_count(domain) > 0:
                    raise ValidationError(f"¡El equipo '{record.equipment_id.name}' ya está reservado en esas fechas!")


    def action_confirm(self):
        for record in self:
            record.state = 'confirmed'

    def action_done(self):
        for record in self:
            record.state = 'done'

    def action_cancel(self):
        for record in self:
            record.state = 'canceled'