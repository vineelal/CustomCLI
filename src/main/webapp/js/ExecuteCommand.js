/**
 * Created by vineelal on 18/11/13.
 */

var prompt = "pramati@pramati:~$";
var temp = null;
var rcvReq = getXmlHttpRequestObject();

function getPrompt() {
	var xmlHttpRequest = getXmlHttpRequestObject();
	xmlHttpRequest.open("POST", 'ExecuteServlet', true);
	xmlHttpRequest.send();
	xmlHttpRequest.onreadystatechange = function() {
		if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
			prompt = xmlHttpRequest.responseText;
		}
	}
}

function nextLine(e) {
	if (e.keyCode === 13) {
		var command = document.getElementById("cmd").value;
		document.getElementById("cmd").readOnly = true;
		executeCommand(command);
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
			document.getElementById("output-message").innerHTML = rcvReq.responseText;
			addNewLine();
		}
	}
	rcvReq.open("GET", 'ExecuteServlet?input=' + input, true);
	rcvReq.send();
}

function addNewLine() {
	var div = document.createElement("div");
	div.setAttribute("id", "terminal");
	var text = document.createTextNode(prompt);
	var input = document.createElement("input");
	input.setAttribute("id", "cmd");
	// input.setAttribute("onkeypress",nextLine(e));
	// input.onkeypress = nextLine(e);
	input.addEventListener("onkeypress", function() {
		nextLine(event)
	}, false);
	var output = document.createElement("input");
	output.setAttribute("id", "output-message");
	div.appendChild(text);
	div.appendChild(input);
	div.appendChild(output);
	document.body.appendChild(div);
	console.log(prompt);
}
