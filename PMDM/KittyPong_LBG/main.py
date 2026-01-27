import pygame, sys, random

# 1. Inicializar
pygame.init()
reloj = pygame.time.Clock()

# Configuración de la pantalla
ancho = 1280
alto = 680
pantalla = pygame.display.set_mode((ancho, alto))
pygame.display.set_caption('Kitty Pong')

# Colores
color_fondo = (54, 69, 79)
color_texto = (255, 149, 0)
color_flash = (255, 107, 107)
color_caja_puntos = (30, 30, 30)

# Variables de imágenes
try:
    imgGatoIzq = pygame.image.load("img/gato.png")
    imgGatoDcha = pygame.image.load("img/gato.png")
    imgOvillo = pygame.image.load("img/ovillo.png")

    img_jugador = pygame.transform.scale(imgGatoDcha, (100, 80))
    img_jugador = pygame.transform.flip(img_jugador, True, False)

    img_rival = pygame.transform.scale(imgGatoIzq, (100, 80))
    img_ovillo = pygame.transform.scale(imgOvillo, (60, 60))

    img_gato_portada = pygame.transform.scale(imgGatoIzq, (150, 120))
    img_gato_portada_flip = pygame.transform.flip(img_gato_portada, True, False)
except:
    img_jugador = None
    img_rival = None
    img_ovillo = None
    img_gato_portada = None

# Variables de Sonido
pygame.mixer.init() # Inicializamos el mezclador de sonido

try:
    sonido_miau = pygame.mixer.Sound("img/miau.mp3")
    sonido_miau.set_volume(0.1) # Volumen al 10%

    sonido_sadmiau = pygame.mixer.Sound("img/sad_miau.mp3")
    sonido_sadmiau.set_volume(0.1)  # Volumen al 10%
except:
    sonido_miau = None
    sonido_sadmiau = None
    print("No se encontró el archivo de sonido 'miau.mp3'")

# Definimos los rectángulos
rect_jugador = pygame.Rect(ancho - 50, alto / 2 - 40, 20, 80)
rect_rival = pygame.Rect(30, alto / 2 - 40, 20, 80)

# Guardamos el tamaño original de las imágenes para el dibujo
ancho_gato, alto_gato = 100, 80

# Ovillo 1 (Principal)
rect_ovillo = pygame.Rect(ancho / 2 - 30, alto / 2 - 30, 60, 60)
velocidad_ovillo_x = 5
velocidad_ovillo_y = 5

# Ovillo 2 (Multibola)
rect_ovillo2 = pygame.Rect(-500, -500, 60, 60)
velocidad_ovillo_x2 = 0
velocidad_ovillo_y2 = 0

# Velocidades jugadores
velocidad_jugador = 0
velocidad_rival = 6
multiplicador_velocidad = 1

# Estado del juego
nivel_4_activado = False
puntos_jugador = 0
puntos_rival = 0
fuente = pygame.font.SysFont("arial", 40, bold=True)
fuente_grande = pygame.font.SysFont("arial", 80, bold=True)
juego_terminado = False
texto_final = ""
puntuacion_maxima = 8  # Puntos para ganar

modo_retro = False

# Portada
def mostrar_portada():
    intro = True
    while intro:
        for evento in pygame.event.get():
            if evento.type == pygame.QUIT:
                pygame.quit()
                sys.exit()
            if evento.type == pygame.KEYDOWN:
                intro = False

        pantalla.fill(color_fondo)

        # Dibujar título principal
        texto_titulo = fuente_grande.render("KITTY PONG", True, color_texto)
        rect_titulo = texto_titulo.get_rect(center=(ancho / 2, alto / 3))
        pantalla.blit(texto_titulo, rect_titulo)

        # Dibujar instrucciones
        texto_inicio = fuente.render("Pulsa ESPACIO para empezar", True, color_texto)
        rect_inicio = texto_inicio.get_rect(center=(ancho / 2, alto / 2 + 80))

        # Fondo para el texto de inicio
        pygame.draw.rect(pantalla, color_fondo, rect_inicio.inflate(40, 20))

        # Borde naranja alrededor de la caja
        pygame.draw.rect(pantalla, color_texto, rect_inicio.inflate(40, 20), 3)


        pantalla.blit(texto_inicio, rect_inicio)

        # Dibujar gatos decorativos si existen
        if img_gato_portada:
            # Gato izquierdo
            pantalla.blit(img_gato_portada, (100, alto / 2 - 60))
            # Gato derecho
            pantalla.blit(img_gato_portada_flip, (ancho - 250, alto / 2 - 60))

        pygame.display.flip()
        reloj.tick(15)  # No necesitamos muchos FPS para un menú estático
mostrar_portada()

# Bucle principal
while True:
    # 1. Detectar eventos
    for evento in pygame.event.get():
        if evento.type == pygame.QUIT:
            pygame.quit()
            sys.exit()

        if evento.type == pygame.KEYDOWN:
            # Reiniciar partida completa (Tecla ESPACIO)
            if evento.key == pygame.K_SPACE and juego_terminado:
                pygame.mixer.stop()
                puntos_jugador = 0
                puntos_rival = 0
                juego_terminado = False
                nivel_4_activado = False

                # Reseteamos ovillo 1
                rect_ovillo.center = (ancho / 2, alto / 2)
                velocidad_ovillo_x = 5 * random.choice((1, -1))
                velocidad_ovillo_y = 5 * random.choice((1, -1))

                # Escondemos ovillo 2
                rect_ovillo2.center = (-500, -500)
                velocidad_ovillo_x2 = 0
                velocidad_ovillo_y2 = 0

            # Movimiento jugador
            if evento.key == pygame.K_DOWN:
                velocidad_jugador = 8
            if evento.key == pygame.K_UP:
                velocidad_jugador = -8

            # Si se pulsa la b cambia de gatito a barra
            if evento.key == pygame.K_b:
                modo_retro = not modo_retro  # Cambia entre True y False

            # Al pulsar la s, el jugador se mueve el doble de rápido por un tiempo
            if evento.key == pygame.K_s:
                multiplicador_velocidad = 2

        if evento.type == pygame.KEYUP:
            if evento.key == pygame.K_DOWN or evento.key == pygame.K_UP:
                velocidad_jugador = 0

            # DESACTIVAR TURBO al soltar la S
            if evento.key == pygame.K_s:
                multiplicador_velocidad = 1  # Vuelve a la normalidad

    # 2. Lógica del movimiento (solo si no ha terminado)
    if not juego_terminado:

        # Mover Jugador
        rect_jugador.y += velocidad_jugador*multiplicador_velocidad
        if rect_jugador.top <= 0: rect_jugador.top = 0
        if rect_jugador.bottom >= alto: rect_jugador.bottom = alto

        # Mover Rival
        if rect_rival.centery < rect_ovillo.centery:
            rect_rival.y += velocidad_rival
        elif rect_rival.centery > rect_ovillo.centery:
            rect_rival.y -= velocidad_rival

        # Límites de pantalla (para que no se salga por abajo)
        if rect_rival.top <= 0: rect_rival.top = 0
        if rect_rival.bottom >= alto: rect_rival.bottom = alto

        # Mover Bola 1
        rect_ovillo.x += velocidad_ovillo_x
        rect_ovillo.y += velocidad_ovillo_y

        # Mover Bola 2 (si está activa)
        if nivel_4_activado:
            rect_ovillo2.x += velocidad_ovillo_x2
            rect_ovillo2.y += velocidad_ovillo_y2

    # Rebote si es gato o línea
    if not modo_retro:
        # Si hay gato, la hitbox es ancha (como antes)
        rect_jugador.width = 100
        rect_rival.width = 100
        # Ajustamos la posición X para que el borde derecho/izquierdo coincida con el gato
        rect_jugador.right = ancho - 20
        rect_rival.left = 20
    else:
        # Si es modo barra, la hitbox es delgada (20px)
        rect_jugador.width = 20
        rect_rival.width = 20
        # Reposicionamos para que la barra esté en su sitio
        rect_jugador.right = ancho - 20
        rect_rival.left = 20

    # 3. Colisiones (Rebotes)

    # Bola 1
    if rect_ovillo.top <= 0 or rect_ovillo.bottom >= alto:
        velocidad_ovillo_y *= -1

    # Colisión jugador - con colliderect se detecta si dos rectángulos se tocan
    if rect_ovillo.colliderect(rect_jugador) and velocidad_ovillo_x > 0:
        # Primero se comprueba si el choque es frontal
        # Se usa un margen de 15 para detectar que la bola está "frente" a la pala
        if abs(rect_ovillo.right - rect_jugador.left) < 15:
            velocidad_ovillo_x *= -1
            # Empujón para evitar que se quede pegada (solo en choque frontal)
            rect_ovillo.right = rect_jugador.left

            # Aceleración
            if abs(velocidad_ovillo_x) < 15:
                velocidad_ovillo_x *= 1.05
                velocidad_ovillo_y *= 1.05
        else:
            # 2. Si choca por arriba o abajo, invierte la Y (rebote lateral)
            velocidad_ovillo_y *= -1

    # Colisión rival
    if rect_ovillo.colliderect(rect_rival) and velocidad_ovillo_x < 0:
        if abs(rect_ovillo.left - rect_rival.right) < 15:
            velocidad_ovillo_x *= -1
            if abs(velocidad_ovillo_x) < 15:  # Aceleración
                velocidad_ovillo_x *= 1.05
                velocidad_ovillo_y *= 1.05
        else:
            velocidad_ovillo_y *= -1

    # Bola 2
    if nivel_4_activado:
        if rect_ovillo2.top <= 0 or rect_ovillo2.bottom >= alto:
            velocidad_ovillo_y2 *= -1

        # Colisión Jugador Bola 2
        if rect_ovillo2.colliderect(rect_jugador) and velocidad_ovillo_x2 > 0:
            if abs(rect_ovillo2.right - rect_jugador.left) < 15:
                velocidad_ovillo_x2 *= -1
                if abs(velocidad_ovillo_x2) < 15:
                    velocidad_ovillo_x2 *= 1.05
                    velocidad_ovillo_y2 *= 1.05
            else:
                velocidad_ovillo_y2 *= -1

        # Colisión rival Bola 2
        if rect_ovillo2.colliderect(rect_rival) and velocidad_ovillo_x2 < 0:
            if abs(rect_ovillo2.left - rect_rival.right) < 15:
                velocidad_ovillo_x2 *= -1
                if abs(velocidad_ovillo_x2) < 15:
                    velocidad_ovillo_x2 *= 1.05
                    velocidad_ovillo_y2 *= 1.05
            else:
                velocidad_ovillo_y2 *= -1

    # 4. Puntuación y Lógica de Goles

    if not juego_terminado:
        se_ha_marcado_gol = False

        # Gol bola 1
        if rect_ovillo.left <= 0:
            puntos_jugador += 1
            if sonido_miau: sonido_miau.play()
            se_ha_marcado_gol = True
        elif rect_ovillo.right >= ancho:
            puntos_rival += 1
            if sonido_sadmiau: sonido_sadmiau.play()
            se_ha_marcado_gol = True

        # Comprobamos victoria inmediatamente para evitar el bug "1-7"
        if points_check := (puntos_jugador >= puntuacion_maxima or puntos_rival >= puntuacion_maxima):
            pass  # Se procesará abajo

        # Gol bola 2
        if nivel_4_activado and not (puntos_jugador >= puntuacion_maxima or puntos_rival >= puntuacion_maxima):
            if rect_ovillo2.left <= 0:
                puntos_jugador += 1
                if sonido_miau: sonido_miau.play()
                se_ha_marcado_gol = True
            elif rect_ovillo2.right >= ancho:
                puntos_rival += 1
                if sonido_sadmiau: sonido_sadmiau.play()
                se_ha_marcado_gol = True

        # Si se ha marcado gol:
        if se_ha_marcado_gol:

            # Flash y pausa
            pantalla.fill(color_flash)
            pygame.display.flip()
            pygame.time.delay(300)

            # 1. Si alguien ha ganado:
            if puntos_jugador >= puntuacion_maxima:
                juego_terminado = True
                texto_final = "¡Ganaste! Eres el rey de la lana."
                puntos_jugador = puntuacion_maxima
                if sonido_miau:
                    pygame.mixer.stop()
                    sonido_miau.play(loops=4)
            elif puntos_rival >= puntuacion_maxima:
                juego_terminado = True
                texto_final = "Oh no... Ganó el rival."
                puntos_rival = puntuacion_maxima
                if sonido_sadmiau:
                    pygame.mixer.stop()
                    sonido_sadmiau.play()

            # 2. Si no ha ganado nadie, saque desde el centro
            else:
                # Calcular si toca activar las 2 bolas
                if puntos_jugador >= 4 or puntos_rival >= 4:
                    nivel_4_activado = True
                else:
                    nivel_4_activado = False

                # Si hay 2 bolas, se separan:
                offset = 40 if nivel_4_activado else 0

                # Reset Bola 1 (Un poco más arriba si hay multibola)
                rect_ovillo.center = (ancho / 2, alto / 2 - offset)
                velocidad_ovillo_x = 5 * random.choice((1, -1))
                velocidad_ovillo_y = 5 * random.choice((1, -1))

                # Reset Bola 2 (Depende de si está activado)
                if nivel_4_activado:
                    # La ponemos un poco más abajo (+ offset)
                    rect_ovillo2.center = (ancho / 2, alto / 2 + offset)

                    velocidad_ovillo_x2 = 3 * random.choice((1, -1))

                    # Para que la bola 2 salga en dirección Y contraria a la 1
                    # para que se separen visualmente más rápido
                    velocidad_ovillo_y2 = -velocidad_ovillo_y

                else:
                    # Si bajamos de nivel o reiniciamos, la escondemos
                    rect_ovillo2.center = (-500, -500)
                    velocidad_ovillo_x2 = 0
                    velocidad_ovillo_y2 = 0

    # 5. Dibujar Pantalla
    pantalla.fill(color_fondo)
    pygame.draw.aaline(pantalla, color_texto, (ancho / 2, 0), (ancho / 2, alto))

    if not modo_retro and img_jugador:
        pantalla.blit(img_jugador, rect_jugador)
        pantalla.blit(img_rival, rect_rival)
    else:
        # Dibujamos barras
        pygame.draw.rect(pantalla, color_flash, rect_jugador, border_radius=5)
        pygame.draw.rect(pantalla, color_flash, rect_rival, border_radius=5)


    if img_ovillo:
        pantalla.blit(img_ovillo, rect_ovillo)
        if nivel_4_activado:
            pantalla.blit(img_ovillo, rect_ovillo2)
    else:
        #Cambio de color de la elipse
        pygame.draw.ellipse(pantalla, (200, 100, 200), rect_ovillo)
        if nivel_4_activado:
            pygame.draw.ellipse(pantalla, (255, 255, 255), rect_ovillo2)



    # Marcador
    texto_puntos = fuente.render(f"{puntos_rival} - {puntos_jugador}", True, color_texto)
    # Obtener el rectángulo del texto y centrar arriba
    rect_texto = texto_puntos.get_rect(center=(ancho / 2, 40))

    # Rectángulo para el fondo
    rect_fondo_puntos = rect_texto.inflate(20, 10)

    # Dibujar fondo
    pygame.draw.rect(pantalla, color_fondo, rect_fondo_puntos)

    # Borde naranja alrededor de la caja
    pygame.draw.rect(pantalla, color_texto, rect_fondo_puntos, 2)

    # Dibujar el texto encima
    pantalla.blit(texto_puntos, rect_texto)



    # Fin del juego
    if juego_terminado:
        fuente_grande_final = pygame.font.SysFont("arial", 60, bold=True)
        cartel = fuente_grande_final.render(texto_final, True, color_texto)
        rect_cartel = cartel.get_rect(center=(ancho / 2, alto / 2))

        rect_fondo_cartel = rect_cartel.inflate(40, 40)
        pygame.draw.rect(pantalla, color_fondo, rect_fondo_cartel)
        pygame.draw.rect(pantalla, color_texto, rect_fondo_cartel, 3)

        pantalla.blit(cartel, rect_cartel)

        texto_reiniciar = fuente.render("Pulsa ESPACIO para reiniciar", True, color_texto)
        rect_reiniciar = texto_reiniciar.get_rect(center=(ancho / 2, alto / 2 + 120))
        rect_fondo_reiniciar = rect_reiniciar.inflate(20, 10)

        pygame.draw.rect(pantalla, color_fondo, rect_fondo_reiniciar)


        pantalla.blit(texto_reiniciar, rect_reiniciar)

    pygame.display.flip()
    reloj.tick(60)