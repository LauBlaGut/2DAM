def post_init_hook(cr, registry):
    cr.execute("""
    CREATE OR REPLACE FUNCTION custom_sales_month_kpis(company_id integer)
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
        RETURN QUERY
        WITH confirmed AS (
            SELECT so.id, so.amount_total, so.partner_id
            FROM sale_order so
            WHERE so.company_id = $1
                AND so.state = 'sale'
                AND so.date_order >= d_from
                AND so.date_order < d_to
        ),
        totals AS (
            SELECT
                COALESCE(SUM(amount_total),0) AS month_total,
                COUNT(*)::int AS month_orders,
                COALESCE(AVG(amount_total),0) AS avg_ticket
            FROM confirmed
        ),
    topc AS ( 
        SELECT rp.name 
        FROM confirmed c 
        JOIN res_partner rp ON rp.id = c.partner_id 
        GROUP BY rp.name 
        ORDER BY SUM(c.amount_total) DESC 
        LIMIT 1 
    ) 
    SELECT 
        t.month_total, 
        t.month_orders, 
        t.avg_ticket, 
        COALESCE((SELECT name FROM topc), '--') AS top_customer 
    FROM totals t; 
END; 
$$; 
""")