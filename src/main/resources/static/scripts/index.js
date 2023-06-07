/*------------- Navbar -------------*/
const navbar = document.getElementById("navbar");
let prevScrollPos = window.pageYOffset;

window.addEventListener('scroll', () => {
  const currentScrollPos = window.pageYOffset;

  if (prevScrollPos < currentScrollPos) {
    navbar.classList.add('fixed');
    navbar.classList.remove('transparent');
  } else {
    navbar.classList.add('transparent');
  }

  prevScrollPos = currentScrollPos;
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

/*------------- Enviar Mensaje -------------*/
function enviarMensaje() {

  var mensaje = document.getElementById("cartel");
  mensaje.classList.remove("d-none");
    setTimeout(function() {
    mensaje.classList.add("d-none");
  }, 3000);

}

/*------------- Verificar fortaleza de contraseña -------------*/

    function verificarFortaleza(){
        
        var contraseña = document.getElementById("password").value;
        var longitud = contraseña.length;
        var mensaje = "";
        
        if (longitud === 0) {
        mensaje = "";
        document.getElementById("mensajePass").style.backgroundColor = "white";
        } else if (longitud < 5) {
        mensaje = "La contraseña es muy débil. Debe tener al menos 8 caracteres.";
        document.getElementById("mensajePass").style.backgroundColor = "red";
        document.getElementById("mensajePass").style.color = "white";
        
        } else if (longitud >= 5 && longitud < 10) {
        mensaje = "La contraseña es moderada. Debe tener al menos 10 caracteres para ser más segura.";
        document.getElementById("mensajePass").style.backgroundColor = "orange";
        document.getElementById("mensajePass").style.color = "black";
        } else {
        mensaje = "La contraseña es fuerte. ¡Bien hecho!";
        document.getElementById("mensajePass").style.backgroundColor = "greenyellow";
        document.getElementById("mensajePass").style.color = "black";
        }

        document.getElementById("mensajePass").innerHTML = mensaje;
      
    }

/*------------- Efecto Contar -------------*/
const Pacientes = document.getElementById('nPacientes');
const Profesionales = document.getElementById('nProfesionales');
const Calificaciones = document.getElementById('calificaciones');
const PacientesValue = parseFloat(Pacientes.innerText);
const ProfesionalesValue = parseFloat(Profesionales.innerText);
const CalificacionesValue = parseFloat(Calificaciones.innerText);

window.addEventListener('scroll', function () {
  function animarNumeros(elemento, valorFinal) {
    let numeroActual = 0;
    const intervalo = setInterval(() => {
      elemento.innerText = numeroActual;
      numeroActual++;
      if (numeroActual > valorFinal) {
        clearInterval(intervalo);
      }
    }, 50);
  }

  animarNumeros(Pacientes, PacientesValue);
  animarNumeros(Profesionales, ProfesionalesValue);
  animarNumeros(Calificaciones, CalificacionesValue);
});