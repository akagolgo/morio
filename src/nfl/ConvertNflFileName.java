package nfl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Rename nfl files to standard
 * @author mori
 *
 */
public class ConvertNflFileName  {

	private final String DIR = "T:/";
	public ConvertNflFileName() throws FileNotFoundException {
		super();
		
		File targetDir = new File(DIR);
		if(!targetDir.exists()) {
			System.out.println("Target dir " + DIR + " does not exist!!");
			throw new FileNotFoundException("Target dir " + DIR + " does not exist!!");
		}
		
	}
	

	public void list() throws IOException {
		
		File targetDir = new File(DIR);		
		File[] targetFiles = targetDir.listFiles();
		ArrayList<NflGameFileName> convertCandidates = new ArrayList<NflGameFileName>();
		

		System.out.println("Looking at dir " + targetDir.getCanonicalPath());
		System.out.println("file count: " + targetFiles.length);
		System.out.println();
		
		for(int i = 0; i < targetFiles.length; i++) { 
			NflGameFileName g = new NflGameFileName(targetFiles[i]);

			if(g.isFilenameFormalized() || (!g.isATarget()))
				System.out.printf("%2d: %s %s\n", i+1, "skipping", targetFiles[i].getName());
			
			else  
				convertCandidates.add(g);
		}
		
		System.out.println();

		for(NflGameFileName g:convertCandidates) {
			
			g.parse();
			System.out.printf("%s\n",   g.getOriginalFile().getName());
			System.out.printf("%s\n\n", g.generateName());
		}
		
		System.out.println();
		System.out.println("use with arg '-c' to execute changes");
		System.out.println();
	}
	
	
	public void convert() throws IOException {
		
		File targetDir = new File(DIR);		
		File[] targetFiles = targetDir.listFiles();
		ArrayList<NflGameFileName> convertCandidates = new ArrayList<NflGameFileName>();
		
		System.out.println("Looking at dir " + targetDir.getCanonicalPath());
		System.out.println("file count: " + targetFiles.length);
		System.out.println();
		
		int changeCount = 0;
		int errorCount = 0;
		int skipCount = 0;
		
		for(int i = 0; i < targetFiles.length; i++) {
			NflGameFileName g = new NflGameFileName(targetFiles[i]);
			if(g.isFilenameFormalized() || (!g.isATarget())) {
				System.out.printf("%2d: %s %s\n", i+1, "skipping", targetFiles[i].getName());
				skipCount++;
			} else  
				convertCandidates.add(g);
		}
		
		for(NflGameFileName g:convertCandidates) {
			
			g.parse();
			System.out.printf("%s\n",   g.getOriginalFile().getName());
			System.out.printf("%s\n\n", g.generateName());
			try {
				g.rename();
				changeCount++;
			} catch(Exception ex) {
				System.err.println("error renaming: " + g.getOriginalFile().getName());					
				System.err.println(ex.getMessage());
				errorCount++;
			}
		}

		System.out.println();
		System.out.println("changeCount: " + changeCount);
		System.out.println(" errorCount: " + errorCount);
		System.out.println("  skipCount: " + skipCount);
		System.out.println();
	}
	
	public static void main(String[] args) throws Exception {
		
		ConvertNflFileName cfn = new ConvertNflFileName();
		
		if(args.length == 0)
			cfn.list();
		
		else if(args[0].equals("-c"))
			cfn.convert();
		
		else
			System.out.println("bad args. try no args.");
	}

}
