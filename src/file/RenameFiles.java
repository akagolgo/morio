package file;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;



/**
 * Used mainly for renaming TV show series. 
 * @author mori
 *
 */
public class RenameFiles  {

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

	private void listDir( File dir) throws IOException {
		
		File files[] = dir.listFiles();
		Arrays.sort(files, new SortComparator());
		
		for( File f:files) {
			if(f.isDirectory())
				System.out.println(f.getCanonicalPath());
			else
				System.out.println(f.getName());
		}
	}

	public static void main(String args[]) throws Exception {
		
		if(args.length == 0) {
			RenameFiles.help();
			System.exit(1);
		}
		
		RenameFiles rf = new RenameFiles();
		
		System.out.println("arg used: " + args[0]);
					
		if( args[0].equals("-rr"))
			rf.replaceText(new File(args[1]), args[2], args[3], false);
		
		else if( args[0].equals("-r"))
			rf.replaceText(new File(args[1]), args[2], args[3], true);
		
		else if( args[0].equals("-l"))
			rf.listDir(new File(args[1]));
		
		else
			RenameFiles.help();
		
	}

	private static void help() {
		System.out.println();
		System.out.println("file.RenameFiles flag dir target replacement");
		System.out.println("use -l to list");
		System.out.println("use -r to test");
		System.out.println("use -rr to commit");
		System.out.println();
		System.exit(0);
	}

}
