package turingMachine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.LinkedHashMap;

public class Machine {
	//store line input from a file
	private String tableInput;
	
	//store each row in the table
	private String[] strRow;
	
	//store a transition function
	private String[] strTf;	
	
	//store the current state
	private String currentState;
	
	//store the entire transition table
	private Hashtable<String, LinkedHashMap<String, String>> table;
	
	//store a transition function 
	private LinkedHashMap<String, String> tf;
	
	//make a tape for string
	private Tape t;
	
	//possible tale alphabeta
	private static String[] possibleValues;
	
	//possible state 
	private String[] possibleStates;
	
	//final state
	private String finalState;
	
	private BufferedReader br;

	
	//initialize a machine
	public Machine() {		
		//initialize a tape
		t = new Tape();
		table = new Hashtable<String, LinkedHashMap<String,String>>();		
		possibleStates = new String[20]; 
	}
	
    public void generateTF(String file) {       
        
    	try {
    		    //open the input reader to read a file
    		    br = new BufferedReader(new FileReader(file));
                //read a first which might be used to store possible table alphabeta
	        	tableInput = br.readLine();
	        	//store the alphabeta to a series of String
	        	possibleValues = new String[tableInput.length()];
	        		        		   
	        	
	        	//get tape alphabeta
                for (int i=0; i < tableInput.length(); i++) { 
	        	    possibleValues[i] = "" + tableInput.charAt(i);
                }
                                
                //read a new line which could be the transition function
	        	tableInput = br.readLine(); 
	        	//state is calculated as 'q' + state number
	        	currentState = "" + tableInput.charAt(0) + tableInput.charAt(1);
	        		        	
	        	//counter for the possible states
	        	int i = 0;
	        	while (tableInput != null) {	
	        		//store each cell of a row divided by ";"
			        strRow = tableInput.split(";");
			        
			        //get the possible states which is stored in first cell of strRow
			        possibleStates[i] = strRow[0];
			        
			        //open a new space for transition function
			        tf = new LinkedHashMap<String, String>();

			        //combine possible values which might from tape alphabeta with transfer functions 
			        for (int j=1; j-1<possibleValues.length; j++) {	
			        	tf.put(possibleValues[j-1], strRow[j]);
			      	}
			        
			        //put combination of possible states and transition function into table
			        table.put(strRow[0], tf);  
                    
			        //read next line from file 
			        tableInput = br.readLine();
			        //read the information about next possible state			       
		        	i++;
	        	}
	        	
	        	//process to find the final state 
	        	//counter to help enumerate every possible states
	        	int j = 0;
	            while (possibleStates[j] != null) {
	            	//get all possible transition functions corresponding to all the possible states
	            	//the toString method might make the string to a form like[a,a,a]. It is necessary to cut all the unused characters
	            	//and put the useful characters together without space.
	            	String a = table.get(possibleStates[j]).values().toString().replace("[", "").replace("]", "").replace(",", "").replace(" ", "");
	            	
	            	//the final state could be the state with all '-' transitions
	            	if (a.matches("[-]+"))
	            		finalState = possibleStates[j];
	                j++;            
	            }		        		
	            
		    } catch (IOException e) {
			    e.printStackTrace();
		    }      			
    }
    
    //make the machine running
    public void run() { 
    	System.out.println("Please enter a string:");
    	//calculate the possible valid input character using makeSigma from possibleValues
        t.readFromKB(makeSigma(possibleValues));
        
        //print the initial tape
    	this.printProcess();
    	
    	//process would keep running until reaching final state
    	while (isFinal() == false) {    		    		
    	    //get transition function for current char of tape
            strTf = table.get(currentState).get(Character.toString(t.charAt(t.getMachinePointer()))).split(",");
            
            //before machine going into final state, if it sees a '-' transition function, it means machine crashed or 
            //input string got rejected
            if (strTf[0].equals("-")) {
        	    System.out.println("REJECTED");
        	    System.out.println();
    		    break;
            }

            //change current state 
    	    currentState = strTf[0];    
            
    	    //String accepted
    	    if (isFinal() == true) {
        	    System.out.println("ACCEPTED");
        	    System.out.println();
    		    break;
            }

            //change current cell Element    	
            t.assignAt(t.getMachinePointer(), strTf[1].charAt(0));	    
    
            //move pointer left or right
	        if (strTf[2].equals("R")) {
	            t.moveRight();
	        }    
            else if (strTf[2].equals("L")) { 
                t.moveLeft();
            } 	
      
	        System.out.print(" |-- ");
    	    this.printProcess();
    	}
    }
    
    //a method return whether the current state is final state
    public boolean isFinal() {
    	if (currentState.equals(finalState))
    		return true;
    	else
    	    return false;
    }
    
	public String getCurrentState() {
		return currentState;
	}

	public void displayTape() {
		t.display();
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (this.getClass() != o.getClass())) {
			return false;
		}
		return false;
	}

	@Override
	public int hashCode() {
		
		return 0;
	}
		
	//print the possible process which might happen to the tape
	public void printProcess() {
		for(int i=0; i<t.getMachinePointer(); i++){
	    	System.out.print(t.charAt(i));
	    }
	    
	    System.out.print(" " + currentState + " ");
	    
	    for(int i=t.getMachinePointer(); i<t.validLength(); i++){
	    	System.out.print(t.charAt(i));
	    }
	   	
	        System.out.println();
	        System.out.println();
	}

	public String[] getPossibleStates() {
		return possibleStates;
	}
	
	//method to calculate possible valid input of a tape which would match the regular expression
	public String makeSigma (String[] original) {
		String sigma = new String();
		sigma = sigma + "[";
		sigma = sigma + original[0]+"|";
		
		for (int i=1; i<original.length - 1; i++) {
			sigma = sigma + original[i]+"|";
		}
		
		sigma = sigma + original[original.length-1];
		sigma = sigma + "]";
		sigma = sigma + "+";
		return sigma;
	}
}
