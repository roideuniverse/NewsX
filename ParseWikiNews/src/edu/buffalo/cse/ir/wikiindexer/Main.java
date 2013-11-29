package edu.buffalo.cse.ir.wikiindexer;

import java.io.File;
import java.util.ArrayList;

import edu.buffalo.cse.ir.wikiindexer.parsers.NewParser;
import edu.buffalo.cse.ir.wikiindexer.wikipedia.WikipediaDocument;

public class Main {

	public static void main(String[] args) {
		
		if(args.length <2 ) {
			printUsage();
			return;
		}
		
		if( !args[0].trim().equals("-o")) {
			printUsage();
			return;
		}
		String fileName = args[1];
		if(fileName.trim().length() <=0) {
			printUsage();
			return;
		}
		File folder = new File(fileName);
		if(!folder.isDirectory()) {
			printUsage();
			return;
		}
		
		//File folder = new File("/home/roide/Fall2013/IR/3_project/temp/extracted2/AA/");
		File[] list = folder.listFiles();
		ArrayList<WikipediaDocument> docList = new ArrayList<WikipediaDocument>();
		NewParser parser = new NewParser();
		
		for( int i=0; i<list.length; i++) {
			File file = list[i];
			if(file.isFile()) {
				System.out.println(list[i].getName());
				parser.parse(file.getAbsolutePath(), docList);
				System.out.println("Total Doc Parsed=" + docList.size());
				//break;
			} else if( file.isDirectory() ) {
				File[] cFiles = file.listFiles();
				for( int j=0; j<cFiles.length; j++) {
					File cFile = cFiles[j];
					if(cFile.isFile()) {
						//System.out.println(list[i].getName());
						parser.parse(cFile.getAbsolutePath(), docList);
						System.out.println("Total Doc Parsed=" + docList.size());
						//break;
					}
				}
			}
		}
	}
	
	private static void printUsage() {
		System.out.println("Plased enter the path of the directory where the wiki docs has been extracted.");
		System.out.println("Ex: -o output_dir ");
	}
}
