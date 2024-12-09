'use strict'

var connecting = document.querySelector(".connecting");
var username = document.querySelector("#username");
var message = document.querySelector("#message");
var messageArea = document.querySelector('#message-area')

var stompClient = null; 

function onConnect() {
  stompClient.subscribe("/topic/public", onMessageRecive);
  var request = JSON.stringify(
    {
      sender: username, 
      type: 'JOIN'
    }
  );
  stompClient.send(
    "/app/chat.addUser",
    {},
    request
  );
  connecting.classList.add("hide")
}

function onError() {
  connecting.textContent = "Could not connect to WebSocket";
  connecting.style.color = "red";
}

function onMessageRecive(payload) {
  
  var message = JSON.parse(payload.body);
  console.log(message);
  
  var msgEle = document.createElement('li');

  var msg = document.createElement('span');
  msg.innerText = message.message;

  var user = document.createElement('span');
  user.innerText = message.sender;

  var type = document.createElement('span');
  type.innerText = message.type;

  msgEle.appendChild(msg);
  msgEle.appendChild(user);
  msgEle.appendChild(type);
  messageArea.appendChild(msgEle);

}

function connect() {
  username = username.value.trim();
  if(username == "") {return}
  
  connecting.classList.remove("hide")
  var socket = new SockJS('/ws');
  stompClient = Stomp.over(socket);

  stompClient.connect({}, onConnect, onError);
}

function sendMessage() {
  var msg = message.value.trim();
  if (message == "") {return};

  var request = JSON.stringify(
    {
      sender: username,
      message: msg, 
      type: 'CHAT'
    }
  );

  stompClient.send(
    "/app/chat.sendMessage",
    {},
    request
  );

  message.content = "";
}
