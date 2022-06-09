import java.sql.SQLException;
import java.util.List;
import entities.*;

public class ProgMain {

	public static void main(String[] args) {
//		List<ElectronicDevices> testList = null;
//		String textName = "phoneList.txt";
//		try {
//			testList = Utils.readPhones(textName);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		System.out.println();
//		for (ElectronicDevices o : testList)
//		{
//			Phone p = (Phone)o;
//			System.out.println(p);
//		}
//		
//		System.out.println("\nReading done. Writing to Binary file...\n");
//		Utils.writeBinaryPhone("Phones.bin", testList);
//		
//		testList.clear();
//		System.out.println("\nNo. of elements now: " + testList.size());
//		
//		testList = Utils.readBinaryPhones("Phones.bin");
//		
////		for (ElectronicDevices o : testList)
////		{
////			Phone p = (Phone)o;
////			System.out.println(p);
////		}		
//		testList.forEach(System.out::println);
//		System.out.println();
//		
//		UtilsDAO.setConnection();
//		String query;
//		try {
//			query = UtilsDAO.selectData();
//			System.out.println(query);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		UtilsDAO.closeConnection();
		
		VectThread t1 = new VectThread("Phones.bin");
		Thread trd = new Thread(t1);
		
		trd.start();
		try {
			trd.join();
			System.out.println("Avg weight - " + Math.round(t1.getAvgWeight() * 100.0) / 100.0 );
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
