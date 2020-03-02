package alignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import jaligner.Alignment;

public class LoadDataBase {
	
	private final int listSize = 15;
	
	
	 public String[] returnAllFilesNames(File directory){
		File[] listOfFiles = directory.listFiles();
		String[] fileNames = new String[listSize];
			 for (int i = 0; i < listOfFiles.length; i++) {
			      if (listOfFiles[i].isFile()) {
			        fileNames[i]=(listOfFiles[i].getName());
			      }
			  }
			 
		return fileNames;
		 
	 }
	 
	 public int numberOfSequences(File fastaFile) throws FileNotFoundException {

		 int numberOfSeqs = 0;
		 
		 
		 
	        try (Scanner sc = new Scanner(fastaFile)) {
	            while (sc.hasNextLine()) {
	                String line = sc.nextLine().trim();
	                if(line.length() != 0){
	                	 if (line.charAt(0) == '>') 
	 	                	numberOfSeqs++;
	 	            }
	               
	            }
	            
	        }
	        return numberOfSeqs;
	 }
	 

	 public String[] giveMyAlignmentsList(List<Alignment> list){
		 String[] myStringList = new String[list.size()];
		 int j = 0;
		 for (int i = list.size()-1 ; i>=0 ;i--) {
			 myStringList[j] = list.get(i).getName1();
			 j++;
		 }
		 return myStringList;
	 }
	 
}

