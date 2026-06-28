package file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Stream;



/**
 * Used mainly for renaming TV show series. 
 * @author mori
 *
 */
public class RenameFilesV2  {

	private class SortComparator implements Comparator<File> {
		@Override
		public int compare(File o1, File o2) {
			if(o1.isDirectory() && o2.isFile())
				return -1;
			else if(o1.isFile() && o2.isDirectory())
				return 1;
			else
				return o1.getName().compareToIgnoreCase(o2.getName());
		}
	}
	
	private void replaceText( File dir, String src, String dest, boolean test) throws IOException {
		
		File files[] = dir.listFiles();
		Arrays.sort(files, new SortComparator());
		String oldName, newName;
		
		for( File f:files) {
			oldName = f.getCanonicalPath();
			newName = oldName.replace(src, dest);

			System.out.println(f.getName().replace(src, dest));
			
			if( !test )
				f.renameTo(new File(newName));
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
			rf.replaceText(new File(args[1]), args[2], args[3], false);
		
		else if( mode.equals("-r"))
			rf.replaceText(new File(args[1]), args[2], args[3], true);
		
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
