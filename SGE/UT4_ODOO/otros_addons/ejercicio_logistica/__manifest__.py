{
    'name': 'Ejercicio 4.1: Logística',
    'version': '1.0',
    'summary': 'Gestión de envíos y logística en Ventas',
    'depends': ['sale'],  # <--- CRUCIAL: Necesitamos heredar de Ventas
    'data': [
        'views/sale_order_view.xml',
    ],
    'installable': True,
    'application': False,
}