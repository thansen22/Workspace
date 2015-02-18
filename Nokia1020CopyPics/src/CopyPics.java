import java.io.File;

import javax.swing.filechooser.FileSystemView;

public class CopyPics {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String sdir = System.getenv("EXTERNAL_STORAGE");
		
		File[] paths;
		FileSystemView fsv = FileSystemView.getFileSystemView();

		// returns pathnames for files and directory
		paths = File.listRoots();
		
		// for each pathname in pathname array
		for(File path:paths)
		{
		    // prints file and directory paths
		    System.out.println("Drive Name: "+path);
		    System.out.println("Description: "+fsv.getSystemTypeDescription(path));
		}
		
		/*
		File dir = new File("C:\\users\\timha_000");
		File[] listOfPics = dir.listFiles();
		if (listOfPics != null)
		{
			for (File file : listOfPics)
			{
				System.out.println(file.getName());			
			}
		}
		*/
	}
}
