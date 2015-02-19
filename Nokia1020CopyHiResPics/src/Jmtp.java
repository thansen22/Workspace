//import be.derycke.pieter.com.Guid;
import java.io.File;
import java.util.LinkedHashMap;
//


import be.derycke.pieter.com.COMException;
import jmtp.*;

public class Jmtp {
	final static String destination = "C:\\Users\\timha_000\\test";
	
    public static void main(String[] args) {
    	System.loadLibrary("jmtp");
        PortableDeviceManager manager = new PortableDeviceManager();
        
        // change this value as the device is probably 0
        PortableDevice device = manager.getDevices()[1];
        // Connect to my mp3-player
        device.open();

        //System.out.println(device.getModel());

        //System.out.println("---------------");

        // Iterate over deviceObjects
        for (PortableDeviceObject object : device.getRootObjects()) {
            // If the object is a storage object
            if (object instanceof PortableDeviceStorageObject) {
                PortableDeviceStorageObject storage = (PortableDeviceStorageObject) object;

                for (PortableDeviceObject o2 : storage.getChildObjects()) {
                    if (o2.getOriginalFileName().equals("Pictures"))
                    {
                    	for (PortableDeviceObject folders1 : ((PortableDeviceFolderObject)o2).getChildObjects())
                    	{                    		
                    		if (folders1.getOriginalFileName().equals("Camera Roll"))
                            {
                    			// Find the last picture we copied over                    			
                    			File dst = new File(destination);
                    			String[] dstPicNames = dst.list();
                    			String lastPicCopied = null;
                    			if (dstPicNames.length > 0)
                    			{
                    				lastPicCopied = dstPicNames[dstPicNames.length - 1];
                    			}
                    			
                    			// grab the new ones
                    			LinkedHashMap<String, String> newHiResPics = new LinkedHashMap<>();                    			
                    			for (PortableDeviceObject pics : ((PortableDeviceFolderObject)folders1).getChildObjects())
                    			{
                    				if (pics.getOriginalFileName().contains("Pro__highres.jpg") && pics.getOriginalFileName().compareToIgnoreCase(lastPicCopied) > 0)
                    				{
                    					newHiResPics.put(pics.getOriginalFileName(), pics.getID());
                    				}
                    			}
                    			                    			
                    			for (String picID : newHiResPics.values())
                    			{
                    				try {
    									new PortableDeviceToHostImpl32().copyFromPortableDeviceToHost(picID, destination, device);
    								} catch (COMException e) {
    									// TODO Auto-generated catch block
    									e.printStackTrace();
    								}
                    			}
                    			                    			                    		
                    			System.out.println(newHiResPics.size());
                    			//System.out.println(pics.length);
                    			//System.out.println(pics[0].getOriginalFileName());
                    			/*
                    			try {
									new PortableDeviceToHostImpl32().copyFromPortableDeviceToHost(pics[0].getID(), "C:\\Users\\timha_000\\test", device);
								} catch (COMException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								*/
                    			
                    			
                            }
                    	}
                    }
                }
            }
        }

        manager.getDevices()[0].close();

    }
    
	public void getFile(String objectId, String destPath, PortableDevice device) throws COMException {
		new PortableDeviceToHostImpl32().copyFromPortableDeviceToHost(objectId, destPath, device);
	}
}