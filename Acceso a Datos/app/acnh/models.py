from django.core.validators import MinValueValidator, MaxValueValidator
from django.utils import timezone
from django_mongodb_backend.fields import ArrayField
from django_mongodb_backend.models import EmbeddedModel
from django.contrib.auth.models import AbstractBaseUser, BaseUserManager, PermissionsMixin
from django.db import models
from django.db.models import JSONField

#pyhton manage.py make migrations --> PREPARA SQL SCRIPT
#python manage.py migrate --> SCRIPT --> BBDD

# Create your models here.

#SQLITE MODELS
class UserManager(BaseUserManager):
    def create_user(self, mail, username, role, password=None):
        if not mail or not username or not role:
            raise ValueError("Debes rellenar los campos requeridos (mail, username, role)")
        mail = self.normalize_email(mail)
        user = self.model(email=mail, nombre=username, rol=role)
        user.set_password(password)
        user.save(using=self._db)
        return user

    def create_superuser(self, mail, username, role='admin', password=None):
        user = self.create_user(mail, username, role, password)
        user.is_superuser = True
        user.is_staff = True
        user.save(using=self._db)
        return user



class User(AbstractBaseUser, PermissionsMixin):
    ROLES = (
        ('admin', 'Administrador'),
        ('cliente', 'Cliente'),
    )

    mail = models.EmailField(unique=True)
    username = models.CharField(max_length=100, unique=True)
    role = models.CharField(max_length=20, choices=ROLES, default='cliente')
    is_active = models.BooleanField(default=True)
    is_staff = models.BooleanField(default=False)

    objects = UserManager()

    USERNAME_FIELD = 'username'
    REQUIRED_FIELDS = ['mail',  'role']

    def __str__(self):
        return self.username



#MONGODB MODELS
class Villager(models.Model):
    code = models.IntegerField(null=False)
    name_data = JSONField(db_column='name')
    personality = models.CharField(max_length=150)
    species = models.CharField(max_length=300)
    hobby = models.CharField(max_length=300)
    catchPhrase = models.CharField(max_length=300, db_column='catch-phrase')
    iconUrl = models.CharField(max_length=800, db_column='icon_uri')
    imageUrl = models.CharField(max_length=800, db_column='image_uri')
    categories = ArrayField(models.IntegerField(), null=True, blank=True, default=list)

    class Meta:
        db_table = 'villagers'
        managed = False

    @property
    def name(self):
        if self.name_data:
            return self.name_data.get('name-EUen', 'No name')
        return 'No name'

    def __str__(self):
        return self.name


class Category(EmbeddedModel):
    code = models.IntegerField(null=False, unique=True)
    name = models.CharField(max_length=150, unique=True)
    description = models.CharField(max_length=300)

    class Meta:
        db_table = 'categories'
        managed = False

    def __str__(self):
        return self.name


class Review(EmbeddedModel):
   user = models.CharField(max_length=150)
   villagerCode = models.IntegerField(null=False)
   reviewDate = models.DateField(default=timezone.now)
   rating = models.PositiveIntegerField(null=False,  validators=[MinValueValidator(1), MaxValueValidator(5)])
   comments = models.TextField()

   def __str__(self):
       return self.user + " " + str(self.rating)

   class Meta:
       db_table = 'reviews'
       managed = False


class Ranking(EmbeddedModel):
    user = models.CharField(max_length=150)
    rankinDate = models.DateField(default=timezone.now)
    categoryCode = models.IntegerField(null=False)
    rankinList = models.JSONField(default=dict)

    def __str__(self):
        return self.user + str(self.categoryCode)

    class Meta:
        db_table = 'rankings'
        managed = False

class Review(models.Model):
    user = models.CharField(max_length=150)
    villager_id = models.IntegerField()
    rating = models.IntegerField(default=0)
    comment = models.TextField(blank=True, null=True)
    date = models.DateField(default=timezone.now)

    class Meta:
        unique_together = ('user', 'villager_id') # Solo una reseña por vecino
        db_table = 'reviews'
        managed = False