//------------- Ordenar------------- 
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
const ratings = {
    a: 2.8,
    b: 3.3,
    c: 1.9,
    d: 4.3,
    e: 4.74,
    f: 4.74,
    g: 4.74
};

// total number of stars
const starTotal = 5;

for (const rating in ratings) {
    const starPercentage = (ratings[rating] / starTotal) * 100;
    const starPercentageRounded = `${(Math.round(starPercentage / 10) * 10)}%`;
    document.querySelector(`.${rating} .stars-inner`).style.width = starPercentageRounded;
}