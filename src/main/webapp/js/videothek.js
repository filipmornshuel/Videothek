/**
 * view-controller for videothek.html
 * @author Filip Slavkovic
 */
document.addEventListener("DOMContentLoaded", () => {
    readFilms();
});

/**
 * reads all books
 */
function readFilms() {
    fetch("./resource/film/list")
        .then(function (response) {
            if (response.ok) {
                return response;
            } else {
                console.log(response);
            }
        })
        .then(response => response.json())
        .then(data => {
            showFilmlist(data);
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * shows the booklist as a table
 * @param data  the books
 */
function showFilmlist(data) {
    let tBody = document.getElementById("filmlist");
    data.forEach(film => {
        let row = tBody.insertRow(-1);
        row.insertCell(-1).innerHTML = film.title;
        row.insertCell(-1).innerHTML = film.producer.producer;
        row.insertCell(-1).innerHTML = film.genre.genre;
        row.insertCell(-1).innerHTML = film.publishDate;
        row.insertCell(-1).innerHTML = film.price;
        row.insertCell(-1).innerHTML = film.lenth;
        row.insertCell(-1).innerHTML = film.ean;

        let button = document.createElement("button");
        button.innerHTML = "Bearbeiten ...";
        button.type = "button";
        button.name = "editFilm";
        button.setAttribute("data-filmuuid", film.filmUUID);
        button.addEventListener("click", editFilm);
        row.insertCell(-1).appendChild(button);

        button = document.createElement("button");
        button.innerHTML = "Löschen ...";
        button.type = "button";
        button.name = "deleteFilm";
        button.setAttribute("data-filmuuid", film.filmUUID);
        button.addEventListener("click", deleteFilm);
        row.insertCell(-1).appendChild(button);

    });
}

/**
 * redirects to the edit-form
 * @param event  the click-event
 */
function editFilm(event) {
    const button = event.target;
    const filmUUID = button.getAttribute("data-filmuuid");
    window.location.href = "./filmedit.html?uuid=" + filmUUID;
}

/**
 * deletes a book
 * @param event  the click-event
 */
function deleteFilm(event) {
    const button = event.target;
    const filmUUID = button.getAttribute("data-filmuuid");

    fetch("./resource/film/delete?uuid=" + filmUUID,
        {
            method: "DELETE"
        })
        .then(function (response) {
            if (response.ok) {
                window.location.href = "./videothek.html";
            } else {
                console.log(response);
            }
        })
        .catch(function (error) {
            console.log(error);
        });
}