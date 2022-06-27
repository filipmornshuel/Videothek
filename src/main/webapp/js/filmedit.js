/**
 * view-controller for filmedit.html
 * @author Filip Slavkovic
 */
document.addEventListener("DOMContentLoaded", () => {
    readProducers();
    readGenres();
    readFilm();

    document.getElementById("save").addEventListener("submit", saveFilm);
    document.getElementById("cancel").addEventListener("click", cancelEdit);
});

/**
 * saves the data of a film
 */
function saveFilm(event) {
    event.preventDefault();

    const filmForm = document.getElementById("filmeditForm");
    const formData = new FormData(filmForm);
    const data = new URLSearchParams(formData);

    let method;
    let url = "./resource/film/";
    const filmUUID = getQueryParam("uuid");
    if (filmUUID == null) {
        method = "POST";
        url += "create";
    } else {
        method = "PUT";
        url += "update";
    }

    fetch(url,
        {
            method: method,
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: data
        })
        .then(function (response) {
            if (!response.ok) {
                console.log(response);
            } else return response;
        })
        .then()
        .catch(function (error) {
            console.log(error);
        });

}

/**
 * reads a film
 */
function readFilm() {
    const filmUUID = getQueryParam("uuid");
    fetch("./resource/film/read?uuid=" + filmUUID)
        .then(function (response) {
            if (response.ok) {
                return response;
            } else {
                console.log(response);
            }
        })
        .then(response => response.json())
        .then(data => {
            showFilm(data);
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * show the data of a film
 * @param data  the film-data
 */
function showFilm(data) {
    document.getElementById("filmUUID").value = data.filmUUID;
    document.getElementById("title").value = data.title;
    document.getElementById("producer").value = data.producerUUID;
    document.getElementById("genre").value = data.genreUUID;
    document.getElementById("price").value = data.price;
    document.getElementById("publishDate").value = data.publishDate;
    document.getElementById("lenth").value = data.lenth;
    document.getElementById("ean").value = data.ean;
}

/**
 * reads all producers as an array
 */
function readProducers() {

    fetch("./resource/producer/list")
        .then(function (response) {
            if (response.ok) {
                return response;
            } else {
                console.log(response);
            }
        })
        .then(response => response.json())
        .then(data => {
            showProducers(data);
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * shows all producers as a dropdown
 * @param data
 */
function showProducers(data) {
    let dropdown = document.getElementById("producer");
    data.forEach(producer => {
        let option = document.createElement("option");
        option.text = producer.producer;
        option.value = producer.producerUUID;
        dropdown.add(option);
    })
}

/**
 * reads all genres as an array
 */
function readGenres() {

    fetch("./resource/genre/list")
        .then(function (response) {
            if (response.ok) {
                return response;
            } else {
                console.log(response);
            }
        })
        .then(response => response.json())
        .then(data => {
            showGenres(data);
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * shows all genres as a dropdown
 * @param data
 */
function showGenres(data) {
    let dropdown = document.getElementById("genre");
    data.forEach(genre => {
        let option = document.createElement("option");
        option.text = genre.genre;
        option.value = genre.genreUUID;
        dropdown.add(option);
    })
}

/**
 * redirects to the videothek
 * @param event  the click-event
 */
function cancelEdit(event) {
    window.location.href = "./videothek.html";
}