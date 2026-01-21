Material de práctica: Odoo con Docker Desktop (Windows 11)

Estructura:
- compose.yaml          -> define los servicios: Postgres, Odoo y pgAdmin (opcional)
- .env.example          -> variables de entorno (copiar a .env y ajustar contraseñas/puertos)
- odoo.conf             -> configuración opcional de Odoo (se monta en /etc/odoo/odoo.conf)
- addons/       -> carpeta para módulos propios del alumnado

---------------------------------------------------------------------------------------------------------------------------------------------------------------

Pasos rápidos:
1) Instalar Docker Desktop (Windows 11) y tener WSL2 activo.
2) Descomprimir esta carpeta en una ruta local (por ejemplo, C:\Users\<usuario>\odoo-clase).
3) Duplicar .env.example a .env y editar si es necesario (contraseña segura, puertos).
4) Abrir una terminal en la carpeta y ejecutar:
   docker compose up -d
5) Acceder a:
   - Odoo:     http://localhost:8069
   - pgAdmin:  http://localhost:5050  (credenciales en compose/.env)

---------------------------------------------------------------------------------------------------------------------------------------------------------------

Comandos útiles:
- Ver estado:           docker compose ps
- Ver logs de Odoo:     docker compose logs -f odoo
- Reiniciar Odoo:       docker compose restart odoo
- Parar servicios:      docker compose stop
- Bajar servicios:      docker compose down
- Bajar + borrar datos: docker compose down -v   (¡elimina volúmenes!)

---------------------------------------------------------------------------------------------------------------------------------------------------------------

Comando para instalar un nuevo módulo:
docker compose exec -u odoo odoo odoo -d odoo_demo --db_host=db --db_port=5432 --db_user=odoo --db_password=odoo --addons-path=/usr/lib/python3/dist-packages/odoo/addons,/mnt/extra-addons -i ejercicio_ventas --stop-after-init

Tras instalarlo hay que reiniciar Odoo (una de las 2):
docker compose restart odoo
docker restart odoo-app

---------------------------------------------------------------------------------------------------------------------------------------------------------------
