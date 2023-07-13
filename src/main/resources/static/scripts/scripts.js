/*------------- Ordenar
document.addEventListener('DOMContentLoaded', function() {
  var table = document.querySelector('.table');
  var headers = table.querySelectorAll('.sortable-header');
  // Variable para realizar un seguimiento del orden actual
  var sortOrder = {
      columnIndex: null,
      ascending: true
  };
  // Agregar un evento clic a los encabezados
  headers.forEach(function (header) {
      header.addEventListener('click', function () {
          // Obtener el índice de la columna desde el atributo data
          var columnIndex = parseInt(header.getAttribute('data-column-index'));
          // Verificar si se ha hecho clic en la misma columna nuevamente
          if (sortOrder.columnIndex === columnIndex) {
              // Cambiar el orden
              sortOrder.ascending = !sortOrder.ascending;
          } else {
              // Establecer la columna actual y el orden ascendente
              sortOrder.columnIndex = columnIndex;
              sortOrder.ascending = true;
          }
          // Obtener todas las filas de la tabla, excepto la primera (encabezados)
          var rows = Array.from(table.querySelectorAll('tbody tr')).slice(0);
          // Ordenar las filas en función del valor de la columna seleccionada y el orden actual
          rows.sort(function (a, b) {
              var aValue = getRatingValue(a);
              var bValue = getRatingValue(b);
              // Comparar los valores según el orden ascendente o descendente
              if (sortOrder.ascending) {
                  return aValue - bValue;
              } else {
                  return bValue - aValue;
              }
          });
          // Función para obtener el valor del precio de una fila
          function getRatingValue(row) {
            const Precios = row.querySelector('.precios');
            const ratingValue = parseFloat(Precios.innerText);
            return ratingValue;
          }        
          // Obtener el padre de las filas (el tbody)
          var parent = rows[0].parentNode;
          // Remover las filas existentes de la tabla
          rows.forEach(function (row) {
              parent.removeChild(row);
          });
          // Agregar las filas ordenadas de nuevo a la tabla
          rows.forEach(function (row) {
              parent.appendChild(row);
          });
      });
  });
});-------------*/

/*------------- Filtrar tabla de turnos -------------*/
function filtrarTabla3(filtro) {
  var filtroElegido = document.querySelector(`.filtro-${filtro}`).value;
  var tabla = document.querySelector(".tablaFiltro"); 
  var filas = tabla.getElementsByTagName("tr");

  for (var i = 1; i < filas.length; i++) { 
    var celdaFiltro= filas[i].querySelector(`.filtro-${filtro}`);

    if (celdaFiltro && (filtroElegido === "TODOS" || celdaFiltro.textContent === filtroElegido)) {
      filas[i].style.display = "";
    } else {
      filas[i].style.display = "none";
    }
  }
}

/*------------- Calificación -------------*/ 
const calificacionElements = document.getElementsByClassName('calificacion');
const starTotal = 5;

for (let i = 0; i < calificacionElements.length; i++) {
  const calificacionValue = parseFloat(calificacionElements[i].innerText);
  const starPercentage = (calificacionValue / starTotal) * 100;
  const starPercentageRounded = `${(Math.round(starPercentage / 10) * 10)}%`;

  const starsInnerElement = calificacionElements[i].nextElementSibling.querySelector('.stars-inner');
  starsInnerElement.style.width = starPercentageRounded;
}

/*------------- Modal Calificar -------------*/ 
var starsContainer = document.querySelector('.stars-container');
var starInputs = starsContainer.querySelectorAll('input[type="radio"]');
var starLabels = starsContainer.querySelectorAll('label');

starLabels.forEach(function(label) {
  label.addEventListener('click', function() {
    var clickedRating = parseInt(this.getAttribute('for').split('-')[1]);

    starLabels.forEach(function(label, index) {
      if (index < clickedRating) {
        label.classList.add('filled');
      } else {
        label.classList.remove('filled');
      }
    });
  });
});