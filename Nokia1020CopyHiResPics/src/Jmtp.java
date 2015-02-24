//import be.derycke.pieter.com.Guid;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
//import javax.swing.JOptionPane;

import java.util.Map.Entry;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import be.derycke.pieter.com.COMException;
import jmtp.*;

public class Jmtp {
	// preferences/custom values
	final static String destination = "Z:\\Shared Pictures\\From Timmay\\Camera roll";
	final static String deviceGuid = "6ac27878-a6fa-4155-ba85-f98f491d4f33"; // get this by iterating through manager.getDevices().toString()		
	final static BigInteger spaceCleanupThresholdGB = BigInteger.valueOf(3); 
	final static Boolean cleanupFirst = true;
	
	// constants
	final static BigInteger gigaByte = BigInteger.valueOf(1024 * 1024 * 1024);
	final static String jmtpLib = "jmtp";
	final static String primaryFolder = "Pictures";
	final static String secondaryFolder = "Camera Roll";
	final static String hiResPicsFilter = "pro__highres.jpg";
	final static String smartPicsFilter = "smart.jpg";
	final static String smartNarFilter = "smart.nar";
	final static String movieFilter = "pro.mp4";
	
	// "global" values
	static Logger errorLog;
	static Logger successLog;
	static PortableDevice device;
	static FilenameFilter hiResFilter = new FilenameFilter() {
		public boolean accept(File dir, String name) {
			String lowercaseName = name.toLowerCase();
			if (lowercaseName.contains(hiResPicsFilter)) {
				return true;
			} else {
				return false;
			}
		}
    };
	static FilenameFilter smartFilter = new FilenameFilter() {
		public boolean accept(File dir, String name) {
			String lowercaseName = name.toLowerCase();
			if (lowercaseName.contains(smartPicsFilter)) {
				return true;
			} else {
				return false;
			}
		}
    };
	static FilenameFilter narFilter = new FilenameFilter() {
		public boolean accept(File dir, String name) {
			String lowercaseName = name.toLowerCase();
			if (lowercaseName.contains(smartNarFilter)) {
				return true;
			} else {
				return false;
			}
		}
    };
	static FilenameFilter movFilter = new FilenameFilter() {
		public boolean accept(File dir, String name) {
			String lowercaseName = name.toLowerCase();
			if (lowercaseName.contains(movieFilter)) {
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
        	if (dev.toString().contains(deviceGuid))
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
                    			
                    			HashMap<String, String> newFiles = new HashMap<>();                    			
                    			// add the hiRes pics
                    			String lastHiResCopied = findLastFileCopied(hiResFilter);              			                    			
                    			newFiles.putAll(findFilesOnDevice(folder, hiResPicsFilter, lastHiResCopied));                    			
                    			// add the smart pics
                    			String lastSmartCopied = findLastFileCopied(smartFilter);              			                    			
                    			newFiles.putAll(findFilesOnDevice(folder, smartPicsFilter, lastSmartCopied));                    			
                    			// add the movies
                    			String lastMovieCopied = findLastFileCopied(movFilter);              			                    			
                    			newFiles.putAll(findFilesOnDevice(folder, movieFilter, lastMovieCopied));                    			
                    			
                    			if (newFiles.size() == 0)
                    			{
                    				System.out.print("No new files to import, exiting...");
                    				System.exit(0);
                    			}
                    			
                    			// use a dialog for input
                    			// String userInput = JOptionPane.showInputDialog("Found " + newHiResPics.size() + " to import. Continue? (Y/N): ");
//                    			for (String name : newFiles.keySet())
//                    			{
//                    				System.out.print(name + ", ");
//                    			}
//                    			System.out.println("");
                				System.out.print("Found " + newFiles.size() + " to import. Continue? (Y/N): ");                    				
                				Scanner scan = new Scanner(System.in);
                				String userInput = scan.nextLine();
                				scan.close();
                    		    if (userInput == null || !userInput.toLowerCase().equals("y"))
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

        manager.getDevices()[0].close();

    }

	private static void cleanup(PortableDeviceStorageObject storage, PortableDeviceObject folder) {		
		BigInteger freeSpace = storage.getFreeSpace();		
		BigInteger spaceCleanupThreshold = spaceCleanupThresholdGB.multiply(gigaByte);
		if (freeSpace.compareTo(spaceCleanupThreshold) < 0)
		{
			System.out.println("Time to cleanup. You have " + freeSpace.divide(gigaByte) + "GB left with a threshold of " + spaceCleanupThreshold.divide(gigaByte) + "GB");
			HashMap<String, String> narFiles = findFilesOnDevice(folder, smartNarFilter, "");
			if (narFiles.size() > 0)
			{
				System.out.print("Found " + narFiles.size() + " file(s). These are only used for on the camera for smart capture.  Would you like to delete them? (Y/N): ");                    				
				Scanner scan = new Scanner(System.in);
				String userInput = scan.nextLine();
				scan.close();
    		    if (userInput != null && userInput.toLowerCase().equals("y"))
    		    {
    		    	//TODO: start the deleting process.  Will require a refactor
    		    }			
			}
		}
	}

	private static HashMap<String, String> findFilesOnDevice(PortableDeviceObject folder, String filter, String lastFileCopied) {
		HashMap<String, String> filesToAdd = new HashMap<>();
		for (PortableDeviceObject file : ((PortableDeviceFolderObject)folder).getChildObjects())
		{
			if (file.getOriginalFileName().toLowerCase().contains(filter))
			{                    					
				if (file.getOriginalFileName().compareToIgnoreCase(lastFileCopied) > 0)
				{
					filesToAdd.put(file.getOriginalFileName(), file.getID());
				}
			}
		}
		return filesToAdd;
	}

	private static String findLastFileCopied(FilenameFilter filter) {
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
			System.out.print("No previous files of type " + filter.toString() + " found in " + destination + " do you wish to proceed? (Y/N): ");                    				
			Scanner scan = new Scanner(System.in);
			String userInput = scan.nextLine();
			scan.close();
		    if (userInput == null || !userInput.toLowerCase().equals("y"))
		    {
		    	System.exit(0);
		    }
		    else 
		    {
		    	lastFileCopied = " "; // space is the first ascii char so all filenames should be greater than this
		    }
		}
		return lastFileCopied;
	}

	private static void copyFiles(HashMap<String, String> filesToCopy) {
		PortableDeviceToHostImpl32 devToHost = new PortableDeviceToHostImpl32();
		int numFilesCopied = 0;
		for (Entry<String, String> picNameID : filesToCopy.entrySet())
		{
			try {
				devToHost.copyFromPortableDeviceToHost(picNameID.getValue(), destination, device);
				log(successLog, "success", "Imported " + ++numFilesCopied + " of " + filesToCopy.size() + " files: " + picNameID.getKey());
			} catch (COMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log(errorLog, "error", "Failed to import file " + picNameID.getKey());
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

            // This block configure the logger with handler and formatter  
            fh = new FileHandler("./logs/" + logName + new SimpleDateFormat("yyyyMMddhhmm'.txt'").format(new Date()) +".log");  
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