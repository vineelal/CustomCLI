package com.imaginea.custom_cli;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.TransportException;

public class Clone {
	// String uri_regexp = "(git|ssh|rsync|ftps?|https?).\\/.*\\.git";
	String uri_regexp = "(git|ssh|rsync|ftps?|https?)(.\\/|\\@).*\\.git";
	String clone_regexp = "git\\sclone";
	String directoryNotPresent_regexp = "\\.git$";
	String directory_regexp = "\\.git\\s.*";
	String repo_directory_regexp ="([^/]+$)";

	Git git = null;
	CloneCommand cloneCommand = Git.cloneRepository();
	Pattern pattern = null;
	Matcher matcher = null;

	private void setURI(String command) {
		pattern = Pattern.compile(uri_regexp);
		matcher = pattern.matcher(command);
		if (matcher.find()) {
			cloneCommand.setURI(matcher.group());
		}
	}

	private void setDirectory(String command) {
		pattern = Pattern.compile(directoryNotPresent_regexp);
		matcher = pattern.matcher(command);
		File directory = null;
		if (!matcher.find()) {
			pattern = Pattern.compile(directory_regexp);
			matcher = pattern.matcher(command);
			if (matcher.find()) {
				directory = new File(matcher.group().substring(5));
				cloneCommand.setDirectory(directory);
			}
		}
		else{
			pattern = Pattern.compile(repo_directory_regexp);
			matcher = pattern.matcher(command);
			if (matcher.find()) {
				directory = new File(matcher.group().substring(0, matcher.group().length()-4));
				cloneCommand.setDirectory(directory);
			}
		}
	}

	private void setOptions(String command) {
		setURI(command);
		setDirectory(command);
	}

	public String clone(String command) {
		setOptions(command);
		try {
			git = cloneCommand.call();
			System.out.println("successfully cloned");
		} catch (InvalidRemoteException e) {
			return "Invalid Remote";
		} catch (TransportException e) {
			return "Transport operation failed";
		} catch (GitAPIException e) {
			return "Git API Exception";
		} catch (JGitInternalException e){
			return "Destination path already exists and is not an empty directory";
		}
		return "Cloned......";
	}
}
