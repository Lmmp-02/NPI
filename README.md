
## Authors
- [Antonio José Heredia Arredondo](https:/https://github.com/antoniojha02ugr)
- [Jorge López Remacho](https://www.github.com/jorgelerre)
- [Luisa María Meseguer Pérez](https:/https://github.com/Lmmp-02)
- [Javier Moreno Morón](https://www.github.com/JMMelcrack)


# Nuevos Paradigmas de Interacción


En este repositorio, mostramos el software desarrollado por nuestro grupo en la asignatura *Nuevos Paradigmas de Interacción*. Durante el progreso de la misma, hemos realizado tres prácticas, las cuales podemos agrupar en dos proyectos:

* El primer proyecto, englobado por la práctica 1, consiste en la implementación de un chatbot en VoiceXML, el cual proporciona información de diferente índole sobre la ETSIIT (Escuela Técnica Superior de Ingeniería Informática y Telecomunicaciones de la Universidad de Granada). 

* El segundo proyecto, que incluye las prácticas 2 y 3, se trata del desarrollo de dos aplicaciones interconectadas que proporcionan información sobre la ETSIIT.
    - La primera de estas (práctica 2) es una aplicación pensada para un tótem situado en la puerta de la facultad. Este tótem consiste en un televisor 4K orientado verticalmente, y consta de un dispositivo *Leap Motion* para la interacción con la aplicación a través de gestos. 
    - Por otro lado (práctica 3), se desarrolla una aplicación Android que complementa a la aplicación anterior. Permite el registro de los alumnos en el sistema, de forma que la aplicación pueda otorgar a los usuarios información personalizada. También permite la autenticación del usuario para realizar ciertas operaciones en el tótem, como el pago (virtual) de menús del comedor.


----

In this repository, we show the software developed by our group in the subject *New Paradigms of Interaction*. During the progress of the course, we have done three practices, which we can group into two projects:

* The first project, encompassed by practice 1, consists of the implementation of a chatbot in VoiceXML, which provides information of different nature about the ETSIIT (Escuela Técnica Superior de Ingeniería Informática y Telecomunicaciones de la Universidad de Granada).

* The second project, which includes practices 2 and 3, is the development of two interconnected applications that provide information about the ETSIIT.

    - The first of these (practice 2) is an application designed for a totem located at the door of the faculty. This totem consists of a 4K TV vertically oriented, and consists of a Leap Motion device for interaction with the application through gestures.
    - On the other hand (practice 3), an Android application is developed to complement the previous application. It allows the registration of students in the system, so that the application can provide users with personalized information. It also allows user authentication to perform certain operations on the totem, such as the (virtual) payment of canteen menus.


## VoiceXML
## Tótem - Leap Motion

Esta práctica consiste en un proyecto implementado en Java con NetBeans. Disponemos de una serie de clases de tipo JPanel. La clase principal es la clase Ventana. Esta funciona como un controlador, que contiene una serie de botones genéricos y un JPanel en el centro en el que se mostrarán el resto de ventanas de la aplicación.

Inicialmente, Ventana muestra la clase Menu\_Inicio, que ofrece una vista con diferentes botones de los diferentes submenús de la aplicación. Al pulsar cada uno de ellos, se muestra el submenú de cada sección. En nuestro caso, hemos desarrollado dos secciones de forma completa: Localización (tanto de clases y despachos como de espacios comunes) y Comedor (donde se muestran los menús y se da la posibilidad de pagarlos usando la aplicación de Android de la siguiente práctica).

<span>![</span><span>Portada aplicación Leap</span><span>]</span><span>(</span><span>https://github.com/Lmmp-02/NPI/readme_assets/portada.jpg</span><span>)</span>

### LeapMotion
El mecanismo de manejo de la aplicación se basa en la detección de manos con Leap Motion. Con el mismo, realizamos las siguientes tareas:

- **Manejo del cursor**: El cursor se mueve siguiendo la posición relativa al Leap de la mano del usuario. Así, tenemos que si la mano se encuentra centrada en el marco de detección del leap, el cursor permanecerá quieto, mientras que si la desplazamos arriba, abajo o a los lados, el cursor seguirá dicha trayectoria. Esto recuerda al uso de un joystick, lo cual es un elemento familiar para mucha gente, por lo que pensamos que es una forma intuitiva de manejar la aplicación.
- **Click**: Para realizar un click en la aplicación, el usuario puede cerrar el puño. Esto será equivalente a pulsar el botón izquierdo del ratón, mientras que el abrir de nuevo la mano equivaldrá a liberar el botón.
- **Swipe horizontal**: El gesto de swipe es un elemento clave en la navegación entre ventanas de nuestra aplicación. El usuario puede conmutar entre las ventanas anteriores y siguientes a la actual realizando un swipe a la izquierda o a la derecha, respectivamente. Claro que, si dichas ventanas no son accesibles, el controlador ignorará el gesto.
- **Swipe vertical**: Este gesto es empleado a la hora de alternar entre elementos de una misma ventana, como ir cambiando de imágenes en los carruseles, o cambiar entre opciones en un ComboBox.
- **Gesto de salida con palmada**: Este gesto se implementa detectando el rápido acercamiento y posterior separación de las palmadas de las manos del usuario, realizando una palmada. En primer lugar se comprueba que el frame capturado por el Leap Motion contenga dos manos. A continuación se calcula la distancia entre las palmas de ambas manos, y dependiendo de los cambios de distancia se interpreta como una palmada, y al detectarla se cierra la aplicación.

### Docencia

En esta sección se ha incorporado un lector de códigos QR donde el usuario puede comprobar su horario, mediante un código QR válido.
    
La aplicación del Leap hace uso de la webcam enseñando por pantalla, mediante un jPanel con un absoluteLayout, la imagen que está tomando la webcam en todo momento, al escanear un código QR, se deshabilita la webcam y el jPanel correspondiente.

A continuación, en la pantalla aparece el horario correspondiente al código QR escaneado.

<span>![</span><span>Menú Docencia</span><span>]</span><span>(</span><span>https://github.com/Lmmp-02/NPI/readme_assets/docencia.jpg</span><span>)</span>


### Trámites

En esta sección se encuentran únicamente tres botones, cada uno simbolizando distintas tareas disponibles: solicitudes genéricas, obtención de certificados académicos y gestión de citas en la secretaría. Todas estas operaciones se efectúan mediante la plataforma electrónica de la universidad.

<span>![</span><span>Menú Trámites</span><span>]</span><span>(</span><span>https://raw.github.com/Lmmp-02/NPI/readme_assets/tramites.jpg</span><span>)</span>

### Profesorado

Se ha incorporado la información de todos los profesores que imparten asignaturas en el cuarto año de la especialidad de Computación y Sistemas Inteligentes. Esto incluye detalles sobre sus horarios de tutorías y oficinas. A través de un menú desplegable, el usuario de la aplicación puede seleccionar el profesor del cual desea obtener información, la cual se muestra posteriormente en un cuadro de texto.

<span>![</span><span>Menú Profesorado</span><span>]</span><span>(</span><span>https://raw.githubusercontent.com/Lmmp-02/NPI/readme_assets/profesorado.jpg</span><span>)</span>

### Localización
La lógica interna del cálculo de rutas internas entre localizaciones de la ETSIIT se ha hecho de tal forma que se pueda seleccionar como origen y destino cualquiera de éstas. Aunque puede no tener demasiado sentido al estar en este caso el origen siempre restringido al totem, el diseño se hizo pensando también en la aplicación android.

Tenemos una clases *Caminos*, que se encarga de cargar los datos correspondientes a las localizaciones y su rutas (explicados a continuación), y calcular el camino óptimo mediante el algoritmo de costo uniforme entre dos localizaciones pasadas como parámetros a uno de sus métodos.


<span>![</span><span>Extracto del grafo de localizaciones ETSIIT</span><span>]</span><span>(</span><span>https://raw.githubusercontent.com/Lmmp-02/NPI/readme_assets/grafo.png</span><span>)</span>

En la Figura anterior, se puede ver una pequeña parte del grafo desarrollado con todos los nodos relevantes de la escuela. Partiendo de ahí se hicieron los dos archivos mencionados anteriormente, *locs.txt*, que contiene el nombre de todos los nodos; y *cams.txt*, que contiene los caminos dirigidos (origen, destino y coste).

La clase *Caminos* dispone de una función que devuelve en un array los nombres de las imágenes y descripciones correspondientes a cada uno de los subcaminos que forman el camino completo. Bastaría con leer esos archivos y mostrar su contenido en los contenedores correspondientes.

<span>![</span><span>Menú Localización</span><span>]</span><span>(</span><span>https://raw.githubusercontent.com/Lmmp-02/NPI/readme_assets/serviciosExt.jpg</span><span>)</span>

### Comedor

En la parte de comedor tenemos distintas pantallas, en la primera podemos seleccionar entre el menú diario o el menú para llevar, posteriormente en el caso de haber seleccionado el menú para llevar debemos seleccionar que tipo de menú queremos ver. Tras esto tanto en menú diario como para llevar se nos mostrarán los menús de toda la semana de la opción seleccionada, para poder ir viendo los menús de cada día basta con ir haciendo swipe hacia arriba o hacia abajo para ir pasándolos.
    
Para poder mostrar los platos de los menús montamos una BBDD de SQL y de ahí obtenemos la información necesaria, construimos la clase Conexion.java para gestionar la conexión con la base de datos, en cuanto a las consultas iniciales como pueden ser la creación de las tablas o la insercción de las filas simplemente usamos un script de SQL, la idea una vez instalado el tótem sería ir actualizando esa BBDD semanalmente de la misma forma que se van actualizando los menús de la página web actual del servicio de comedores.
    
Estando en esta pantalla tienes dos opciones, o hacer swipe hacia la derecha para volver al menú principal, o pasar a reservar dicho menú pulsando el botón de reservar, una vez hecho esto el tótem generará un QR que deberá ser escaneado por la app de Android para poder continuar con el proceso de reserva del menú, adicionalmente la idea es que a la vez que se genera el QR también se esté emitiendo el mensaje vía NFC y con la app de Android también puedas hacer la reserva mediante NFC, aunque por falta de recursos el tótem realmente no emite nada por NFC.

<span>![</span><span>Menú Comedor</span><span>]</span><span>(</span><span>https://raw.githubusercontent.com/Lmmp-02/NPI/readme_assets/comedor.jpg</span><span>)</span>

### Quizz

Se ha añadido un pequeño juego de preguntas tipo quizz, de temática informática, donde el usuario ha de selecciónar la opcion correcta en cada pregunta.
    
El juego esta construido en un JPanel el cual dispone de 2 etiquetas y 3 botones, la primera etiqueta representa la pregunta y la segunda el tiempo restante.Cada botón corresponden a las opciones de la pregunta, si se responde correctamente se marca en color verde el botón, por el contrario si la respuesta es incorrecta el botón se podnrá de color rojo.
    
El usuario dispone de 20 segundos por pregunta y si no responde en este tiempo, se maracará la opción correcta pero no se contabilizara como acierto.Una vez contestadas 10 preguntas aparecerá un pequeño mensaje con el numero de aciertos y fallos, al pulsar en aceptar se cambiará al menú principal.
## Asistente ETSIIT

Hemos decidido realizar la practica en Android Studio, ya que este es el IDE oficial para desarrollo de aplicaciones para Android, en cuanto al lenguaje de programación usado, obtamos por Java frente a Kotlin, debido a nuestro mayor manejo del primero.

En cuanto a la estructura de la app esta consta de una pantalla inicial de inicio de sesión + registro de nuevos usuarios, para luego llevar al usuario a la aplicación como tal con todas sus funcionalidades, el menú principal consta de una barra inferior con botones para acceder a las distintas funciones implementadas, entre las que constan: consulta de horarios, reserva de comedor, información de localizaciones y una interfaz oral.

Para el desarrollo de la misma hacemos uso de distintos sensores del teléfono móvil, estos son: 

- Cámara: Usada para poder capturar los QRs que nos muestra el tótem.
- Lector NFC: Usado para poder capturar las emisiones NFC del tótem (Suponiendo que se le añade esa funcionalidad al tótem), hay que tener en cuenta que no todos los móviles cuentan con este sensor, por lo que hacemos la comprobación al entrar en las partes de la app que usan este sensor y nos salimos en caso de que no se tenga dicho sensor.
- Sensor multitáctil: Es la capacidad del móvil de detectar varios dedos en la pantalla, lo hemos usado para poder detectar el gesto de pago de menú.
- Brújula: Usada para poder guiar al usuario a las localizaciones que desee.
- Acelerómetro: Usado para ir pasando los puntos intermedios de una ruta al solicitar información de una localización, cuando se detecta un cambio brusco en su valor se pasa de imágen.

### Inicio de sesión
En nuestra aplicación, con el fin de poder ofrecer una experiencia personalizada a cada usuario, hemos implementado un sistema de autenticación, en el cual cualquier usuario puede registrarse indicando su nombre de usuario, correo, grado y curso en el que está, además de seleccionar una contraseña segura. Para montar este sistema, hacemos uso de la base de datos de Firebase, concretamente de su funcionalidad de autenticación por correo y contraseña.

No obstante, también se ofrece la posibilidad de entrar en la aplicación como usuario anónimo. A diferencia de los usuarios autenticados, no sabremos los datos del usuario a la hora de emplear la aplicación, lo cual resultará en una navegación algo más lenta, aunque aún funcional.

Estas funcionalidades están implementadas en las actividades *IniciaSesionActivity* y *RegistraUsuarioActivity*.

### Consulta de horarios

Los horarios se muestran en el apartado de *Inicio* en un *TabView* junto con la página web de la facultad. La idea aquí es utilizar la base de datos con la información de los usuarios para mostrar información personalizada en función del grado y el curso.

<span>![</span><span>Consulta de horarios</span><span>]</span><span>(</span><span>https://raw.githubusercontent.com/Lmmp-02/NPI/readme_assets/horario.jpg</span><span>)</span>

### Pago comedor

En este fragment tenemos dos opciones, por un lado usar un lector QR y por otro uno de NFC, cabe recordar que el tótem de la anterior práctica tiene la tarea tanto de generar códigos QR, como NFC para permitir reservar menús en la aplicación de Android.

En el caso de seleccionar la opción de QR la aplicación nos solicitará permisos para usar la cámara y la abrirá, una vez leido un QR, si este tiene el formato correcto nos mostrará la siguiente pantalla, que es un Activity donde aparece el menú deseado, en caso contrario simplemente volverá al fragment anterior.

En el caso de seleccionar la opción de NFC pasaremos a un Activity que espera a que se escanee un emisor de NFC, al hacerlo la idea es que se hiciera lo mismo que con el QR, que nos pasara al Activity que muestra el menú deseado, sin embargo al no disponer de un emisor de NFC real en el tótem lo que hace esta parte en realidad es simplemente mostrar la información del NFC escaneado.

Una vez estamos en el Activity que muestra el menú podemos reservar este, para ello hemos implementado un gesto multitactil, este consiste en hacer swipe hacia arriba con dos dedos, para evitar gestos fantasma y reservar un menú cuando en realidad no querías los dedos deben de estar bastante separados al hacer el swipe up.

Finalmente al realizar el gesto y pagar el menú se muestra una pantalla de confirmación de pago que desaparece automáticamente en unos segundos, enviando al usuario de vuelta al menú principal.

### Localización

Dentro del apartado localización tenemos dos botones funcionales, *Introducir Localización* y *Servicios Externos*:
    
El primero de ellos, abre un selector (en un fragmento) para introducir dos localizaciones cualquiera de la ETSIIT y posteriormente mostrar la ruta óptima entre éstas por medio de una actividad que utiliza la brújula y el acelerómetro.
    
El segundo muestra un listado de servicios externos a la ETSIIT de la UGR, al pulsar en uno de ellos se abre Google Maps (en caso de estar instalado) con la ruta a éste.

A continuación, se van a describir detalladamente cada uno de los selectores así como la actividad que muestra las rutas internas a la facultad y que también es llamada por la interfaz oral:

#### Selector Localizaciones Internas

El selector está implementado en el fragmento *SelectorRutaFragment*, que a su vez contiene otros dos fragmentos dentro de un *TabLayout*, *EspaciosComunesFragment* y *ClasesDespachosFragment*.

Debe ser llamado dos veces, una para seleccionar el origen y otra para seleccionar el destino. Para ello, al crearlo se le pasa como parámetro un String *origen*, si es *null* estamos seleccionando el origen, y si no (ya se ha escogido origen), estamos seleccionando el destino. Se muestra un *textView* para indicar al usuario si se está escogiendo una cosa u tra (origen o destino).

<span>![</span><span>Selector de localizaciones internas</span><span>]</span><span>(</span><span>https://raw.githubusercontent.com/Lmmp-02/NPI/readme_assets/selectorclases.jpg}</span><span>)</span>

El fragmento de espacios comunes (*EspaciosComunesFragment*) consiste en un *ScrollView* al que se le han añadido botones con imágenes (y el nombre) de cada uno de los espacios comunes de la ETSIIT. Para dar feedback al usuario de cuándo ha pulsado un botón, se cambia el background del botón pulsado y se añade un *Handler* para volver a cambiar el color al original y así si el usuario vuelve hacia atrás, que la pantalla esté igual.

El selector de clases y despachos se basa en dos *NumberPicker*, que hacen muy intuitiva la selección de la planta y número de la localización deseada. Este tipo de widgets trabaja internamente con números por lo que debemos tener muy en cuenta el orden de las cadenas mostradas en ellos. Dependiendo si se pulsa el botón de clases o despachos, las opciones mostradas van a variar.

#### Visualizador de ruta

El visualizador de rutas , implementado mediante la actividad *MuestraRutaActivity* recibe como parametros en el *Intent*, el origen y el destino. Como se hizo y explicó en la pŕactica de leap, utilizamos la clase Caminos para calcular la ruta y obtener el nombre de los archivos de las imágenes y descripciones a mostrar.

Sin embargo, para poder utilizar la brújula hemos tenido que hacer algunos cambios. Ahora, el archivo de caminos (dirigidos), además de los dos nodos del camino y el coste correspondiente, incluye también la orientación que se debería seguir para ir desde el origen al destino (en las escaleras no se incluye ya que no tendría mucho sentido).

Junto con la imagen se muestra en el borde un indicador de la dirección que se debe seguir. Para ello se discretiza la orientación resultante respecto al móvil y se muestra como fondo una determinada imagen.

Además, se utiliza el acelerómetro para pasar de imágenes con un movimiento lo suficientemente brusco en el eje Z (adelante y atrás). También se implementó el poder moverse por las imágenes mediante swipe en lugar de con los botones.
        
<span>![</span><span>Visualizador de ruta</span><span>]</span><span>(</span><span>https://raw.githubusercontent.com/Lmmp-02/NPI/readme_assets/ruta.jpg}</span><span>)</span>

#### Selector de Servicios Externos

El funcionamiento del selector de servicios externos es realmente muy similar al de los espacios comunes. La diferencia está en que ahora, cuando se pulsa un botón, se inicia Google Maps (comprobando previamente si está instalado) con el *uri* correspondiente a la  localización en la que se encuentra ese servicio.\\

Un *uri* es una cadena de caracteres que identifica un recurso en un sistema de manera única. Aplicado a las localizaciones se incluyen en él el protocolo o formato utilizado, las coordenadas de la localización, y el nombre de ésta. En nuestro caso no incluimos coordenadas ya que Maps es capaz de obtenerlas mediante el nombre (que normalmente incluye la dirección).

<span>![</span><span>Servicios Externos</span><span>]</span><span>(</span><span>https://raw.githubusercontent.com/Lmmp-02/NPI/readme_assets/servext.jpg}</span><span>)</span>

## Interfaz oral

### Introducción
La aplicación anterior cuenta con un asistente de voz virtual, al que hemos llamado *WaluigiBot*. Para su implementación, hemos usado la plataforma DialogFlow ES de Google, apoyada por un *webhook* que puede acceder a la información que hemos guardado en nuestra base de datos de Firebase. Para conectar el bot de DialogFlow con nuestra aplicación, utilizamos la interfaz de la plataforma *Kommunicate*.

En esta sección, vamos a indagar en la implementación de este sistema.

### Integración con la app

Tal y como hemos comentado, hemos empleado la interfaz y la API de *Kommunicate* para la integración de *WaluigiBot* en la aplicación. La clase *InterfazOralActivity* es la encargada de ello. Cuando el usuario pulsa en el menú principal la opción del asistente, se sigue el siguiente flujo de acción:

- La aplicación muestra una pantalla de carga de la interfaz oral.
- Si el usuario no ha dado permisos para usar el micrófono a la aplicación, se le piden. En caso de que no los dé, la interfaz oral deshabilita la opción de usar el micrófono, de forma que el usuario solo puede comunicarse empleando un chat de texto. 
- Creamos la interfaz oral empleando la API de \textit{Kommunicate}.
    - Conectamos la aplicación con nuestro bot llamando a la función \textit{Kommunicate.init} y especificando el id de nuestro bot. Además, habilitamos sus opciones de TTS y STT para que, efectivamente, tengamos una interfaz oral.
    - Creamos un usuario de \textit{Kommunicate} para nuestro usuario actual. Este usuario comparte la misma ID que la autenticación de Firebase, de forma que podemos identificar fácilmente al usuario en el chat de voz.
    - Iniciamos sesión con dicho usuario, y creamos una conversación con el bot si no existe. En caso de tener ya una, la abrimos.
    - 
El resultado de los anteriores pasos es la interfaz que se puede ver en la siguiente imagen. Cabe destacar que los colores que emplea la interfaz han sido modificados para que combinen mejor con el estilo de la app, usando para ello el archivo *applozic-settings.json*.

<span>![</span><span>Vista de la interfaz oral. En la esquina inferior derecha, tenemos el botón para activar el \textit{Speech To Text}, representado por el micrófono.</span><span>]</span><span>(</span><span>https://raw.githubusercontent.com/Lmmp-02/NPI/readme_assets/interfaz-kommunicate.jpeg}</span><span>)</span>

### Webhook
DialogFlow ES ofrece un estupendo modelo de reconocimiento de \textit{intents}, pero tiene bastantes limitaciones cuando la respuesta que se espera del bot cambia mucho según los parámetros que se le pasan al bot. Es por esto que nos hemos visto empujados a implementar un \textit{webhook}

Un \textit{webhook} es un servidor externo que ayuda a DialogFlow a atender aquellas peticiones más complejas, o que necesiten consultar información de la base de datos de Firebase. DialogFlow permite configurar ciertos \textit{intents} para que, en vez de manejarlos por sí mismo, mande la información de la petición a nuestro servidor en formato JSON, y allí nos encarguemos de gestionar la petición y responder de forma adecuada. Este mecanismo tiene también la ventaja de permitir la consulta de datos en nuestra base de datos de Firebase, compartiéndola con la aplicación Android.\\

Para montar el servidor, hemos empleado la plataforma \textit{Glitch}, que nos permite montar dicho servidor. El lenguaje que usaremos en el mismo será \textit{Javascript}, en conjunción con el framework \textit{express}. Nuestro servidor posee dos archivos principales: 

- *package.json*: En él, se especifican los recursos que se utilizan en el servidor, de forma que al iniciarse se descargan las dependencias ahí presentes. De entre estos recursos, podemos destacar la librería de \textit{dialogflow-fulfillment}, que nos permitirá enviar respuestas y contextos de vuelta a DialogFlow.- - *server.js*: Aquí se implementa la lógica del servidor.

DialogFlow envía a la información al servidor mediante el puerto 3000, con peticiones POST en formato JSON. De dicho JSON podemos obtener toda la información referente al \textit{intent} activo, los parámetros detectados y los diferentes contextos activos. Por tanto, nuestro archivo \textit{server.js} escucha dicho puerto, e implementa las funciones de manejo de peticiones dentro de la sección de POST.

### Funcionalidades

En esta sección, vamos a realizar un recorrido por todas las funcionalidades que hemos implementado en nuestra interfaz oral.

#### Consultas sobre profesores
En primer lugar, el bot es capaz de responder consultas simples sobre cierto profesor. Concretamente, pueden formularse las siguientes peticiones:

- ConsultaDespacho/ConsultaDespacho\_Contexto: Permite obtener el despacho de cierto profesor. El primero requiere que se especifique el profesor de forma explícita, mientras que el segundo toma del contexto de la conversación dicha información. Idealmente, las dos siguientes funcionalidades deberían tener también una versión "Contexto", pero hemos carecido del tiempo suficiente para extender esta funcionalidad a ellas. Además, una vez especificado el despacho, el asistente ofrece al usuario ir a dicha localización, pasando opcionalmente a la función de localización que veremos más adelante.
- ConsultaProfesor\_Asignatura: Permite obtener las asignaturas que da cierto profesor, especificándole el mismo. Para ello, una vez obtiene el profesor, realiza una consulta en la BBDD para ver qué asignaturas imparte, y se las muestra al usuario.
- ConsultaAsignatura\_Profesores: Permite obtener los profesores que imparten cierta asignatura, indicada por el usuario.

#### Redirección a función de localización
Nuestra interfaz oral también es capaz de captar origen y destino del usuario, para posteriormente llamar a la función de localización de la aplicación de Android con dichos parámetros. Para ello, implementamos una serie de intents dedicados a captar el origen y destino de diferentes maneras y en cualquier orden, tal y como se puede ver en la figura \ref{fig:io_grafolocalizacion}.

<span>![</span><span>Grafo de flujo de los posibles diálogos para obtener origen y destino del usuario. Los cuadrados representan los contextos activos en cada momento, mientras que las flechas indican las transiciones posibles entre estados a través de diferentes \textit{intents}, indicados por los nombres sobre las flechas.</span><span>]</span><span>(</span><span>https://raw.githubusercontent.com/Lmmp-02/NPI/readme_assets/interfazoral_grafolocalizacion.jpeg}</span><span>)</span>

En esta figura, vemos que en la mayoría de flechas tenemos dos versiones: una para localizaciones normales y otras para despachos. A estas últimas se les comunica que se quiere ir al despacho de cierto profesor, y la interfaz oral se encarga de encontrar el despacho mediante consulta a la base de datos. También vemos que existe la opción de decir dos localizaciones base en un único \textit{intent}, de forma que es el propio DialogFlow el que tiene que distinguir cuál de ellas es el origen y cuál de ellas es el destino. A veces confunde ambas si la frase está formulada de manera extraña, pero suele distinguirlas bien en la mayoría de casos.

Una vez se obtiene toda la información del usuario y el mismo confirma que la ruta es correcta, redirigimos al usuario a la función de localización implementada anteriormente. Para hacer esto, se implementa un \textit{listener} de los mensajes que llegan del bot en nuestra aplicación. Si dicho mensaje coincide con cierta sintaxis, se reconocen automáticamente el origen y el destino, y la aplicación pasa a mostrar la ruta. Una vez se terminan las indicaciones, el programa vuelve a abrir la interfaz oral para seguir la conversación.

#### \subsubsection{Redirección a pago de comedores}

De forma similar al apartado anterior, también se puede emplear la interfaz oral para acceder a los diferentes métodos de pago del comedor que hemos implementado: tanto QR como NFC.

<span>![</span><span>Grafo de flujo de los posibles diálogos para el pago del comedor.</span><span>]</span><span>(</span><span>https://raw.githubusercontent.com/Lmmp-02/NPI/readme_assets/interfazoral_grafopago.jpeg}</span><span>)</span>

En la figura \ref{fig:io_grafopago}, podemos ver que un usuario puede acceder de manera directa a ambas funcionalidades, mientras que si no se concreta el método de pago, \textit{WaluigiBot} pregunta al usuario por cuál de ellos prefiere. \textit{PagoComedor\_Vacio\_Ayuda} informa al usuario de los tipos de pago disponibles en la aplicación.

#### Consulta de horarios
Un estudiante puede preguntar a la interfaz sobre su horario, pudiendo obtener información sobre qué clase tiene cierto día a cierta hora. En este caso de uso de la interfaz se utiliza mucha información contextual, como el día y hora actuales (si no se especifican), o el curso y grado del usuario (si el mismo está registrado, consultándose en la base de datos).

Las conversaciones en este caso comienzan con el usuario pidiendo la clase que le toca ahora, o cierto día a cierta hora. El bot es capaz de reconocer correctamente las palabras 'hoy' y 'mañana' como días. Posteriormente, el bot puede seguir los siguientes flujos:

- Si el usuario no se ha registrado en la aplicación y ha accedido como anónimo, le pediremos el grado y curso del que desea obtener la información. El contexto en este caso será 'PreguntaCurso', y una vez se cumplimenten los campos se seguirá el flujo de la conversación.
- Si conocemos el grado y curso del usuario, pero este no se encuentra en la base de datos, el bot se disculpa diciendo que no puede consultar el horario del alumno, y preguntando si puede ayudarlo en otra cosa.
- Si obtenemos el horario pero vemos que a dicha hora hay varias clases a la vez (grupos reducidos de prácticas), el bot pregunta al usuario el grupo de prácticas, guardando en el contexto 'PreguntaSubgrupo' las alternativas encontradas. Una vez el usuario especifique el grupo, se pasará al siguiente (y último) caso.
- Finalmente, si el bot conoce la clase que le toca al estudiante, le informa tanto de la asignatura como del aula donde se imparte, ofreciendo al usuario ayuda para ir hasta allí.

#### Consultas de menús del comedor
Finalmente, el bot puede informar de qué hay para comer en los menús del comedor en cualquier día de la semana. También distingue entre menú general o vegetariano, preguntando al usuario cuál quiere en caso de no especificarlo.

Tambien se incluye información sobre los horarios de la cafetería y el comedor los cuales pueden ser consultados por el usuario.
