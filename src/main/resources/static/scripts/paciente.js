
/*------------- Filtrar tabla de turnos -------------*/
function filtrarTablaConsultas(filtro) {
  var filtroElegido = document.querySelector(`.filtro-${filtro}`).value;
  var tabla = document.querySelector(".consultaTablaFiltro"); 
  var filas = tabla.getElementsByTagName("tr");
console.log(filtroElegido)
  for (var i = 1; i < filas.length; i++) { 
    var celdaFiltro= filas[i].querySelector(`.filtro-${filtro}`);

    if (celdaFiltro && (filtroElegido === "TODOS" || celdaFiltro.textContent === filtroElegido)) {
      filas[i].style.display = "";
    } else {
      filas[i].style.display = "none";
    }
  }
}


/*------------- Filtrar tabla de turnos -------------*/
function filtrarTablaMisConsultas(filtro) {
  var filtroElegido = document.querySelector(`.filtro-${filtro}`).value;
  var tabla = document.querySelector(".miTablaFiltro"); 
  var filas = tabla.getElementsByTagName("tr");
console.log(filtroElegido)
  for (var i = 1; i < filas.length; i++) { 
    var celdaFiltro= filas[i].querySelector(`.filtro-${filtro}`);

    if (celdaFiltro && (filtroElegido === "TODOS" || celdaFiltro.textContent === filtroElegido)) {
      filas[i].style.display = "";
    } else {
      filas[i].style.display = "none";
    }
  }
}
