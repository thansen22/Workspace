//import be.derycke.pieter.com.Guid;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.swing.JOptionPane;

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import be.derycke.pieter.com.COMException;
import jmtp.*;

public class Jmtp {
	// preferences/custom values
	final static String destination = "C:/Users/timha_000/test"; //"Z:/Shared Pictures/From Timmay/Camera roll";
	final static String deviceId = "vid_0421&pid_0661&mi_00#7&1bb7d0ae&0&0000"; // get this by iterating through manager.getDevices().toString()		
	final static BigInteger spaceCleanupThresholdGB = BigInteger.valueOf(3); 
	final static Boolean cleanupFirst = false;
	final static int cleanupMoviePercentage = 25;
	
	// constants
	final static BigInteger gigaByte = BigInteger.valueOf(1024 * 1024 * 1024);
	final static BigInteger megaByte = BigInteger.valueOf(1024 * 1024);
	final static String jmtpLib = "jmtp";
	final static String primaryFolder = "Pictures";
	final static String secondaryFolder = "Camera Roll";
	final static String allPicsFilterPattern = ".jpg";
	final static String hiResPicsFilterPattern = "__highres.jpg";
	final static String smartPicsFilterPattern = "smart.jpg";
	final static String smartNarFilterPattern = "smart.nar";
	final static String movieFilterPattern = "pro.mp4";
	
	// "global" values
	static Logger errorLog;
	static Logger successLog;
	static PortableDevice device;
	static FilenameFilter allPicsFilter = new FilenameFilter() {
		public boolean accept(File dir, String name) {
			String lowercaseName = name.toLowerCase();
			if (lowercaseName.contains(allPicsFilterPattern)) {
				return true;
			} else {
				return false;
			}
		}
    };
	static FilenameFilter hiResFilter = new FilenameFilter() {
		public boolean accept(File dir, String name) {
			String lowercaseName = name.toLowerCase();
			if (lowercaseName.contains(hiResPicsFilterPattern)) {
				return true;
			} else {
				return false;
			}
		}
    };
	static FilenameFilter smartFilter = new FilenameFilter() {
		public boolean accept(File dir, String name) {
			String lowercaseName = name.toLowerCase();
			if (lowercaseName.contains(smartPicsFilterPattern)) {
				return true;
			} else {
				return false;
			}
		}
    };
	static FilenameFilter narFilter = new FilenameFilter() {
		public boolean accept(File dir, String name) {
			String lowercaseName = name.toLowerCase();
			if (lowercaseName.contains(smartNarFilterPattern)) {
				return true;
			} else {
				return false;
			}
		}
    };
	static FilenameFilter movFilter = new FilenameFilter() {
		public boolean accept(File dir, String name) {
			String lowercaseName = name.toLowerCase();
			if (lowercaseName.contains(movieFilterPattern)) {
				return true;
			} else {
				return false;
			}
		}
    };
	
    public static void main(String[] args) {
    	System.loadLibrary(jmtpLib);
        PortableDeviceManager manager = new PortableDeviceManager();        
       
        for (PortableDevice dev : manager.getDevices())
        {
        	if (dev.toString().contains(deviceId))
        	{
        		device = dev;
        	}
        }
       
        if (device == null)
        {
        	System.out.println("Nokia 1020 phone not found");
        	System.exit(0);
        }
        
        // Connect to my mp3-player
        device.open();

        //System.out.println(device.getModel());

        // Iterate over deviceObjects
        for (PortableDeviceObject object : device.getRootObjects()) 
        {
            // If the object is a storage object
            if (object instanceof PortableDeviceStorageObject) 
            {
                PortableDeviceStorageObject storage = (PortableDeviceStorageObject) object;
                for (PortableDeviceObject o2 : storage.getChildObjects()) 
                {
                    if (o2.getOriginalFileName().equals(primaryFolder))
                    {
                    	for (PortableDeviceObject folder : ((PortableDeviceFolderObject)o2).getChildObjects())
                    	{                    		
                    		if (folder.getOriginalFileName().equals(secondaryFolder))
                            {
                    			if (cleanupFirst)
                    			{
                    				cleanup(storage, folder);
                    			}
                    			
                    			ArrayList<PortableDeviceObject> originalFiles = new ArrayList<>();
                    			originalFiles.addAll(findFilesOnDevice(folder, allPicsFilterPattern, ""));
                    			
                    			ArrayList<PortableDeviceObject> newFiles = new ArrayList<>();
                    			newFiles.addAll(findFilesOnDevice(folder, allPicsFilterPattern, ""));
                    			
//                    			System.out.println("All pics before reduction: " + newFiles.size());
                    			int allFilesIndex = 0;                    			
                    			for(PortableDeviceObject file : originalFiles)
                    			{                    				
                    				// we will copy the .jpg files unless there's a __highres file with the same name and the same creation date.
                    				// the other types of .jpg files can be those taken that are from smart capture, office lens, front facing camera, etc... 
                    				if (!file.getOriginalFileName().toLowerCase().contains(hiResPicsFilterPattern))
                    				{
                    					// check to see if it has a highres counterpart
                    					String possibleHiResFileName = file.getOriginalFileName().substring(0, file.getOriginalFileName().indexOf(".jpg")).concat(hiResPicsFilterPattern);
                    					
                    					// unfortunately we have to go through the entire collection to see if it exists as order could be anything, however, since it's most likely the very next object... doing a hack shortcut for speed...                    					
                    					if (allFilesIndex + 1 < originalFiles.size() && originalFiles.get(allFilesIndex + 1).getOriginalFileName().equals(possibleHiResFileName))
                    					{
                    						if (originalFiles.get(allFilesIndex + 1).getDateAuthored() == null)
                    						{
                    							System.out.println(originalFiles.get(allFilesIndex + 1).getOriginalFileName());                    							
                    						}
                    						//System.out.println("found alternate high res file: " + possibleHiResFileName + " still need to check creation date...");
                    						if (originalFiles.get(allFilesIndex + 1).getDateAuthored().equals(file.getDateAuthored()))
                    						{
                    							System.out.println("remove file");
                    							newFiles.remove(file);
                    						}                    						                    						
                    					}
                    					else
                    					{
                    						// didn't find it, so now we have to go through everything.  This hit appears to be only for the other file types, and possibly some other user manipulation, just don't want to assume anything...
                    						for (PortableDeviceObject secondFileCheck : originalFiles)
                    						{
                    							if (secondFileCheck.getOriginalFileName().equals(possibleHiResFileName))
                    							{
                    								System.out.println("found alternate high res file in a different location than the next iterator: " + possibleHiResFileName + ". Remove this else block if this message is never displayed as it's slow...");
                    								newFiles.remove(secondFileCheck);
                    							}
                    						}                    						
                    					}
                    				}
                    				allFilesIndex++;
                    			}
//                    			System.out.println("All pics after reduction: " + newFiles.size());
//                    			for (PortableDeviceObject file : newFiles)
//                    			{
//                    				System.out.println(file.getOriginalFileName());
//                    			}
                    			
//
//                    			// add the hiRes pics
//                    			String lastHiResCopied = findLastFileCopied(hiResFilter, hiResPicsFilterPattern);              			                    			
//                    			newFiles.addAll(findFilesOnDevice(folder, hiResPicsFilterPattern, lastHiResCopied));                    			
//                    			// add the smart pics
//                    			String lastSmartCopied = findLastFileCopied(smartFilter, smartPicsFilterPattern);              			                    			
//                    			newFiles.addAll(findFilesOnDevice(folder, smartPicsFilterPattern, lastSmartCopied));                    			
//                    			// add the movies
//                    			String lastMovieCopied = findLastFileCopied(movFilter, movieFilterPattern);              			                    			
//                    			newFiles.addAll(findFilesOnDevice(folder, movieFilterPattern, lastMovieCopied));                    			
                    			
                    			//TODO: temp here to play around with the new pics logic
                    			System.exit(0);
                    			
                    			if (newFiles.size() == 0)
                    			{
                    				System.out.print("No new files to import, exiting...");
                    				System.exit(0);
                    			}
                    			
                    			// use a dialog for input
                    			int userInput = JOptionPane.showConfirmDialog(null, "Found " + newFiles.size() + " to import.", "Import files?", JOptionPane.YES_NO_OPTION);
                    		    if (userInput == JOptionPane.NO_OPTION)
                    		    {
                    		    	System.exit(0);
                    		    }
                    			                
                    			copyFiles(newFiles);
                    			                    			                    		
                    			System.out.println("Completed importing " + newFiles.size() + " pic(s)");   
                    			
                                cleanup(storage, folder);
                            }
                    	}
                    }
                }
            }
        }

        device.close();
    }

	private static void cleanup(PortableDeviceStorageObject storage, PortableDeviceObject folder) {		
		BigInteger freeSpace = storage.getFreeSpace();
		BigInteger spaceCleanupThreshold = spaceCleanupThresholdGB.multiply(gigaByte);
		if (freeSpace.compareTo(spaceCleanupThreshold) < 0)
		{
			System.out.println("Time to cleanup. You have " + freeSpace.divide(megaByte) + "MB left with a threshold of " + spaceCleanupThreshold.divide(megaByte) + "MB");
			
			// Nar files first as they are sufficiently large and typically useless as you never come back later to edit the smart capture
			deleteNarFiles(folder);
			
			freeSpace = storage.getFreeSpace();
			if (freeSpace.compareTo(spaceCleanupThreshold) < 0)
			{
				System.out.println("Still not enough. You now have " + freeSpace.divide(megaByte) + "MB left with a threshold of " + spaceCleanupThreshold.divide(megaByte) + "MB");				
				deleteMovieFiles(folder);
			}
		}
	}

	private static void deleteMovieFiles(PortableDeviceObject folder) {
		ArrayList<PortableDeviceObject> movFiles = findFilesOnDevice(folder, movieFilterPattern, "");
		if (movFiles.size() > 0)
		{
			BigInteger totalMovieSpace = BigInteger.ZERO;				    
			BigInteger partialMovieSpace = BigInteger.ZERO;
			int filesToDelete = cleanupMoviePercentage * movFiles.size() / 100;
			int filesToDeleteCounter = filesToDelete;
			for (PortableDeviceObject movie : movFiles)
			{
				BigInteger movSize = movie.getSize();
				totalMovieSpace = totalMovieSpace.add(movSize);
				if (filesToDeleteCounter > 0)
				{
					partialMovieSpace = partialMovieSpace.add(movSize);
					filesToDeleteCounter--;
				}
			}
			System.out.println("Your " + movFiles.size() + " videos are taking up a total of " + totalMovieSpace.divide(megaByte) + "MBs");
			System.out.println("Your preferences are to delete " + cleanupMoviePercentage + "% of your total videos, this translates into " + filesToDelete + " and a total space of " + partialMovieSpace.divide(megaByte) + "MBs.");
			
			int confirm = JOptionPane.NO_OPTION;
			do
			{
				String userInput = JOptionPane.showInputDialog("You can use this dialog to delete more or less files modifying the deletion of: " + partialMovieSpace.divide(megaByte) + "MBs of videos. Modifying the value will recalculate and show a confirmation. Click cancel to skip deleting videos", filesToDelete);
				if (userInput == null)
				{
					System.out.println("User cancelled video deletion");
					break;
				}
				else 				
			    {	
					filesToDelete = Integer.parseInt(userInput);
					if (filesToDelete > 0 && filesToDelete <= movFiles.size())
					{
						partialMovieSpace = BigInteger.ZERO;
						for (int i = 0; i < filesToDelete; i++)
						{
							partialMovieSpace = partialMovieSpace.add(movFiles.get(i).getSize());
						}
						confirm = JOptionPane.showConfirmDialog(null, "Going to delete " + userInput + " videos now freeing up " + partialMovieSpace.divide(megaByte) + "MBs... Confirm?", "Delete Confirmation", JOptionPane.YES_NO_OPTION);
					}
			    }
			} while (confirm == JOptionPane.NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION)
			{
				System.out.println("Start the deleting of videos!");
				int vidFilesDeleted = 0;
				int totalFilesToDelete = movFiles.size();
				for (int i = 0; i < filesToDelete; i++)
				{
					String vidFileName = movFiles.get(i).getOriginalFileName();
					File checkForFile = new File(destination, movFiles.get(i).getOriginalFileName());
					if (checkForFile.exists())
					{
						try
						{
							//movFiles.get(i).delete();
							log(successLog, "success", "Deleted " + ++vidFilesDeleted + " of " +totalFilesToDelete + " files: " + vidFileName);
						} 							
						catch (Exception e)
						{
							e.printStackTrace();
							log(errorLog, "error", "Failed to import file " + vidFileName);
						}
					}
					else
					{
						log(errorLog, "error", "File " + checkForFile + " not found. You must first manually copy this file to the destination folder or manually delete this file from your phone.");
						totalFilesToDelete--;
					}
				}	
			}
		}
	}

	private static void deleteNarFiles(PortableDeviceObject folder) {
		ArrayList<PortableDeviceObject> narFiles = findFilesOnDevice(folder, smartNarFilterPattern, "");
		if (narFiles.size() > 0)
		{
			int userInput = JOptionPane.showConfirmDialog(null, "Found " + narFiles.size() + " .nar file(s). These are only used for on the camera for smart capture.  Would you like to delete them?", "Delete .nar files?", JOptionPane.YES_NO_OPTION);
			if (userInput == JOptionPane.YES_OPTION)				
		    {
		    	// start the deleting process
				int narFilesDeleted = 0;
				for (PortableDeviceObject narFile : narFiles)
				{
					if (narFile.canDelete())
					{
						String narFileName = narFile.getOriginalFileName();
						try
						{
							narFile.delete();
							log(successLog, "success", "Deleted " + ++narFilesDeleted + " of " + narFiles.size() + " files: " + narFileName);
						} 							
						catch (Exception e)
						{
							e.printStackTrace();
							log(errorLog, "error", "Failed to import file " + narFileName);
						}
					}
				}
		    }			
		}
		else 
		{
			System.out.println("No .nar files found on device to delete");
		}
	}

	private static ArrayList<PortableDeviceObject> findFilesOnDevice(PortableDeviceObject folder, String filter, String lastFileCopied) {
		ArrayList<PortableDeviceObject> filesOnDevice = new ArrayList<>();
		for (PortableDeviceObject file : ((PortableDeviceFolderObject)folder).getChildObjects())
		{
			if (file.getOriginalFileName().toLowerCase().contains(filter))
			{                    					
				if (file.getOriginalFileName().compareToIgnoreCase(lastFileCopied) > 0)
				{
					filesOnDevice.add(file);					
				}
			}
		}
		return filesOnDevice;
	}

	private static String findLastFileCopied(FilenameFilter filter, String filePattern) {
		// Find the last picture we copied over                    			
		File dst = new File(destination);
		String[] dstPicNames = dst.list(filter);		
		String lastFileCopied = null;
		if (dstPicNames.length > 0)
		{
			// have to do a sort on the values because it's sorted by date rather than name which isn't necessarily in alphabetical order
			Arrays.sort(dstPicNames);
			lastFileCopied = dstPicNames[dstPicNames.length - 1];
			//System.out.println(lastFileCopied);
		}
		else
		{
			// Use joption pane if this ever runs out of debug...
			//String userInput = JOptionPane.showInputDialog("No previous pictures found in " + destination + " do you wish to proceed? (Y/N):");
			int userInput = JOptionPane.showConfirmDialog(null, "No previous files with the pattern *" + filePattern + " found in " + destination + " do you wish to proceed?", "New destination or new files found?", JOptionPane.YES_NO_OPTION);
			if (userInput == JOptionPane.NO_OPTION)
		    {
		    	System.exit(0);
		    }
		    else 
		    {
		    	lastFileCopied = ""; // space is the first ascii char so all filenames should be greater than this
		    }
		}
		return lastFileCopied;
	}

	private static void copyFiles(ArrayList<PortableDeviceObject> filesToCopy) {
		PortableDeviceToHostImpl32 devToHost = new PortableDeviceToHostImpl32();
		int numFilesCopied = 0;
		for (PortableDeviceObject file : filesToCopy)
		{
			try 
			{
				devToHost.copyFromPortableDeviceToHost(file.getID(), destination, device);
				log(successLog, "success", "Imported " + ++numFilesCopied + " of " + filesToCopy.size() + " files: " + file.getOriginalFileName());
			} 
			catch (COMException e) 
			{				
				e.printStackTrace();
				log(errorLog, "error", "Failed to import file " + file.getOriginalFileName());
			}
			catch (Exception e)
			{
				e.printStackTrace();
				log(errorLog, "error", "Failed to import file " + file.getOriginalFileName());
			}
		}
	}

	private static void log(Logger log, String logType, String msg) {
		if (log == null)
		{
			log = createLogger(logType);
		}
		
		log.info(msg);
	}
	
	private static Logger createLogger(String logName) {
		Logger logger = Logger.getLogger(logName);  
        FileHandler fh;  

        try {  
        	File logs = new File("./logs/");
        	if (!logs.exists())
        	{
        		logs.mkdirs();
        	}
            // This block configure the logger with handler and formatter  
            fh = new FileHandler("./logs/" + logName + new SimpleDateFormat("yyyyMMddhhmm").format(new Date()) +".log");  
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();  
            fh.setFormatter(formatter);  
        } catch (SecurityException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }
		return logger;
	}
}