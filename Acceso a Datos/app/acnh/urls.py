from django.contrib import admin
from django.urls import path, include

from acnh.views import *

urlpatterns = [
    path('', go_home, name='home'),
    path('villagers/', show_villagers, name='villagers'),
    path('login/', do_login, name='do_login'),
    path('register/', do_register, name='do_register'),
    path('logout/', logout_user, name='logout_user'),
    path('ranking/', do_ranking, name='do_ranking'),
    path('review/', do_reviews, name='do_reviews'),
    path('review/save/', save_review, name='save_review'),

]