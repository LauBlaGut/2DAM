from django import forms
from django.contrib.auth.forms import AuthenticationForm
from acnh.models import *


class RegisterForm(forms.Form):
    username = forms.CharField(max_length=100)
    mail = forms.EmailField()
    password = forms.CharField(widget=forms.PasswordInput)
    repeat_password = forms.CharField(widget=forms.PasswordInput)
    secret_code = forms.CharField(max_length=50, label="Código Secreto") # Nuevo campo

class LoginForm(AuthenticationForm):
    username = forms.CharField(label="Username")