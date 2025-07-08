const links = document.querySelectorAll('.top a');
const currentPath = window.location.pathname;

links.forEach(link => {
    if (link.getAttribute('href') === currentPath) {
        link.classList.add('active');
    }
});