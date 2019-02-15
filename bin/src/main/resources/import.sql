/* Populate tables */
INSERT INTO usuarios (username, password, email, enabled, creado) VALUES ('Gasakana', '12345', 'avb.dam@gmail.com', 1, NOW());
INSERT INTO socios (dni, usuario_id, nombre, apellido1, apellido2, movil, foto) VALUES('44066675D', 1 ,'Alejandro', 'Virlán', 'Burgos', '657942675', 'foto.jpg');

INSERT INTO roles(nombre_rol) VALUES ('ROLE_ADMIN');
INSERT INTO roles(nombre_rol) VALUES ('ROLE_USER');

INSERT INTO roles_usuario(usuario_id, rol_id) VALUES (1, 1);
INSERT INTO roles_usuario(usuario_id, rol_id) VALUES (1, 2);

INSERT INTO juegos_mesa(nombre, precio, resumen, slug, vendido, video) VALUES ('Bang', 20.00, 'El juego del bang', 'bang', 0, '');

INSERT INTO reservas(fecha_hora,juego_mesa_id,socio_id) VALUES (NOW(), 1, 1);

INSERT INTO transacciones(concepto, socio_id, fecha, importe, escaneo_factura) VALUES ('Pago mensual peña',1,NOW(),50.00,'');


INSERT INTO usuarios (username, password, email, enabled, creado) VALUES ('Jedilojo', '123456', 'jedilojo@gmail.com', 1, NOW());
INSERT INTO socios (dni, usuario_id, nombre, apellido1, apellido2, movil, foto) VALUES('74837458S', 2 ,'Juan Carlos', 'Lojo', 'Ariza', '666743675', 'foto1.jpg');

INSERT INTO roles_usuario(usuario_id, rol_id) VALUES (2, 2);

INSERT INTO juegos_mesa(nombre, precio, resumen, slug, vendido, video) VALUES ('La Cosa', 30.00, 'El juego de la cosa', 'la-cosa', 0, '');

INSERT INTO reservas(fecha_hora,juego_mesa_id,socio_id) VALUES (NOW(), 2, 2);

INSERT INTO transacciones(concepto, socio_id, fecha, importe, escaneo_factura) VALUES ('Pago mensual peña',2,NOW(),50.00,'');