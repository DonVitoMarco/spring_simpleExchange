var socket = new WebSocket("ws://webtask.future-processing.com:8068/ws/stocks");
socket.onmessage = onMessage;
var gItems = '';

function onMessage(event) {
    console.log("New Messeage from server");
    var msg = JSON.parse(event.data);
    gItems = msg.Items;
    var codes = createCodes(msg.Items);
    createOptions(codes);
}

function createCodes(items) {
    var codes = [];
    for(var i = 0; i < items.length; i++) {
        codes.push(items[i].Code);
    }
    return codes;
}

function createOptions(codes) {
    var select = document.getElementById("code");
    while(select.hasChildNodes()) {
        select.removeChild(select.lastChild);
    }
    var emptyOption = document.createElement("OPTION");
    emptyOption.innerHTML = "";
    select.appendChild(emptyOption);
    for(var i = 0; i < codes.length; i++) {
        var option = document.createElement("OPTION");
        option.value = codes[i];
        option.innerHTML = codes[i];
        select.appendChild(option);
    }
}

function changeSelect() {
    var selectCode = document.getElementById("code");
    console.log(selectCode.value);
    var selectPrice = document.getElementById("price");
    var selectUnit = document.getElementById("unit");
    for(var i = 0; i < gItems.length; i++) {
        if(gItems[i].Code === selectCode.value) {
            selectPrice.value = gItems[i].Price;
            selectPrice.innerHTML = gItems[i].Price;
            selectUnit.value = gItems[i].Unit;
            selectUnit.innerHTML = gItems[i].Unit;
        }
    }
}

function changeUnit() {
    var selectCode = document.getElementById("code");
    var selectPrice = document.getElementById("price");
    var selectUnit = document.getElementById("unit");
    for(var i = 0; i < gItems.length; i++) {
        if(gItems[i].Code === selectCode.value) {
            if(selectUnit.value % gItems[i].Unit != 0) {
                console.log("PROBLEM");
            }
        }
    }
}