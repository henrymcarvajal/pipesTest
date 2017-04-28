c2e587f797e5e694b65bbd6b649acfc5

delete from l4_oferta;
delete from l4_servicio;
delete from l4_vehiculo;
delete from l4_transportador;
delete from l4_usuario;

DROP TABLE l4_oferta;
DROP TABLE l4_servicio;
DROP TABLE l4_transportador;
DROP TABLE l4_usuario;


CREATE TABLE l4_transportador
(
  id character varying(64) NOT NULL,
  nombre character varying(128),
  correo character varying(32),
  contrasena character varying(128),
  CONSTRAINT transportador_pk PRIMARY KEY (id)
);

CREATE TABLE l4_usuario
(
  id character varying(64) NOT NULL,
  nombre character varying(128),
  correo character varying(32),
  CONSTRAINT usuario_pk PRIMARY KEY (id)
);

CREATE TABLE l4_servicio
(
  id character varying(64) NOT NULL,
  origen character varying(128),
  destino character varying(128),
  hora_llegada timestamp without time zone,
  hora_salida timestamp without time zone,
  id_usuario character varying(64) NOT NULL,
  numero_pasajeros integer,
  CONSTRAINT servicio_pk PRIMARY KEY (id),
  CONSTRAINT usuario_servicio_fk FOREIGN KEY (id_usuario)
      REFERENCES l4_usuario (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE l4_oferta
(
  id character varying(64) not null,
  id_servicio character varying(64) NOT NULL,
  id_transportador character varying(64) NOT NULL,
  valor integer,
  fecha time without time zone,
  CONSTRAINT oferta_pk PRIMARY KEY (id),
CONSTRAINT servicio_oferta_fk FOREIGN KEY (id_servicio)
      REFERENCES l4_servicio (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT transportador_oferta_fk FOREIGN KEY (id_transportador)
      REFERENCES l4_transportador (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

alter table l4_usuario add column empresa character varying(64);
alter table l4_usuario add column identificacion bigint;
alter table l4_usuario add column telefono bigint;
alter table l4_usuario add column edad smallint;

alter table l4_servicio add column fecha_creacion timestamp without time zone;
alter table l4_servicio add column fecha_publicacion timestamp without time zone;
alter table l4_servicio add column fecha_ejecucion timestamp without time zone;
alter table l4_servicio add column fecha_terminacion timestamp without time zone;

alter table l4_transportador add column credito integer;

alter table l4_oferta drop column fecha;
alter table l4_oferta add column fecha timestamp without time zone;

alter table l4_servicio add column distancia smallint;
alter table l4_servicio add column disponibilidad boolean;
alter table l4_servicio alter column numero_pasajeros set data type character varying(32);

alter table l4_servicio rename column disponibilidad to redondo;

alter table l4_transportador add column ofertas_servidas smallint;

alter table l4_servicio add column disponibilidad boolean;

alter table l4_transportador add column numero_contacto bigint;

alter table l4_transportador add column fecha_creacion timestamp without time zone;

CREATE TABLE l4_vehiculo
(
  id character varying(64) NOT NULL,
  id_transportador character varying(64) NOT NULL,
  placa character varying(8),
  numero_pasajeros character varying(8),
  ciudad character varying(64),
  imagen1 bytea,
  imagen2 bytea,
  CONSTRAINT l4_vehiculo_pk PRIMARY KEY (id),
    CONSTRAINT l4_vehiculo_transportador_fk FOREIGN KEY (id_transportador)
      REFERENCES l4_transportador (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

alter table l4_vehiculo add column fecha_creacion timestamp without time zone;

alter table l4_transportador add column numero_identificacion bigint;
alter table l4_transportador add column tipo_identificacion character varying(2);


alter table l4_vehiculo rename column imagen1 to soat;
alter table l4_vehiculo rename column imagen2 to revision_tecnomecanica;

alter table l4_vehiculo add column seguro_contractual bytea;
alter table l4_vehiculo add column seguro_extracontractual bytea;
alter table l4_vehiculo add column tarjeta_propiedad bytea;
alter table l4_vehiculo add column foto_vehiculo bytea;

alter table l4_oferta add column aceptada boolean;

alter table l4_transportador rename column ofertas_servidas to servicios_atendidos;

alter table l4_usuario add column servicios_completados smallint;

alter table l4_usuario drop column distancia;
alter table l4_usuario drop column disponibilidad;

alter table l4_vehiculo add column marca character varying(16);
alter table l4_vehiculo add column modelo character varying(8);
alter table l4_vehiculo add column acondicionado boolean;


create table l4_factura (
id character varying(64),
id_transportador character varying(64),
valor bigint,
fecha_hora_tx character varying(32),
codigo_aprobacion integer,
numero_referencia_payco integer,
codigo_respuesta smallint,
descripcion_respuesta character varying(64),
estado_tx character varying(16),
generada boolean,
constraint l4_factura_pk primary key (id),
constraint transportador_factura_fk foreign key (id_transportador) references l4_transportador (id)
)

alter table l4_transportador add apellidos character varying(32);
alter table l4_transportador rename column nombre to nombres;
alter table l4_transportador alter column nombres set data type character varying(32);

alter table l4_transportador add column reputacion numeric (2, 1);

alter table l4_usuario add column reputacion numeric (2, 1);

alter table l4_transportador add column servicios_calificados smallint;
alter table l4_usuario add column servicios_calificados smallint;

alter table l4_servicio add column calificacion_usuario smallint;
alter table l4_servicio add column calificacion_transportador smallint;

alter table l4_servicio alter column distancia set data type numeric (4, 1);

alter table l4_vehiculo add column revisado boolean;

alter table l4_vehiculo add column tarjeta_operacion bytea;

alter table l4_usuario rename column correo to buzon_electronico;

alter table l4_transportador rename column correo to buzon_electronico;

alter table l4_usuario add column fecha_creacion timestamp without time zone;

alter table l4_usuario add column tipo_usuario character varying(2);


CREATE TABLE l4_admin_usuario
(
  usuario character varying(32) not null,
  contrasena character varying(128),
  CONSTRAINT l4_admin_usuario_pk PRIMARY KEY (usuario)
);

ALTER TABLE l4_admin_usuario ADD buzon_electronico character varying(32);