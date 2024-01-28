const express = require("express");
const app = express();
const { WebhookClient } = require("dialogflow-fulfillment");
const fb_admin = require('firebase-admin');

//Conexion con BBDD
var serviceAccount = require('./qrgen-7cd84-firebase-adminsdk-l7kla-953d480234.json');
fb_admin.initializeApp({
  credential: fb_admin.credential.cert(serviceAccount),
  databaseURL: 'https://qrgen-7cd84-default-rtdb.firebaseio.com/'
});
const db = fb_admin.database();


app.get("/", function (req, res) {
  res.send("Hello World");
});


//-----Aqui llegan las peticiones de DialogFlow------
app.post("/webhook", express.json(), function (req, res) {
  
  const agent = new WebhookClient({ request: req, response: res });
  const uid = req.body.originalDetectIntentRequest.payload.from;
  
  console.log("Dialogflow Request headers: " + JSON.stringify(req.headers));
  console.log("Dialogflow Request body: " + JSON.stringify(req.body));

  // Obtiene información del usuario emisor del mensaje
  async function getUser(uid) {
    //const uid = req.body.queryResult()
    if(uid === 'e.jorgelremacho@go.ugr.es'){
      return null;
    }
    try {
      const refUserUid = db.ref('user').child(uid);
      const snapshot = await refUserUid.once('value');
      const usuario = snapshot.val();
      console.log('Usuario: ' + JSON.stringify(usuario));
      return usuario;
    } catch (error) {
      //console.error('Error al obtener datos:', error);
      throw error; // Relanza el error para que sea manejado por el siguiente .catch
    }
  }
  
  // Obtiene información del profesor a partir de su nombre
  async function getProfesor(profesor) {
    try {
      const refProfesor = db.ref('profesor').child(profesor);
      const snapshot = await refProfesor.once('value');
      const prof = snapshot.val();
      console.log('Profesor: ' + JSON.stringify(prof.despacho));
      return prof;
    } catch (error) {
      console.error('Error al obtener datos:', error);
      throw error; // Relanza el error para que sea manejado por el siguiente .catch
    }
  }
  
  //////////////////////////////////INTENTS BASE//////////////////////////////////
  //Saludo personalizado
  async function welcome(agent) {
    if(uid !== 'e.jorgelremacho@go.ugr.es'){  //Primer mensaje (metadatos incorrectos)
      try {
        const usuario = await getUser(uid);
        if (usuario !== null) {
          agent.add(`¡Bienvenido, ${usuario.nombre_usuario}!`);
        } else {
          agent.add('¡Bienvenido, desconocido!');
        }
      } catch (error) {
        //console.error('Error al obtener usuario:', error);
        agent.add('¡Bienvenido, desconocido!');
      }
    }
    else{
      console.log("Primer mensaje");
      agent.add('¡Muy buenas!');
    }
    agent.add('Aquí WaluigiBot, ¿tienes alguna consulta?')
  }
  
  //Funcion de prueba del webhook
  function ProbandoWebhook(agent) {
    agent.add(`Esto es un Easter Egg! :0`);
  }
  
  
  //////////////////INTENTS RELACIONADOS CON DESPACHOS//////////////////

  //Busca el despacho y la zona de un profesor
  async function buscaDespacho(profesor) {
    let despacho = {nombre:null, loc:null};
    try{
      const p = await getProfesor(profesor);
      despacho = p.despacho;
    }catch (error){
      console.error('Error al obtener despacho:', error);
    }
    return despacho;
  }
  
  async function ConsultaDespacho(agent) {
    let profesor = req.body.queryResult.parameters.profesor;
    //Simulamos consulta en base de datos
    try{
      let busqueda = await buscaDespacho(profesor);
      if(busqueda.nombre === null){
        agent.add(`Lo siento, no sé donde está el despacho de ` + profesor + `. ¿Deseas algo más?`);
      }
      else{
        agent.add(`El despacho de `+profesor+` es el `+ busqueda.nombre + `. ¿Quieres que te lleve?`)
        agent.context.set({ name: 'Localizacion_Destino', lifespan: 1, parameters: { destino: busqueda.nombre }});
        agent.context.set({ name: 'ConsultaDespacho_SiNo', lifespan: 1, parameters: {}});
      }
    }catch(error){
      console.error('Error en consulta despacho:',error);
    }
  }
  
  async function IrADespacho(agent) {
    let profesor = req.body.queryResult.parameters.profesor;
    //Simulamos consulta en base de datos
    let busqueda = await buscaDespacho(profesor);
    if(busqueda.nombre === null){
      agent.add(`Lo siento, no sé donde está el despacho de ` + profesor + `. ¿Deseas algo más?`);
      agent.context.set({ name: 'Localizacion_Origen', lifespan: 0, parameters: {}});
      agent.context.set({ name: 'Localizacion_Destino', lifespan: 0, parameters: {}});
    }
    else{
      agent.add(`Ok, entonces iremos al despacho `+ busqueda.nombre + `.`)
      agent.context.set({ name: 'Localizacion_Destino', lifespan: 5, parameters: { destino: busqueda.nombre }});
      return busqueda.nombre
    }
    agent.context.set({ name: 'Localizacion_Vacio', lifespan: 0, parameters: {}})
  }
  
  async function IrDesdeDespacho(agent) {
    let profesor = req.body.queryResult.parameters.profesor;
    //Simulamos consulta en base de datos
    let busqueda = await buscaDespacho(profesor)
    if(busqueda.nombre === null){
      agent.add(`Lo siento, no sé donde está ese despacho, así que no puedo calcular la ruta :(. ¿Puedo ayudarte en algo más?`);
      agent.context.set({ name: 'Localizacion_Origen', lifespan: 0, parameters: {}});
      agent.context.set({ name: 'Localizacion_Destino', lifespan: 0, parameters: {}});
    }
    else{
      agent.add(`Ok, entonces partiremos desde el despacho `+ busqueda.nombre + `.`)
      agent.context.set({ name: 'Localizacion_Origen', lifespan: 5, parameters: { origen: busqueda.nombre }});
      return busqueda.nombre
    }
    agent.context.set({ name: 'Localizacion_Vacio', lifespan: 0, parameters: {}})
  }
  
  async function LocalizacionIniciaDestinoDespacho(agent) {
    await IrADespacho(agent);
    agent.add(`¿Dónde estamos ahora?`);
  }
  
  async function LocalizacionVacioOrigenDespacho(agent) {
    await IrDesdeDespacho(agent);
    agent.add(`¿A dónde quieres ir?`);
  }
  
  async function LocalizacionVacioDestinoDespacho(agent) {
    await IrDesdeDespacho(agent);
    agent.add(`¿Dónde estamos ahora?`);
  }
  
  async function LocalizacionOrigenFinDespacho(agent) {
    let destino = await IrADespacho(agent);
    // Filtrar el array de outputContexts para encontrar el elemento con el nombre que contiene "localizacion_origen"
    const contextoLocalizacionOrigen = req.body.queryResult.outputContexts.find(contexto =>
      contexto.name.includes("localizacion_origen")
    );
    let origen;
    // Verificar si se encontró el contexto
    if (contextoLocalizacionOrigen) {
      // Acceder al campo "origen"
      origen = contextoLocalizacionOrigen.parameters.origen;
      agent.add(`Muy bien, te puedo llevar desde ` + origen +` hasta `+destino);
      agent.add(`¿Vamos para allá?`)
    } else {
      console.log("No se encontró el contexto con nombre que contiene 'localizacion_origen'");
    }
  }
  
  async function LocalizacionDestinoFinDespacho(agent) {
    let destino;
    let origen = await IrDesdeDespacho(agent);
    // Filtrar el array de outputContexts para encontrar el elemento con el nombre que contiene "localizacion_destino"
    const contextoLocalizacionDestino = req.body.queryResult.outputContexts.find(contexto =>
      contexto.name.includes("localizacion_destino")
    );
    
    // Verificar si se encontró el contexto
    if (contextoLocalizacionDestino) {
      // Acceder al campo "origen"
      destino = contextoLocalizacionDestino.parameters.destino;
      agent.add(`Muy bien, te puedo llevar desde ` + origen +` hasta `+destino);
      agent.add(`¿Pasamos a la función de localización?`)
    } else {
      console.log("No se encontró el contexto con nombre que contiene 'localizacion_destino'");
    }
  }
  
  async function ConsultaDespacho_Contexto(agent){
    let profesor;
    // Filtrar el array de outputContexts para encontrar el elemento con el nombre que contiene "contextoProfesor"
    const contextoProfesor = req.body.queryResult.outputContexts.find(contexto =>
      contexto.name.includes("contextoprofesor")
    );
    // Verificar si se encontró el contexto
    if (contextoProfesor) {
      console.log("ContextoProfesor: "+JSON.stringify(contextoProfesor));
      profesor = contextoProfesor.parameters.profesor;
      console.log("Profesor: "+profesor);
      let busqueda = await buscaDespacho(profesor)
      if(busqueda.nombre === null){
        agent.add(`Lo siento, no sé donde está el despacho de ` + profesor + `. ¿Deseas algo más?`);
      }
      else{
        agent.add(`El despacho de `+profesor+` es el `+ busqueda.nombre + `. ¿Quieres que te lleve?`)
        agent.context.set({ name: 'Localizacion_Destino', lifespan: 1, parameters: { destino: busqueda.nombre }});
        agent.context.set({ name: 'ConsultaDespacho_SiNo', lifespan: 1, parameters: {}});
      }
    }
  }
  
  /////////////////INTENTS RELACIONADOS CON CONSULTAS PROFESOR<->ASIGNATURA//////////////////////
  //Busca profesores por asignatura
  async function obtenerProfesoresPorAsignatura(asignaturaBuscada) {
    let nombresProfesores = null;
    try {
      const refUserUid = db.ref('profesor');
      const snapshot = await refUserUid.once('value');
      const profesores = snapshot.val();
      console.log('profesores: ' + JSON.stringify(profesores));
      // Filtrar los profesores por la asignatura proporcionada
      nombresProfesores = Object.keys(profesores).filter((nombre) => {
        return profesores[nombre].asignatura === asignaturaBuscada;
      });
      console.log('profesoresFiltrados: ' + JSON.stringify(nombresProfesores));
      
    } catch (error) {
      //console.error('Error al obtener datos:', error);
      throw error; // Relanza el error para que sea manejado por el siguiente .catch
    }
    console.log('Nombres profesores: ' + nombresProfesores);
    return nombresProfesores;
  }
  
  async function ConsultaAsignaturaProfesores(agent){
    let profesor;
    // Obtenemos asignatura de los parametros
    const asignatura = req.body.queryResult.parameters.Asignatura;
    console.log('Asignatura: ', asignatura);
    try{
      let busqueda = await obtenerProfesoresPorAsignatura(asignatura);  //Obtenemos los profes de esa asignatura
      //No se encuentran profesores
      if(JSON.stringify(busqueda) === "[]"){
        agent.add(`Actualmente no hay ningún profesor de `+asignatura+' en los registros.');
      }
      //Se encuentra mas de un profesor
      else if(JSON.stringify(busqueda).includes(',')){
        agent.add(`Los profesores de `+asignatura+` son: `+busqueda.join(', ')+`.`);
        agent.context.set({ name: 'contextoasignatura', lifespan: 2, parameters: {nombre: asignatura}});
      }
      //Se encuentra un unico profesor
      else{
        agent.add(`El profesor de `+asignatura+` es `+busqueda[0]+`.`);
        agent.context.set({ name: 'contextoprofesor', lifespan: 2, parameters: { profesor: busqueda[0] }});
        agent.context.set({ name: 'contextoasignatura', lifespan: 2, parameters: {nombre: asignatura}});
      }
    } catch(error){
      console.error('Error en ConsultaAsignaturaProfesores', error)
      console.log('Error en ConsultaAsignaturaProfesores');
      agent.add(`Error en la asignatura.`);
    }
  }
  
  async function ConsultaProfesorAsignatura(agent){
    let asignatura;
    //Obtenemos profesor de los parámetros
    const profesor = req.body.queryResult.parameters.profesor;
    console.log('Profesor: ', profesor);
    try{
      //Obtenemos asignatura
      const refUserUid = db.ref('profesor').child(profesor).child('asignatura');
      const snapshot = await refUserUid.once('value');
      const busqueda = snapshot.val();
      console.log('Busqueda: '+busqueda);
      //No se encuentran profesores
      if(JSON.stringify(busqueda) === null){
        agent.add(`${profesor} no tiene ninguna asignatura`);
      }
      //Se encuentra mas de una asignatura
      else if(JSON.stringify(busqueda).includes(',')){
        agent.add(`${profesor} tiene las siguientes asignaturas: `+busqueda.join(', ')+`.`);
        agent.context.set({ name: 'contextoprofesor', lifespan: 2, parameters: {nombre: profesor}});
      }
      //Se encuentra solo una asignatura
      else{
        agent.add(`${profesor} imparte ${busqueda}.`);
        agent.context.set({ name: 'contextoprofesor', lifespan: 2, parameters: {nombre: profesor}});
        agent.context.set({ name: 'contextoasignatura', lifespan: 2, parameters: {nombre: busqueda}});
      }
    } catch(error){
      console.error('Error en ConsultaAsignaturaProfesores', error)
      console.log('Error en ConsultaAsignaturaProfesores');
      agent.add(`Error en la asignatura.`);
    }
  }
  
  /////////////////INTENTS RELACIONADOS CON CONSULTACLASEACTUAL//////////////////////
  
  async function buscarHorario(curso, grado) {
    try{
      //Obtenemos los horarios de la BBDD
      const ref = db.ref('schedule');
      const snapshot = await ref.once('value');
      const horarios = snapshot.val();
      
      //Buscamos el horario correspondiente
      for (const clave in horarios) {
        if (
          horarios[clave].curso === curso &&
          horarios[clave].grado === grado
        ) {
          return horarios[clave];
        }
      }
    } catch (error){
      console.error('Error en buscarHorario', error)
      agent.add(`Error en buscarHorario.`);
    }
    console.log('Horario no encontrado');
    // Si no se encuentra ningún elemento que cumpla con las condiciones
    return null;
  }
  
  //Obtiene la hora correspondiente a la clase actual
  function horaActual(){
    const fechaHoraActual = new Date();
    const hora = fechaHoraActual.getHours();
    if(hora < 8 || hora > 14){
      return null;
    }
    else if(hora < 9){
      return "8:30";
    }
    else if(hora < 10){
      return "9:30";
    }
    else if(hora < 11){
      return "10:30";
    }
    else if(hora < 12){
      return "11:30";
    }
    else if(hora < 13){
      return "12:30";
    }
    else{
      return "13:30";
    }
  }
  
  
  async function ConsultaClaseActual(agent){
    //Comprobamos si el usuario está registrado en la BBDD.
    try {
      const usuario = await getUser(uid);
      //Si el usuario esta registrado
      if (usuario !== null) {
        //Obtenemos el horario del usuario
        const grado = usuario.grado;
        const curso = usuario.curso;
        const horario = await buscarHorario(curso, grado);
        //Si no tenemos el horario en la BBDD
        if(horario === null){
          agent.add("Lo sentimos, no conozco aun el horario del curso "+curso+" de "+grado);
          agent.add("¿En qué más puedo ayudarte, "+usuario.nombre_usuario+"?")
        }
        //Si encontramos el horario
        else{
          console.log("HORARIO: "+JSON.stringify(horario.horario));
          agent.add("Comprobando horario...");

          //Obtenemos el dia y hora actuales
          const dia = hoyOmanana("hoy");
          const hora = horaActual();
          console.log("Dia: "+dia);
          console.log("Hora: "+hora);
          
          //Obtenemos la casilla correspondiente en el horario
          const casilla_horario = horario.horario?.dia?.hora;
          console.log("HORARIO hoy: "+JSON.stringify(casilla_horario));
          if(casilla_horario !== undefined){
            //Vemos si hay subgrupos en dicha casilla
            if(casilla_horario?.subgrupo1 !== undefined){
              //Consultamos en qué subgrupo está el usuario
              agent.add("He encontrado varias clases en esa franja horaria. ¿En qué subgrupo estás?");
              agent.context.set({ name: 'PreguntaSubgrupo', lifespan: 3, parameters: {clase:casilla_horario}});
            }
            //Si no, mostramos al usuario su clase
            else{
              agent.add("Tu clase de las "+hora+" es "+casilla_horario.asignatura+" en la clase "+casilla_horario.clase);
              agent.add("¿Quieres que te lleve?");
              agent.context.set({ name: 'ContextoClase', lifespan: 3, parameters: {clase:casilla_horario.clase}});
            }
          }
          else{
            agent.add("Ahora mismo no tienes clase. ¡Eres libre!")
            agent.add("Si necesitas algo más, no dudes en preguntarme.")
          }
        }
      }
      //Si no esta registrado, preguntamos curso 
      else {
        agent.add('De acuerdo. Me puedes decir en qué curso y grado estás, forastero?');
        agent.context.set({ name: 'PreguntaCurso', lifespan: 3});
      }
    } catch (error) {
      //console.error('Error al obtener usuario:', error);
      agent.add('Ha ocurrido un error :(. ¿Necesitas alguna otra cosa?');
    }
  }
  
  async function ConsultaClaseDiaYHora(agent){
    //Obtenemos el dia y hora
    let dia = req.body.queryResult.parameters?.dia;
    if(dia === undefined){
      dia = hoyOmanana("hoy");
    }
    else{
      dia = hoyOmanana(dia);
    }
    let hora = req.body.queryResult.parameters.Hora;
    console.log("Dia: "+dia);
    console.log("Hora: "+hora);
    
    //Comprobamos si el usuario está registrado en la BBDD.
    try {
      const usuario = await getUser(uid);
      //Si el usuario esta registrado
      if (usuario !== null) {
        //Obtenemos el horario del usuario
        const grado = usuario.grado;
        const curso = usuario.curso;
        const horario = await buscarHorario(curso, grado);
        //Si no tenemos el horario en la BBDD
        if(horario === null){
          agent.add("Lo sentimos, no conozco aun el horario del curso "+curso+" de "+grado+".");
          agent.add("¿En qué más puedo ayudarte, "+usuario.nombre_usuario+"?")
        }
        //Si encontramos el horario
        else{
          console.log("HORARIO: "+JSON.stringify(horario.horario));
          agent.add("Comprobando horario...");

          //Obtenemos la casilla correspondiente en el horario
          const casilla_horario = horario.horario?.[dia]?.[hora];
          console.log("HORARIO hoy: "+JSON.stringify(casilla_horario));
          if(casilla_horario !== undefined){
            //Vemos si hay subgrupos en dicha casilla
            if(casilla_horario?.subgrupo1 !== undefined){
              //Consultamos en qué subgrupo está el usuario
              agent.add("He encontrado varias clases en esa franja horaria. ¿En qué subgrupo estás?");
              agent.context.set({ name: 'PreguntaSubgrupo', lifespan: 3, parameters: {clase:casilla_horario}});
              agent.context.set({ name: 'DiaYHora', lifespan: 5, parameters: {dia:dia, hora:hora}});
            }
            //Si no, mostramos al usuario su clase
            else{
              agent.add("Tu clase de las "+hora+" es "+casilla_horario.asignatura+" en la clase "+casilla_horario.clase);
              agent.add("¿Quieres que te lleve?");
              agent.context.set({ name: 'ContextoClase', lifespan: 3, parameters: {clase:casilla_horario.clase}});
            }
          }
          //Si no tiene clase, lo comunicamos
          else{
            agent.add("A esa hora eres libre. ¡Disfruta!")
          }
        }
      }
      //Si el usuario no esta registrado, preguntamos curso y grado
      else {
        agent.add('De acuerdo. Me puedes decir en qué curso y grado estás, forastero?');
        agent.context.set({ name: 'PreguntaCurso', lifespan: 3});
        agent.context.set({ name: 'DiaYHora', lifespan: 5, parameters: {dia:dia, hora:hora}});
      }
    } catch (error) {
      console.error('Error al obtener usuario:', error);
      agent.add('Ha ocurrido un error al obtener el usuario :(. ¿Necesitas alguna otra cosa?');
    }
  }
  
  async function ConsultaClaseActualCurso(agent){
    try {
      //Obtenemos fecha y hora
      let dia, hora;
      let contextoDyH = req.body.queryResult.outputContexts.find(contexto =>
        contexto.name.includes("diayhora")
      );
      if (contextoDyH) {
        dia  = contextoDyH.parameters.dia;
        hora = contextoDyH.parameters.hora;
      }else{
        dia = hoyOmanana("hoy");
        hora = horaActual();
      }
      console.log("Dia: "+dia);
      console.log("Hora: "+hora);
      
      //Obtenemos el horario del usuario por los parametros
      const grado =  req.body.queryResult.parameters.Grado;
      const curso =  req.body.queryResult.parameters.curso;
      const horario = await buscarHorario(curso, grado);
      //Si no tenemos el horario en la BBDD
      if(horario === null){
        agent.add("Lo sentimos, no conozco aun el horario del curso "+curso+" de "+grado);
        agent.add("¿En qué más puedo ayudarte?")
        //Eliminamos contextos
        agent.context.set({ name: 'PreguntaCurso', lifespan: 0});  
        agent.context.set({ name: 'DiaYHora', lifespan: 0});
      }
      //Si encontramos el horario
      else{
        console.log("HORARIO: "+JSON.stringify(horario.horario));
        agent.add("Comprobando horario...");

        //Obtenemos la casilla correspondiente en el horario
        const casilla_horario = horario.horario?.[dia]?.[hora];
        console.log("HORARIO hoy: "+JSON.stringify(casilla_horario));
        if(casilla_horario !== undefined){
          //Vemos si hay subgrupos en dicha casilla
          if(casilla_horario?.subgrupo1 !== undefined){
            //Consultamos en qué subgrupo está el usuario
            agent.add("He encontrado varias clases en esa franja horaria. ¿En qué subgrupo estás?");
            agent.context.set({ name: 'PreguntaSubgrupo', lifespan: 3, parameters: {clase:casilla_horario}});
          }
          //Si no, mostramos al usuario su clase
          else{
            agent.add("Tu clase de las "+hora+" es "+casilla_horario.asignatura+" en la clase "+casilla_horario.clase);
            agent.add("¿Quieres que te lleve?");
            agent.context.set({ name: 'ContextoClase', lifespan: 3, parameters: {clase:casilla_horario.clase}});
          }
        }
        //Si no hay clase, lo notificamos
        else{
          agent.add("A esa hora eres libre. ¡Disfruta!")
          agent.context.set({ name: 'DiaYHora', lifespan: 0});
        }
        
        agent.context.set({ name: 'PreguntaCurso', lifespan: 0});  //Eliminamos contexto
      }
    } catch (error) {
      console.error('Error en ConsultaClaseActualCurso', error);
      agent.add('Ha ocurrido un error :(. ¿Necesitas alguna otra cosa?');
    }
  }
  
  async function ConsultaClaseActualSubgrupo(agent){
    // Obtenemos las clases del contexto
    // Buscamos el contexto PreguntaSubgrupo
    let subgrupo = req.body.queryResult.parameters.subgrupo;
    let clase;
    let casilla_horario = {}
    let contextoClase = req.body.queryResult.outputContexts.find(contexto =>
      contexto.name.includes("preguntasubgrupo")
    );
    if (contextoClase) {
      casilla_horario =contextoClase.parameters.clase;
    }
    
    //Encontramos la clase de dicho subgrupo
    if(casilla_horario?.[subgrupo] !== undefined){
      clase = casilla_horario?.[subgrupo]
    }
    else{
      agent.add("Error al buscar clase en "+subgrupo);
      return;
    }
      agent.add("Tu clase es "+clase.asignatura+" en la clase "+clase.clase);
      agent.add("¿Quieres que te lleve?");
      agent.context.set({ name: 'PreguntaSubgrupo', lifespan: 0});
      agent.context.set({ name: 'ContextoClase', lifespan: 3, parameters:{clase:clase.clase}});
  }
  
  function ConsultaClaseActualLlevarSi(agent){
    //Obtenemos la clase del contextoClase
    let destino = null
    let contextoClase = req.body.queryResult.outputContexts.find(contexto =>
      contexto.name.includes("contextoclase")
    );
    
    destino =contextoClase.parameters.clase;
    agent.add("¡Muy bien, vamos a la clase "+destino+"! ¿Desde dónde vamos a partir?");
    agent.context.set({ name: 'Localizacion_Destino', lifespan: 5, parameters:{destino:destino}});
    agent.context.set({ name: 'ContextoClase', lifespan: 0});
  }
  
  function ConsultaClaseActualLlevarNo(agent){
    //Borramos contextoClase
    agent.add("Ok... Si necesitas algo más, no dudes en decírmelo :)");
    agent.context.set({ name: 'ContextoClase', lifespan: 0});
  }
  
  
  //----------------------------------------------------------
  //A partir de aquí son las funciones que he hecho(Manuel)

  function ConsultaDia_Contexto(agent){
    // Filtrar el array de outputContexts para encontrar el elemento con el nombre que contiene "contextodia"
    let dia = ""
    let contextoDia = req.body.queryResult.outputContexts.find(contexto =>
      contexto.name.includes("contextodia")
    );
    if (contextoDia) {
      dia =contextoDia.parameters.Dia;
    }
    return dia;
  }
  
  function hoyOmanana(dia){
    const moment = require('moment');
    moment.locale('es');
    const hoy = moment().format('dddd');
    const manana = moment().add(1, 'days').format('dddd');    

    if (dia.toLowerCase() === 'hoy') {
      res = hoy;
    } else if (dia.toLowerCase() === 'mañana') {
      res = manana;
    }
    else return dia;
    return res
  }
  
  function buscaDia(dia){
    let res = 0;    
    let dias =['lunes','martes','miércoles','jueves','viernes']    
    if (dias.includes(dia)){
      res = 1;
    }
    return res;
  }

  
  function buscaMenu(dia,opcion){
    let menu = {platos: null};
    
    if (opcion === 'normal'){
      switch (dia) {
        case 'lunes':
            menu = {platos: `Menú del Lunes: Primero Arroz brut, Segundo Flamenquines de jamón y queso con salsa de tomate dulce, Acompañamiento Pimientos fritos , Postre Manzana`};
            break;
        case 'martes':
            menu = {platos: `Menú del Martes: Primero Empanada de sobrasada, queso y cabello, Segundo Perca a la florentina, Acompañamiento Patatas a la riojana, Postre Pera`};
            break;
        case 'miércoles':
            menu = {platos: `Menú del Miércoles: Primero Lasaña, Segundo Centro de atún con cama de verduras, Postre Mandarinas`};
            break;
        case 'jueves':
            menu = {platos: `Menú del Jueves: Primero Escudella, Segundo Muslo de pollo con chutney de pera, Postre Natillas `};
            break;
        case 'viernes':
            menu = {platos: `Menú del Viernes: Primero Sopa minestrone piamontesa, Segundo Ternera a la carbonada, Acompañamiento Salteado de verdura, Postre Plátano`};
            break;
        default:
            menu = {platos: "error"};
    }
    }
    else{
    switch (dia) {
        case 'lunes':
            menu = {platos: `Menú del Lunes: Primero Arroz meloso con acelgas y setas cultivadas, Segundo Tofu con ali-oli de piña gratinado, Acompañamiento Pimientos fritos, Postre Manzana`};
            break;
        case 'martes':
            menu = {platos: `Menú del Martes: Patatas en ajo pollo , Segundo Ensalada de patatas y verduras con vinagreta de frutas, Acompañamiento Berenjenas a la parmesana, Postre Pera`};
            break;
        case 'miércoles':
            menu = {platos: `Menú del Miércoles:Primero Pudin de verduras, Segundo Lasaña de verduras, Acompañamiento Habas y acelgas salteadas con huevo escalfado, Postre Mandarinas`};
            break;
        case 'jueves':
            menu = {platos: `Menú del Jueves: Primero Pipirrana tropical, Segundo Escudella vegetariana, Acompañamiento Alcachofas con huevo duro gratinado,Postre Tocino de cielo y natillas de coco con almendras crocante`};
            break;
        case 'viernes':
            menu = {platos: `Menú del Viernes:Primero Sopa minestrone piamontesa, Segundo Hamburguesas de espinacas y tofu, Acompañamiento Salteado de verdura, Postre Plátano`};
            break;
        default:
            menu = {platos: "error"};
    }
  }

    return menu;
  }
  

function ConsultaComedorTipoMenu(agent) {

    let dia = ConsultaDia_Contexto(agent);     
    dia = dia.toLowerCase();
    dia = hoyOmanana(dia)
    let opcion = req.body.queryResult.parameters.Menu;
    let menu = buscaMenu(dia,opcion)
    agent.add(menu.platos);
}
  
  
  function ConsultaComedorDia(agent){ 
    //req es directamente la respuesta del cliente (no va vinculado con agent) ya va
    //Este intent se podría hacer sin webhook. 
    //Para hacer referencia a variables en DialogFlow, puedes usar $dia, o si están en un contexto especifico, #Nombre_Contexto.dia
    //Lo de arriba solo es en el propio DialogFlow, no en javascript :( a vale jajajaj
    
    let dia = req.body.queryResult.parameters.Dia; 
    dia = dia.toLowerCase()
    dia = hoyOmanana(dia)    
    let comprobarDia =buscaDia(dia)    
    if(comprobarDia === 0){
      agent.add(`El comedor cierra en fin de semana`);
          
    }else {
      agent.add(`Tienes el menú del dia y el vegetariano para el ` + dia + ` . ¿Cuál prefieres?`);
      agent.context.set('contextodia', 5, { dia });
    }
   
  }
  
  /////////////////////////////MAPA DE INTENTS///////////////////////////////////////////
  //Creamos mapa de intents
  let intentMap = new Map();
  intentMap.set("Default Welcome Intent", welcome);
  intentMap.set("ProbandoWebhook", ProbandoWebhook);
  intentMap.set("ConsultaDespacho", ConsultaDespacho);
  intentMap.set("Localizacion Inicia DestinoDespacho", LocalizacionIniciaDestinoDespacho);
  intentMap.set("Localizacion Vacio OrigenDespacho", LocalizacionVacioOrigenDespacho);
  intentMap.set("Localizacion Vacio DestinoDespacho", LocalizacionVacioDestinoDespacho);
  intentMap.set("Localizacion Origen FinDespacho", LocalizacionOrigenFinDespacho);
  intentMap.set("Localizacion Destino FinDespacho", LocalizacionDestinoFinDespacho);
  intentMap.set("ConsultaDespacho_Contexto", ConsultaDespacho_Contexto);
  intentMap.set("Consulta Comedor Dia", ConsultaComedorDia);
  intentMap.set("Consulta Comedor Tipo Menu", ConsultaComedorTipoMenu);
  intentMap.set("ConsultaAsignatura->Profesores", ConsultaAsignaturaProfesores);
  intentMap.set("ConsultaProfesor->Asignaturas", ConsultaProfesorAsignatura);
  intentMap.set("ConsultaClaseActual", ConsultaClaseActual);
  intentMap.set("ConsultaClaseDiaYHora", ConsultaClaseDiaYHora);
  intentMap.set("ConsultaClaseActual-Curso", ConsultaClaseActualCurso);
  intentMap.set("ConsultaClaseActual-Subgrupo", ConsultaClaseActualSubgrupo);
  intentMap.set("ConsultaClaseActual-LlevarSi", ConsultaClaseActualLlevarSi);
  intentMap.set("ConsultaClaseActual-LlevarNo", ConsultaClaseActualLlevarNo);
  
  agent.handleRequest(intentMap);
});


let port = 3000;
app.listen(port, () => {
  console.log("Estamos ejecutando el servidor en el puerto " + port);
});