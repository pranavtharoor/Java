var requestWrite, requestRead;
var msg;
var urlWrite = "ChatAppWrite.jsp?msg=";
var urlRead = "ChatAppRead.jsp";

if(window.XMLHttpRequest) {
	requestWrite = new XMLHttpRequest();
	requestRead = new XMLHttpRequest(); 
} else if(window.ActiveXObject) {
	requestWrite = new ActiveXObject("Microsoft.XMLHTTP");
	requestRead = new ActiveXObject("Microsoft.XMLHTTP");
}

function sendMsg() {
	msg = document.getElementById("inputBox").value;
	try {
		requestWrite.open("POST", urlWrite + msg, true);  
		requestWrite.send();  
	} catch(e) {
		alert("Unable to connect to server");
	}  
}

setInterval(function(){
	try {
		requestRead.onreadystatechange = getMsgs;
		requestRead.open("POST", urlRead, true);
		requestRead.send();
		
	} catch(e) {
		alert("Unable to connect to server");
	}  
}, 500);

function getMsgs() {
	if(requestRead.readyState==4) {
	var val = requestRead.responseText;
	document.getElementById('messages').innerHTML = val;
	}
}
