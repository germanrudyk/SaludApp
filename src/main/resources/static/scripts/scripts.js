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
/* ------ CAROUSEL ------ */
const carouselSlide = document.querySelector('.carousel-slide');
const carouselDots = document.querySelectorAll('.carousel-dot');
const carouselPrev = document.querySelector('.carousel-button-prev');
const carouselNext = document.querySelector('.carousel-button-next');

let counter = 0;

function moveCarousel() {
    carouselSlide.style.transform = 'translateX(' + (-25 * counter) + '%)';
}

function updateDots() {
    carouselDots.forEach(dot => dot.classList.remove('active'));
    carouselDots[counter].classList.add('active');
}

function resetTimer() {
    clearInterval(timer);
    timer = setInterval(autoCarousel, 5000);
}

function autoCarousel() {
    if (counter >= carouselSlide.children.length - 1) return;
    counter++;
    moveCarousel();
    updateDots();
}

let timer = setInterval(autoCarousel, 5000);

carouselPrev.addEventListener('click', () => {
    if (counter <= 0) return;
    counter--;
    moveCarousel();
    updateDots();
    resetTimer();
});

carouselNext.addEventListener('click', () => {
    if (counter >= carouselSlide.children.length - 1) return;
    counter++;
    moveCarousel();
    updateDots();
    resetTimer();
});

carouselDots.forEach((dot, index) => {
    dot.addEventListener('click', () => {
        counter = index;
        moveCarousel();
        updateDots();
        resetTimer();
    });
});

/* ------- UP Button -------- */
window.onscroll = function () { scrollFunction() };

function scrollFunction() {
    if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
        document.getElementById("myBtn").style.display = "block";
    } else {
        document.getElementById("myBtn").style.display = "none";
    }
}

/* ------- Up Section -------- */
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

/* ------- Animated Bar -------- */
const upSections = document.querySelectorAll('.up-section');
const bars = document.querySelectorAll('.cont__bar:not(.invisible)');

function resetProgressBar(bar) {
    const percentNumber = bar.querySelector('.percent-number');
    percentNumber.innerText = '0%';
    bar.querySelector('.bar').style.setProperty('--bar-length', '0');
}

function animateProgressBar(bar) {
    const percentNumber = bar.querySelector('.percent-number');
    const barLength = parseInt(bar.querySelector('.bar').classList[1].slice(5));
    let percent = 0;
    const animationDuration = 2000;
    const startTime = performance.now();
    const interval = setInterval(() => {
        const currentTime = performance.now();
        const elapsedTime = currentTime - startTime;
        percent = Math.min(barLength, Math.floor((elapsedTime / animationDuration) * barLength));
        percentNumber.innerText = `${percent}%`;
        bar.querySelector('.bar').style.setProperty('--bar-length', `${percent}%`);
        if (percent >= barLength) {
            clearInterval(interval);
        }
    }, 16);
}

function handleScroll() {
    for (let i = 0; i < upSections.length; i++) {
        const upSectionPosition = upSections[i].getBoundingClientRect().top;
        const screenPosition = window.innerHeight / 1.3;
        const contBars = upSections[i].querySelectorAll('.cont__bar');
        if (upSectionPosition < screenPosition) {
            upSections[i].style.opacity = '1';
            upSections[i].style.transform = 'translateY(0)';
            for (let j = 0; j < contBars.length; j++) {
                if (contBars[j].classList.contains('invisible')) {
                    contBars[j].classList.remove('invisible');
                    resetProgressBar(contBars[j]);
                    animateProgressBar(contBars[j]);
                }
            }
        } else {
            for (let j = 0; j < contBars.length; j++) {
                if (!contBars[j].classList.contains('invisible')) {
                    contBars[j].classList.add('invisible');
                }
            }
        }
    }
}

window.addEventListener('scroll', handleScroll);
handleScroll();

// Counter Porcent Bar
bars.forEach(bar => {
    const percentNumber = bar.querySelector('.percent-number');
    const barEl = bar.querySelector('.bar');
    const barLength = parseInt(barEl.classList[1].slice(5));
    let percent = 0;
    const animationDuration = 2000; // Duración de la animación en milisegundos
    const interval = setInterval(() => {
        const transform = window.getComputedStyle(barEl).getPropertyValue('transform');
        const match = transform.match(/matrix\((.*)\)/);
        const matrixValues = match ? match[1].split(', ') : [1, 0, 0, 1, 0, 0];
        const scaleX = parseFloat(matrixValues[0]);
        percent = Math.min(barLength, Math.round(scaleX * barLength));
        percentNumber.innerText = `${percent}%`;
        barEl.style.setProperty('--bar-lenght', `${percent}%`);
        if (percent >= barLength) {
            clearInterval(interval);
        }
    }, animationDuration / 60); // Intervalo de actualización en milisegundos (aproximadamente 60 fps)
});

/* ------- Profesional Filter -------- */
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