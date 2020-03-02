package filebreaker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import alignment.LoadDataBase;
import fastaReader.FASTADataErrorException;
import fastaReader.FASTAEntry;
import fastaReader.FASTAReader;
import jaligner.Sequence;
import net.sf.jfasta.FASTAElement;
import net.sf.jfasta.impl.FASTAElementImpl;
import net.sf.jfasta.impl.FASTAFileWriter;

public class FileBreaker {
	

	private static int currentSeq=0;



	public static void fileBreaker (List<Sequence> proteinSequences ,int numberOfCores ) throws IOException {
		
		System.out.println("Number of Cores: " + numberOfCores);
		int numberOfSequences = proteinSequences.size();
		System.out.println("Number of Sequences: " + numberOfSequences);
		int numberOfSeqPerFiles = numberOfSequences/numberOfCores;
		System.out.println("Number of Sequences per File: " + numberOfSeqPerFiles);
		

		
		for (int i=0 ; i<numberOfCores;i++) {
			
				File justfile  = new File ("FASTABASE" + Integer.toString(i) + ".fasta");
				FASTAFileWriter writer = new FASTAFileWriter(justfile);
				
				for(int j = 0; j< numberOfSeqPerFiles ; j++){
				
					FASTAElement e = new FASTAElementImpl(  proteinSequences.get(currentSeq).getDescription()
														  , proteinSequences.get(currentSeq).getSequence());
					
					writer.write(e);
			
					
					currentSeq++;
					
				}
			
				writer.close();
			}
	}

	
	
	public static void initializeMyLists (List<ArrayList<Sequence>> mySequences, int numberOfDevices){
		
		for (int i=0;i< numberOfDevices ; i++){
			mySequences.add(new ArrayList<Sequence>());
		}
		
	}
	
	
	

	public static List<ArrayList<Sequence>> breakIntoSmallerLists (File fastaFile, int numberOfDevices) throws FASTADataErrorException, Exception{
		 
		List<ArrayList<Sequence>> proteinBases = new ArrayList<ArrayList<Sequence>>();
		ArrayList<Sequence> proteinPiece = new ArrayList<>();
		FASTAReader reader = FASTAReader.getInstance(fastaFile);
		LoadDataBase db = new LoadDataBase();
		
		int numberOfSeqsPerList =  db.numberOfSequences(fastaFile)/ numberOfDevices;
		System.out.println("Number of Sequences per device: " + numberOfSeqsPerList);
		int currentSeq = 0;
		

		FASTAEntry myseq;
		myseq = reader.readNext();
		for (int i=0;i< numberOfDevices;i++){
			
			while (myseq != null && currentSeq <= numberOfSeqsPerList){
				
				proteinPiece.add(new Sequence(  myseq.getSequence(),
												myseq.getHeaderLine(),
												myseq.getHeaderLine(),
												currentSeq * numberOfDevices
											  ));
				
				myseq = reader.readNext();
				currentSeq++;
			}
			proteinBases.add(proteinPiece);
			currentSeq = 0; 
		}
		
		 
		 
		return proteinBases;
		
	}

}


