package com.problem1.main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.logging.Logger;

import com.problem1.util.InputFileProcessing;


public class MainClass {

	private static final String INPUT_FILE = "input.txt";
	private static final String OUTPUT_FILE = "output.txt";
	static final Logger LOGGER = Logger.getLogger(MainClass.class.getName());


	public static void main(String[] args) {


		Scanner scanner = new Scanner( MainClass.class.getResourceAsStream(INPUT_FILE));
		String inputString = new String();
		while(scanner.hasNextLine()){
			inputString = inputString + scanner.nextLine();
		}
		System.out.println("inputString data: " + inputString);
		LOGGER.fine("inputString data: " + inputString);


		if (inputString != null) {
			InputFileProcessing processor = new InputFileProcessing();
			try {
				processor.processInputFile(inputString);
			} catch (Exception e) {
				LOGGER.severe("Could not inputFileReader the conveyor system. " + e.getMessage());
			}
			/*try {
				Files.write(Paths.get(new File("").getAbsolutePath().concat(OUTPUT_FILE)),
						processor.routeBags().getBytes());
			} catch (IOException e) {

			} catch (Exception e) {
				LOGGER.severe("Could not route the bags. " + e.getMessage());
			}
*/		}
	}

}
