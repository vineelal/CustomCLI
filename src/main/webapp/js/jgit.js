/**
 * Created by vineelal on 18/11/13.
 */

function nextLine(e) {
	if (e.keyCode === 13) {
		var command = document.getElementById("input").value;
		document.getElementById("input").readOnly = true;
		executeCommand(command);
	}
}

function getXmlHttpRequestObject() {
	if (window.XMLHttpRequest) {
		return new XMLHttpRequest(); // To support the browsers IE7+,
										// Firefox, Chrome, Opera, Safari
	} else if (window.ActiveXObject) {
		return new ActiveXObject("Microsoft.XMLHTTP"); // For the browsers IE6,
														// IE5
	} else {
		alert("Error due to old verion of browser upgrade your browser");
	}
}

var rcvReq = getXmlHttpRequestObject();

function executeCommand(input) {
	rcvReq.onreadystatechange=function()
	  {
	  if (rcvReq.readyState==4 && rcvReq.status==200)
	    {
		  document.getElementById("output").value = rcvReq.responseText;
		  document.getElementById("output").readOnly = true;
		  addNewLine();
	    }
	  }
	rcvReq.open("GET", 'ExecuteServlet?input='+input, true);
	rcvReq.send();
}

function addNewLine(){
	var div = document.createElement("div");
	div.setAttribute("id", "user");
	var text = document.createTextNode("vineelal@vineela-l:~$");
	var input = document.createElement("input");
	// input.setAttribute("onkeypress",nextLine(e));
	// input.onkeypress = nextLine(e);
	input.addEventListener("onkeypress", function() {
		nextLine(event)
	}, false);
	var output = document.createElement("input");
	div.appendChild(text);
	div.appendChild(input);
	div.appendChild(output);
	document.body.appendChild(div);
}
