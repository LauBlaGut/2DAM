/*DROP SCHEMA safaeventos CASCADE;*/
create schema safaeventos;

CREATE TABLE usuario (
    id SERIAL PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    contrasenia VARCHAR(100) NOT NULL,
    rol SMALLINT NOT NULL,  -- 0 = alumno, 1 = organizador (definido en Spring)
    verificacion BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE perfil (
    id SERIAL PRIMARY KEY,
    id_usuario INT NOT NULL REFERENCES usuario(id) ON DELETE CASCADE ON UPDATE CASCADE,
    nombre VARCHAR(100) NOT NULL,
    apellidos VARCHAR(150) NOT NULL,
    curso SMALLINT,  -- valores mapeados en Spring: 0=1ºESO, 1=2ºESO, etc.
    fecha_registro DATE NOT NULL
);


CREATE TABLE evento (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    descripcion TEXT NOT NULL,
    fecha_hora TIMESTAMP NOT NULL,
    ubicacion VARCHAR(200) NOT NULL,
    foto VARCHAR(200) not null,
    precio NUMERIC(10,2) DEFAULT 0 CHECK (precio >= 0),
    categoria SMALLINT NOT NULL,  -- 0=Deportes, 1=Cultura, 2=Música, 3=Otro
    id_organizador INT NOT NULL REFERENCES usuario(id) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE inscripcion (
    id SERIAL PRIMARY KEY,
    id_usuario INT NOT NULL REFERENCES usuario(id) ON DELETE CASCADE ON UPDATE CASCADE,
    id_evento INT NOT NULL REFERENCES evento(id) ON DELETE CASCADE ON UPDATE CASCADE,
    pago_realizado BOOLEAN DEFAULT FALSE,
    metodo_pago smallint not NULL,
    tiene_coste BOOLEAN NOT NULL DEFAULT FALSE,
    UNIQUE (id_usuario, id_evento)
);


CREATE TABLE me_interesa (
    id SERIAL PRIMARY KEY,
    id_usuario INT NOT NULL REFERENCES usuario(id) ON DELETE CASCADE ON UPDATE CASCADE,
    id_evento INT NOT NULL REFERENCES evento(id) ON DELETE CASCADE ON UPDATE CASCADE,
    fecha_guardado DATE NOT NULL,
    UNIQUE (id_usuario, id_evento)
);


CREATE TABLE notificacion (
    id SERIAL PRIMARY KEY,
    id_usuario INT NOT NULL REFERENCES usuario(id) ON DELETE CASCADE ON UPDATE CASCADE,
    mensaje TEXT NOT NULL,
    fecha_envio DATE NOT NULL,
    leido BOOLEAN DEFAULT FALSE
);


CREATE TABLE foto_evento (
    id SERIAL PRIMARY KEY,
    id_evento INT NOT NULL REFERENCES evento(id) ON DELETE CASCADE ON UPDATE CASCADE,
    id_usuario INT NOT NULL REFERENCES usuario(id) ON DELETE CASCADE ON UPDATE CASCADE,
    ruta_foto VARCHAR(255) NOT NULL,
    fecha_subida TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario, id_evento)
        REFERENCES inscripcion(id_usuario, id_evento)
        ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE comentario_evento (
    id SERIAL PRIMARY KEY,
    id_evento INT NOT NULL REFERENCES evento(id) ON DELETE CASCADE ON UPDATE CASCADE,
    id_usuario INT NOT NULL REFERENCES usuario(id) ON DELETE CASCADE ON UPDATE CASCADE,
    comentario TEXT NOT NULL,
    fecha_comentario TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario, id_evento)
        REFERENCES inscripcion(id_usuario, id_evento)
        ON DELETE CASCADE ON UPDATE CASCADE
);