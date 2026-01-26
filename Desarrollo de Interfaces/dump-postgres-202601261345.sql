--
-- PostgreSQL database dump
--

\restrict LMV3WwaqfbNVUhanUNmzYTzf6Iipau4Q2DHgTHHfTyyhWdfQvHI8enzvt6stGT8

-- Dumped from database version 17.6
-- Dumped by pg_dump version 17.6

-- Started on 2026-01-26 13:45:33

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE postgres;
--
-- TOC entry 5025 (class 1262 OID 5)
-- Name: postgres; Type: DATABASE; Schema: -; Owner: -
--

CREATE DATABASE postgres WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'es';


\unrestrict LMV3WwaqfbNVUhanUNmzYTzf6Iipau4Q2DHgTHHfTyyhWdfQvHI8enzvt6stGT8
\connect postgres
\restrict LMV3WwaqfbNVUhanUNmzYTzf6Iipau4Q2DHgTHHfTyyhWdfQvHI8enzvt6stGT8

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 5026 (class 0 OID 0)
-- Dependencies: 5025
-- Name: DATABASE postgres; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON DATABASE postgres IS 'default administrative connection database';


--
-- TOC entry 6 (class 2615 OID 17256)
-- Name: safaeventos; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA safaeventos;


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 249 (class 1259 OID 17371)
-- Name: comentario_evento; Type: TABLE; Schema: safaeventos; Owner: -
--

CREATE TABLE safaeventos.comentario_evento (
    id integer NOT NULL,
    id_evento integer NOT NULL,
    id_usuario integer NOT NULL,
    comentario text NOT NULL,
    fecha_comentario timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


--
-- TOC entry 248 (class 1259 OID 17370)
-- Name: comentario_evento_id_seq; Type: SEQUENCE; Schema: safaeventos; Owner: -
--

CREATE SEQUENCE safaeventos.comentario_evento_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5027 (class 0 OID 0)
-- Dependencies: 248
-- Name: comentario_evento_id_seq; Type: SEQUENCE OWNED BY; Schema: safaeventos; Owner: -
--

ALTER SEQUENCE safaeventos.comentario_evento_id_seq OWNED BY safaeventos.comentario_evento.id;


--
-- TOC entry 239 (class 1259 OID 17282)
-- Name: evento; Type: TABLE; Schema: safaeventos; Owner: -
--

CREATE TABLE safaeventos.evento (
    id integer NOT NULL,
    titulo character varying(200) NOT NULL,
    descripcion text NOT NULL,
    fecha_hora timestamp without time zone NOT NULL,
    ubicacion character varying(200) NOT NULL,
    foto character varying(200) NOT NULL,
    precio numeric(10,2) DEFAULT 0,
    categoria smallint NOT NULL,
    id_organizador integer NOT NULL,
    CONSTRAINT evento_precio_check CHECK ((precio >= (0)::numeric))
);


--
-- TOC entry 238 (class 1259 OID 17281)
-- Name: evento_id_seq; Type: SEQUENCE; Schema: safaeventos; Owner: -
--

CREATE SEQUENCE safaeventos.evento_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5028 (class 0 OID 0)
-- Dependencies: 238
-- Name: evento_id_seq; Type: SEQUENCE OWNED BY; Schema: safaeventos; Owner: -
--

ALTER SEQUENCE safaeventos.evento_id_seq OWNED BY safaeventos.evento.id;


--
-- TOC entry 247 (class 1259 OID 17353)
-- Name: foto_evento; Type: TABLE; Schema: safaeventos; Owner: -
--

CREATE TABLE safaeventos.foto_evento (
    id integer NOT NULL,
    id_evento integer NOT NULL,
    id_usuario integer NOT NULL,
    ruta_foto character varying(255) NOT NULL,
    fecha_subida timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


--
-- TOC entry 246 (class 1259 OID 17352)
-- Name: foto_evento_id_seq; Type: SEQUENCE; Schema: safaeventos; Owner: -
--

CREATE SEQUENCE safaeventos.foto_evento_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5029 (class 0 OID 0)
-- Dependencies: 246
-- Name: foto_evento_id_seq; Type: SEQUENCE OWNED BY; Schema: safaeventos; Owner: -
--

ALTER SEQUENCE safaeventos.foto_evento_id_seq OWNED BY safaeventos.foto_evento.id;


--
-- TOC entry 241 (class 1259 OID 17298)
-- Name: inscripcion; Type: TABLE; Schema: safaeventos; Owner: -
--

CREATE TABLE safaeventos.inscripcion (
    id integer NOT NULL,
    id_usuario integer NOT NULL,
    id_evento integer NOT NULL,
    pago_realizado boolean DEFAULT false,
    metodo_pago smallint NOT NULL,
    tiene_coste boolean DEFAULT false NOT NULL
);


--
-- TOC entry 240 (class 1259 OID 17297)
-- Name: inscripcion_id_seq; Type: SEQUENCE; Schema: safaeventos; Owner: -
--

CREATE SEQUENCE safaeventos.inscripcion_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5030 (class 0 OID 0)
-- Dependencies: 240
-- Name: inscripcion_id_seq; Type: SEQUENCE OWNED BY; Schema: safaeventos; Owner: -
--

ALTER SEQUENCE safaeventos.inscripcion_id_seq OWNED BY safaeventos.inscripcion.id;


--
-- TOC entry 243 (class 1259 OID 17319)
-- Name: me_interesa; Type: TABLE; Schema: safaeventos; Owner: -
--

CREATE TABLE safaeventos.me_interesa (
    id integer NOT NULL,
    id_usuario integer NOT NULL,
    id_evento integer NOT NULL,
    fecha_guardado date NOT NULL
);


--
-- TOC entry 242 (class 1259 OID 17318)
-- Name: me_interesa_id_seq; Type: SEQUENCE; Schema: safaeventos; Owner: -
--

CREATE SEQUENCE safaeventos.me_interesa_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5031 (class 0 OID 0)
-- Dependencies: 242
-- Name: me_interesa_id_seq; Type: SEQUENCE OWNED BY; Schema: safaeventos; Owner: -
--

ALTER SEQUENCE safaeventos.me_interesa_id_seq OWNED BY safaeventos.me_interesa.id;


--
-- TOC entry 245 (class 1259 OID 17338)
-- Name: notificacion; Type: TABLE; Schema: safaeventos; Owner: -
--

CREATE TABLE safaeventos.notificacion (
    id integer NOT NULL,
    id_usuario integer NOT NULL,
    mensaje text NOT NULL,
    fecha_envio date NOT NULL,
    leido boolean DEFAULT false
);


--
-- TOC entry 244 (class 1259 OID 17337)
-- Name: notificacion_id_seq; Type: SEQUENCE; Schema: safaeventos; Owner: -
--

CREATE SEQUENCE safaeventos.notificacion_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5032 (class 0 OID 0)
-- Dependencies: 244
-- Name: notificacion_id_seq; Type: SEQUENCE OWNED BY; Schema: safaeventos; Owner: -
--

ALTER SEQUENCE safaeventos.notificacion_id_seq OWNED BY safaeventos.notificacion.id;


--
-- TOC entry 237 (class 1259 OID 17268)
-- Name: perfil; Type: TABLE; Schema: safaeventos; Owner: -
--

CREATE TABLE safaeventos.perfil (
    id integer NOT NULL,
    id_usuario integer NOT NULL,
    nombre character varying(100) NOT NULL,
    apellidos character varying(150) NOT NULL,
    curso smallint,
    fecha_registro date NOT NULL,
    foto_url character varying(600)
);


--
-- TOC entry 236 (class 1259 OID 17267)
-- Name: perfil_id_seq; Type: SEQUENCE; Schema: safaeventos; Owner: -
--

CREATE SEQUENCE safaeventos.perfil_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5033 (class 0 OID 0)
-- Dependencies: 236
-- Name: perfil_id_seq; Type: SEQUENCE OWNED BY; Schema: safaeventos; Owner: -
--

ALTER SEQUENCE safaeventos.perfil_id_seq OWNED BY safaeventos.perfil.id;


--
-- TOC entry 235 (class 1259 OID 17258)
-- Name: usuario; Type: TABLE; Schema: safaeventos; Owner: -
--

CREATE TABLE safaeventos.usuario (
    id integer NOT NULL,
    email character varying(100) NOT NULL,
    contrasenia character varying(100) NOT NULL,
    rol smallint NOT NULL,
    verificacion boolean DEFAULT false NOT NULL
);


--
-- TOC entry 234 (class 1259 OID 17257)
-- Name: usuario_id_seq; Type: SEQUENCE; Schema: safaeventos; Owner: -
--

CREATE SEQUENCE safaeventos.usuario_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5034 (class 0 OID 0)
-- Dependencies: 234
-- Name: usuario_id_seq; Type: SEQUENCE OWNED BY; Schema: safaeventos; Owner: -
--

ALTER SEQUENCE safaeventos.usuario_id_seq OWNED BY safaeventos.usuario.id;


--
-- TOC entry 4823 (class 2604 OID 17374)
-- Name: comentario_evento id; Type: DEFAULT; Schema: safaeventos; Owner: -
--

ALTER TABLE ONLY safaeventos.comentario_evento ALTER COLUMN id SET DEFAULT nextval('safaeventos.comentario_evento_id_seq'::regclass);


--
-- TOC entry 4813 (class 2604 OID 17285)
-- Name: evento id; Type: DEFAULT; Schema: safaeventos; Owner: -
--

ALTER TABLE ONLY safaeventos.evento ALTER COLUMN id SET DEFAULT nextval('safaeventos.evento_id_seq'::regclass);


--
-- TOC entry 4821 (class 2604 OID 17356)
-- Name: foto_evento id; Type: DEFAULT; Schema: safaeventos; Owner: -
--

ALTER TABLE ONLY safaeventos.foto_evento ALTER COLUMN id SET DEFAULT nextval('safaeventos.foto_evento_id_seq'::regclass);


--
-- TOC entry 4815 (class 2604 OID 17301)
-- Name: inscripcion id; Type: DEFAULT; Schema: safaeventos; Owner: -
--

ALTER TABLE ONLY safaeventos.inscripcion ALTER COLUMN id SET DEFAULT nextval('safaeventos.inscripcion_id_seq'::regclass);


--
-- TOC entry 4818 (class 2604 OID 17322)
-- Name: me_interesa id; Type: DEFAULT; Schema: safaeventos; Owner: -
--

ALTER TABLE ONLY safaeventos.me_interesa ALTER COLUMN id SET DEFAULT nextval('safaeventos.me_interesa_id_seq'::regclass);


--
-- TOC entry 4819 (class 2604 OID 17341)
-- Name: notificacion id; Type: DEFAULT; Schema: safaeventos; Owner: -
--

ALTER TABLE ONLY safaeventos.notificacion ALTER COLUMN id SET DEFAULT nextval('safaeventos.notificacion_id_seq'::regclass);


--
-- TOC entry 4812 (class 2604 OID 17271)
-- Name: perfil id; Type: DEFAULT; Schema: safaeventos; Owner: -
--

ALTER TABLE ONLY safaeventos.perfil ALTER COLUMN id SET DEFAULT nextval('safaeventos.perfil_id_seq'::regclass);


--
-- TOC entry 4810 (class 2604 OID 17261)
-- Name: usuario id; Type: DEFAULT; Schema: safaeventos; Owner: -
--

ALTER TABLE ONLY safaeventos.usuario ALTER COLUMN id SET DEFAULT nextval('safaeventos.usuario_id_seq'::regclass);


--
-- TOC entry 5019 (class 0 OID 17371)
-- Dependencies: 249
-- Data for Name: comentario_evento; Type: TABLE DATA; Schema: safaeventos; Owner: -
--

INSERT INTO safaeventos.comentario_evento VALUES (1, 1, 6, 'Los experimentos de química estuvieron increíbles, sobre todo el de reacciones en cadena.', '2025-11-24 14:20:44.101205');
INSERT INTO safaeventos.comentario_evento VALUES (2, 1, 7, 'Me encantó ver tantos proyectos originales, la zona de robótica fue mi favorita.', '2025-11-24 14:20:44.101205');
INSERT INTO safaeventos.comentario_evento VALUES (3, 2, 8, 'La actuación de baile estuvo superbien montada, felicidades a todos.', '2025-11-24 14:20:44.101205');
INSERT INTO safaeventos.comentario_evento VALUES (4, 2, 9, 'La exposición de pintura tenía trabajos impresionantes, gran nivel artístico.', '2025-11-24 14:20:44.101205');
INSERT INTO safaeventos.comentario_evento VALUES (5, 3, 10, 'Partidazo entre 4ºA y 4ºB, nos mantuvo a todos al borde del asiento.', '2025-11-24 14:20:44.101205');
INSERT INTO safaeventos.comentario_evento VALUES (6, 3, 11, 'Muy buena organización, ojalá haya otro torneo pronto.', '2025-11-24 14:20:44.101205');
INSERT INTO safaeventos.comentario_evento VALUES (7, 4, 12, 'El coro sonó espectacular, se pueden ver las horas de ensayo detrás.', '2025-11-24 14:20:44.101205');
INSERT INTO safaeventos.comentario_evento VALUES (8, 4, 13, 'La orquesta tocó muy bien, el ambiente fue precioso.', '2025-11-24 14:20:44.101205');
INSERT INTO safaeventos.comentario_evento VALUES (9, 5, 14, 'La charla de ingeniería fue muy clara y motivadora.', '2025-11-24 14:20:44.101205');
INSERT INTO safaeventos.comentario_evento VALUES (10, 5, 15, 'Me ayudó mucho a decidir qué quiero estudiar después del bachillerato.', '2025-11-24 14:20:44.101205');
INSERT INTO safaeventos.comentario_evento VALUES (11, 6, 16, 'Los problemas de lógica estuvieron bastante desafiantes, lo disfruté mucho.', '2025-11-24 14:20:44.101205');
INSERT INTO safaeventos.comentario_evento VALUES (12, 6, 17, 'Muy divertido resolver acertijos en equipo, debería repetirse más veces.', '2025-11-24 14:20:44.101205');
INSERT INTO safaeventos.comentario_evento VALUES (13, 7, 18, 'Me relajó un montón pintar libremente, aprendí nuevas técnicas de acuarela.', '2025-11-24 14:20:44.101205');
INSERT INTO safaeventos.comentario_evento VALUES (14, 7, 19, 'Las obras que se expusieron al final quedaron increíbles.', '2025-11-24 14:20:44.101205');
INSERT INTO safaeventos.comentario_evento VALUES (15, 8, 20, 'Los robots autónomos fueron una pasada, sobre todo los que hacían el laberinto.', '2025-11-24 14:20:44.101205');
INSERT INTO safaeventos.comentario_evento VALUES (16, 8, 21, 'Qué nivel de programación, me dieron ganas de apuntarme al taller de Arduino.', '2025-11-24 14:20:44.101205');
INSERT INTO safaeventos.comentario_evento VALUES (17, 9, 22, 'La obra estuvo superemocionante, las actuaciones fueron muy profesionales.', '2025-11-24 14:20:44.101205');
INSERT INTO safaeventos.comentario_evento VALUES (18, 9, 23, 'El escenario y la iluminación quedaron perfectos, gran trabajo del equipo técnico.', '2025-11-24 14:20:44.101205');
INSERT INTO safaeventos.comentario_evento VALUES (19, 10, 24, 'Las preguntas eran difíciles pero aprendí un montón.', '2025-11-24 14:20:44.101205');
INSERT INTO safaeventos.comentario_evento VALUES (20, 10, 25, 'Mi equipo quedó segundo, pero nos divertimos muchísimo.', '2025-11-24 14:20:44.101205');
INSERT INTO safaeventos.comentario_evento VALUES (21, 12, 20, 'El evento estuvo genial!', '2025-11-27 19:51:04.573397');


--
-- TOC entry 5009 (class 0 OID 17282)
-- Dependencies: 239
-- Data for Name: evento; Type: TABLE DATA; Schema: safaeventos; Owner: -
--

INSERT INTO safaeventos.evento VALUES (1, 'Feria de Ciencias', 'Gran feria científica donde los estudiantes presentarán proyectos de física, química, biología y robótica. Habrá demostraciones en vivo, experimentos interactivos y stands explicativos. Se premiarán los mejores trabajos y se realizará una exhibición final. Fecha del evento: 6 de diciembre de 2025.', '2025-12-06 10:00:00', 'Gimnasio', 'https://images.pexels.com/photos/256369/pexels-photo-256369.jpeg', 0.00, 1, 1);
INSERT INTO safaeventos.evento VALUES (2, 'Festival Cultural Escolar', 'Celebración artística con música en vivo, danza tradicional, exposiciones de pintura, artesanías y gastronomía multicultural. El objetivo es promover la expresión artística y la convivencia escolar. Fecha: 7 de diciembre de 2025.', '2025-12-07 17:00:00', 'Patio Central', 'https://images.pexels.com/photos/1763075/pexels-photo-1763075.jpeg', 0.00, 3, 1);
INSERT INTO safaeventos.evento VALUES (6, 'Maratón Matemático', 'Competencia de resolución de problemas matemáticos, acertijos lógicos, cálculos rápidos y pruebas de razonamiento. Participan equipos de 3 alumnos. Fecha: 11 de diciembre de 2025.', '2025-12-11 09:00:00', 'Edificio B - Aula 12', 'https://images.pexels.com/photos/542883/pexels-photo-542883.jpeg', 0.00, 1, 3);
INSERT INTO safaeventos.evento VALUES (7, 'Taller de Pintura Creativa', 'Sesión libre de pintura donde los alumnos podrán crear obras propias con materiales proporcionados por el centro. Al final se realizará una mini exposición de trabajos. Fecha: 12 de diciembre de 2025.', '2025-12-12 16:00:00', 'Sala de Arte', 'https://images.pexels.com/photos/207067/pexels-photo-207067.jpeg', 2.00, 3, 4);
INSERT INTO safaeventos.evento VALUES (8, 'Competencia de Robótica Escolar', 'Torneo de robótica educativa con robots LEGO Mindstorms y Arduino. Se evaluará velocidad, precisión y autonomía. Participan equipos de varios niveles educativos. Fecha: 13 de diciembre de 2025.', '2025-12-13 10:00:00', 'Laboratorio de Tecnología', 'https://images.pexels.com/photos/2599244/pexels-photo-2599244.jpeg', 0.00, 4, 4);
INSERT INTO safaeventos.evento VALUES (9, 'Obra de Teatro: Sueños en Escena', 'Presentación teatral escrita y actuada por estudiantes del grupo de teatro escolar. La obra mezcla comedia, drama y música original. Habrá dos funciones. Fecha: 14 de diciembre de 2025.', '2025-12-14 19:00:00', 'Teatro del Centro', 'https://images.pexels.com/photos/713149/pexels-photo-713149.jpeg', 0.00, 3, 5);
INSERT INTO safaeventos.evento VALUES (12, 'Exposición STEAM: Ciencia, Tecnología y Arte', 'Muestra interdisciplinar donde los estudiantes presentarán proyectos que combinan ciencia, tecnología, ingeniería, arte y matemáticas. La exposición incluye maquetas impresas en 3D, prototipos electrónicos, instalaciones artísticas interactivas y presentaciones multimedia. Fecha: 16 de diciembre de 2025.', '2025-12-16 17:00:00', 'Hall Principal', 'https://images.pexels.com/photos/256417/pexels-photo-256417.jpeg', 0.00, 4, 2);
INSERT INTO safaeventos.evento VALUES (14, 'Competencia de Ajedrez Escolar', 'Torneo suizo de ajedrez dirigido a alumnos de todas las edades. Incluye charlas introductorias, análisis de partidas famosas y enfrentamientos eliminatorios. Se entregarán medallas y diplomas. Fecha: 18 de diciembre de 2025.', '2025-12-18 09:00:00', 'Biblioteca', 'https://images.pexels.com/photos/247373/pexels-photo-247373.jpeg', 0.00, 2, 4);
INSERT INTO safaeventos.evento VALUES (16, 'Feria del Libro Escolar', 'Evento cultural dedicado a la lectura con stands de editoriales, autores invitados, cuentacuentos, trueque de libros usados, actividades para niños y concursos literarios. Se promoverá el hábito lector y la creatividad escrita. Fecha: 19 de diciembre de 2025.', '2025-12-19 10:00:00', 'Patio Cubierto', 'https://images.pexels.com/photos/159711/books-bookstore-book-reading-159711.jpeg', 0.00, 3, 1);
INSERT INTO safaeventos.evento VALUES (19, 'Feria Gastronómica Estudiantil', 'Exposición y degustación de platos preparados por estudiantes y familias, con gastronomía internacional, concursos culinarios, sorteos y premios a la mejor presentación. Fecha: 20 de diciembre de 2025.', '2025-12-20 18:00:00', 'Patio Principal', 'https://images.pexels.com/photos/70497/pexels-photo-70497.jpeg', 2.00, 3, 4);
INSERT INTO safaeventos.evento VALUES (5, 'Evento modificado', 'Profesionales invitados hablarán sobre sus experiencias en medicina, ingeniería, arte, educación, informática y oficios especializados. Habrá ronda de preguntas y entrega de guías informativas. Fecha: 10 de diciembre de 2025.', '2025-12-10 12:00:00', 'Aula Magna', 'https://images.pexels.com/photos/1181395/pexels-photo-1181395.jpeg', 5.00, 1, 3);
INSERT INTO safaeventos.evento VALUES (10, 'Rally del Conocimiento', 'Concurso por equipos donde los estudiantes deberán resolver pruebas de historia, ciencia, arte, tecnología y cultura general en el menor tiempo posible. Fecha: 15 de diciembre de 2025.', '2025-12-15 09:00:00', 'Biblioteca', 'https://images.pexels.com/photos/3184644/pexels-photo-3184644.jpeg', 0.00, 1, 5);
INSERT INTO safaeventos.evento VALUES (3, 'Evento modificado', 'Competición deportiva donde los distintos cursos se enfrentarán en un torneo eliminatorio. Habrá árbitros oficiales, marcador digital y entrega de medallas. Se fomenta el trabajo en equipo y la deportividad. Fecha: 8 de diciembre de 2025.', '2025-12-08 15:00:00', 'Campo Deportivo', 'https://images.pexels.com/photos/399187/pexels-photo-399187.jpeg', 5.00, 2, 2);
INSERT INTO safaeventos.evento VALUES (13, 'Evento modificado', 'Taller práctico para aprender técnicas de composición, manejo de luz, encuadre y edición digital. Los asistentes deben traer su móvil o cámara. Se realizará una sesión fotográfica en exteriores y una mini exposición de resultados. Fecha: 17 de diciembre de 2025.', '2025-12-17 16:00:00', 'Sala Multimedia', 'https://images.pexels.com/photos/212372/pexels-photo-212372.jpeg', 5.00, 3, 3);
INSERT INTO safaeventos.evento VALUES (17, 'Concurso de Fotografía Artística', 'Concurso donde los alumnos presentarán fotografías originales relacionadas con naturaleza, arquitectura y vida escolar. Las mejores obras serán expuestas en la entrada del centro. Habrá jurado profesional y premios especiales. Fecha: 19 de diciembre de 2025.', '2025-12-19 17:00:00', 'Sala de Arte', 'https://images.pexels.com/photos/167832/pexels-photo-167832.jpeg', 5.00, 3, 2);
INSERT INTO safaeventos.evento VALUES (11, 'Competencia de Debate Político', 'Torneo interno de debate donde los estudiantes participarán en rondas clasificatorias argumentando sobre temas actuales relacionados con educación, ciencia, sociedad y tecnología. El evento fomenta el pensamiento crítico, la capacidad de expresión y el análisis profundo. Se contará con jurado compuesto por profesores de lengua y filosofía. Fecha: 16 de diciembre de 2025.', '2025-12-16 10:00:00', 'Aula Magna', 'https://images.pexels.com/photos/3182751/pexels-photo-3182751.jpeg', 0.00, 1, 1);
INSERT INTO safaeventos.evento VALUES (20, 'Velada de Talentos Estudiantiles', 'Gala nocturna donde los alumnos presentan números musicales, de danza, magia, poesía, humor y actuaciones libres. El evento se divide en categorías y culmina con una presentación grupal final. Fecha: 21 de diciembre de 2025.', '2025-12-21 19:00:00', 'Auditorio', 'https://images.pexels.com/photos/210922/pexels-photo-210922.jpeg', 0.00, 3, 5);
INSERT INTO safaeventos.evento VALUES (22, 'Charla de introducción a Spring Boot', 'Sesión para aprender los conceptos básicos de Spring Boot.', '2025-12-05 18:30:00', 'Aula de Informática 1, Colegio SAFA', 'https://culturadigitalcomodoro.com.ar/wp-content/uploads/2025/08/Actividad-IA-27ago.jpg', 0.00, 0, 1);
INSERT INTO safaeventos.evento VALUES (23, 'Charla de introducción a Spring Boot', 'Sesión para aprender los conceptos básicos de Spring Boot.', '2025-12-05 18:30:00', 'Aula de Informática 1, Colegio SAFA', 'https://culturadigitalcomodoro.com.ar/wp-content/uploads/2025/08/Actividad-IA-27ago.jpg', 0.00, 0, 1);
INSERT INTO safaeventos.evento VALUES (24, 'Charla de introducción a Spring Boot', 'Sesión para aprender los conceptos básicos de Spring Boot.', '2025-12-05 18:30:00', 'Aula de Informática 1, Colegio SAFA', 'https://culturadigitalcomodoro.com.ar/wp-content/uploads/2025/08/Actividad-IA-27ago.jpg', 0.00, 0, 1);
INSERT INTO safaeventos.evento VALUES (25, 'Charla de introducción a Spring Boot', 'Sesión para aprender los conceptos básicos de Spring Boot.', '2025-12-05 18:30:00', 'Aula de Informática 1, Colegio SAFA', 'https://culturadigitalcomodoro.com.ar/wp-content/uploads/2025/08/Actividad-IA-27ago.jpg', 0.00, 0, 1);
INSERT INTO safaeventos.evento VALUES (26, 'Charla de introducción a Spring Boot', 'Sesión para aprender los conceptos básicos de Spring Boot.', '2025-12-05 18:30:00', 'Aula de Informática 1, Colegio SAFA', 'https://culturadigitalcomodoro.com.ar/wp-content/uploads/2025/08/Actividad-IA-27ago.jpg', 0.00, 0, 1);
INSERT INTO safaeventos.evento VALUES (27, 'Charla de introducción a Spring Boot', 'Sesión para aprender los conceptos básicos de Spring Boot.', '2025-12-05 18:30:00', 'Aula de Informática 1, Colegio SAFA', 'https://culturadigitalcomodoro.com.ar/wp-content/uploads/2025/08/Actividad-IA-27ago.jpg', 0.00, 0, 1);
INSERT INTO safaeventos.evento VALUES (28, 'Charla de introducción a Spring Boot', 'Sesión para aprender los conceptos básicos de Spring Boot.', '2025-12-05 18:30:00', 'Aula de Informática 1, Colegio SAFA', 'https://culturadigitalcomodoro.com.ar/wp-content/uploads/2025/08/Actividad-IA-27ago.jpg', 0.00, 0, 1);
INSERT INTO safaeventos.evento VALUES (29, 'Charla de introducción a Spring Boot', 'Sesión para aprender los conceptos básicos de Spring Boot.', '2025-12-05 18:30:00', 'Aula de Informática 1, Colegio SAFA', 'https://culturadigitalcomodoro.com.ar/wp-content/uploads/2025/08/Actividad-IA-27ago.jpg', 0.00, 0, 1);
INSERT INTO safaeventos.evento VALUES (30, 'Charla de introducción a Spring Boot', 'Sesión para aprender los conceptos básicos de Spring Boot.', '2025-12-05 18:30:00', 'Aula de Informática 1, Colegio SAFA', 'https://culturadigitalcomodoro.com.ar/wp-content/uploads/2025/08/Actividad-IA-27ago.jpg', 0.00, 0, 1);
INSERT INTO safaeventos.evento VALUES (4, 'Concierto Navideño Escolar', 'Presentación musical preparada por el coro y la orquesta escolar. Incluye villancicos, piezas modernas y actuaciones individuales. Ideal para comenzar el ambiente navideño. Fecha: 9 de diciembre de 2025.', '2025-12-09 18:00:00', 'Auditorio', 'https://images.pexels.com/photos/1667857/pexels-photo-1667857.jpeg', 5.00, 3, 2);
INSERT INTO safaeventos.evento VALUES (33, 'Jornada de Tecnología y Robótica', 'Evento educativo dirigido al alumnado donde se realizarán talleres de programación, robótica educativa y uso responsable de la tecnología.', '2025-12-20 18:30:00', 'Aula de Informática 1, Colegio SAFA', 'https://images.unsplash.com/photo-1581092160562-40aa08e78837', 0.00, 4, 1);
INSERT INTO safaeventos.evento VALUES (35, 'Charla de robótica', 'Evento educativo dirigido al alumnado donde se realizarán talleres de programación, robótica educativa y uso responsable de la tecnología.', '2025-12-20 18:30:00', 'Aula de Informática 1, Colegio SAFA', 'https://images.unsplash.com/photo-1581092160562-40aa08e78837', 0.00, 4, 1);
INSERT INTO safaeventos.evento VALUES (40, 'Charla de Historia', 'Charla sobre la historia de la Edad Media.', '2025-12-19 10:30:00', 'Clase de historia', 'https://www.acoruna.uned.es/archivos_publicos/qnews/7783/halconbodaedadmedialarge_med.jpg', 0.00, 1, 1);


--
-- TOC entry 5017 (class 0 OID 17353)
-- Dependencies: 247
-- Data for Name: foto_evento; Type: TABLE DATA; Schema: safaeventos; Owner: -
--

INSERT INTO safaeventos.foto_evento VALUES (1, 12, 5, 'https://imusic.b-cdn.net/images/item/original/702/0050087245702.jpg?disney-karaoke-series-disney-junior-theme-var-2016-disney-karaoke-series-disney-junior-theme-var-cd&class=scaled&v=1586724360', '2025-11-27 19:27:24.011969');
INSERT INTO safaeventos.foto_evento VALUES (2, 12, 5, 'https://imusic.b-cdn.net/images/item/original/702/0050087245702.jpg?disney-karaoke-series-disney-junior-theme-var-2016-disney-karaoke-series-disney-junior-theme-var-cd&class=scaled&v=1586724360', '2025-11-27 19:30:25.067126');
INSERT INTO safaeventos.foto_evento VALUES (3, 12, 5, 'https://imusic.b-cdn.net/images/item/original/702/0050087245702.jpg?disney-karaoke-series-disney-junior-theme-var-2016-disney-karaoke-series-disney-junior-theme-var-cd&class=scaled&v=1586724360', '2025-11-27 19:30:40.211087');
INSERT INTO safaeventos.foto_evento VALUES (4, 12, 5, 'https://imusic.b-cdn.net/images/item/original/702/0050087245702.jpg?disney-karaoke-series-disney-junior-theme-var-2016-disney-karaoke-series-disney-junior-theme-var-cd&class=scaled&v=1586724360', '2025-11-27 19:32:02.69919');
INSERT INTO safaeventos.foto_evento VALUES (5, 12, 5, 'https://imusic.b-cdn.net/images/item/original/702/0050087245702.jpg?disney-karaoke-series-disney-junior-theme-var-2016-disney-karaoke-series-disney-junior-theme-var-cd&class=scaled&v=1586724360', '2025-11-27 19:32:15.716371');
INSERT INTO safaeventos.foto_evento VALUES (6, 12, 5, 'https://imusic.b-cdn.net/images/item/original/702/0050087245702.jpg?disney-karaoke-series-disney-junior-theme-var-2016-disney-karaoke-series-disney-junior-theme-var-cd&class=scaled&v=1586724360', '2025-11-27 19:33:24.197254');
INSERT INTO safaeventos.foto_evento VALUES (7, 12, 5, 'https://imusic.b-cdn.net/images/item/original/702/0050087245702.jpg?disney-karaoke-series-disney-junior-theme-var-2016-disney-karaoke-series-disney-junior-theme-var-cd&class=scaled&v=1586724360', '2025-11-27 19:46:40.700216');
INSERT INTO safaeventos.foto_evento VALUES (8, 2, 1, 'https://siempre-en-fiesta.com/images/feria.jpg', '2025-12-15 18:36:10.206161');
INSERT INTO safaeventos.foto_evento VALUES (9, 13, 5, 'https://siempre-en-fiesta.com/images/feria.jpg', '2025-12-15 19:03:39.570946');


--
-- TOC entry 5011 (class 0 OID 17298)
-- Dependencies: 241
-- Data for Name: inscripcion; Type: TABLE DATA; Schema: safaeventos; Owner: -
--

INSERT INTO safaeventos.inscripcion VALUES (21, 6, 1, false, 0, false);
INSERT INTO safaeventos.inscripcion VALUES (22, 7, 1, false, 0, false);
INSERT INTO safaeventos.inscripcion VALUES (23, 8, 1, false, 0, false);
INSERT INTO safaeventos.inscripcion VALUES (24, 9, 2, false, 0, false);
INSERT INTO safaeventos.inscripcion VALUES (25, 10, 2, false, 0, false);
INSERT INTO safaeventos.inscripcion VALUES (26, 11, 3, false, 0, false);
INSERT INTO safaeventos.inscripcion VALUES (27, 12, 3, false, 0, false);
INSERT INTO safaeventos.inscripcion VALUES (28, 13, 3, false, 0, false);
INSERT INTO safaeventos.inscripcion VALUES (29, 14, 4, false, 0, false);
INSERT INTO safaeventos.inscripcion VALUES (30, 15, 4, false, 0, false);
INSERT INTO safaeventos.inscripcion VALUES (31, 16, 5, false, 0, false);
INSERT INTO safaeventos.inscripcion VALUES (32, 17, 5, false, 0, false);
INSERT INTO safaeventos.inscripcion VALUES (33, 18, 7, true, 1, true);
INSERT INTO safaeventos.inscripcion VALUES (34, 19, 7, true, 2, true);
INSERT INTO safaeventos.inscripcion VALUES (37, 22, 19, true, 1, true);
INSERT INTO safaeventos.inscripcion VALUES (38, 23, 19, true, 1, true);
INSERT INTO safaeventos.inscripcion VALUES (39, 24, 10, false, 0, false);
INSERT INTO safaeventos.inscripcion VALUES (40, 25, 10, false, 0, false);
INSERT INTO safaeventos.inscripcion VALUES (41, 1, 11, false, 1, false);
INSERT INTO safaeventos.inscripcion VALUES (42, 2, 11, false, 1, false);
INSERT INTO safaeventos.inscripcion VALUES (43, 3, 12, false, 1, false);
INSERT INTO safaeventos.inscripcion VALUES (44, 4, 12, false, 1, false);
INSERT INTO safaeventos.inscripcion VALUES (45, 5, 12, false, 1, false);
INSERT INTO safaeventos.inscripcion VALUES (46, 6, 13, false, 1, false);
INSERT INTO safaeventos.inscripcion VALUES (47, 7, 13, false, 1, false);
INSERT INTO safaeventos.inscripcion VALUES (48, 8, 14, false, 1, false);
INSERT INTO safaeventos.inscripcion VALUES (49, 9, 14, false, 1, false);
INSERT INTO safaeventos.inscripcion VALUES (50, 10, 14, false, 1, false);
INSERT INTO safaeventos.inscripcion VALUES (54, 14, 16, false, 1, false);
INSERT INTO safaeventos.inscripcion VALUES (55, 15, 16, false, 1, false);
INSERT INTO safaeventos.inscripcion VALUES (56, 16, 17, false, 1, false);
INSERT INTO safaeventos.inscripcion VALUES (57, 17, 17, false, 1, false);
INSERT INTO safaeventos.inscripcion VALUES (58, 18, 17, false, 1, false);
INSERT INTO safaeventos.inscripcion VALUES (59, 19, 19, true, 3, true);
INSERT INTO safaeventos.inscripcion VALUES (60, 20, 19, true, 1, true);
INSERT INTO safaeventos.inscripcion VALUES (61, 21, 20, false, 1, false);
INSERT INTO safaeventos.inscripcion VALUES (62, 22, 20, false, 1, false);
INSERT INTO safaeventos.inscripcion VALUES (63, 10, 12, false, 3, false);
INSERT INTO safaeventos.inscripcion VALUES (64, 8, 12, true, 3, false);
INSERT INTO safaeventos.inscripcion VALUES (65, 3, 5, false, 3, true);
INSERT INTO safaeventos.inscripcion VALUES (66, 11, 13, false, 3, true);


--
-- TOC entry 5013 (class 0 OID 17319)
-- Dependencies: 243
-- Data for Name: me_interesa; Type: TABLE DATA; Schema: safaeventos; Owner: -
--

INSERT INTO safaeventos.me_interesa VALUES (1, 1, 3, '2025-11-24');
INSERT INTO safaeventos.me_interesa VALUES (2, 2, 5, '2025-11-24');
INSERT INTO safaeventos.me_interesa VALUES (3, 3, 7, '2025-11-24');
INSERT INTO safaeventos.me_interesa VALUES (4, 4, 1, '2025-11-24');
INSERT INTO safaeventos.me_interesa VALUES (5, 5, 2, '2025-11-24');
INSERT INTO safaeventos.me_interesa VALUES (6, 6, 8, '2025-11-24');
INSERT INTO safaeventos.me_interesa VALUES (7, 7, 9, '2025-11-24');
INSERT INTO safaeventos.me_interesa VALUES (8, 8, 4, '2025-11-24');
INSERT INTO safaeventos.me_interesa VALUES (9, 9, 6, '2025-11-24');
INSERT INTO safaeventos.me_interesa VALUES (10, 10, 10, '2025-11-24');
INSERT INTO safaeventos.me_interesa VALUES (11, 11, 12, '2025-11-24');
INSERT INTO safaeventos.me_interesa VALUES (12, 12, 11, '2025-11-24');
INSERT INTO safaeventos.me_interesa VALUES (13, 13, 13, '2025-11-24');
INSERT INTO safaeventos.me_interesa VALUES (14, 14, 14, '2025-11-24');
INSERT INTO safaeventos.me_interesa VALUES (16, 16, 16, '2025-11-24');
INSERT INTO safaeventos.me_interesa VALUES (18, 18, 17, '2025-11-24');
INSERT INTO safaeventos.me_interesa VALUES (19, 19, 19, '2025-11-24');
INSERT INTO safaeventos.me_interesa VALUES (20, 20, 20, '2025-11-24');


--
-- TOC entry 5015 (class 0 OID 17338)
-- Dependencies: 245
-- Data for Name: notificacion; Type: TABLE DATA; Schema: safaeventos; Owner: -
--

INSERT INTO safaeventos.notificacion VALUES (1, 6, 'Tu inscripción en la Feria de Ciencias ha sido confirmada.', '2025-11-24', false);
INSERT INTO safaeventos.notificacion VALUES (2, 7, 'Recordatorio: La Feria de Ciencias comienza mañana a las 10:00.', '2025-11-24', true);
INSERT INTO safaeventos.notificacion VALUES (3, 8, 'Nuevas fotos disponibles del Festival Cultural.', '2025-11-24', false);
INSERT INTO safaeventos.notificacion VALUES (4, 9, 'Has marcado interés en el Festival Cultural. ¡No olvides asistir!', '2025-11-24', true);
INSERT INTO safaeventos.notificacion VALUES (5, 10, 'Tu equipo jugará en el Torneo de Fútbol este lunes.', '2025-11-24', false);
INSERT INTO safaeventos.notificacion VALUES (6, 11, 'Actualización: Se ha añadido una nueva galería al Torneo de Fútbol.', '2025-11-24', false);
INSERT INTO safaeventos.notificacion VALUES (7, 12, 'El Concierto Navideño tendrá ensayo general mañana.', '2025-11-24', true);
INSERT INTO safaeventos.notificacion VALUES (8, 13, 'Nueva foto subida al Concierto Navideño.', '2025-11-24', false);
INSERT INTO safaeventos.notificacion VALUES (9, 14, 'Gracias por asistir a la Charla Vocacional.', '2025-11-24', false);
INSERT INTO safaeventos.notificacion VALUES (10, 15, 'Se han añadido diapositivas nuevas de la Charla Vocacional.', '2025-11-24', true);
INSERT INTO safaeventos.notificacion VALUES (11, 16, 'Tu equipo ha sido registrado para el Maratón Matemático.', '2025-11-24', false);
INSERT INTO safaeventos.notificacion VALUES (12, 17, 'Recordatorio: mañana es el Maratón Matemático.', '2025-11-24', true);
INSERT INTO safaeventos.notificacion VALUES (13, 18, 'Tu participación en el Taller de Pintura fue registrada correctamente.', '2025-11-24', false);
INSERT INTO safaeventos.notificacion VALUES (14, 19, 'La galería del Taller de Pintura ya está disponible.', '2025-11-24', true);
INSERT INTO safaeventos.notificacion VALUES (15, 20, '¡Tu robot pasó a la siguiente fase en la Competencia de Robótica!', '2025-11-24', false);
INSERT INTO safaeventos.notificacion VALUES (16, 21, 'El evento de Robótica tiene nuevas fotos subidas por alumnos.', '2025-11-24', true);
INSERT INTO safaeventos.notificacion VALUES (17, 22, 'La obra de teatro ya está disponible en video para los participantes.', '2025-11-24', false);
INSERT INTO safaeventos.notificacion VALUES (18, 23, 'Has recibido un reconocimiento por tu actuación en Sueños en Escena.', '2025-11-24', true);


--
-- TOC entry 5007 (class 0 OID 17268)
-- Dependencies: 237
-- Data for Name: perfil; Type: TABLE DATA; Schema: safaeventos; Owner: -
--

INSERT INTO safaeventos.perfil VALUES (51, 1, 'Juan', 'Martínez López', NULL, '2025-11-24', 'https://cdn-icons-png.flaticon.com/512/4140/4140030.png');
INSERT INTO safaeventos.perfil VALUES (52, 2, 'Ana', 'Gómez Ruiz', NULL, '2025-11-24', 'https://cdn-icons-png.flaticon.com/512/4140/4140048.png');
INSERT INTO safaeventos.perfil VALUES (53, 3, 'Pablo', 'Santos Peña', NULL, '2025-11-24', 'https://cdn-icons-png.flaticon.com/512/4140/4140047.png');
INSERT INTO safaeventos.perfil VALUES (54, 4, 'Carmen', 'Rodríguez Soto', NULL, '2025-11-24', 'https://cdn-icons-png.flaticon.com/512/4140/4140051.png');
INSERT INTO safaeventos.perfil VALUES (55, 5, 'Luis', 'Navarro Díaz', NULL, '2025-11-24', 'https://cdn-icons-png.flaticon.com/512/4140/4140035.png');
INSERT INTO safaeventos.perfil VALUES (56, 6, 'Alba', 'García Torres', 1, '2025-11-24', 'https://cdn-icons-png.flaticon.com/512/4140/4140037.png');
INSERT INTO safaeventos.perfil VALUES (57, 7, 'Mario', 'López García', 2, '2025-11-24', 'https://cdn-icons-png.flaticon.com/512/4140/4140027.png');
INSERT INTO safaeventos.perfil VALUES (58, 8, 'Lucía', 'Serrano Pérez', 3, '2025-11-24', 'https://cdn-icons-png.flaticon.com/512/4140/4140046.png');
INSERT INTO safaeventos.perfil VALUES (59, 9, 'Daniel', 'Hernández Ruiz', 4, '2025-11-24', 'https://cdn-icons-png.flaticon.com/512/4140/4140043.png');
INSERT INTO safaeventos.perfil VALUES (60, 10, 'Sara', 'Romero Vargas', 5, '2025-11-24', 'https://cdn-icons-png.flaticon.com/512/4140/4140031.png');
INSERT INTO safaeventos.perfil VALUES (61, 11, 'Irene', 'Campos Soto', 6, '2025-11-24', 'https://cdn-icons-png.flaticon.com/512/4140/4140050.png');
INSERT INTO safaeventos.perfil VALUES (62, 12, 'Óscar', 'Gómez Rivas', 7, '2025-11-24', 'https://cdn-icons-png.flaticon.com/512/4140/4140039.png');
INSERT INTO safaeventos.perfil VALUES (63, 13, 'Javier', 'Moreno Reyes', 8, '2025-11-24', 'https://cdn-icons-png.flaticon.com/512/4140/4140042.png');
INSERT INTO safaeventos.perfil VALUES (64, 14, 'Clara', 'Santos Jiménez', 9, '2025-11-24', 'https://cdn-icons-png.flaticon.com/512/4140/4140053.png');
INSERT INTO safaeventos.perfil VALUES (65, 15, 'Diego', 'Vargas Torres', 10, '2025-11-24', 'https://cdn-icons-png.flaticon.com/512/4140/4140022.png');
INSERT INTO safaeventos.perfil VALUES (66, 16, 'Nuria', 'Flores Martín', 11, '2025-11-24', 'https://cdn-icons-png.flaticon.com/512/4140/4140045.png');
INSERT INTO safaeventos.perfil VALUES (67, 17, 'Eva', 'Cabrera León', 12, '2025-11-24', 'https://cdn-icons-png.flaticon.com/512/4140/4140033.png');
INSERT INTO safaeventos.perfil VALUES (68, 18, 'Sergio', 'Reina López', 13, '2025-11-24', 'https://cdn-icons-png.flaticon.com/512/4140/4140038.png');
INSERT INTO safaeventos.perfil VALUES (69, 19, 'Helena', 'Fuentes Gil', 14, '2025-11-24', 'https://cdn-icons-png.flaticon.com/512/4140/4140061.png');
INSERT INTO safaeventos.perfil VALUES (70, 20, 'Adrián', 'Navas Ruiz', 15, '2025-11-24', 'https://cdn-icons-png.flaticon.com/512/4140/4140055.png');
INSERT INTO safaeventos.perfil VALUES (71, 21, 'Paula', 'Cano Peña', 16, '2025-11-24', 'https://cdn-icons-png.flaticon.com/512/4140/4140061.png');
INSERT INTO safaeventos.perfil VALUES (72, 22, 'Alfonso', 'Mena Díaz', 1, '2025-11-24', 'https://cdn-icons-png.flaticon.com/512/4140/4140030.png');
INSERT INTO safaeventos.perfil VALUES (73, 23, 'Teresa', 'Prado Muñoz', 4, '2025-11-24', 'https://cdn-icons-png.flaticon.com/512/4140/4140037.png');
INSERT INTO safaeventos.perfil VALUES (74, 24, 'Rosa', 'Vega Ríos', 9, '2025-11-24', 'https://cdn-icons-png.flaticon.com/512/4140/4140048.png');
INSERT INTO safaeventos.perfil VALUES (75, 25, 'Marco', 'Linares Cruz', 13, '2025-11-24', 'https://cdn-icons-png.flaticon.com/512/4140/4140047.png');
INSERT INTO safaeventos.perfil VALUES (76, 26, 'Maria', 'Lorenzo Lorenzo', 0, '2025-11-24', NULL);
INSERT INTO safaeventos.perfil VALUES (77, 34, 'Laura', 'Blanco Gutierrez', 13, '2025-12-13', NULL);
INSERT INTO safaeventos.perfil VALUES (78, 35, 'Laura', 'Blanco Gutierrez', 13, '2025-12-13', NULL);
INSERT INTO safaeventos.perfil VALUES (79, 36, 'Laura', 'Blanco Gutierrez', 13, '2025-12-13', NULL);
INSERT INTO safaeventos.perfil VALUES (80, 37, 'Laura', 'Blanco Gutierrez', 13, '2025-12-13', NULL);
INSERT INTO safaeventos.perfil VALUES (81, 38, 'Laura', 'Blanco Gutierrez', 13, '2025-12-13', NULL);


--
-- TOC entry 5005 (class 0 OID 17258)
-- Dependencies: 235
-- Data for Name: usuario; Type: TABLE DATA; Schema: safaeventos; Owner: -
--

INSERT INTO safaeventos.usuario VALUES (6, 'alumno1@safareyes.es', 'pass1', 1, true);
INSERT INTO safaeventos.usuario VALUES (7, 'alumno2@safareyes.es', 'pass2', 1, true);
INSERT INTO safaeventos.usuario VALUES (8, 'alumno3@safareyes.es', 'pass3', 1, true);
INSERT INTO safaeventos.usuario VALUES (9, 'alumno4@safareyes.es', 'pass4', 1, true);
INSERT INTO safaeventos.usuario VALUES (10, 'alumno5@safareyes.es', 'pass5', 1, true);
INSERT INTO safaeventos.usuario VALUES (11, 'alumno6@safareyes.es', 'pass6', 1, true);
INSERT INTO safaeventos.usuario VALUES (12, 'alumno7@safareyes.es', 'pass7', 1, true);
INSERT INTO safaeventos.usuario VALUES (13, 'alumno8@safareyes.es', 'pass8', 1, true);
INSERT INTO safaeventos.usuario VALUES (14, 'alumno9@safareyes.es', 'pass9', 1, true);
INSERT INTO safaeventos.usuario VALUES (15, 'alumno10@safareyes.es', 'pass10', 1, true);
INSERT INTO safaeventos.usuario VALUES (16, 'alumno11@safareyes.es', 'pass11', 1, true);
INSERT INTO safaeventos.usuario VALUES (17, 'alumno12@safareyes.es', 'pass12', 1, true);
INSERT INTO safaeventos.usuario VALUES (18, 'alumno13@safareyes.es', 'pass13', 1, true);
INSERT INTO safaeventos.usuario VALUES (19, 'alumno14@safareyes.es', 'pass14', 1, true);
INSERT INTO safaeventos.usuario VALUES (20, 'alumno15@safareyes.es', 'pass15', 1, true);
INSERT INTO safaeventos.usuario VALUES (21, 'alumno16@safareyes.es', 'pass16', 1, true);
INSERT INTO safaeventos.usuario VALUES (22, 'alumno17@safareyes.es', 'pass17', 1, true);
INSERT INTO safaeventos.usuario VALUES (23, 'alumno18@safareyes.es', 'pass18', 1, true);
INSERT INTO safaeventos.usuario VALUES (24, 'alumno19@safareyes.es', 'pass19', 1, true);
INSERT INTO safaeventos.usuario VALUES (25, 'alumno20@safareyes.es', 'pass20', 1, true);
INSERT INTO safaeventos.usuario VALUES (1, 'profe1@fundacionsafa.es', 'pass1', 0, true);
INSERT INTO safaeventos.usuario VALUES (2, 'profe2@fundacionsafa.es', 'pass2', 0, true);
INSERT INTO safaeventos.usuario VALUES (3, 'profe3@fundacionsafa.es', 'pass3', 0, true);
INSERT INTO safaeventos.usuario VALUES (4, 'profe4@fundacionsafa.es', 'pass4', 0, true);
INSERT INTO safaeventos.usuario VALUES (5, 'profe5@fundacionsafa.es', 'pass5', 0, true);
INSERT INTO safaeventos.usuario VALUES (26, 'maria@safareyes.es', '123456', 0, true);
INSERT INTO safaeventos.usuario VALUES (34, 'laura@safareyes.es', '123123', 0, true);
INSERT INTO safaeventos.usuario VALUES (35, 'laurablanco@safareyes.es', '123123', 0, true);
INSERT INTO safaeventos.usuario VALUES (36, 'lblancogutierrez@safareyes.es', '123123', 0, true);
INSERT INTO safaeventos.usuario VALUES (37, 'blancogutierrez@safareyes.es', '123123', 0, true);
INSERT INTO safaeventos.usuario VALUES (38, 'blanco@safareyes.es', '123123', 0, true);


--
-- TOC entry 5035 (class 0 OID 0)
-- Dependencies: 248
-- Name: comentario_evento_id_seq; Type: SEQUENCE SET; Schema: safaeventos; Owner: -
--

SELECT pg_catalog.setval('safaeventos.comentario_evento_id_seq', 21, true);


--
-- TOC entry 5036 (class 0 OID 0)
-- Dependencies: 238
-- Name: evento_id_seq; Type: SEQUENCE SET; Schema: safaeventos; Owner: -
--

SELECT pg_catalog.setval('safaeventos.evento_id_seq', 41, true);


--
-- TOC entry 5037 (class 0 OID 0)
-- Dependencies: 246
-- Name: foto_evento_id_seq; Type: SEQUENCE SET; Schema: safaeventos; Owner: -
--

SELECT pg_catalog.setval('safaeventos.foto_evento_id_seq', 9, true);


--
-- TOC entry 5038 (class 0 OID 0)
-- Dependencies: 240
-- Name: inscripcion_id_seq; Type: SEQUENCE SET; Schema: safaeventos; Owner: -
--

SELECT pg_catalog.setval('safaeventos.inscripcion_id_seq', 66, true);


--
-- TOC entry 5039 (class 0 OID 0)
-- Dependencies: 242
-- Name: me_interesa_id_seq; Type: SEQUENCE SET; Schema: safaeventos; Owner: -
--

SELECT pg_catalog.setval('safaeventos.me_interesa_id_seq', 20, true);


--
-- TOC entry 5040 (class 0 OID 0)
-- Dependencies: 244
-- Name: notificacion_id_seq; Type: SEQUENCE SET; Schema: safaeventos; Owner: -
--

SELECT pg_catalog.setval('safaeventos.notificacion_id_seq', 18, true);


--
-- TOC entry 5041 (class 0 OID 0)
-- Dependencies: 236
-- Name: perfil_id_seq; Type: SEQUENCE SET; Schema: safaeventos; Owner: -
--

SELECT pg_catalog.setval('safaeventos.perfil_id_seq', 81, true);


--
-- TOC entry 5042 (class 0 OID 0)
-- Dependencies: 234
-- Name: usuario_id_seq; Type: SEQUENCE SET; Schema: safaeventos; Owner: -
--

SELECT pg_catalog.setval('safaeventos.usuario_id_seq', 38, true);


--
-- TOC entry 4847 (class 2606 OID 17379)
-- Name: comentario_evento comentario_evento_pkey; Type: CONSTRAINT; Schema: safaeventos; Owner: -
--

ALTER TABLE ONLY safaeventos.comentario_evento
    ADD CONSTRAINT comentario_evento_pkey PRIMARY KEY (id);


--
-- TOC entry 4833 (class 2606 OID 17291)
-- Name: evento evento_pkey; Type: CONSTRAINT; Schema: safaeventos; Owner: -
--

ALTER TABLE ONLY safaeventos.evento
    ADD CONSTRAINT evento_pkey PRIMARY KEY (id);


--
-- TOC entry 4845 (class 2606 OID 17359)
-- Name: foto_evento foto_evento_pkey; Type: CONSTRAINT; Schema: safaeventos; Owner: -
--

ALTER TABLE ONLY safaeventos.foto_evento
    ADD CONSTRAINT foto_evento_pkey PRIMARY KEY (id);


--
-- TOC entry 4835 (class 2606 OID 17307)
-- Name: inscripcion inscripcion_id_usuario_id_evento_key; Type: CONSTRAINT; Schema: safaeventos; Owner: -
--

ALTER TABLE ONLY safaeventos.inscripcion
    ADD CONSTRAINT inscripcion_id_usuario_id_evento_key UNIQUE (id_usuario, id_evento);


--
-- TOC entry 4837 (class 2606 OID 17305)
-- Name: inscripcion inscripcion_pkey; Type: CONSTRAINT; Schema: safaeventos; Owner: -
--

ALTER TABLE ONLY safaeventos.inscripcion
    ADD CONSTRAINT inscripcion_pkey PRIMARY KEY (id);


--
-- TOC entry 4839 (class 2606 OID 17326)
-- Name: me_interesa me_interesa_id_usuario_id_evento_key; Type: CONSTRAINT; Schema: safaeventos; Owner: -
--

ALTER TABLE ONLY safaeventos.me_interesa
    ADD CONSTRAINT me_interesa_id_usuario_id_evento_key UNIQUE (id_usuario, id_evento);


--
-- TOC entry 4841 (class 2606 OID 17324)
-- Name: me_interesa me_interesa_pkey; Type: CONSTRAINT; Schema: safaeventos; Owner: -
--

ALTER TABLE ONLY safaeventos.me_interesa
    ADD CONSTRAINT me_interesa_pkey PRIMARY KEY (id);


--
-- TOC entry 4843 (class 2606 OID 17346)
-- Name: notificacion notificacion_pkey; Type: CONSTRAINT; Schema: safaeventos; Owner: -
--

ALTER TABLE ONLY safaeventos.notificacion
    ADD CONSTRAINT notificacion_pkey PRIMARY KEY (id);


--
-- TOC entry 4831 (class 2606 OID 17275)
-- Name: perfil perfil_pkey; Type: CONSTRAINT; Schema: safaeventos; Owner: -
--

ALTER TABLE ONLY safaeventos.perfil
    ADD CONSTRAINT perfil_pkey PRIMARY KEY (id);


--
-- TOC entry 4827 (class 2606 OID 17266)
-- Name: usuario usuario_email_key; Type: CONSTRAINT; Schema: safaeventos; Owner: -
--

ALTER TABLE ONLY safaeventos.usuario
    ADD CONSTRAINT usuario_email_key UNIQUE (email);


--
-- TOC entry 4829 (class 2606 OID 17264)
-- Name: usuario usuario_pkey; Type: CONSTRAINT; Schema: safaeventos; Owner: -
--

ALTER TABLE ONLY safaeventos.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);


--
-- TOC entry 4857 (class 2606 OID 17385)
-- Name: comentario_evento fk_comentario_evento_evento; Type: FK CONSTRAINT; Schema: safaeventos; Owner: -
--

ALTER TABLE ONLY safaeventos.comentario_evento
    ADD CONSTRAINT fk_comentario_evento_evento FOREIGN KEY (id_evento) REFERENCES safaeventos.evento(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 4858 (class 2606 OID 17380)
-- Name: comentario_evento fk_comentario_evento_usuario; Type: FK CONSTRAINT; Schema: safaeventos; Owner: -
--

ALTER TABLE ONLY safaeventos.comentario_evento
    ADD CONSTRAINT fk_comentario_evento_usuario FOREIGN KEY (id_usuario) REFERENCES safaeventos.usuario(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 4849 (class 2606 OID 17292)
-- Name: evento fk_evento_usuario; Type: FK CONSTRAINT; Schema: safaeventos; Owner: -
--

ALTER TABLE ONLY safaeventos.evento
    ADD CONSTRAINT fk_evento_usuario FOREIGN KEY (id_organizador) REFERENCES safaeventos.usuario(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 4855 (class 2606 OID 17365)
-- Name: foto_evento fk_foto_evento_evento; Type: FK CONSTRAINT; Schema: safaeventos; Owner: -
--

ALTER TABLE ONLY safaeventos.foto_evento
    ADD CONSTRAINT fk_foto_evento_evento FOREIGN KEY (id_evento) REFERENCES safaeventos.evento(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 4856 (class 2606 OID 17360)
-- Name: foto_evento fk_foto_evento_usuario; Type: FK CONSTRAINT; Schema: safaeventos; Owner: -
--

ALTER TABLE ONLY safaeventos.foto_evento
    ADD CONSTRAINT fk_foto_evento_usuario FOREIGN KEY (id_usuario) REFERENCES safaeventos.usuario(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 4850 (class 2606 OID 17313)
-- Name: inscripcion fk_incscripcion_evento; Type: FK CONSTRAINT; Schema: safaeventos; Owner: -
--

ALTER TABLE ONLY safaeventos.inscripcion
    ADD CONSTRAINT fk_incscripcion_evento FOREIGN KEY (id_evento) REFERENCES safaeventos.evento(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 4851 (class 2606 OID 17308)
-- Name: inscripcion fk_inscripcion_usuario; Type: FK CONSTRAINT; Schema: safaeventos; Owner: -
--

ALTER TABLE ONLY safaeventos.inscripcion
    ADD CONSTRAINT fk_inscripcion_usuario FOREIGN KEY (id_usuario) REFERENCES safaeventos.usuario(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 4852 (class 2606 OID 17332)
-- Name: me_interesa fk_meinteresa_evento; Type: FK CONSTRAINT; Schema: safaeventos; Owner: -
--

ALTER TABLE ONLY safaeventos.me_interesa
    ADD CONSTRAINT fk_meinteresa_evento FOREIGN KEY (id_evento) REFERENCES safaeventos.evento(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 4853 (class 2606 OID 17327)
-- Name: me_interesa fk_meinteresa_usuario; Type: FK CONSTRAINT; Schema: safaeventos; Owner: -
--

ALTER TABLE ONLY safaeventos.me_interesa
    ADD CONSTRAINT fk_meinteresa_usuario FOREIGN KEY (id_usuario) REFERENCES safaeventos.usuario(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 4854 (class 2606 OID 17347)
-- Name: notificacion fk_notificacion_usuario; Type: FK CONSTRAINT; Schema: safaeventos; Owner: -
--

ALTER TABLE ONLY safaeventos.notificacion
    ADD CONSTRAINT fk_notificacion_usuario FOREIGN KEY (id_usuario) REFERENCES safaeventos.usuario(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 4848 (class 2606 OID 17276)
-- Name: perfil fk_perfil_usuario; Type: FK CONSTRAINT; Schema: safaeventos; Owner: -
--

ALTER TABLE ONLY safaeventos.perfil
    ADD CONSTRAINT fk_perfil_usuario FOREIGN KEY (id_usuario) REFERENCES safaeventos.usuario(id) ON UPDATE CASCADE ON DELETE CASCADE;


-- Completed on 2026-01-26 13:45:34

--
-- PostgreSQL database dump complete
--

\unrestrict LMV3WwaqfbNVUhanUNmzYTzf6Iipau4Q2DHgTHHfTyyhWdfQvHI8enzvt6stGT8

