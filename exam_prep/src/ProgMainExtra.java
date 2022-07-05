
public class ProgMainExtra {

	public static void main(String[] args) {
		// playing with enum
		enum COLORS_ENUM {
			RED, GREEN, BLUE
		};
		
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

	}	// end main

}
