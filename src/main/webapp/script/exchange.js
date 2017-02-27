var socket = new WebSocket("ws://webtask.future-processing.com:8068/ws/stocks");
socket.onmessage = onMessage;

function onMessage(event) {
    var msg = JSON.parse(event.data);
    document.getElementById("server-date").innerHTML = msg.PublicationDate;
    createTable(msg.Items);
}

function createTable(items) {
    var divTable = document.getElementById("tableItems");
    while(divTable.hasChildNodes()) {
        divTable.removeChild(divTable.lastChild);
    }
    var table = document.createElement("TABLE");

    var tableHead = document.createElement("THEAD");
    var trHead = document.createElement("TR");
    tableHead.appendChild(trHead);
    var tdHeadCompany = document.createElement("TD");
    tdHeadCompany.appendChild(document.createTextNode("COMPANY"));
    var tdHeadValue = document.createElement("TD");
    tdHeadValue.appendChild(document.createTextNode("VALUE"))
    var tdHeadActions = document.createElement("TD");
    tdHeadActions.appendChild(document.createTextNode("ACTIONS"));
    trHead.appendChild(tdHeadCompany);
    trHead.appendChild(tdHeadValue);
    trHead.appendChild(tdHeadActions);
    table.appendChild(tableHead);

    var tableBody = document.createElement("TBODY");
    table.appendChild(tableBody);
    for (var i = 0; i < items.length; i++) {
        var tr = document.createElement("TR");
        tableBody.appendChild(tr);
        var tdCode = document.createElement("TD");
        tdCode.appendChild(document.createTextNode(items[i].Code));
        tr.appendChild(tdCode);
        var tdValue = document.createElement("TD");
        tdValue.appendChild(document.createTextNode(items[i].Price));
        tr.appendChild(tdValue);
        var button = document.createElement("button");
        var attOnClick = document.createAttribute("onclick");
        attOnClick.value = "buyForm(this.id)";
        button.setAttributeNode(attOnClick);
        var attId = document.createAttribute("id");
        attId.value = "buy-button-" + items[i].Code;
        button.setAttributeNode(attId);
        button.classList.add("btn-cls");
        button.classList.add("btn-cls-small");
        button.innerHTML = 'BUY';
        tr.appendChild(button);
    }
    divTable.appendChild(table);
}

function buyForm(code) {
    var codeVal = code.substring(11, code.length);
    window.location.pathname = '/buy/' + codeVal;
}

