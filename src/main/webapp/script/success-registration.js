init();

function init() {

    var res = window.location.href.split("?");
    var infobox = document.getElementById("infobox");
    infobox.style.visibility = "hidden";

    if (res[1] == "success") {
        var infobox1 = document.getElementById("infobox");
        infobox1.style.visibility = "visible";
        infobox1.innerHTML = "User registration - success!";
    }

}