{
    'name': 'Gestión de Productos LBG',
    'version': '1.0',
    'summary': 'Práctica 2 SGE - Extensión de productos',
    'author': 'LBG',
    'depends': ['product', 'sale'],

    'data': [
        'security/ir.model.access.csv',
        'data/kpi_data.xml',
        'wizard/product_import_wizard_view.xml',
        'views/product_views.xml',
        'views/kpi_views.xml',
        'report/product_report.xml',
    ],

    'post_init_hook': 'post_init_hook',
    'installable': True,
    'application': True,
}
