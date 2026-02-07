{
    'name': 'Custom Sales - Nota Personalizada',
    'version': '1.0.0',
    'summary': 'Añade campo nota personalizada a pedidos de venta',
    'description': 'Añade un campo custom_note al modelo sale.order.',
    'category': 'Sales/Sales',
    'author': 'JAB',
    'license': 'LGPL-3',
    'depends': ['sale'],
    'data': [
        'security/ir.model.access.csv',
        'wizard/import_logistics_view.xml',
        'views/sale_order_list_inherit_export_csv.xml',
        'views/sale_kpi_views.xml',
        'views/sale_dashboard_views.xml',
        'data/ir_cron.xml',
        'views/sale_order_view.xml',
        'views/sale_order_search.xml',
        'report/sale_order_report.xml',
        'report/sale_order_report_template.xml',
    ],
    # AÑADIR ESTA SECCIÓN PARA INCLUIR EL CSS EN EL BACKEND
    "assets": {
        'web.assets_backend': [
            'custom_sales/static/src/css/estilos.css', # RUTA CORRECTA A TU ARCHIVO CSS
        ],
    },
'   post_init_hook': 'post_init_hook',
    'installable': True,
    'application': False,
    'auto_install': False,
}
