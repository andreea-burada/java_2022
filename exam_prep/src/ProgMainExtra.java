import java.time.LocalDate;
import java.time.Month;

enum COLORS_ENUM {
	RED, GREEN, BLUE
};

public class ProgMainExtra {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		// playing with enum
		COLORS_ENUM color1 = COLORS_ENUM.RED;
		
		switch(color1) {
			case RED: {
				System.out.println("Color is RED");
				break;
			}
			case GREEN: {
				System.out.println("Color is GREEN");
				break;
			}
			case BLUE: {
				System.out.println("Color is BLUE");
				break;
			}
			default: {
				System.out.println("Sorry! Color unknown");
			}
		}
		
		// playing with date
		System.out.println("Date types\n");
		
		//@SuppressWarnings("deprecation")
		//Date date1 = new Date("05.07.2022");
		//System.out.println(date1.getMonth());
		
		LocalDate date2 = LocalDate.of(2022, 7, 5);
		System.out.println(date2.getMonth().toString());
		
		Month month1 = Month.MARCH;
		System.out.println(month1);

	}	// end main

}
