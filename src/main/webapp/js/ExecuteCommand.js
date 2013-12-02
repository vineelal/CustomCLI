/**
 * Created by vineelal on 18/11/13.
 */

var prompt = "pramati@pramati:~$";
var rcvReq = getXmlHttpRequestObject();

function getPrompt() {
	var xmlHttpRequest = getXmlHttpRequestObject();
	xmlHttpRequest.open("POST", 'ExecuteServlet', true);
	xmlHttpRequest.send();
	xmlHttpRequest.onreadystatechange = function() {
		if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
			prompt = xmlHttpRequest.responseText;
			addInput();
		}
	}
}

function addNewLine(e) {
	if (e.keyCode === 13) {
		var command = document.getElementById("terminal").lastChild;
		command.readOnly = true;
		executeCommand(command.value);
	}
}

function getXmlHttpRequestObject() {
	if (window.XMLHttpRequest) {
		return new XMLHttpRequest();
	} else if (window.ActiveXObject) {
		return new ActiveXObject("Microsoft.XMLHTTP");
	} else {
		alert("Error due to old verion of browser upgrade your browser");
	}
}

function executeCommand(input) {
	rcvReq.onreadystatechange = function() {
		if (rcvReq.readyState == 4 && rcvReq.status == 200) {
			addOutput(rcvReq.responseText);
		}
	}
	rcvReq.open("GET", 'ExecuteServlet?input=' + input, true);
	rcvReq.send();
}

function addOutput(message) {
	var output = document.createElement("span");
	output.setAttribute("id", "output-message");
	output.innerText = message;
	document.body.appendChild(output);
	addInput();
}

function addInput(){
	var promptText = document.createElement("span");
	promptText.innerHTML = prompt;
	promptText.setAttribute("id","prompt");
	var input = document.createElement("input");
	input.setAttribute("id", "cmd");
	input.setAttribute("onkeypress", "nextLine(event)");
	document.body.appendChild(promptText);
	document.body.appendChild(input);
}
