create database safaeventos;

CREATE TABLE usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellidos VARCHAR(150) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    contrasenia VARCHAR(100) NOT NULL,
    curso ENUM('1º ESO', '2º ESO', '3º ESO', '4º ESO', '1º BACH', '2º BACH', '1º FP BÁSICA', '2ª FP BÁSICA', '1º CFM', '2º CFM', '1º CFS', '2ºCFS', '1º DAM', '2º DAM', '1º DAW', '2º DAW') NULL,
    rol ENUM('organizador', 'alumno') NOT NULL,
    verificacion BOOLEAN NOT NULL DEFAULT FALSE,
    fecha_registro DATE NOT NULL
);



CREATE TABLE evento (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    descripcion TEXT NOT NULL,
    fecha DATE NOT NULL,
    ubicacion VARCHAR(200) NOT NULL,
    precio DECIMAL(10,2) DEFAULT 0,
    foto VARCHAR(255),
    foto_subidas VARCHAR(255),
    comentarios TEXT,
    categoria ENUM('Deportes', 'Cultura', 'Música', 'Otro') NOT NULL
);



CREATE TABLE notificacion (
    id INT AUTO_INCREMENT PRIMARY KEY,
    mensaje TEXT NOT NULL,
    fecha_envio DATE NOT NULL,
    leido BOOLEAN DEFAULT FALSE
);




CREATE TABLE inscripcion (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    id_evento INT NOT NULL,
    pago_realizado BOOLEAN DEFAULT FALSE,
    metodo_pago VARCHAR(100),
    tiene_coste BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_evento) REFERENCES evento(id)
        ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE organizar (
    id_usuario INT NOT NULL,
    id_evento INT NOT NULL,
    PRIMARY KEY (id_usuario, id_evento),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_evento) REFERENCES evento(id)
        ON DELETE CASCADE ON UPDATE CASCADE
)

CREATE TABLE guardar (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    id_evento INT NOT NULL,
    fecha_guardado DATE NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_evento) REFERENCES evento(id)
        ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE recibir (
    id_usuario INT NOT NULL,
    id_notificacion INT NOT NULL,
    PRIMARY KEY (id_usuario, id_notificacion),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_notificacion) REFERENCES notificacion(id)
        ON DELETE CASCADE ON UPDATE CASCADE
);


