import csv
import io
import json

from django.contrib.messages.storage import default_storage
from django.core.serializers.json import DjangoJSONEncoder
from django.db import connections
from django.http import Http404
from django.shortcuts import render, redirect, get_object_or_404
from django.contrib.auth import authenticate, login, logout
from django.template.defaulttags import comment

from acnh.forms import *
from acnh.models import *
from django.contrib.auth.decorators import login_required, user_passes_test
from django.utils import timezone


def go_home(request):
    return render(request, 'home.html')


def is_admin(user):
    return user.is_authenticated and user.is_staff


def show_villagers(request):
    list_villagers = Villager.objects.using('mongodb').all()

    return render(request, 'villagers.html', {"list": list_villagers} )


def do_login(request):

    if request.method == 'POST':
        form = LoginForm(request, data=request.POST)
        if form.is_valid():
            username = form.cleaned_data.get('username')
            password = form.cleaned_data.get('password')
            user = authenticate(request, username=username, password=password)
            if user is not None:
                login(request, user)
                next_url = request.GET.get('next', 'go_home')
                return redirect(next_url)
    else:
        form = LoginForm()

    return render(request, 'login.html', {"form": form})


def do_register(request):
    # Definimos nuestros códigos secretos
    CODE_USER = "acnh"
    CODE_ADMIN = "acnh_secret"

    if request.user.is_authenticated:
        return redirect('go_home')

    if request.method == 'POST':
        form = RegisterForm(request.POST)
        if form.is_valid():
            username = form.cleaned_data.get('username')
            mail = form.cleaned_data.get('mail')
            password = form.cleaned_data.get('password')
            repeat_password = form.cleaned_data.get('repeat_password')
            secret_code = form.cleaned_data.get('secret_code')

            # 1. Validación de contraseñas
            if password != repeat_password:
                form.add_error('repeat_password', 'Las contraseñas no coinciden')
                return render(request, 'register.html', {"form": form})

            # 2. Validación del Código Secreto
            is_admin_code = (secret_code == CODE_ADMIN)
            is_user_code = (secret_code == CODE_USER)

            if not is_admin_code and not is_user_code:
                form.add_error('secret_code', 'El código secreto es incorrecto')
                return render(request, 'register.html', {"form": form})

            # 3. Crear el usuario según el código
            from .models import User

            if is_admin_code:
                new_user = User.objects.create_superuser(
                    username=username,
                    mail=mail,
                    password=password
                )
            else:
                # Crea un usuario normal
                new_user = User.objects.create_user(
                    username=username,
                    mail=mail,
                    password=password
                )
            return redirect('do_login')
    else:
        form = RegisterForm()

    return render(request, 'register.html', {"form": form})


def logout_user(request):
    logout(request)
    return redirect('go_home')


#ADMIN
@user_passes_test(is_admin)
def admin_panel(request):
    return render(request, 'admin.html')


@user_passes_test(is_admin)
def data_load(request):
    if request.method == "POST":
        uploaded_file = request.FILES.get('csvFile')

        if not uploaded_file:
            return render(request, 'data_load.html', {'error': 'No se seleccionó ningún archivo.'})

        file_content = uploaded_file.read().decode('utf-8')
        file_name = uploaded_file.name.lower()

        base_img_url = "https://raw.githubusercontent.com/alexislours/ACNHAPI/refs/heads/master/images/villagers/"

        try:
            # JSON
            if file_name.endswith('.json'):
                raw_data = json.loads(file_content)
                count = 0

                if isinstance(raw_data, dict):
                    iterator = raw_data.items()
                else:
                    iterator = enumerate(raw_data)

                for key, row in iterator:
                    data_row = row
                    name_data = data_row.get('name')
                    if not name_data:
                        continue

                    file_name_img = data_row.get('file-name') or (key if isinstance(raw_data, dict) else "")
                    img_url = f"{base_img_url}{file_name_img}.png" if file_name_img else ""

                    Villager.objects.using('mongodb').create(
                        code=data_row.get('id') or data_row.get('code'),
                        name_data=name_data,
                        personality=data_row.get('personality', 'Unknown'),
                        species=data_row.get('species', 'Unknown'),
                        hobby=data_row.get('hobby', 'None'),
                        catchPhrase=data_row.get('catch-phrase') or "No phrase",
                        iconUrl=img_url,
                        imageUrl=img_url,
                        categories=[]
                    )
                    count += 1

                if count == 0:
                    return render(request, 'data_load.html',
                                  {'error': 'No se encontraron vecinos válidos con nombre en el archivo.'})

                return render(request, 'data_load.html',
                              {'success': f'¡Éxito! Cargados {count} vecinos válidos desde JSON.'})

            # CSV
            else:
                decoded_file = io.StringIO(file_content)
                reader = csv.DictReader(decoded_file)
                count = 0

                for row in reader:
                    raw_name = row.get('name')

                    if not raw_name:
                        continue

                    fname = row.get('file-name') or ""
                    img_url = f"{base_img_url}{fname}.png" if fname else ""

                    Villager.objects.using('mongodb').create(
                        code=row.get('code'),
                        name_data={'name-USen': raw_name},
                        personality=row.get('personality', 'Unknown'),
                        species=row.get('species', 'Unknown'),
                        hobby=row.get('hobby', 'None'),
                        catchPhrase=row.get('catch-phrase') or "No phrase",
                        iconUrl=img_url,
                        imageUrl=img_url
                    )
                    count += 1

                return render(request, 'data_load.html', {'success': f'Cargados {count} vecinos desde CSV.'})

        except Exception as e:
            return render(request, 'data_load.html', {'error': f'Error técnico: {e}'})

    return render(request, 'data_load.html')


@user_passes_test(is_admin)
def user_panels(request):
    users = User.objects.all().order_by('-id')
    return render(request, 'user_panels.html', {'users': users})


@user_passes_test(is_admin)
def toggle_user_role(request, user_id):
    user_to_edit = get_object_or_404(User, id=user_id)

    if user_to_edit.role == 'admin':
        user_to_edit.role = 'cliente'
        user_to_edit.is_staff = False
        user_to_edit.is_superuser = False
    else:
        user_to_edit.role = 'admin'
        user_to_edit.is_staff = True
        user_to_edit.is_superuser = True

    user_to_edit.save()
    return redirect('user_panels')


@user_passes_test(is_admin)
def delete_user(request, user_id):
    if request.user.id == user_id:
        return redirect('user_panels')

    user_to_delete = get_object_or_404(User, id=user_id)
    user_to_delete.delete()
    return redirect('user_panels')


#VILLAGERS
def villager_detail(request, code):
    item = get_object_or_404(Villager, code=code)
    user_str = str(request.user)
    queryset_review = Review.objects.filter(user=user_str, villagerCode=int(code))
    latest_reviews = Review.objects.filter(villagerCode=int(code)).order_by('-reviewDate')[:5]

    user_review = None
    if request.user.is_authenticated:
        user_review = Review.objects.filter(user=request.user, villager_id=code).first()

    if request.method == "POST":
        if not request.user.is_authenticated:
            return redirect('do_login')

        rating = request.POST.get('rating')
        comments = request.POST.get('review')

        Review.objects.update_or_create(
            user=request.user,
            villager_id=code,
            defaults={
                'rating': int(rating),
                'comment': comment,
                'created_at': timezone.now()
            }
        )

        return redirect('villager_detail', code=code)

    return render(request, 'villager_review.html', {
        'item': item,
        'user_review': user_review,
        'latest_reviews': latest_reviews,
    })


@user_passes_test(is_admin)
def manage_villagers(request):
    # Forzamos la consulta a MongoDB
    list_villagers = Villager.objects.using('mongodb').all().order_by('name_data')
    return render(request, 'manage_villagers.html', {"villagers": list_villagers})


@user_passes_test(is_admin)
def delete_villager(request, villager_code):
    villager = Villager.objects.using('mongodb').filter(code=villager_code).first()
    if villager:
        villager.delete()
    return redirect('manage_villagers')


@user_passes_test(is_admin)
def edit_villager(request, villager_code):
    villager = Villager.objects.using('mongodb').filter(code=villager_code).first()

    if not villager:
        return redirect('manage_villagers')

    if request.method == 'POST':
        new_name = request.POST.get('name')
        villager.name_data = {'name-USen': new_name}

        villager.species = request.POST.get('species')
        villager.personality = request.POST.get('personality')
        villager.hobby = request.POST.get('hobby')

        # Guardamos los cambios indicando la base de datos
        villager.save(using='mongodb')
        return redirect('manage_villagers')

    return render(request, 'edit_villager_form.html', {'villager': villager})


#CATEGORIES
@user_passes_test(is_admin)
def categories(request):
    categories_list = Category.objects.all().order_by('code')
    all_villagers = Villager.objects.all()
    list_villagers = sorted(all_villagers, key=lambda v: v.name_data.get('name-USen', ''))

    if request.method == "POST":
        code_str = request.POST.get('code')
        name = request.POST.get('name')
        description = request.POST.get('description')
        cat_type = request.POST.get('type')
        image_file = request.FILES.get('logo_file')
        logo = request.POST.get('existing_logo')

        if image_file:
            path = default_storage.save(f'categories/{image_file.name}', image_file)
            logo = path

        selected_ids = json.loads(request.POST.get('selected_elements', '[]'))
        elements = [int(x) for x in selected_ids]

        if code_str:  # MODO EDICIÓN
            Category.objects.filter(code=int(code_str)).update(
                name=name,
                logo=logo,
                description=description,
                type=cat_type,
                elements=elements
            )
        else:  # MODO CREACIÓN
            last_category = Category.objects.order_by('code').last()
            new_code = (last_category.code + 1) if last_category else 1

            Category.objects.create(
                code=new_code,
                name=name,
                logo=logo,
                description=description,
                type=cat_type,
                elements=elements
            )

        return redirect('go_categories')

    categories_json = json.dumps(list(categories_list.values()), cls=DjangoJSONEncoder)

    return render(request, 'categories.html', {
        'categories': categories_list,
        'categories_json': categories_json,
        'villagers': list_villagers,
    })


def show_categories(request):
    categories_list = Category.objects.using('mongodb').all().order_by('code')

    if request.user.is_authenticated:
        categories_ranked_user = Ranking.objects.using('mongodb').filter(user=request.user.username)
        done_codes = set(categories_ranked_user.values_list('categoryCode', flat=True))

        for c in categories_list:
            c.done = c.code in done_codes
    else:
        for c in categories_list:
            c.done = False

    return render(request, 'ranking_categories.html', {'categories': categories_list})


@user_passes_test(is_admin)
def delete_category(request, code):
    deleted_count, _ = Category.objects.using('mongodb').filter(code=code).delete()

    if deleted_count == 0:
        raise Http404("La categoría no existe")

    return redirect('go_categories')


#RANKINGS
@login_required()
def go_rankings(request, code):
    code_int = int(code)
    category = Category.objects.using('mongodb').get(code=code_int)

    # Obtener vecinos
    villager_ids = getattr(category, 'elements', [])
    all_villagers = list(Villager.objects.using('mongodb').filter(code__in=villager_ids))
    villager_map = {str(v.code): v for v in all_villagers}

    # Guardar
    if request.method == 'POST':
        data_string = request.POST.get('ranking_data')
        if data_string:
            data_dict = json.loads(data_string)

            updated_count = Ranking.objects.using('mongodb').filter(
                user=request.user.username,
                categoryCode=code_int
            ).update(
                rankinList=data_dict,
                rankinDate=timezone.now().date()
            )

            if updated_count == 0:
                Ranking.objects.using('mongodb').create(
                    user=request.user.username,
                    categoryCode=code_int,
                    rankinList=data_dict,
                    rankinDate=timezone.now().date()
                )

            return redirect('go_rankings', code=code_int)

    saved_ranking = Ranking.objects.using('mongodb').filter(
        user=request.user.username,
        categoryCode=code_int
    ).first()

    tiered_villagers = {
        "Dreamies": [],
        "Residents": [],
        "AutoFill": [],
        "Isabelle": [],
        "NetHit": [],
        "Pool": []
    }

    if saved_ranking and saved_ranking.rankinList:
        used_ids = set()
        for tier_name, id_list in saved_ranking.rankinList.items():
            if tier_name in tiered_villagers:
                for v_id in id_list:
                    v_id_str = str(v_id)
                    if v_id_str in villager_map:
                        tiered_villagers[tier_name].append(villager_map[v_id_str])
                        used_ids.add(int(v_id_str))

        # El resto de vecinos que no están en el JSON van a la Pool
        for v in all_villagers:
            if int(v.code) not in used_ids:
                tiered_villagers["Pool"].append(v)
    else:
        # Si no hay ranking, todos a la Pool
        tiered_villagers["Pool"] = all_villagers

    return render(request, 'ranking.html', {
        'category': category,
        'tiers': tiered_villagers,
        'exists': saved_ranking is not None
    })

@login_required
def save_ranking(request):
    if request.method == "POST":
        order_data = request.POST.get('order')
        category_code = request.POST.get('category_code')

        if order_data:
            Ranking.objects.create(
                user=request.user,
                category_code=category_code,
                ranking_list=json.loads(order_data),
                created_at=timezone.now()
            )

    return redirect('go_home')


def dashboard_rankings(request, category_code):
    connection = connections['mongodb']
    db = connection.database

    category_info = db['categories'].find_one({"code": int(category_code)})

    pipeline = [
        {"$match": {"categoryCode": int(category_code)}},
        {"$unwind": "$rankinList"},
        {"$group": {
            "_id": "$rankinList.id",
            "realAvg": {
                "$avg": {
                    "$convert": {
                        "input": "$rankinList.position",
                        "to": "double",
                        "onError": 0.0,
                        "onNull": 0.0
                    }
                }
            },
            "totalVotes": {"$sum": 1}
        }},
        {"$sort": {"realAvg": 1}}
    ]

    results = list(db[Ranking._meta.db_table].aggregate(pipeline))

    villager_ids = [item['_id'] for item in results]

    villagers_sql = Villager.objects.in_bulk(villager_ids)

    stats = []
    for item in results:
        v_id = item['_id']
        villager_obj = villagers_sql.get(v_id)

        if villager_obj:
            image_url = f"https://raw.githubusercontent.com/alexislours/ACNHAPI/refs/heads/master/images/villagers/{villager_obj.file_name}.png"

            stats.append({
                'code': v_id,
                'name': villager_obj.name,
                'logo': image_url,
                'species': villager_obj.species,
                'avgPosition': round(item['realAvg'], 2),
                'realAvg': item['realAvg'],
                'totalVotes': item['totalVotes']
            })

    return render(request, 'ranking_dashboard.html', {
        'stats': stats,
        'category': category_info,
        'category_code': category_code
    })



#Stats
def save_top(request):
    order = request.POST.get('order')
    order_list = json.loads(order)
    category_size = request.POST.get('category_size')

    if len(order_list) != category_size:
        ranking = Ranking()
        ranking.rankinDate = timezone.now()
        ranking.user = request.user
        ranking.categoryCode = request.POST.get('category_code')
        ranking.rankinList = [order_list]
        ranking.save()

    return redirect('go_home')


def show_categories_stats(request):
    puntos_tier = {
        "Dreamies": 5,
        "Residents": 4,
        "AutoFill": 3,
        "Isabelle": 2,
        "NetHit": 1,
        "Pool": 0
    }


    rankings_db = Ranking.objects.using('mongodb').all().order_by('-rankinDate')
    categories_db = Category.objects.using('mongodb').all()
    cat_map = {c.code: c for c in categories_db}

    datos_agrupados = {}

    registros_vistos = set()

    for r in rankings_db:
        llave_usuario_cat = f"{r.user}_{r.categoryCode}"

        if llave_usuario_cat in registros_vistos:
            continue

        registros_vistos.add(llave_usuario_cat)

        c_code = r.categoryCode
        if c_code not in datos_agrupados:
            datos_agrupados[c_code] = {}

        data = r.rankinList or {}
        for tier, ids in data.items():
            pts = puntos_tier.get(tier, 0)
            for v_id in ids:
                try:
                    v_id_int = int(v_id)
                    if v_id_int not in datos_agrupados[c_code]:
                        datos_agrupados[c_code][v_id_int] = []

                    datos_agrupados[c_code][v_id_int].append(pts)
                except (ValueError, TypeError):
                    continue

    total_aventuras = len(registros_vistos)

    villager_ids = set()
    for cat_data in datos_agrupados.values():
        villager_ids.update(cat_data.keys())

    all_v = Villager.objects.using('mongodb').filter(code__in=list(villager_ids))
    v_lookup = {v.code: v for v in all_v}

    final_stats = []
    for c_code, v_scores in datos_agrupados.items():
        cat_obj = cat_map.get(c_code)
        if not cat_obj: continue

        personajes_stats = []
        puntos_totales_cat = []

        for v_id, scores in v_scores.items():
            v_obj = v_lookup.get(v_id)
            if v_obj:
                avg = sum(scores) / len(scores)
                puntos_totales_cat.extend(scores)
                personajes_stats.append({
                    'nombre': v_obj.name,
                    'imagen': v_obj.iconUrl,
                    'puntuacion': round(avg, 2),
                    'votos': len(scores)
                })

        personajes_stats = sorted(personajes_stats, key=lambda x: x['puntuacion'], reverse=True)

        final_stats.append({
            'cat_name': cat_obj.name,
            'cat_code': c_code,
            'cat_avg': round(sum(puntos_totales_cat) / len(puntos_totales_cat), 2) if puntos_totales_cat else 0,
            'personajes': personajes_stats
        })

    return render(request, 'categories_stats.html', {
        'stats_globales': final_stats,
        'total_tierlists': total_aventuras,
    })

#Reviews
def add_review(request, villager_id):
    if not request.user.is_authenticated or request.user.is_superuser:
        return redirect('villagers')

    if request.method == 'POST':
        villager = get_object_or_404(Villager, id=villager_id)
        rating = request.POST.get('rating')
        # Crear review
        Review.objects.create(
            user=request.user,
            villager=villager,
            rating=rating
        )
    return redirect('villagers')


def show_reviews(request, villager_id):
    # Obtener todas las reviews
    reviews = Review.objects.filter(villager_id=villager_id).order_by('-created_at')

    # 2. Comprobar si ya hay review
    user_review = None
    if request.user.is_authenticated:
        user_review = reviews.filter(user=request.user).first()

    if request.method == 'POST':
        if not request.user.is_authenticated:
            return redirect('login')

        rating = request.POST.get('rating')
        comment = request.POST.get('comment')

        review, created = Review.objects.update_or_create(
            villager_id=villager_id,
            user=request.user,
            defaults={'rating': rating, 'comment': comment}
        )

        return redirect('show_reviews', villager_id=villager_id)

    return render(request, 'reviews.html', {
        'reviews': reviews,
        'user_review': user_review,
        'villager_id': villager_id
    })

def all_reviews(request):
    if request.method == "POST" and request.user.is_authenticated:
        v_code = int(request.POST.get('villager_code'))
        rating = int(request.POST.get('rating'))
        comment = request.POST.get('comment')

        Review.objects.update_or_create(
            user=request.user,
            villagerCode=v_code,
            defaults={
                'rating': rating,
                'comments': comment,
                'reviewDate': timezone.now()
            }
        )
        return redirect('all_reviews_list')

    reviews = Review.objects.all().order_by('-reviewDate')
    villagers_list = list(Villager.objects.all())
    villager_dict = {v.code: v for v in villagers_list}

    user_reviews_data = {}
    if request.user.is_authenticated:
        user_reviews = Review.objects.filter(user=request.user)
        for ur in user_reviews:
            user_reviews_data[int(ur.villagerCode)] = {
                'rating': ur.rating,
                'comments': ur.comments
            }

    for r in reviews:
        r.author_name = r.user.username if hasattr(r.user, 'username') else str(r.user)
        v_obj = villager_dict.get(int(r.villagerCode))
        if v_obj:
            r.villager_us_name = v_obj.name_data.get('name-USen', 'Unknown')
            r.villager_img_url = v_obj.imageUrl
        else:
            r.villager_us_name = "Unknown"
            r.villager_img_url = "https://raw.githubusercontent.com/alexislours/ACNHAPI/refs/heads/master/images/villagers/Tom_Nook.png"

    for v in villagers_list:
        v.us_name = v.name_data.get('name-USen', 'Unknown')
        v.full_img_url = v.imageUrl

    return render(request, 'all_reviews.html', {
        'reviews': reviews,
        'villagers': sorted(villagers_list, key=lambda x: x.us_name),
        'user_reviews_json': user_reviews_data  # Pasamos los datos del usuario
    })
