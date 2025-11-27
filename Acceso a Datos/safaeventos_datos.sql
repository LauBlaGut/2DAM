--USUARIOS
INSERT INTO usuario (email, contrasenia, rol, verificacion) VALUES
-- Profesores (5)
('profe1@fundacionsafa.es', 'pass1', 0, TRUE),
('profe2@fundacionsafa.es', 'pass2', 0, TRUE),
('profe3@fundacionsafa.es', 'pass3', 0, TRUE),
('profe4@fundacionsafa.es', 'pass4', 0, TRUE),
('profe5@fundacionsafa.es', 'pass5', 0, TRUE),

/*UPDATE usuario
SET rol = 0
WHERE id = 5;*/

-- Alumnos (20)
('alumno1@safareyes.es', 'pass1', 1, TRUE),
('alumno2@safareyes.es', 'pass2', 1, TRUE),
('alumno3@safareyes.es', 'pass3', 1, TRUE),
('alumno4@safareyes.es', 'pass4', 1, TRUE),
('alumno5@safareyes.es', 'pass5', 1, TRUE),
('alumno6@safareyes.es', 'pass6', 1, TRUE),
('alumno7@safareyes.es', 'pass7', 1, TRUE),
('alumno8@safareyes.es', 'pass8', 1, TRUE),
('alumno9@safareyes.es', 'pass9', 1, TRUE),
('alumno10@safareyes.es', 'pass10', 1, TRUE),
('alumno11@safareyes.es', 'pass11', 1, TRUE),
('alumno12@safareyes.es', 'pass12', 1, TRUE),
('alumno13@safareyes.es', 'pass13', 1, TRUE),
('alumno14@safareyes.es', 'pass14', 1, TRUE),
('alumno15@safareyes.es', 'pass15', 1, TRUE),
('alumno16@safareyes.es', 'pass16', 1, TRUE),
('alumno17@safareyes.es', 'pass17', 1, TRUE),
('alumno18@safareyes.es', 'pass18', 1, TRUE),
('alumno19@safareyes.es', 'pass19', 1, TRUE),
('alumno20@safareyes.es', 'pass20', 1, TRUE);


--PERFILES
INSERT INTO perfil (id_usuario, nombre, apellidos, curso, fecha_registro, foto_url) VALUES

-- Profesores
(1, 'Juan',   'Martínez López',   NULL, CURRENT_DATE, 'https://cdn-icons-png.flaticon.com/512/4140/4140030.png'),
(2, 'Ana',    'Gómez Ruiz',        NULL, CURRENT_DATE, 'https://cdn-icons-png.flaticon.com/512/4140/4140048.png'),
(3, 'Pablo',  'Santos Peña',       NULL, CURRENT_DATE, 'https://cdn-icons-png.flaticon.com/512/4140/4140047.png'),
(4, 'Carmen', 'Rodríguez Soto',    NULL, CURRENT_DATE, 'https://cdn-icons-png.flaticon.com/512/4140/4140051.png'),
(5, 'Luis',   'Navarro Díaz',      NULL, CURRENT_DATE, 'https://cdn-icons-png.flaticon.com/512/4140/4140035.png'),

-- Alumnos
(6,  'Alba',   'García Torres',     1, CURRENT_DATE, 'https://cdn-icons-png.flaticon.com/512/4140/4140037.png'),
(7,  'Mario',  'López García',      2, CURRENT_DATE, 'https://cdn-icons-png.flaticon.com/512/4140/4140027.png'),
(8,  'Lucía',  'Serrano Pérez',     3, CURRENT_DATE, 'https://cdn-icons-png.flaticon.com/512/4140/4140046.png'),
(9,  'Daniel', 'Hernández Ruiz',    4, CURRENT_DATE, 'https://cdn-icons-png.flaticon.com/512/4140/4140043.png'),
(10, 'Sara',   'Romero Vargas',     5, CURRENT_DATE, 'https://cdn-icons-png.flaticon.com/512/4140/4140031.png'),

(11, 'Irene',  'Campos Soto',       6, CURRENT_DATE, 'https://cdn-icons-png.flaticon.com/512/4140/4140050.png'),
(12, 'Óscar',  'Gómez Rivas',       7, CURRENT_DATE, 'https://cdn-icons-png.flaticon.com/512/4140/4140039.png'),
(13, 'Javier', 'Moreno Reyes',      8, CURRENT_DATE, 'https://cdn-icons-png.flaticon.com/512/4140/4140042.png'),
(14, 'Clara',  'Santos Jiménez',    9, CURRENT_DATE, 'https://cdn-icons-png.flaticon.com/512/4140/4140053.png'),
(15, 'Diego',  'Vargas Torres',    10, CURRENT_DATE, 'https://cdn-icons-png.flaticon.com/512/4140/4140022.png'),

(16, 'Nuria',  'Flores Martín',    11, CURRENT_DATE, 'https://cdn-icons-png.flaticon.com/512/4140/4140045.png'),
(17, 'Eva',    'Cabrera León',     12, CURRENT_DATE, 'https://cdn-icons-png.flaticon.com/512/4140/4140033.png'),
(18, 'Sergio', 'Reina López',      13, CURRENT_DATE, 'https://cdn-icons-png.flaticon.com/512/4140/4140038.png'),
(19, 'Helena', 'Fuentes Gil',      14, CURRENT_DATE, 'https://cdn-icons-png.flaticon.com/512/4140/4140061.png'),
(20, 'Adrián', 'Navas Ruiz',       15, CURRENT_DATE, 'https://cdn-icons-png.flaticon.com/512/4140/4140055.png'),

(21, 'Paula',  'Cano Peña',        16, CURRENT_DATE, 'https://cdn-icons-png.flaticon.com/512/4140/4140061.png'),
(22, 'Alfonso','Mena Díaz',         1, CURRENT_DATE, 'https://cdn-icons-png.flaticon.com/512/4140/4140030.png'),
(23, 'Teresa', 'Prado Muñoz',       4, CURRENT_DATE, 'https://cdn-icons-png.flaticon.com/512/4140/4140037.png'),
(24, 'Rosa',   'Vega Ríos',         9, CURRENT_DATE, 'https://cdn-icons-png.flaticon.com/512/4140/4140048.png'),
(25, 'Marco',  'Linares Cruz',     13, CURRENT_DATE, 'https://cdn-icons-png.flaticon.com/512/4140/4140047.png');


--EVENTOS
INSERT INTO evento (titulo, descripcion, fecha_hora, ubicacion, foto, precio, categoria, id_organizador) VALUES
('Feria de Ciencias',
 'Gran feria científica donde los estudiantes presentarán proyectos de física, química, biología y robótica. Habrá demostraciones en vivo, experimentos interactivos y stands explicativos. Se premiarán los mejores trabajos y se realizará una exhibición final. Fecha del evento: 6 de diciembre de 2025.',
 '2025-12-06 10:00', 'Gimnasio',
 'https://images.pexels.com/photos/256369/pexels-photo-256369.jpeg', 0, 1, 1),

('Festival Cultural Escolar',
 'Celebración artística con música en vivo, danza tradicional, exposiciones de pintura, artesanías y gastronomía multicultural. El objetivo es promover la expresión artística y la convivencia escolar. Fecha: 7 de diciembre de 2025.',
 '2025-12-07 17:00', 'Patio Central',
 'https://images.pexels.com/photos/1763075/pexels-photo-1763075.jpeg', 0, 3, 1),

('Torneo de Fútbol Intercursos',
 'Competición deportiva donde los distintos cursos se enfrentarán en un torneo eliminatorio. Habrá árbitros oficiales, marcador digital y entrega de medallas. Se fomenta el trabajo en equipo y la deportividad. Fecha: 8 de diciembre de 2025.',
 '2025-12-08 15:00', 'Campo Deportivo',
 'https://images.pexels.com/photos/399187/pexels-photo-399187.jpeg', 0, 2, 2),

('Concierto Navideño Escolar',
 'Presentación musical preparada por el coro y la orquesta escolar. Incluye villancicos, piezas modernas y actuaciones individuales. Ideal para comenzar el ambiente navideño. Fecha: 9 de diciembre de 2025.',
 '2025-12-09 18:00', 'Auditorio',
 'https://images.pexels.com/photos/1667857/pexels-photo-1667857.jpeg', 0, 3, 2),

('Charla de Orientación Vocacional',
 'Profesionales invitados hablarán sobre sus experiencias en medicina, ingeniería, arte, educación, informática y oficios especializados. Habrá ronda de preguntas y entrega de guías informativas. Fecha: 10 de diciembre de 2025.',
 '2025-12-10 12:00', 'Aula Magna',
 'https://images.pexels.com/photos/1181395/pexels-photo-1181395.jpeg', 0, 1, 3),

('Maratón Matemático',
 'Competencia de resolución de problemas matemáticos, acertijos lógicos, cálculos rápidos y pruebas de razonamiento. Participan equipos de 3 alumnos. Fecha: 11 de diciembre de 2025.',
 '2025-12-11 09:00', 'Edificio B - Aula 12',
 'https://images.pexels.com/photos/542883/pexels-photo-542883.jpeg', 0, 1, 3),

('Taller de Pintura Creativa',
 'Sesión libre de pintura donde los alumnos podrán crear obras propias con materiales proporcionados por el centro. Al final se realizará una mini exposición de trabajos. Fecha: 12 de diciembre de 2025.',
 '2025-12-12 16:00', 'Sala de Arte',
 'https://images.pexels.com/photos/207067/pexels-photo-207067.jpeg', 2.00, 3, 4),

('Competencia de Robótica Escolar',
 'Torneo de robótica educativa con robots LEGO Mindstorms y Arduino. Se evaluará velocidad, precisión y autonomía. Participan equipos de varios niveles educativos. Fecha: 13 de diciembre de 2025.',
 '2025-12-13 10:00', 'Laboratorio de Tecnología',
 'https://images.pexels.com/photos/2599244/pexels-photo-2599244.jpeg', 0, 4, 4),

('Obra de Teatro: Sueños en Escena',
 'Presentación teatral escrita y actuada por estudiantes del grupo de teatro escolar. La obra mezcla comedia, drama y música original. Habrá dos funciones. Fecha: 14 de diciembre de 2025.',
 '2025-12-14 19:00', 'Teatro del Centro',
 'https://images.pexels.com/photos/713149/pexels-photo-713149.jpeg', 0, 3, 5),

('Rally del Conocimiento',
 'Concurso por equipos donde los estudiantes deberán resolver pruebas de historia, ciencia, arte, tecnología y cultura general en el menor tiempo posible. Fecha: 15 de diciembre de 2025.',
 '2025-12-15 09:00', 'Biblioteca',
 'https://images.pexels.com/photos/3184644/pexels-photo-3184644.jpeg', 0, 1, 5),

('Competencia de Debate Escolar',
 'Torneo interno de debate donde los estudiantes participarán en rondas clasificatorias argumentando sobre temas actuales relacionados con educación, ciencia, sociedad y tecnología. El evento fomenta el pensamiento crítico, la capacidad de expresión y el análisis profundo. Se contará con jurado compuesto por profesores de lengua y filosofía. Fecha: 16 de diciembre de 2025.',
 '2025-12-16 10:00', 'Aula Magna',
 'https://images.pexels.com/photos/3182751/pexels-photo-3182751.jpeg', 0, 1, 1),

('Exposición STEAM: Ciencia, Tecnología y Arte',
 'Muestra interdisciplinar donde los estudiantes presentarán proyectos que combinan ciencia, tecnología, ingeniería, arte y matemáticas. La exposición incluye maquetas impresas en 3D, prototipos electrónicos, instalaciones artísticas interactivas y presentaciones multimedia. Fecha: 16 de diciembre de 2025.',
 '2025-12-16 17:00', 'Hall Principal',
 'https://images.pexels.com/photos/256417/pexels-photo-256417.jpeg', 0, 4, 2),

('Taller Intensivo de Fotografía Digital',
 'Taller práctico para aprender técnicas de composición, manejo de luz, encuadre y edición digital. Los asistentes deben traer su móvil o cámara. Se realizará una sesión fotográfica en exteriores y una mini exposición de resultados. Fecha: 17 de diciembre de 2025.',
 '2025-12-17 16:00', 'Sala Multimedia',
 'https://images.pexels.com/photos/212372/pexels-photo-212372.jpeg', 0, 3, 3),

('Competencia de Ajedrez Escolar',
 'Torneo suizo de ajedrez dirigido a alumnos de todas las edades. Incluye charlas introductorias, análisis de partidas famosas y enfrentamientos eliminatorios. Se entregarán medallas y diplomas. Fecha: 18 de diciembre de 2025.',
 '2025-12-18 09:00', 'Biblioteca',
 'https://images.pexels.com/photos/247373/pexels-photo-247373.jpeg', 0, 2, 4),

('Caminata Solidaria por la Comunidad',
 'Actividad al aire libre donde estudiantes y profesores recorrerán un circuito urbano para recaudar fondos destinados a organizaciones locales. Incluye puntos de hidratación, animadores y entrega de camisetas. Fecha: 18 de diciembre de 2025.',
 '2025-12-18 12:00', 'Salida Principal',
 'https://images.pexels.com/photos/1199592/pexels-photo-1199592.jpeg', 1.00, 5, 5),

('Feria del Libro Escolar',
 'Evento cultural dedicado a la lectura con stands de editoriales, autores invitados, cuentacuentos, trueque de libros usados, actividades para niños y concursos literarios. Se promoverá el hábito lector y la creatividad escrita. Fecha: 19 de diciembre de 2025.',
 '2025-12-19 10:00', 'Patio Cubierto',
 'https://images.pexels.com/photos/159711/books-bookstore-book-reading-159711.jpeg', 0, 3, 1),

('Concurso de Fotografía Artística',
 'Concurso donde los alumnos presentarán fotografías originales relacionadas con naturaleza, arquitectura y vida escolar. Las mejores obras serán expuestas en la entrada del centro. Habrá jurado profesional y premios especiales. Fecha: 19 de diciembre de 2025.',
 '2025-12-19 17:00', 'Sala de Arte',
 'https://images.pexels.com/photos/167832/pexels-photo-167832.jpeg', 0, 3, 2),

('Torneo de Ping Pong Escolar',
 'Competencia deportiva abierta a todos los alumnos. Se jugarán rondas clasificatorias, semifinales y final. Habrá categorías junior, senior y libre. Se proporcionan palas y pelotas. Fecha: 20 de diciembre de 2025.',
 '2025-12-20 11:00', 'Gimnasio',
 'https://images.pexels.com/photos/1192093/pexels-photo-1192093.jpeg', 0, 2, 3),

('Feria Gastronómica Estudiantil',
 'Exposición y degustación de platos preparados por estudiantes y familias, con gastronomía internacional, concursos culinarios, sorteos y premios a la mejor presentación. Fecha: 20 de diciembre de 2025.',
 '2025-12-20 18:00', 'Patio Principal',
 'https://images.pexels.com/photos/70497/pexels-photo-70497.jpeg', 2.00, 3, 4),

('Velada de Talentos Estudiantiles',
 'Gala nocturna donde los alumnos presentan números musicales, de danza, magia, poesía, humor y actuaciones libres. El evento se divide en categorías y culmina con una presentación grupal final. Fecha: 21 de diciembre de 2025.',
 '2025-12-21 19:00', 'Auditorio',
 'https://images.pexels.com/photos/210922/pexels-photo-210922.jpeg', 0, 3, 5);


--INSCRIPCIONES
INSERT INTO inscripcion (id_usuario, id_evento, pago_realizado, metodo_pago, tiene_coste) VALUES
(6, 1, FALSE, 0, FALSE),
(7, 1, FALSE, 0, FALSE),
(8, 1, FALSE, 0, FALSE),

(9, 2, FALSE, 0, FALSE),
(10, 2, FALSE, 0, FALSE),

(11, 3, FALSE, 0, FALSE),
(12, 3, FALSE, 0, FALSE),
(13, 3, FALSE, 0, FALSE),

(14, 4, FALSE, 0, FALSE),
(15, 4, FALSE, 0, FALSE),

(16, 5, FALSE, 0, FALSE),
(17, 5, FALSE, 0, FALSE),

(18, 7, TRUE, 1, TRUE),
(19, 7, TRUE, 2, TRUE),

(20, 15, TRUE, 2, TRUE),
(21, 15, TRUE, 1, TRUE),

(22, 19, TRUE, 1, TRUE),
(23, 19, TRUE, 1, TRUE),

(24, 10, FALSE, 0, FALSE),
(25, 10, FALSE, 0, FALSE),

(1, 11, FALSE, 1, FALSE),
(2, 11, FALSE, 1, FALSE),

(3, 12, FALSE, 1, FALSE),
(4, 12, FALSE, 1, FALSE),
(5, 12, FALSE, 1, FALSE),

(6, 13, FALSE, 1, FALSE),
(7, 13, FALSE, 1, FALSE),

(8, 14, FALSE, 1, FALSE),
(9, 14, FALSE, 1, FALSE),
(10, 14, FALSE, 1, FALSE),

(11, 15, TRUE, 2, TRUE),
(12, 15, TRUE, 1, TRUE),
(13, 15, FALSE, 2, TRUE),

(14, 16, FALSE, 1, FALSE),
(15, 16, FALSE, 1, FALSE),

(16, 17, FALSE, 1, FALSE),
(17, 17, FALSE, 1, FALSE),
(18, 17, FALSE, 1, FALSE),

(19, 19, TRUE, 3, TRUE),
(20, 19, TRUE, 1, TRUE),

(21, 20, FALSE, 1, FALSE),
(22, 20, FALSE, 1, FALSE);

--ME INTERESA
INSERT INTO me_interesa (id_usuario, id_evento, fecha_guardado) VALUES
(1,  3, CURRENT_DATE),
(2,  5, CURRENT_DATE),
(3,  7, CURRENT_DATE),
(4,  1, CURRENT_DATE),
(5,  2, CURRENT_DATE),

(6,  8, CURRENT_DATE),
(7,  9, CURRENT_DATE),
(8,  4, CURRENT_DATE),
(9,  6, CURRENT_DATE),
(10, 10, CURRENT_DATE),

(11, 12, CURRENT_DATE),
(12, 11, CURRENT_DATE),
(13, 13, CURRENT_DATE),
(14, 14, CURRENT_DATE),
(15, 15, CURRENT_DATE),

(16, 16, CURRENT_DATE),
(17, 18, CURRENT_DATE),
(18, 17, CURRENT_DATE),
(19, 19, CURRENT_DATE),
(20, 20, CURRENT_DATE);


--FOTOS EVENTO
INSERT INTO foto_evento (id_evento, id_usuario, ruta_foto) VALUES
-- Feria de Ciencias (Evento 1)
(1, 6,  '/media/eventos/ciencia/experimento_quimica.jpg'),
(1, 7,  '/media/eventos/ciencia/robotica_proyecto1.jpg'),

-- Festival Cultural (Evento 2)
(2, 8,  '/media/eventos/cultural/baile_folclore.jpg'),
(2, 9,  '/media/eventos/cultural/expo_pintura.jpg'),

-- Torneo de Fútbol (Evento 3)
(3, 10, '/media/eventos/deportes/partido_equipoA_vs_B.jpg'),
(3, 11, '/media/eventos/deportes/trofeo_futbol.jpg'),

-- Concierto Navideño (Evento 4)
(4, 12, '/media/eventos/cultural/coro_actuacion.jpg'),
(4, 13, '/media/eventos/cultural/orquesta_ensayo.jpg'),

-- Charla Vocacional (Evento 5)
(5, 14, '/media/eventos/academico/charla_orientacion.jpg'),
(5, 15, '/media/eventos/academico/ponentes_invitados.jpg'),

-- Maratón Matemático (Evento 6)
(6, 16, '/media/eventos/academico/concurso_mates.jpg'),
(6, 17, '/media/eventos/academico/logica_retos.jpg'),

-- Taller de Pintura (Evento 7)
(7, 18, '/media/eventos/cultural/pintura_taller.jpg'),
(7, 19, '/media/eventos/cultural/obras_estudiantes.jpg'),

-- Competencia de Robótica (Evento 8)
(8, 20, '/media/eventos/tecnologia/robots_competencia.jpg'),
(8, 21, '/media/eventos/tecnologia/arduino_proyecto.jpg'),

-- Teatro Escolar (Evento 9)
(9, 22, '/media/eventos/cultural/obra_teatro.jpg'),
(9, 23, '/media/eventos/cultural/escenario_actores.jpg'),

-- Rally del Conocimiento (Evento 10)
(10, 24, '/media/eventos/academico/rally_equipo1.jpg'),
(10, 25, '/media/eventos/academico/desafio_preguntas.jpg');



--COMENTARIOS EVENTO
INSERT INTO comentario_evento (id_evento, id_usuario, comentario) VALUES
-- Evento 1: Feria de Ciencias
(1, 6,  'Los experimentos de química estuvieron increíbles, sobre todo el de reacciones en cadena.'),
(1, 7,  'Me encantó ver tantos proyectos originales, la zona de robótica fue mi favorita.'),

-- Evento 2: Festival Cultural
(2, 8,  'La actuación de baile estuvo superbien montada, felicidades a todos.'),
(2, 9,  'La exposición de pintura tenía trabajos impresionantes, gran nivel artístico.'),

-- Evento 3: Torneo de Fútbol
(3, 10, 'Partidazo entre 4ºA y 4ºB, nos mantuvo a todos al borde del asiento.'),
(3, 11, 'Muy buena organización, ojalá haya otro torneo pronto.'),

-- Evento 4: Concierto Navideño
(4, 12, 'El coro sonó espectacular, se pueden ver las horas de ensayo detrás.'),
(4, 13, 'La orquesta tocó muy bien, el ambiente fue precioso.'),

-- Evento 5: Charla Vocacional
(5, 14, 'La charla de ingeniería fue muy clara y motivadora.'),
(5, 15, 'Me ayudó mucho a decidir qué quiero estudiar después del bachillerato.'),

-- Evento 6: Maratón Matemático
(6, 16, 'Los problemas de lógica estuvieron bastante desafiantes, lo disfruté mucho.'),
(6, 17, 'Muy divertido resolver acertijos en equipo, debería repetirse más veces.'),

-- Evento 7: Taller de Pintura
(7, 18, 'Me relajó un montón pintar libremente, aprendí nuevas técnicas de acuarela.'),
(7, 19, 'Las obras que se expusieron al final quedaron increíbles.'),

-- Evento 8: Competencia de Robótica
(8, 20, 'Los robots autónomos fueron una pasada, sobre todo los que hacían el laberinto.'),
(8, 21, 'Qué nivel de programación, me dieron ganas de apuntarme al taller de Arduino.'),

-- Evento 9: Teatro Escolar
(9, 22, 'La obra estuvo superemocionante, las actuaciones fueron muy profesionales.'),
(9, 23, 'El escenario y la iluminación quedaron perfectos, gran trabajo del equipo técnico.'),

-- Evento 10: Rally del Conocimiento
(10, 24,'Las preguntas eran difíciles pero aprendí un montón.'),
(10, 25,'Mi equipo quedó segundo, pero nos divertimos muchísimo.');



--NOTIFICACIÓN
INSERT INTO notificacion (id_usuario, mensaje, fecha_envio, leido) VALUES
(6,  'Tu inscripción en la Feria de Ciencias ha sido confirmada.', CURRENT_DATE, FALSE),
(7,  'Recordatorio: La Feria de Ciencias comienza mañana a las 10:00.', CURRENT_DATE, TRUE),

(8,  'Nuevas fotos disponibles del Festival Cultural.', CURRENT_DATE, FALSE),
(9,  'Has marcado interés en el Festival Cultural. ¡No olvides asistir!', CURRENT_DATE, TRUE),

(10, 'Tu equipo jugará en el Torneo de Fútbol este lunes.', CURRENT_DATE, FALSE),
(11, 'Actualización: Se ha añadido una nueva galería al Torneo de Fútbol.', CURRENT_DATE, FALSE),

(12, 'El Concierto Navideño tendrá ensayo general mañana.', CURRENT_DATE, TRUE),
(13, 'Nueva foto subida al Concierto Navideño.', CURRENT_DATE, FALSE),

(14, 'Gracias por asistir a la Charla Vocacional.', CURRENT_DATE, FALSE),
(15, 'Se han añadido diapositivas nuevas de la Charla Vocacional.', CURRENT_DATE, TRUE),

(16, 'Tu equipo ha sido registrado para el Maratón Matemático.', CURRENT_DATE, FALSE),
(17, 'Recordatorio: mañana es el Maratón Matemático.', CURRENT_DATE, TRUE),

(18, 'Tu participación en el Taller de Pintura fue registrada correctamente.', CURRENT_DATE, FALSE),
(19, 'La galería del Taller de Pintura ya está disponible.', CURRENT_DATE, TRUE),

(20, '¡Tu robot pasó a la siguiente fase en la Competencia de Robótica!', CURRENT_DATE, FALSE),
(21, 'El evento de Robótica tiene nuevas fotos subidas por alumnos.', CURRENT_DATE, TRUE),

(22, 'La obra de teatro ya está disponible en video para los participantes.', CURRENT_DATE, FALSE),
(23, 'Has recibido un reconocimiento por tu actuación en Sueños en Escena.', CURRENT_DATE, TRUE);




















