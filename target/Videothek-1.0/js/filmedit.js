/**
 * view-controller for filmedit.html
 * @author Filip Slavkovic
 */



document.addEventListener("DOMContentLoaded", () => {
    readProducers();
    readGenres();
    readFilm();

    //document.getElementById("filmeditForm").addEventListener("submit", saveFilm);
    /**
    document.getElementById("cancel").addEventListener("click", cancelEdit);
    $(document).ready(function (){
        document.getElementById("filmeditForm").addEventListener("submit", saveFilm);

    });
     */
    $(document).ready(function (){
        $("#filmeditForm").submit(saveFilm);
        $("#cancel").click(cancelEdit);
    });
});

/**
 * saves the data of a film
 */
function saveFilm(event) {
    event.preventDefault();

    const filmEditForm = document.getElementById("filmeditForm");
    const formData = new FormData(filmEditForm);
    const data = new URLSearchParams(formData);
    console.log(data);

    let method;
    let url = "./resource/film/";
    const filmUUID = getQueryParam("filmUUID");
    if (filmUUID == null) {
        method = "POST";
        url += "create";
    } else {
        method = "PUT";
        url += "update";
    }

    $
        .ajax({
            url: url,
            dataType: "text",
            type: method,
            data: $("#filmeditForm").serialize()
        })
        .done(function (){
            window.location.href = "./filmList.html";
        })
        .fail(function (error){
            console.log(error)
        })
    /*
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
            } else{
                //window.location.href = "./filmList.html";
                return response;
            }
        })
        .catch(function (error) {
            console.log(error);
        });
     */

}

/**
 * reads a film
 */
function readFilm() {
    const filmUUID = getQueryParam("filmUUID");
    fetch("./resource/film/read?filmUUID=" + filmUUID)
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
    document.getElementById("publishDate").value = data.publishDate;
    document.getElementById("price").value = data.price;
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
    window.location.href = "./filmList.html";
}