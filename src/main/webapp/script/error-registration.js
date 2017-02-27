init();

function init() {

    var res = window.location.href.split("?");
    var errorbox = document.getElementById("errorbox");
    errorbox.style.visibility = "hidden";

    if (res[1] == "error") {
        var errorbox1 = document.getElementById("errorbox");
        errorbox1.style.visibility = "visible";
        errorbox1.innerHTML = "Passwords do not match";
    }
    if (res[1] == "validUsername") {
        var errorbox2 = document.getElementById("errorbox");
        errorbox2.style.visibility = "visible";
        errorbox2.innerHTML = "Username already exists";
    }

}