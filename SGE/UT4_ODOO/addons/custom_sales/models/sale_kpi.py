from odoo import models, fields
import logging

_logger = logging.getLogger(__name__)

class CustomSaleKPI(models.Model):
    _name = "custom.sale.kpi"
    _description = "KPIs de Ventas Personalizados"
    _order = "sequence, id"


    name = fields.Char(required=True)
    key = fields.Char(required=True, index=True)
    value_float = fields.Float()
    value_char = fields.Char()
    currency_id = fields.Many2one("res.currency", default=lambda self:
        self.env.company.currency_id.id)
    sequence = fields.Integer(default=10)
    last_update = fields.Datetime(readonly=True)

    def _upsert_kpi(self, key, name, seq, value_float=None, value_char=None):
        rec = self.search([("key", "=", key)], limit=1)
        vals = {
            "name": name,
            "key": key,
            "sequence": seq,
            "value_float": value_float or 0.0,
            "value_char": value_char or False,
            "last_update": fields.Datetime.now(),
        }
        if rec:
            rec.write(vals)
        else:
            self.create(vals)

    def action_refresh_kpis(self):
        company_id = self.env.company.id
        self.env.cr.execute("SELECT * FROM custom_sales_month_kpis(%s);", (company_id,))
        row = self.env.cr.fetchone()
        _logger.info("Resultado KPIs SQL: %s", row)

        if not row:
            return True

        month_total, month_orders, avg_ticket, top_customer = row

        self._upsert_kpi("month_total", "Ventas confirmadas (mes)", 10, value_float=float(month_total))
        self._upsert_kpi("month_orders_confirmed", "Pedidos confirmados (mes)", 20, value_char=str(month_orders))
        self._upsert_kpi("avg_ticket", "Ticket medio (mes)", 30, value_float=float(avg_ticket))
        self._upsert_kpi("top_customer", "Top cliente (mes)", 40, value_char=top_customer or "--")

        return True

    #Si el módulo ya existía:
    def init(self):
        self.env.cr.execute("""CREATE OR REPLACE FUNCTION 
                            custom_sales_month_kpis(company_id integer)    
                            RETURNS TABLE(        
                                month_total numeric,        
                                month_orders integer,        
                                avg_ticket numeric,        
                                top_customer text    
                            )    
                            LANGUAGE plpgsql    
                            AS $$    
                            DECLARE 
                                d_from date := date_trunc('month', current_date)::date;
                                d_to date := (date_trunc('month', current_date) + interval '1 month')::date;
                            BEGIN                        
                            RETURN 
                                QUERY WITH confirmed AS (            
                                SELECT so.id, so.amount_total, so.partner_id            
                                FROM sale_order so            
                                WHERE so.company_id = $1
                                AND so.state = 'sale'
                                AND so.date_order >= d_from
                                AND so.date_order < d_to
                            ),                            
                            totals AS (                            
                                SELECT COALESCE (SUM (amount_total),0) AS month_total,
                                COUNT (*):: int AS month_orders,
                                COALESCE (AVG (amount_total),0) AS avg_ticket
                                FROM confirmed                            
                            ),                            
                            topc AS (
                                SELECT rp.name  
                                FROM confirmed c 
                                JOIN res_partner rp ON rp.id = c.partner_id
                                GROUP BY rp.name
                                ORDER BY SUM (c.amount_total) DESC LIMIT 1
                            )                        
                            SELECT t.month_total, t.month_orders, t.avg_ticket, 
                            COALESCE((SELECT name::text FROM topc), '—'::text) AS top_customer                        
                            FROM totals t;                        
                            END;
                            $$;
                            """)