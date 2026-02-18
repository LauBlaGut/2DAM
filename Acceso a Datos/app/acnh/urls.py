from django.contrib import admin
from django.urls import path, include

from acnh.views import *

urlpatterns = [
    path('', go_home, name='go_home'),
    path('villagers/', show_villagers, name='villagers'),
    path('login/', do_login, name='do_login'),
    path('register/', do_register, name='do_register'),
    path('logout/', logout_user, name='logout_user'),
    path('rankings/<int:code>/', go_rankings, name='go_rankings'),
    path('delete_category/<int:code>/', delete_category, name='delete_category'),
    path('save_top/', save_top, name='save_top'),
    path('categories/', categories, name='go_categories'),
    path('categories/show', show_categories, name='show_categories'),
    path('admin_panel/', admin_panel, name='go_admin_panel'),
    path('categories/stats', show_categories_stats, name='categories_stats'),
    path('villager/<str:code>/', villager_detail, name='villager_review'),
    path('data_load/', data_load, name='go_data_load'),
    path('stats/<str:category_code>/', dashboard_rankings, name='dashboard'),
    path('reviews/all/', all_reviews, name='all_reviews_list'),
    path('admin_panel/users/', user_panels, name='user_panels'),
    path('admin_panel/villagers/manage/', manage_villagers, name='manage_villagers'),
    path('admin_panel/villagers/edit/<int:villager_code>/', edit_villager, name='edit_villager'),
    path('admin_panel/villagers/delete/<int:villager_code>/', delete_villager, name='delete_villager'),
    path('admin_panel/users/toggle/<int:user_id>/', toggle_user_role, name='toggle_user_role'),
    path('admin_panel/users/delete/<int:user_id>/', delete_user, name='delete_user'),
path('categories/delete/<int:code>/', delete_category, name='delete_category'),
]