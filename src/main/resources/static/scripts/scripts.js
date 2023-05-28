/*//------------- Ordenar------------- 
// Obtener la tabla
var table = document.querySelector('.table');

// Obtener los encabezados de la tabla
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

        // Función para obtener el valor de calificación de una fila
        function getRatingValue(row) {
            var starsInner = row.querySelector('.stars-inner');
            var starWidth = starsInner.style.width;

            // Obtener el ancho de las estrellas internas como un porcentaje
            var starPercentage = parseFloat(starWidth.replace('%', ''));

            // Convertir el porcentaje en un valor de calificación entre 0 y 5
            var ratingValue = (starPercentage / 100) * 5;

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

//------------- Estrella------------- 
const starTotal = 5;

rows.forEach(function (row) {
    const rating = document.getElementById('calification').value;
    const starTotal = 5; // Agregar esta línea para definir la variable starTotal
    const starPercentage = (rating / starTotal) * 100;
    const starPercentageRounded = `${Math.round(starPercentage / 10) * 10}%`;
    row.querySelector('.stars-inner').style.width = starPercentageRounded;
});


/*------------- UP Button -------------*/
window.onscroll = function () { scrollFunction() };

function scrollFunction() {
    if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
        document.getElementById("myBtn").style.display = "block";
    } else {
        document.getElementById("myBtn").style.display = "none";
    }
}

/*------------- Up Section -------------*/
const upSections = document.querySelectorAll('.up-section');
window.addEventListener('scroll', function () {
    var upSections = document.querySelectorAll('.up-section'); // Selecionamos todas las secciones

    for (var i = 0; i < upSections.length; i++) {
        var upSectionPosition = upSections[i].getBoundingClientRect().top;
        var screenPosition = window.innerHeight / 1.3;

        if (upSectionPosition < screenPosition) {
            upSections[i].style.opacity = "1";
            upSections[i].style.transform = "translateY(0)";
        }
    }
});

/*------------- Profesional Filter -------------*/
const filterContainer = document.querySelector(".categories");
const galleryItems = document.querySelectorAll(".card");
filterContainer.addEventListener("click", (event) => {
    if (event.target.classList.contains("filter-item")) {
        filterContainer.querySelector(".active").classList.remove("active");
        event.target.classList.add("active")
        const filterValue = event.target.getAttribute("data-filter")
        galleryItems.forEach(item => {
            if (item.classList.contains(filterValue) || filterValue === "all") {
                item.classList.remove("hide")
                item.classList.add("show")
            } else {
                item.classList.remove("show")
                item.classList.add("hide")
            }
        })
    }
})

/*------------- Profesional horizontalScroll -------------*/
function horizontalScroll(event) {
    if (event.deltaY !== 0) {
        event.preventDefault();
        document.querySelector('.overflow-container').scrollLeft += event.deltaY;
    }
}

/*------------- Filtrar tabla de turnos -------------*/

function filtrarTabla(filtro) {
    var filtroElegido = document.querySelector(`.filtro-${filtro}`).value;
  
    var tabla = document.querySelector(".table"); 
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
/* ------- Profesional horizontalScroll -------- */

function horizontalScroll(event) {
        if (event.deltaY !== 0) {
            event.preventDefault();
            document.querySelector('.overflow-container').scrollLeft += event.deltaY;
        }
    }

/* ------- Filtrar tabla de turnos -------- */

function filtrarTabla(filtro) {
  var filtroElegido = document.querySelector(`.filtro-${filtro}`).value;
  
  var tabla = document.querySelector(".table"); 
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
