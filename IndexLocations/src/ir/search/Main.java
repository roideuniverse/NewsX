package ir.search;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ir.search.parsers.NewParser;
import ir.search.wikipedia.WikipediaDocument;

public class Main {

	public static void main(String[] args) {
		List<WikipediaDocument> docList = null;
		try {
			if (args.length < 4) {
				printUsage();
				return;
			}
			//--server http://localhost:8983/solr/newsx --dir /home/roide/Fall2013/IR/3_project/temp/extTest
			String server ="";
			String dir = "";
			for(int i=0;i<4; i=i+2) {
				String input = args[i].trim();
				if(input.equals("--server")) {
					server = args[i+1].trim();
					FileUtil.setSolrUrl(server);
				} else if(input.equals("--dir")) {
					dir = args[i+1].trim();
				} else {
					printUsage();
					return;
				}
			}

			if (dir == null || dir.trim().length() <= 0 || server == null || server.trim().length() <=0 ) {
				printUsage();
				return;
			}
			docList = implementparser(dir);

		} catch (Exception e) {
			System.out.println("Error Message:" + e.getMessage());
		}

	}

	private static List<WikipediaDocument> implementparser(String fileName) {
		List<WikipediaDocument> docList = null;
		try {
			File folder = new File(fileName);
			if (!folder.isDirectory()) {
				printUsage();
				return docList;
			}
			File[] list = folder.listFiles();
			NewParser parser = new NewParser();
			docList = new ArrayList<>();
			for (int i = 0; i < list.length; i++) {
				File file = list[i];
				if (file.isFile()) {

					parser.parse(file.getAbsolutePath(), docList);

				} else if (file.isDirectory()) {
					File[] cFiles = file.listFiles();
					for (int j = 0; j < cFiles.length; j++) {
						File cFile = cFiles[j];
						if (cFile.isFile()) {
							// System.out.println(list[i].getName());
							parser.parse(cFile.getAbsolutePath(), docList);
							indexDocs(docList);
							docList.clear();
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			System.err.println("Error Message:" + e.getMessage());
		}
		return docList;
	}

	private static void indexDocs(List<WikipediaDocument> docList) {
		try {
			IndexDoc objIndexDoc = new IndexDoc();
			objIndexDoc.implementIndexing(docList);
			System.out.println("Complete");
		} catch (Exception e) {
			System.err.println("Error Message:" + e.getMessage());
		}
	}

	private static void printUsage() {
		System.out.println("Plased enter the url and path of the directory where the wiki docs has been extracted.");
		System.out.println("usage: --server http://localhost:8983/solr/coreName");
		System.out.println("usage: --dir wikiDumpExtractedFolder ");
		System.out.println("usage: java -jar newsindexer.jar --server http://localhost:8983/solr/coreName --dir wikiDumpExtractedFolder ");
	}
}
