def post_init_hook(env):
    env.cr.execute("""
        CREATE OR REPLACE FUNCTION products_kpis_lbg()
        RETURNS TABLE(
            total_products integer,
            avg_price numeric,
            expensive_products integer
        )
        LANGUAGE plpgsql
        AS $$
        BEGIN
            RETURN QUERY
            SELECT 
                COUNT(*)::integer AS total_products,
                AVG(list_price)::numeric AS avg_price,
                COUNT(CASE WHEN list_price > 50 THEN 1 END)::integer AS expensive_products
            FROM product_template
            WHERE active = true;
        END;
        $$;
    """)
