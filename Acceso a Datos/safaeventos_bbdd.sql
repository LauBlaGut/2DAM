DROP SCHEMA safaeventos CASCADE;
create schema safaeventos;

create table usuario (
    id serial primary key,
    email varchar(100) not null UNIQUE,
    contrasenia varchar(100) not null,
    rol smallint not null,  
    verificacion BOOLEAN not null DEFAULT FALSE
);

create table perfil (
    id serial primary key,
    id_usuario int not null,
    nombre varchar(100) not null,
    apellidos varchar(150) not null,
    curso SMALLint,
    fecha_registro DATE not null,
    foto_url VARCHAR(600),
    constraint fk_perfil_usuario
	    foreign key(id_usuario)
	    references usuario(id) 
	    on delete cascade 
	    on update cascade
);


create table evento (
    id serial primary key,
    titulo varchar(200) not null,
    descripcion TEXT not null,
    fecha_hora TIMESTAMP not null,
    ubicacion varchar(200) not null,
    foto varchar(200) not null,
    precio NUMERIC(10,2) DEFAULT 0 CHECK (precio >= 0),
    categoria SMALLint not null,
    id_organizador int not null,
    constraint fk_evento_usuario
	    foreign key(id_organizador)
	    references usuario(id) 
	    on delete cascade 
	    on update cascade
);


create table inscripcion (
    id serial primary key,
    id_usuario int not null,
    id_evento int not null,
    pago_realizado BOOLEAN DEFAULT FALSE,
    metodo_pago smallint not null,
    tiene_coste BOOLEAN not null DEFAULT FALSE,
    UNIQUE (id_usuario, id_evento),
    constraint fk_inscripcion_usuario
	    foreign key(id_usuario)
	    references usuario(id) 
	    on delete cascade 
	    on update cascade,
    constraint fk_incscripcion_evento
	    foreign key(id_evento)
	    references evento(id)
	    on delete cascade 
	    on update cascade
);


create table me_interesa (
    id serial primary key,
    id_usuario int not null,
    id_evento int not null,
    fecha_guardado DATE not null,
    UNIQUE (id_usuario, id_evento),
    constraint fk_meinteresa_usuario
    	foreign key(id_usuario)
    	references usuario(id) 
    	on delete cascade 
    	on update cascade,
    constraint fk_meinteresa_evento
    	foreign key(id_evento)
    	references evento(id) 
    	on delete cascade 
    	on update cascade
);


create table notificacion (
    id serial primary key,
    id_usuario int not null,
    mensaje TEXT not null,
    fecha_envio DATE not null,
    leido BOOLEAN DEFAULT false,
    constraint fk_notificacion_usuario
    	foreign key(id_usuario)
    	references usuario(id) 
    	on delete cascade 
    	on update cascade
);


create table foto_evento (
    id serial primary key,
    id_evento int not null,
    id_usuario int not null,
    ruta_foto varchar(255) not null,
    fecha_subida TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    constraint fk_foto_evento_usuario
    	foreign key(id_usuario)
    	references usuario(id) 
    	on delete cascade 
    	on update cascade,
    constraint fk_foto_evento_evento
    	foreign key(id_evento)
    	references evento(id) 
    	on delete cascade 
    	on update cascade
);


create table comentario_evento (
    id serial primary key,
    id_evento int not null,
    id_usuario int not null,
    comentario TEXT not null,
    fecha_comentario TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    constraint fk_comentario_evento_usuario
    	foreign key(id_usuario)
    	references usuario(id) 
    	on delete cascade 
    	on update cascade,
    constraint fk_comentario_evento_evento
    	foreign key(id_evento)
    	references evento(id) 
    	on delete cascade 
    	on update cascade
);

select * from usuario;
select * from evento;
SELECT id, fecha_hora
FROM evento
WHERE fecha_hora IS NULL;




SELECT e
FROM safaeventos.evento e
where DATE(e.fecha_hora) = '2025-12-12'
  AND e.categoria = 3;

 
 
select * from inscripcion i  where id_evento =1;

SELECT id, fecha_hora, CURRENT_DATE
FROM evento
WHERE fecha_hora >= CURRENT_DATE
ORDER BY fecha_hora ASC;


SELECT COUNT(*) FROM evento;
SELECT COUNT(*) FROM safaeventos.evento;

delete from usuario where id = 33;

