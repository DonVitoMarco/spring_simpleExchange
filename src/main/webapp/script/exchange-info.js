init();

function init() {

    var res = window.location.href.split("?");
    var errorbox = document.getElementById("errorbox");
    var successbox = document.getElementById("infobox");
    errorbox.style.visibility = "hidden";
    successbox.style.visibility = "hidden";

    if (res[1] == "errorsell") {
        errorbox.style.visibility = "visible";
        errorbox.innerHTML = "something goes wrong";
    }
    if (res[1] == "successsell") {
        successbox.style.visibility = "visible";
        successbox.innerHTML = "transaction successfully completed";
    }
    if (res[1] == "errorbuy") {
        errorbox.style.visibility = "visible";
        errorbox.innerHTML = "something goes wrong";
    }
    if (res[1] == "successbuy") {
        successbox.style.visibility = "visible";
        successbox.innerHTML = "transaction successfully completed";
    }

}