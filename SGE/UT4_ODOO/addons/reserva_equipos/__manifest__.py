{
    'name': 'Gestión de Reservas IT',
    'version': '1.0',
    'category': 'Tools',
    'summary': 'Reservas de equipos informáticos',
    'description': 'Módulo para gestionar préstamos de portátiles, proyectores, etc.',
    'author': 'SOL',
    'depends': ['base'],
    'data': [
        'security/ir.model.access.csv',
        'views/equipment_views.xml',
        'views/reservation_views.xml',
        'views/menu_views.xml',
        'views/category_views.xml',
        'demo/demo.xml',
    ],
    'installable': True,
    'application': True,
}
