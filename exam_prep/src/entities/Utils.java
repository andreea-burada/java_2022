package entities;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;

public class Utils {
	private static List<ElectronicDevices> deviceList = null ;
	
	public static List<ElectronicDevices> createPhones(int n) throws Exception
	{
		if (n < 0)
			throw new Exception ("Invalid n");
		
		if (deviceList == null)
			deviceList = new ArrayList<ElectronicDevices>();
		else
			deviceList.removeAll(deviceList);
		
		for (var i = 0; i < n; i++)
		{
			Phone toAdd = new Phone();
			deviceList.add(toAdd);
		}
		
		return deviceList;
	}
	
	// read from .txt file
	public static List<ElectronicDevices> readPhones(String file) throws Exception
	{
		// reading line by line 
		// declaring the file
		File phonesFile = new File(file);
		Utils.createPhones(0);
		// accessing the content
		try {
			RandomAccessFile phonesRAF = new RandomAccessFile(phonesFile, "rw");
			// long  x = phonesRAF.length(); // - for testing
			while (phonesRAF.getFilePointer() < phonesRAF.length())
			{
				float weig = Float.parseFloat(phonesRAF.readLine());	// phonesRAF.readFloat();
				double diag = Double.parseDouble(phonesRAF.readLine());// phonesRAF.readDouble();
				String prod = phonesRAF.readLine();
				Phone toAdd = new Phone();
				
				toAdd.setWeight(weig);
				toAdd.setDiagonal(diag);
				toAdd.setProducer(prod);
				
				deviceList.add(toAdd);
			}
			
			phonesRAF.close();
		}
		catch (IOException ioex)
		{
			ioex.printStackTrace();
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		finally
		{
			System.out.println("\nReading complete.");
		}
		
		return deviceList;
	}
	
	public static void writeBinaryPhone(String file, List<ElectronicDevices> listP)
	{
		if (listP.size() < 1)
		{
			System.out.println("\nList is empty!");
			return;
		}
		// data output stream <- buffer output stream <- file output stream
		DataOutputStream dataOUT = null;
		try {
			dataOUT = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
			for (ElectronicDevices item : listP)
			{
				Phone phoneItem = (Phone) item;
				// write to file
				dataOUT.writeFloat(phoneItem.getWeight());
				dataOUT.writeDouble(phoneItem.getDiagonal());
				dataOUT.writeUTF(phoneItem.getProducer());
			}
			dataOUT.close();
			System.out.println("Finished writing to Binary File.");
		}
		catch (IOException ioex)
		{
			ioex.printStackTrace();
		}
	}
	
	public static List<ElectronicDevices> readBinaryPhones(String file)
	{
		try {
			Utils.createPhones(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// data input stream <- buffer input stream <- file input stream
		DataInputStream dataIN = null;
		try {
			dataIN = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
			try 
			{
				while (true)
				{
					float weigh = dataIN.readFloat();
					double diag = dataIN.readDouble();
					String prod = dataIN.readUTF();
					Phone toAdd = new Phone();
					try {
					toAdd.setWeight(weigh); toAdd.setDiagonal(diag); toAdd.setProducer(prod);
					}
					catch (Exception ex)
					{
						System.out.println(ex.getMessage());
					}
					deviceList.add(toAdd);
				}
			}
			catch (EOFException eofex)
			{
				// eofex.printStackTrace();
				System.out.println("\nReading from Binary File done.\n");
			}
			// done reading
			dataIN.close();	
		}
		catch (IOException ioex)
		{
			ioex.printStackTrace();
		}
		
		return deviceList;
	}
}
