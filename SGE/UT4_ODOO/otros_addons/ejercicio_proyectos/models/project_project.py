# models/project_project.py

from odoo import models, fields

class ProjectProject(models.Model):
    _inherit = 'project.project'

    # PASO DE CORRECCIÓN CRUCIAL:
    # Definir el campo de moneda relacionado con la compañía.
    # El modelo project.project tiene el campo 'company_id', que a su vez tiene 'currency_id'.
    company_currency_id = fields.Many2one(
        'res.currency', 
        related='company_id.currency_id', 
        string='Moneda de la Compañía', 
        readonly=True, 
        store=True  # Almacenarlo mejora el rendimiento
    )
    
    # Campo de presupuesto monetario, que ahora referencia al campo de moneda definido.
    x_presupuesto = fields.Monetary(string="Presupuesto Total", currency_field='company_currency_id')