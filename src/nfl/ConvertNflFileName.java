package nfl;

import java.io.File;
import java.io.IOException;


/**
 * Rename nfl files to standard
 * @author mori
 *
 */
public class ConvertNflFileName  {

	private final String DIR = "M:/video/NFL/2025";
	//private final String DIR = "P:/handbrake";
	//private final String DIR =  "T:/";
	public ConvertNflFileName() {
		super();
	}
	

	/**
	 * @param week can be null. Unspecified
	 * @throws IOException
	 */
	public void list(String week) throws IOException {
		
		File targetDir = new File(DIR);		
		File[] targetFiles = targetDir.listFiles();
		NflGameFileName g = new NflGameFileName();

		System.out.println("Looking at dir " + targetDir.getCanonicalPath());
		System.out.println("file count: " + targetFiles.length);
		System.out.println();
		
		for(int i = 0; i < targetFiles.length; i++) { 
			if(g.isWellFormed(targetFiles[i].getName()) || (!g.isATarget(targetFiles[i])))
				System.out.printf("%2d: %s %s\n", i+1, "skipping", targetFiles[i].getName());			
			else  {
				g.parse(targetFiles[i].getName());
				System.out.printf("%2d: %s\n", i+1, targetFiles[i].getName());
				System.out.printf("%2d: %s\n\n", i+1, g.generateName(week));
			}
		}
		System.out.println();
		System.out.println("use '-c' as second arg to execute changes");
		System.out.println();
	}
	

	
	public void convert(String week) throws IOException {
		
		File targetDir = new File(DIR);		
		File[] targetFiles = targetDir.listFiles();
		NflGameFileName g = new NflGameFileName();

		System.out.println("Looking at dir " + targetDir.getCanonicalPath());
		System.out.println("file count: " + targetFiles.length);
		System.out.println();
		
		int changeCount = 0;
		int errorCount = 0;
		int skipCount = 0;
		
		for(int i = 0; i < targetFiles.length; i++) { 
			if(g.isWellFormed(targetFiles[i].getName()) || (!g.isATarget(targetFiles[i]))) {
				System.out.printf("%2d: %s %s\n", i+1, "skipping", targetFiles[i].getName());
				skipCount++;
			} else  {
				g.parse(targetFiles[i].getName());
				System.out.printf("%2d: %s\n", i+1, targetFiles[i].getName());
				System.out.printf("%2d: %s\n\n", i+1, g.generateName(week));
				try {
					g.rename(targetFiles[i], week);
					changeCount++;
				} catch(Exception ex) {
					System.err.println("error renaming: " + targetFiles[i].getName());					
					System.err.println(ex.getMessage());
					errorCount++;
				}
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
			System.out.println("bad args. specify week as first arg.");
		
		else if(args.length == 1)
			cfn.list(args[0]);
		
		// commit change with week specified
		else if(args.length == 2 && args[1].equals("-c"))
			cfn.convert(args[0]);
		
		else
			System.out.println("bad args. specify week as first arg.");
	}

}
