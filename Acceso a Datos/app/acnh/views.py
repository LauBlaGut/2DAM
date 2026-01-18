import json

from django.http import JsonResponse
from django.shortcuts import render, redirect
from django.contrib.auth import authenticate, login, logout
from acnh.forms import *
from acnh.models import *
from django.contrib.auth.decorators import login_required

# Create your views here.
def go_home(request):
    return render(request, 'home.html')


def show_villagers(request):

    list_villagers = Villager.objects.all()

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
            return redirect('home')
    else:
        form = LoginForm()


    return render(request, 'login.html', {"form": form})


def do_register(request):


    if request.method == 'POST':
        dataform = RegisterForm(request.POST)

        #validaciones
        if dataform.is_valid():
            user = dataform.save(commit=False)
            user.set_password(dataform.cleaned_data['password'])
            user.save()
            return redirect('do_login')
        else:
            return render(request, 'register.html', {"form": dataform})




    else:
        form = RegisterForm()
        return render(request, 'register.html', {"form": form})



def logout_user(request):
    logout(request)
    return redirect('home')


@login_required(login_url='do_login')  # Si no está logueado, lo manda al login
def do_ranking(request):
    if request.method == 'POST':
        tiers_json = request.POST.get('ranking_data')
        category_code = request.POST.get('category_code', 1)

        if tiers_json:
            tiers_data = json.loads(tiers_json)

            Ranking.objects.create(
                user=request.user.username,
                categoryCode=int(category_code),
                rankinList=tiers_data
            )

        return redirect('go_home')

    villagers = Villager.objects.all()
    return render(request, 'ranking.html', {'villagers': villagers})

@login_required(login_url='do_login')
def do_reviews(request):
    villagers = Villager.objects.all()

    user_reviews = Review.objects.filter(user=request.user.username)

    reviews_data = {
        r.villager_id: {'rating': r.rating, 'comment': r.comment}
        for r in user_reviews
    }

    return render(request, 'review.html', {
        'villagers': villagers,
        'reviews_json': json.dumps(reviews_data)
    })


@login_required
def save_review(request):
    if request.method == 'POST':
        data = json.loads(request.body)

        Review.objects.update_or_create(
            user=request.user.username,
            villager_id=data['villager_id'],
            defaults={
                'rating': data['rating'],
                'comment': data['comment']
            }
        )
        return JsonResponse({'status': 'ok'})

    return JsonResponse({'status': 'error'}, status=400)