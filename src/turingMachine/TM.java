package turingMachine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*******************************************************************/
/**************************TURING MACHINE***************************/
/*******************************************************************/
/*******************************************************************/
/***************************** AUTHORS *****************************/
/*******************************************************************/
//DONG HANG

public class TM {
	//a string to store file directory
	private static String file;
	public static boolean internal = true;
	private static boolean goingOn = true;
	
	public static void main(String[] args) {
		System.out.println("*******************************************************************");
	    System.out.println("**************************TURING MACHINE***************************");
	    System.out.println("*******************************************************************");
	    System.out.println("*******************************************************************");
	    System.out.println("***************************** AUTHORS *****************************");
	    System.out.println("*******************************************************************");
	    System.out.println("DONG HANG");
	    System.out.println();
	    System.out.println();
	    
		while (goingOn == true) {
		        menu();
                Machine m = new Machine();  
                m.generateTF(file);
                m.run();       
		}       
	}
	
	public static String getFile() {
		return file;
	}

	//user interface menu
	private static void menu() {
		try {
			InputStreamReader isr = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(isr);					    
			    System.out.println("Please enter the number of Turing Machine: ");
			    System.out.println("1. 0n1n");
			    System.out.println("2. ends with 0");
			    System.out.println("3. anbncn");
			    System.out.println("4. enter a new machine");
			    System.out.println("5. exit");
			    
			    int selection = Integer.parseInt(br.readLine());
			
			    if (selection == 1) {	    
			    	file = "src/0n1n.txt";
			    } else if (selection == 2) {	    
			            file = "src/endsWithA0.txt";
			    } else if (selection == 3) {	    
			    	    file = "src/anbncn.txt";  
			    } else if (selection == 4) {	    
				    	   System.out.println("Please enter file location:");
			    	       file = br.readLine();         
			    } else if (selection == 5) {
			    	   goingOn = false;
			           System.exit(0);
			    } else {
			    	System.out.println("invalid input...");
			    }
		} catch (IOException e) {
            
			e.printStackTrace();
		}
	}
}