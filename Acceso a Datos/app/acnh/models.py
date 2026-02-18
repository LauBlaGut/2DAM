from django.core.validators import MinValueValidator, MaxValueValidator
from django.utils import timezone
from django_mongodb_backend.fields import ArrayField
from django_mongodb_backend.models import EmbeddedModel
from django.contrib.auth.models import AbstractBaseUser, BaseUserManager, PermissionsMixin
from django.db import models
from django.db.models import JSONField


#SQLITE MODELS
class UserManager(BaseUserManager):
    def create_user(self, username, mail, role='cliente', password=None):
        if not mail or not username:
            raise ValueError("El usuario debe tener un email y un nombre de usuario")

        user = self.model(
            mail=self.normalize_email(mail),
            username=username,
            role=role,
        )
        user.set_password(password)
        user.save(using=self._db)
        return user

    def create_superuser(self, username, mail, role='admin', password=None):
        user = self.create_user(username, mail, role, password)
        user.is_staff = True
        user.is_superuser = True
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
            return self.name_data.get('name-USen', 'No name')
        return 'No name'

    def __str__(self):
        return self.name


class Category(models.Model):
    code = models.IntegerField(null=False, unique=True)
    name = models.CharField(max_length=150, unique=True)
    description = models.CharField(max_length=300)
    elements = ArrayField(models.IntegerField(), null=True, blank=True, default=list)
    logo = models.CharField(max_length=255, null=True, blank=True)
    type = models.CharField(max_length=100, null=True, blank=True)

    class Meta:
        db_table = 'categories'
        managed = False

    def __str__(self):
        return self.name


class Review(models.Model):
   user = models.CharField(max_length=150)
   villagerCode = models.IntegerField(null=False)
   reviewDate = models.DateField(default=timezone.now)
   rating = models.PositiveIntegerField(null=False,  validators=[MinValueValidator(1), MaxValueValidator(5)])
   comments = models.TextField()
   created_at = models.DateTimeField(auto_now_add=True)
   updated_at = models.DateTimeField(auto_now=True)

   def __str__(self):
       return self.user + " " + str(self.rating)

   class Meta:
       db_table = 'reviews'
       unique_together = ('villagerCode', 'user')
       managed = False


class Ranking(models.Model):
    user = models.CharField(max_length=150)
    rankinDate = models.DateField(default=timezone.now)
    categoryCode = models.IntegerField(null=False)
    rankinList = models.JSONField(default=dict)

    def __str__(self):
        return self.user + str(self.categoryCode)

    class Meta:
        db_table = 'rankings'
        managed = True

