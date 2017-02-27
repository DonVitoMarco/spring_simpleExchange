var socket = new WebSocket("ws://webtask.future-processing.com:8068/ws/stocks");
socket.onmessage = onMessage;


function onMessage(event) {
    console.log("New Messeage from server");
    var msg = JSON.parse(event.data);

    var res = window.location.href.split("?");
    var codeToBuy = res[1];

    for(var i = 0; i < msg.Items.length; i++) {
        if (msg.Items[i].Code == res[1]) {
            var code = document.getElementById("code");
            var unit = document.getElementById("unit");
            var price = document.getElementById("price");
            code.value = msg.Items[i].Code;
            unit.value = msg.Items[i].Unit;
            price.value = msg.Items[i].Price;
        }
    }

}
