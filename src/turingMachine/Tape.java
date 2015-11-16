package turingMachine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Tape {
	//a series of char to  store tape elements
	private char[] tape = new char[50];
	
	//a pointer used to insert element
	private int pointer = 0;
	
	//a machine pointer which points to the current cell of the process
	public int machinePointer = 0;

	//when initialize a tape, it would be filled with 'B' which means blank to all cells
	public Tape() {
		int j = 0;
		while (j < tape.length){
			tape[j] = 'B';
			j++;
		}
	}
	
	//a method used to add new element
	private void insert (char elem) {
		tape[pointer] = elem;
		pointer++;
	}
	
	//return a cell content
	public char charAt (int i) {
		return tape[i];
	}
	
	//change the content of a cell
	public void assignAt (int i, char elem) {
		tape[i] = elem;
	}
	
	//move the machine pointer to left, if the current machine pointer is 0, it would becomes 49 after move left
	//so the tape would seems like infinite
	public void moveLeft() {
		machinePointer = mod((machinePointer - 1), 50);
	}
	
	//move the machine pointer to left, if the current machine pointer is 0, it would becomes 49 after move left
	//so the tape would seems like infinite
	public void moveRight() {
		machinePointer = mod((machinePointer + 1), 50);
	}
	
	//make the cell display itself
	public void display() {
		int i=0;
		while (i < validLength()){
			System.out.print(tape[i]);
			i++;
		}
		System.out.println();
	}
	
	//read the tape content from keyboard
	public void readFromKB(String sigma) {
		try {
			InputStreamReader isr = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(isr);
			String content = br.readLine();
			int i = 0;
			
			//if the input does not match the sigma or tape alphabeta plus epsilon, it would be counted as invalid
			if (content.matches(sigma)) {				
			    while(i < content.length()) {
				    insert(content.charAt(i));
				    i++;
			    }
			}
			else { 
				System.out.println("invalid input!");
				this.readFromKB(sigma);
			}	
		} catch (IOException e) {
            
			e.printStackTrace();
		}
	}
	
	//count the valid tape length include a 'B' after all valid character
	public int validLength() {
		int counter = 0;
		while (counter < tape.length)
		    if (tape[counter] != 'B') 
		    	counter++;
		    else
		    	break;
		return ++counter;
	}
	
	//a method to return a mod of two numbers	
	private int mod(int x, int y)
	{
	    int result = x % y;
	    if (result < 0)
	    {
	        result += y;
	    }
	    return result;
	}

	public int getMachinePointer() {
		return machinePointer;
	}
}
