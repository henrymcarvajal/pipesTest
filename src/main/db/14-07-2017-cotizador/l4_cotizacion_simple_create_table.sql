CREATE TABLE l4_cotizacion_simple
(
  id character varying(64) NOT NULL,
  ubicacion_origen character varying(200),
  ubicacion_destino character varying(200),
  correo character varying(50),
  numero_pasajeros integer,
  precio_minimo integer,
  precio_maximo integer,
  CONSTRAINT cotizador_pk PRIMARY KEY (id)
);

CREATE TABLE public.l4_parametros_vehiculo
(
  id character varying(64) NOT NULL,
  llave character varying(100),
  valor character varying(100),
  estado character varying(20),
  unidad_medida character varying(20),
  CONSTRAINT parametro_pk PRIMARY KEY (id)
);

  CREATE TABLE l4_analisis_costos
(
  id character varying(64) NOT NULL,
  precio integer,
  ganancia integer,
  sueldo_conductor integer,
  ahorro_vehiculo integer,
  CONSTRAINT analisis_costos_pk PRIMARY KEY (id)
);

CREATE TABLE l4_analisis_costos
(
  id character varying(64) NOT NULL,
  precio integer,
  ganancia integer,
  sueldo_conductor integer,
  ahorro_vehiculo integer,
  CONSTRAINT analisis_costos_pk PRIMARY KEY (id)
);

alter table l4_cotizacion_simple 
add column id_peor_escenario character 
varying(64);

alter table l4_cotizacion_simple 
add column id_mejor_escenario character 
varying(64);

alter table l4_cotizacion_simple
add constraint analisis_costos_fk FOREIGN KEY (id_peor_escenario)
      REFERENCES l4_analisis_costos (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;

      alter table l4_cotizacion_simple
add constraint analisis_costos_mejor_fk FOREIGN KEY (id_mejor_escenario)
      REFERENCES l4_analisis_costos (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;

	    alter table public.l4_cotizacion_simple drop column precio_minimo;
  alter table public.l4_cotizacion_simple drop column precio_maximo;


 alter table l4_servicio add column id_cotizacion character varying(64);
 
 
alter table l4_cotizacion_simple 
add column distancia character 
varying(10);
 
 
 INSERT INTO public.l4_parametros_vehiculo(
            llave, valor, estado, unidad_medida)
    VALUES ('PRECIO_COMBUSTIBLE_ACTUALIZADO', '8000', 'ACTIVO', 'PESOS_COLOMBIANO');
INSERT INTO public.l4_parametros_vehiculo(
            llave, valor, estado, unidad_medida)
    VALUES ('KMS_CAMBIO_LLANTAS', '60000', 'ACTIVO', 'KMS');
INSERT INTO public.l4_parametros_vehiculo(
            llave, valor, estado, unidad_medida)
    VALUES ('KMS_MANTENIMIENTO_FRENOS', '15000', 'ACTIVO', 'KMS');
INSERT INTO public.l4_parametros_vehiculo(
            llave, valor, estado, unidad_medida)
    VALUES ('KMS_CAMBIO_ACEITE', '5000', 'ACTIVO', 'KMS');
INSERT INTO public.l4_parametros_vehiculo(
            llave, valor, estado, unidad_medida)
    VALUES ('KMS_CAMBIO_CORREA_FRENOS', '60000', 'ACTIVO', 'KMS');
INSERT INTO public.l4_parametros_vehiculo(
            llave, valor, estado, unidad_medida)
    VALUES ('RECORRIDO_PROMEDIO_ANUAL', '70000', 'ACTIVO', 'KMS');
INSERT INTO public.l4_parametros_vehiculo(
            llave, valor, estado, unidad_medida)
    VALUES ('RECORRIDO_PROMEDIO_ANUAL', '70000', 'ACTIVO', 'KMS');
	
	INSERT INTO public.l4_parametros_vehiculo(
            llave, valor, estado, unidad_medida,id)
    VALUES ('GANANCIA_MINIMA_9', '90000', 'ACTIVO', 'PESOS COLOMBIANOS','(0,56)');

INSERT INTO public.l4_parametros_vehiculo(
            llave, valor, estado, unidad_medida,id)
    VALUES ('GANANCIA_MINIMA_17', '110000', 'ACTIVO', 'PESOS COLOMBIANOS','(0,57)');

INSERT INTO public.l4_parametros_vehiculo(
            llave, valor, estado, unidad_medida,id)
    VALUES ('GANANCIA_MINIMA_24', '130000', 'ACTIVO', 'PESOS COLOMBIANOS','(0,58)');

INSERT INTO public.l4_parametros_vehiculo(
            llave, valor, estado, unidad_medida,id)
    VALUES ('GANANCIA_MINIMA_32', '150000', 'ACTIVO', 'PESOS COLOMBIANOS','(0,59)');

INSERT INTO public.l4_parametros_vehiculo(
            llave, valor, estado, unidad_medida,id)
    VALUES ('GANANCIA_MINIMA_42', '170000', 'ACTIVO', 'PESOS COLOMBIANOS','(0,60)');

--Relacion combustible por # pasajeros

INSERT INTO public.l4_parametros_vehiculo(
            llave, valor, estado, unidad_medida)
    VALUES ('RELACION_COMBUSTIBLE_RECORRIDO_9', '55', 'ACTIVO', 'KMS/GAL');
INSERT INTO public.l4_parametros_vehiculo(
            llave, valor, estado, unidad_medida)
    VALUES ('RELACION_COMBUSTIBLE_RECORRIDO_17', '30', 'ACTIVO', 'KMS/GAL');
INSERT INTO public.l4_parametros_vehiculo(
            llave, valor, estado, unidad_medida)
    VALUES ('RELACION_COMBUSTIBLE_RECORRIDO_24', '25', 'ACTIVO', 'KMS/GAL');
INSERT INTO public.l4_parametros_vehiculo(
            llave, valor, estado, unidad_medida)
    VALUES ('RELACION_COMBUSTIBLE_RECORRIDO_32', '23', 'ACTIVO', 'KMS/GAL');
INSERT INTO public.l4_parametros_vehiculo(
            llave, valor, estado, unidad_medida)
    VALUES ('RELACION_COMBUSTIBLE_RECORRIDO_42', '20', 'ACTIVO', 'KMS/GAL');

--Precio tecnomecanica

INSERT INTO public.l4_parametros_vehiculo(
            llave, valor, estado, unidad_medida)
    VALUES ('PRECIO_TECNOMACNICA_9', '150000', 'ACTIVO', 'PESOS COLOMBIANOS');
INSERT INTO public.l4_parametros_vehiculo(
            llave, valor, estado, unidad_medida)
    VALUES ('PRECIO_TECNOMACNICA_17_42', '250000', 'ACTIVO', 'PESOS COLOMBIANOS');

--Costo promedio SEGURO SOAT

INSERT INTO public.l4_parametros_vehiculo(
            llave, valor, estado, unidad_medida)
    VALUES ('PRECIO_SOAT_PROMEDIO', '1190000', 'ACTIVO', 'PESOS COLOMBIANOS');

--Costo promedio TECNOMECANICIO

INSERT INTO public.l4_parametros_vehiculo(
            llave, valor, estado, unidad_medida)
    VALUES ('PRECIO_REVISION_TECNOMECANICA_PROMEDIO', '1190000', 'ACTIVO', 'PESOS COLOMBIANOS');

    --Devaluación anual por capacidad

INSERT INTO public.l4_parametros_vehiculo(
            llave, valor, estado, unidad_medida)
    VALUES ('DEVALUACION_ANUAL_9', '2500000', 'ACTIVO', 'PESOS COLOMBIANOS');
INSERT INTO public.l4_parametros_vehiculo(
            llave, valor, estado, unidad_medida)
    VALUES ('DEVALUACION_ANUAL_17', '3500000', 'ACTIVO', 'PESOS COLOMBIANOS');
INSERT INTO public.l4_parametros_vehiculo(
            llave, valor, estado, unidad_medida)
    VALUES ('DEVALUACION_ANUAL_24', '4000000', 'ACTIVO', 'PESOS COLOMBIANOS');
INSERT INTO public.l4_parametros_vehiculo(
            llave, valor, estado, unidad_medida)
        VALUES ('DEVALUACION_ANUAL_32', '4000000', 'ACTIVO', 'PESOS COLOMBIANOS');
INSERT INTO public.l4_parametros_vehiculo(
            llave, valor, estado, unidad_medida)
      VALUES ('DEVALUACION_ANUAL_42', '5000000', 'ACTIVO', 'PESOS COLOMBIANOS');

    
--precio mantinimiento de frenos

INSERT INTO public.l4_parametros_vehiculo(
            llave, valor, estado, unidad_medida)
    VALUES ('PRECIO_MANTENIMIMENTO_FRENOS_PROMEDIO', '180000', 'ACTIVO', 'PESOS COLOMBIANOS');

        
--precio cambio de correa y embrague

INSERT INTO public.l4_parametros_vehiculo(
            llave, valor, estado, unidad_medida)
    VALUES ('PRECIO_CORREA_EMBRAGUE', '1000000', 'ACTIVO', 'PESOS COLOMBIANOS');

    --precio cambio de aceite promedio

INSERT INTO public.l4_parametros_vehiculo(
            llave, valor, estado, unidad_medida)
    VALUES ('PRECIO_CAMBIO_ACEITE_PROMEDIO', '240000', 'ACTIVO', 'PESOS COLOMBIANOS');

    

    --precio cambio de llantas
INSERT INTO public.l4_parametros_vehiculo(
            llave, valor, estado, unidad_medida)
    VALUES ('PRECIO_CAMBIO_LLANTAS', '1700000', 'ACTIVO', 'PESOS COLOMBIANOS');
	
	--PRECIO VEHICULOS PROMEDIO
	
	INSERT INTO public.l4_parametros_vehiculo(
            llave, valor, estado, unidad_medida, id)
    VALUES ('PRECIO_PROMEDIO_VEHICULO_9', '40000000', 'ACTIVO', 'PESOS COLOMBIANOS', '(0,51)');

    INSERT INTO public.l4_parametros_vehiculo(
            llave, valor, estado, unidad_medida, id)
    VALUES ('PRECIO_PROMEDIO_VEHICULO_17', '55000000', 'ACTIVO', 'PESOS COLOMBIANOS', '(0,52)');
    INSERT INTO public.l4_parametros_vehiculo(
            llave, valor, estado, unidad_medida, id)
    VALUES ('PRECIO_PROMEDIO_VEHICULO_24', '80000000', 'ACTIVO', 'PESOS COLOMBIANOS', '(0,53)');
     INSERT INTO public.l4_parametros_vehiculo(
            llave, valor, estado, unidad_medida, id)
    VALUES ('PRECIO_PROMEDIO_VEHICULO_32', '95000000', 'ACTIVO', 'PESOS COLOMBIANOS', '(0,54)');
    INSERT INTO public.l4_parametros_vehiculo(
            llave, valor, estado, unidad_medida, id)
    VALUES ('PRECIO_PROMEDIO_VEHICULO_42', '110000000', 'ACTIVO', 'PESOS COLOMBIANOS', '(0,55)');



