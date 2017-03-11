//*****************************************************************************/
//*****************************************************************************/
//
//
//      file: Schenck_GameOfLife.java
//      author: E. Schenck
//      class: CS 141 Intro to Programming 
//
//      assignment: Program 6
//      date last modified: 3/11/2017
//
//      purpose: Implement Conway's Game of Life. Life is a simulation of 
//		simple one-celled organisms and therefore typically played on a 
//		2D grid of square cells. Each cell has two possible states: dead 
//		or alive. To calculate each new generation of the board, use the following 
//		rules:
//		For spaces already "alive":
//		- Each cell with less than two neighbors, who are alive, dies of loneliness 
//		in the next generation.
//		- Each cell with more than three neighbors, who are alive, dies of 
//		overpopulation in the next generation.
//		- Each cell with two or three neighbors, who are alive, continues to 
//		be alive in the next generation.
//		For spaces already "dead":
//		- Each cell with three neighbors, who are alive, comes to life in the 
//		next generation.
//		Note: Every cell has eight neighbors which are the cells that are 
//		diagonally, vertically, or horizontally adjacent.
//		For your program, use a 2D char array to represent the board. Use '0' to 
//		represent dead cells of the board and 'X' to represent cells which are alive. 
//		Your program will load game board data from a file to begin the game, with
//		the first line of the input file consisting of two ints representing the 
//		number of columns and rows for the board. 
//
//
//*****************************************************************************/
//*****************************************************************************/


import java.util.StringTokenizer;
import java.util.Scanner;
import java.io.*;


public class Schenck_GameOfLife
{
	private static int rows;			
	private static int columns;
	private static int generations;
	private int genCounter = 0;
	private int[][] gameboard;
	private String toHoldFile = "";
	
	
	public static void main(String[] args) throws IOException
	{
		// creating a GameOfLife game board object called game 
		// calling this method will prompt user to input a .life file 
		Schenck_GameOfLife game = new Schenck_GameOfLife();
		
		game.print();		// printing initial gameboard 
		
		// prompting user to enter a number of generations to run game 
		System.out.println("\nEnter number of generations: ");
		
		Scanner keyboard = new Scanner(System.in);
		
		generations = keyboard.nextInt();	// User input 
		
		System.out.print("\n");			// skipping a line 
		
		game.computeNextGeneration(generations);
		
	} // end of public void main 
	
	
	/**
	*	Constructor - initializes a new game board by prompting the user  for
	*	the file name and loading the game board data from the file
	*
	*/
	public Schenck_GameOfLife() throws IOException
	{
		
		System.out.print("\nEnter File Name: ");	// prompt for user input
		Scanner keyboard = new Scanner(System.in);	// Scanner object called keyboard
		String userFile = "C:\\Users\\Eric\\Desktop\\Java_Programs\\" + keyboard.nextLine();
		File file1 = new File(userFile);	// opening file object with user input
		
		// if statement validates file1
        if(!file1.exists())
		{
			System.out.println("File does not exist.");
			System.exit(0);
		}
		
		Scanner inputFile = new Scanner(file1);
		
		columns = inputFile.nextInt();		// saves number of columns
		
		rows = inputFile.nextInt();			// saves number of rows
		
		gameboard = new int[rows][columns];	// initializing gameboard array to given col and row
		
		inputFile.nextLine();	// using nextLine() to get to the 2nd line, or first line of input 
		
		for(int i = 0; i <= rows; i++)	// using for loop to save entire file to a String
		{
			toHoldFile += (inputFile.nextLine() + " ");
		}
		
		// creating StringTokenizer object with the toHoldFile String 
		StringTokenizer toTokenize = new StringTokenizer(toHoldFile);	
		
		
		for (int i = 0; i < rows; i++)	// for loop to handle gameboard creation
		{
			for (int j = 0; j < columns; j++)
			{
				if(toTokenize.nextToken().equals("X"))
				{
					gameboard[i][j] = 'X';	// set equal to ascii value of X
				}
				else 
				{
					gameboard[i][j] = '0';	// sets equal to ascii value of 0
				}		
			}
		}
	}
	
	
	/**
	*	get Column() - method that returns the amount of columns on gameboard
	*
	*/
	public int getColumn()
	{
		return columns;
	}
	
	
	/**
	*	get Rows() - method that returns the number of rows on gameboard
	*
	*/
	public int getRows()
	{
		return rows;
	}
	
	
	/**
	*	getCell(int column, int row) - method that returns the value of the cell, 
	*	returns 0 if the column of the row is outside the bounds of the game board. 
	*
	*/
	public int getCell(int column, int row)
	{	
		try{
			return gameboard[row][column];
		} catch (Exception e)
		{
			return '0';
		}
	}
	
	
	/**
	*	setCell(int column, int row, int value), sets the value of the cell. 
	*	does not have to handle out-of-bounds column or row values.
	*
	*/
	public void setCell(int column, int row, int value)
	{
		if(value == 48 || value == 88)
			gameboard[row][column] = value;
		else 
			System.out.println("incorrect value");
	}
	
	
	/**
	*  	computeNextGeneration(int generation), creates a temporary 2D array 
	* 	to compute the next iteration of the board containing the next generation 
	*	of organisms, as determined by the Rules of Life. Then updates the board 
	*	to represent the next generation. The argument passed in represents the number
	*	of generations the user wants to compute. To compute each generation, the method 
	*	should recursively call itself and decrement the integer until it terminates 
	*	when there are no more generations left to compute.
	*
	*/
	public void computeNextGeneration(int generation)
	{
		int[][] tempArray = new int[rows][columns]; //creating a temp 2D array
		
		for (int i = 0; i < rows; i++)	// for loop to handle gameboard creation
		{
			for (int j = 0; j < columns; j++)
			{
				int aliveCounter = 0;	// counter variable to keep track of living cells
				
				// block of if statements to check surrounding cells 
				// if they are alive, then aliveCounter increases by 1
				if(getCell(j,i-1) == 'X')
					aliveCounter++;
				if(getCell(j+1,i-1) == 'X')
					aliveCounter++;
				if(getCell(j+1,i) == 'X')
					aliveCounter++;					
				if(getCell(j+1,i+1) == 'X')
					aliveCounter++;
				if(getCell(j,i+1) == 'X')
					aliveCounter++;
				if(getCell(j-1,i+1) == 'X')
					aliveCounter++;
				if(getCell(j-1,i) == 'X')
					aliveCounter++;
				if(getCell(j-1,i-1) == 'X')
					aliveCounter++;
				
				//System.out.print("Counter: " + aliveCounter);
				
				if(gameboard[i][j] == 'X')	//checks if current cell is alive 
				{							// if alive implements rules 
					if(aliveCounter == 2 || aliveCounter == 3)
						tempArray[i][j] = 'X';
					else 
						tempArray[i][j] = '0';
				}
				else if(gameboard[i][j] != 'X') // if current cell is dead
				{
					if(aliveCounter == 3)		// implements rules
						tempArray[i][j] = 'X';
					else
						tempArray[i][j] = '0';
				}
			}	// end of for loop for columns 
		}	// end of for loop for rows
		
		// for loop transfers data from tempArray to gameboard
		for(int i = 0; i < rows; i++)
		{
			for(int j = 0; j < columns; j++)
			{
				gameboard[i][j] = tempArray[i][j];
			}
		}
		
		genCounter++;
		
		System.out.print("\ngeneration " + genCounter);
		
		print();	// print game board 
		
		if(generation > 1)		// checks if there is a remaining generation and 
		{						// calls the method for the next generation 
			computeNextGeneration(generation - 1);
		}
	}
	
	
	/**
	*	print method that prints the game console to the screen.
	*	Since all data has been stored as integers and specifically the 
	* 	represented ascii values ( 88 == 'X' & 48 == '0' )
	*
	*/
	public void print()
	{
		System.out.print("\n");			// skipping a line 
		
		for (int i = 0; i < rows; i++)	// for loop to handle printing gameboard
		{
			System.out.println();
			for (int j = 0; j < columns; j++)
			{
				if(gameboard[i][j] == 'X')		// if cell == ascii value 'X'
					System.out.print("X");		// print out an actual X char 
				else							
					System.out.print("0");		// print out an actual 0 char	
			}
		}
		
		System.out.print("\n");			// skipping a line 
	}
	
}
