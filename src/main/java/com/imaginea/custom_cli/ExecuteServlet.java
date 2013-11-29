package com.imaginea.custom_cli;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExecuteServlet extends HttpServlet {
	private String message;
	String userName = System.getenv("USER");
	String machineName = null;

	public void init() throws ServletException {
		try {
			machineName = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			machineName = "localhost";
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String command = request.getParameter("input");
		String clone_regexp = "git\\sclone";
		Pattern pattern = Pattern.compile(clone_regexp);
		Matcher matcher = pattern.matcher(command);
		if (matcher.find()) {
			Clone c = new Clone();
			message = c.clone(command);
		}
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(message);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println(userName + "@" + machineName + ":~$");
	}
}
