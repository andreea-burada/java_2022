import java.io.IOException;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import entities.*;

public class ProgMain {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		List<ElectronicDevices> testList = null;
		String textName = "phoneList.txt";
		try {
			testList = Utils.readPhones(textName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println();
		for (ElectronicDevices o : testList)
		{
			Phone p = (Phone)o;
			System.out.println(p);
		}
		
		System.out.println("\nReading done. Writing to Binary file...\n");
		Utils.writeBinaryPhone("Phones.bin", testList);
		
		testList.clear();
		System.out.println("\nNo. of elements now: " + testList.size());
		
		testList = Utils.readBinaryPhones("Phones.bin");
		
		for (ElectronicDevices o : testList)
		{
			Phone p = (Phone)o;
			System.out.println(p);
		}		
		//testList.forEach(System.out::println);
		System.out.println();
		
//		--- STREAMS ---
		List<ElectronicDevices> streamedList = null;
		streamedList = testList.stream().filter(x -> ((Phone)x).getDiagonal() > 5)
				.sorted((a, b) -> ((Phone)a).getProducer().compareTo(((Phone)b).getProducer()))
				.collect(Collectors.toList());
		
		System.out.println("Streams\n");	
		for(ElectronicDevices o : streamedList)
		{
			Phone p = (Phone)o;
			System.out.println(p);
		}
		System.out.println("\n");
		
		
//		--- DB TEST ---
		try {
			UtilsDAO.setConnection();
			String query;
			
			query = UtilsDAO.selectData();
			System.out.println(query);
			
			UtilsDAO.closeConnection();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		

		VectThread t1 = new VectThread("Phones.bin");
		Thread trd = new Thread(t1);
		
		trd.start();
		try {
			trd.join();
			System.out.println("Avg weight - " + Math.round(t1.getAvgWeight() * 100.0) / 100.0 );
			String phonesList = "";
			List<ElectronicDevices> pList = t1.getDevicesList();
			for(ElectronicDevices eDev : pList)
			{
				Phone currentPhone = (Phone)eDev;
				phonesList += currentPhone.toString() + "*\n";
			}
			System.out.println(phonesList);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}	// end main

}
