package com.imaginea.custom_cli;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;

public class Clone {
	String uri_regexp = "(git|ssh|ftps?|https?).\\/.*\\.git";
	String clone_regexp = "git\\sclone";
	String directoryNotPresent_regexp = "\\.git$";
	String directory_regexp = "\\.git\\s.*";
	Boolean cloned = false;

	Git git = null;
	CloneCommand cloneCommand = Git.cloneRepository();
	Pattern pattern = null;
	Matcher matcher = null;

	public void setURI(String command) {
		pattern = Pattern.compile(uri_regexp);
		matcher = pattern.matcher(command);
		if (matcher.find()) {
			cloneCommand.setURI(matcher.group());
		}
	}

	public void setDirectory(String command) {
		pattern = Pattern.compile(directoryNotPresent_regexp);
		matcher = pattern.matcher(command);
		if (!matcher.find()) {
			pattern = Pattern.compile(directory_regexp);
			matcher = pattern.matcher(command);
			if (matcher.find()) {
				File directory = new File(matcher.group().substring(5));
				cloneCommand.setDirectory(directory);
			}
		}
	}

	public void setOptions(String command) {
		setURI(command);
		setDirectory(command);
	}

	public String clone(String command) {
		setOptions(command);
		cloned = true;
		try {
			git = cloneCommand.call();
			System.out.println("successfully cloned");
		} catch (InvalidRemoteException e) {
			e.printStackTrace();
		} catch (TransportException e) {
			e.printStackTrace();
		} catch (GitAPIException e) {
			e.printStackTrace();
		}
		String message = cloned ? "Cloned......":"Not cloned";
		return message;
	}
}
