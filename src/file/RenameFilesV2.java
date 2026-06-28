package file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;



/**
 * Used mainly for renaming TV show series. 
 * @author mori
 *
 */
public class RenameFilesV2  {

	private void replaceText(Path directory, String targetText, String replacement, boolean test) throws IOException {

        // Sanity check: Ensure the path exists and is actually a directory
        if (!Files.exists(directory) || !Files.isDirectory(directory)) {
            System.err.println("Error: The provided path is not a valid directory.");
            return;
        }

        // Use a try-with-resources block to ensure the file stream closes properly
        try (Stream<Path> stream = Files.list(directory)) {
            
            stream.filter(Files::isRegularFile) // Only modify files, skip sub-directories
                  .forEach(path -> {
                      String originalName = path.getFileName().toString();
                      
                      // Check if the file contains the target text before processing
                      if (originalName.contains(targetText)) {
                          String newName = originalName.replace(targetText, replacement);
                          Path targetPath = path.resolveSibling(newName);
                          
                          System.out.println(newName);
                          
                    	  if(!test) {
	                          try {
	                              Files.move(path, targetPath);
	                              //System.out.println("Renamed: " + originalName + " -> " + newName);
	                          } catch (IOException e) {
	                              System.err.println("Failed to rename " + originalName + ": " + e.getMessage());
	                          }
                    	  }
                      }
                  });

        } catch (IOException e) {
            System.err.println("Error reading directory: " + e.getMessage());
        }		
	}

	private void listDir(Path dirPath) throws IOException {
		
		try (Stream<Path> stream = Files.list(dirPath)) {
            stream.filter(Files::isRegularFile)
                  .sorted() // <-- Sorts alphabetically automatically
                  .map(Path::getFileName)
                  .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	public static void main(String args[]) throws Exception {
		
		if(args.length == 0) {
			RenameFilesV2.help();
			System.exit(1);
		}
		
		RenameFilesV2 rf = new RenameFilesV2();
		
		String mode = args[0];
		
		System.out.println("arg used: " + mode);
					
		if( mode.equals("-rr"))
			rf.replaceText(Paths.get(args[1]), args[2], args[3], false);
		
		else if( mode.equals("-r"))
			rf.replaceText(Paths.get(args[1]), args[2], args[3], true);
		
		else if( mode.equals("-l"))
			rf.listDir(Paths.get(args[1]));
		
		else
			RenameFilesV2.help();
		
	}

	private static void help() {
		System.out.println();
		System.out.println("list dir: -l dir");
		System.out.println("file.RenameFiles flag dir target replacement");
		System.out.println("test rename: -r dir target replacement");
		System.out.println("commit rename: -rr dir target replacement");
		System.out.println();
		System.exit(0);
	}

}
