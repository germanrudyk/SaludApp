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