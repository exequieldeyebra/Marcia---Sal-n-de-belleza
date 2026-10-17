1. Módulo de Autenticación y Gestión de Personal 

Requerimiento de Usuario: El administrador desea tener un control sobre el personal a cargo del local, como también establecer restricciones a cada uno.  

Requerimiento de sistema: Inicio de sesión 

Entrada: Credenciales de acceso (correo electrónico y contraseña). 

Proceso: El sistema valida la información ingresada contra la base de datos verificando que el perfil activo corresponda al rol de administrador. 

Salida: Redirección al panel principal del administrador y confirmación de acceso seguro. 



Requerimiento de sistema: Alta de Usuario (Administradores y Personal) 

Entrada: Datos personales del usuario (DNI, nombre, apellido, teléfono, mail, dirección, contacto de emergencia) y rol de usuario (administrador, peluquero, estilista, manicura).  

Proceso: El sistema valida la información ingresada, se genera un identificador único, crea el perfil del usuario y lo almacena.  

Salida: Confirmación de alta del usuario en una pantalla emergente.  

Requerimiento de sistema: Baja de Usuario 

Entrada: Se ingresa el DNI del Usuario.  

Proceso: El sistema valida la información ingresada, busca el perfil, y cambia su estado a "inactivo".  

Salida: Confirmación de baja del usuario en una pantalla emergente.  

Requerimiento de sistema: Modificación de Usuario 

Entrada: Se ingresa el DNI del usuario.  

Proceso: El sistema valida la información ingresada, busca el perfil, y permite realizar las modificaciones necesarias (nombre, apellido, teléfono, mail, contraseña, dirección, contacto de emergencia, rol).  

Salida: Confirmación de modificación del usuario en una pantalla emergente.  

Requerimiento de sistema: Listado de personal y administradores 

Entrada: Los datos del personal se encuentran en el sistema o se puede realizar la búsqueda mediante un filtrado (por dni del usuario, estado: activo o inactivo, rol).  

Proceso: El sistema valida la información ingresada, filtra en caso de que se ingresaran parámetros y se busca en el sistema.  

Salida: Se muestran los datos del personal (dni, nombre, apellido, mail, teléfono, estado: activo o inactivo, rol, contacto de emergencia).  



2. Módulo de Gestión de Clientes 

Requerimiento de Usuario: El administrador debe poder realizar la gestión de clientes para tener acceso a información actualizada sobre cada persona.  

Requerimiento de sistema: Alta de Clientes 

Entrada: Datos personales del cliente (DNI, nombre, apellido, teléfono, mail, fecha de nacimiento).  

Proceso: El sistema valida la información ingresada, se genera un identificador único, crea el perfil del cliente y lo almacena.  

Salida: Confirmación de alta del usuario en una pantalla emergente.  

Requerimiento de sistema: Baja de Clientes 

Entrada: Se ingresa el DNI del cliente.  

Proceso: El sistema valida la información ingresada, busca el perfil, y cambia su estado a "inactivo".  

Salida: Confirmación de baja del usuario en una pantalla emergente.  

Requerimiento de sistema: Modificación de Clientes 

Entrada: Se ingresa el DNI del cliente.  

Proceso: El sistema valida la información ingresada, filtra por DNI de clientes para encontrar el perfil, y permite realizar las modificaciones necesarias (nombre, apellido, mail, contraseña, dirección, contacto de emergencia).  

Salida: Confirmación de modificación del usuario en una pantalla emergente.  

Requerimiento de sistema: Listado de clientes 

Entrada: Los datos de los clientes se encuentran en el sistema o se puede realizar la búsqueda mediante un filtrado (por dni de cliente, estado: activo o inactivo).  

Proceso: El sistema valida la información ingresada, filtra en caso de que se ingresaran parámetros y se busca en el sistema.  

Salida: Se muestran los datos de los clientes (dni, nombre, apellido, mail, teléfono, estado: activo o inactivo, contacto de emergencia).  

 

3. Módulo de Gestión de Servicios 

Requerimiento de Usuario: El administrador es responsable de administrar el catálogo de servicios estéticos ofrecidos en el local. 

Requerimiento de sistema: Alta de Servicios 

Entrada: Se ingresan los datos del servicio (nombre, descripción, categoría, costo y duración asignada). 

Proceso: El sistema verifica los datos ingresados, genera un identificador único y almacena el servicio en el catálogo. 

Salida: Confirmación de alta en una pantalla emergente. 

Requerimiento de sistema: Baja de Servicios 

Entrada: Se ingresa el identificador único o nombre del servicio. 

Proceso: El sistema verifica los datos ingresados y cambia el estado del servicio seleccionado a "inactivo". 

Salida: Confirmación de baja en una pantalla emergente. 

Requerimiento de sistema: Modificación de Servicios 

Entrada: Se ingresa el identificador único o nombre del servicio. 

Proceso: El sistema verifica, busca el servicio y permite la modificación de nombre, descripción, categoría, precio o duración asignada. 

Salida: Confirmación de la modificación en una pantalla emergente. 

Requerimiento de sistema: Listado de Servicios 

Entrada: Los datos se encuentran en el sistema o se puede realizar la búsqueda mediante un filtrado (por categoría, nombre o estado: activo/inactivo). 

Proceso: El sistema valida la información ingresada, filtra en caso de que se ingresaran parámetros y busca en el sistema. 

Salida: Se muestran los datos del catálogo de servicios en pantalla (nombre, descripción, categoría, costo, duración). 



4. Módulo de Gestión de Productos 

Requerimiento de Usuario: El administrador es responsable de administrar el inventario físico y stock de productos del local. 

Requerimiento de sistema: Alta de Productos 

Entrada: Se ingresan los datos del producto (nombre, descripción, categoría, costo y volumen de stock inicial). 

Proceso: El sistema verifica los datos ingresados, genera un identificador único y almacena el ítem en el inventario. 

Salida: Confirmación de alta en una pantalla emergente. 

Requerimiento de sistema: Baja de Productos 

Entrada: Se ingresa el identificador único o nombre del producto. 

Proceso: El sistema verifica los datos ingresados y cambia el estado del producto seleccionado a "inactivo". 

Salida: Confirmación de baja en una pantalla emergente. 

Requerimiento de sistema: Modificación de Productos 

Entrada: Se ingresa el identificador único o nombre del producto. 

Proceso: El sistema verifica, busca el ítem y permite la modificación del nombre, precio, nivel de stock, descripción o categoría. 

Salida: Confirmación de la modificación en una pantalla emergente. 

Requerimiento de sistema: Listado de Productos 

Entrada: Los datos se encuentran en el sistema o se puede realizar la búsqueda mediante un filtrado (por categoría, nombre, nivel de stock o estado: activo/inactivo). 

Proceso: El sistema valida la información ingresada, filtra en caso de que se ingresaran parámetros y busca en el sistema. 

Salida: Se muestran los datos del inventario de productos en pantalla (nombre, descripción, costo, cantidad). 

 

5. Módulo de Gestor de Turnos 

Requerimiento de Usuario: El administrador debe ser capaz de administrar, consultar y asignar turnos a clientes, organizando la agenda diaria del salón. 

Requerimiento de sistema: Asignación de turno 

Entrada: Se selecciona el DNI del cliente, el ID del servicio requerido, el DNI del profesional a cargo, la fecha y el horario de inicio/salida.  

Proceso: El sistema valida que el usuario, profesional y servicio estén activos, verifica la disponibilidad de agenda en el horario solicitado, y registra la asignación.  

Salida: Confirmación de asignación exitosa en pantalla con el resumen del turno (nombre de cliente, nombre de profesional, nombre de servicio, fecha y horario, costo total) vinculado al perfil del cliente.  

Requerimiento de sistema: Cancelación de turno 

Entrada: Se ingresa el ID del turno asignado. 

Proceso: El sistema verifica que el turno esté activo y permite cancelarlo marcándolo como "inactivo". 

Salida: Notificación en pantalla confirmando la cancelación.  

Requerimiento de sistema: Listado de agenda de turnos 

Entrada: Selección del período a consultar (rango de fechas desde/hasta) y el filtro opcional por profesional asignado (turnos realizados, pendientes o cancelados).  

Proceso: El sistema filtra los turnos en base de los parámetros y recupera la agenda correspondiente.  

Salida: Despliegue en la interfaz gráfica el listado de turnos del día con sus respectivos horarios, clientes y servicios.  



6. Módulo de Gestión de Ventas y Recibos 

Requerimiento de Usuario: El administrador requiere un control detallado sobre los cobros de servicios y el flujo de ventas del salón. 

Requerimiento de sistema: Registro de venta servicio 

Entrada: Se ingresa el DNI del cliente, los servicios, el tipo de pago (efectivo, transferencia o ambos) y el profesional que realizó el trabajo. 

Proceso: El sistema calcula el costo total, registra la transacción y descuenta de forma automática el stock de los productos que hayan sido utilizados en el servicio.  

Salida: Confirmación del registro en pantalla emergente con el detalle (dni del cliente, dni del profesional, tipo de servicio, tipo de pago).  

Requerimiento de sistema: Registro de venta producto 

Entrada: Se ingresa el DNI del cliente, productos vendidos, el tipo de pago (efectivo, transferencia o ambos) y el empleado que realizó la venta. 

Proceso: El sistema calcula el costo total, registra la transacción y descuenta de forma automática el stock de los productos que hayan sido vendidos.  

Salida: Confirmación del registro en pantalla emergente con el detalle (dni cliente, listado de productos, tipo de pago y costo). 

Requerimiento de sistema: Emisión de recibos 

Entrada: Se ingresa el ID de la transacción aprobada. 

Proceso: El sistema recupera los detalles de la venta, desglosando los ítems, los montos parciales y el método de pago utilizado. 

Salida: Comprobante digital generado en pantalla para su impresión, descarga o envío al cliente.  



7. Módulo de Estadísticas y Desempeño 

Requerimiento de Usuario: El negocio requiere un análisis de métricas operativas para medir la rentabilidad del salón y la satisfacción con el servicio brindado por el personal.  

Requerimiento de sistema: Estadísticas de ventas y servicios o productos más solicitados 

Entrada: Selección del período a consultar (rango de fechas desde/hasta, filtro mensual o anual).  

Proceso: El sistema procesa los registros históricos de ventas, agrupa por producto o servicio y suma los totales para identificar tendencias y rentabilidad.  

Salida: Se muestra un panel gráfico con las métricas solicitadas, donde se utilicen gráficos de torta para una mejor visualización.  

Requerimiento de sistema: Calificaciones de los empleados 

Entrada: DNI del empleado (estilista, manicura, peluquero) y rango de fechas a consultar. 

Proceso: El sistema recopila el historial de turnos finalizados asociados a ese empleado y cruza la información con las puntuaciones o reseñas otorgadas por los clientes. 

Salida: Visualización en pantalla del promedio general de calificación del empleado junto a los comentarios de valoración. 
