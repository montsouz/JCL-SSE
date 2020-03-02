package alignment;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fastaReader.FASTAEntry;
import fastaReader.FASTAReader;
import jaligner.*;
import jaligner.matrix.Matrix;



public class JalignmentTask {
	
	private Sequence myTargetSequence;
	private static  int numberOfElements=10;
	private Matrix myMatrix;
	
	

	public int getNumberOfElements() {
		return numberOfElements;
	}


	public void setNumberOfElements(int numberOfElements) {
		JalignmentTask.numberOfElements = numberOfElements;
	}


	public JalignmentTask() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public JalignmentTask(Sequence myTargetSequence, Matrix myMatrix) {
		super();
		this.myTargetSequence = myTargetSequence;
		this.myMatrix = myMatrix;
	}

	
	public Matrix getMyMatrix() {
		return myMatrix;
	}



	public void setMyMatrix(Matrix myMatrix) {
		this.myMatrix = myMatrix;
	}



	public Sequence getMyTargetSequence() {
		return myTargetSequence;
	}



	public void setMyTargetSequence(Sequence myTargetSequence) {
		this.myTargetSequence = myTargetSequence;
	}

	
	/*
	 * 
	 */
	public static Alignment performJaligner (Sequence seq,Sequence targetSeq , Matrix matrix) {
		Alignment a = SmithWatermanGotoh.align(seq, targetSeq, matrix, 10.0f, 0.5f);
		return a;
			     
	}
	
	public static List<Alignment> performJalignerForDataBase(Sequence targetSeq,Matrix matrix,File currentDataBase) throws Exception{
		 Sequence justSeq = new Sequence ();
		 List<Alignment> mySeqList = new ArrayList<>();
		 FASTAReader reader = FASTAReader.getInstance(currentDataBase);
		 FASTAEntry fasta_entry;

				 
		 fasta_entry = reader.readNext();
		 while (fasta_entry != null){
			 
			 justSeq.setSequence(fasta_entry.getSequence());
			 justSeq.setId((fasta_entry.getHeaderLine()));
	    	 justSeq.setDescription(fasta_entry.getHeaderLine());
	    	 sortAlignmentList(mySeqList,performJaligner(justSeq, targetSeq, matrix));
//	    	 System.out.println(mySeqList.get(0).getScore());
			 fasta_entry = reader.readNext();
	    	 
		 }
		 
		 return mySeqList;
	}
	
	public  List<Alignment> justPerform (Sequence targetSeq,Matrix matrix,int numberOfCores) throws Exception{
		
		 List<Alignment> mySeqList = new ArrayList<>();
		 System.out.println("Number of Cores in this machine: " + numberOfCores);
		
		for (int i=0;i<numberOfCores;i++){
			
			File currentDataBase = new File("FASTABASE"+i+".fasta");
			System.out.println("Reading File: " + currentDataBase.getAbsoluteFile());
			mySeqList.addAll(performJalignerForDataBase(targetSeq,matrix,currentDataBase));
		}
		
		return mySeqList;
		
			
	}
	
	public static void sortAlignmentList (List<Alignment> aList ,Alignment a ){
		Collections.sort(aList , new AlignmentComparator() );
		
	
		if(aList.size() < numberOfElements-1){
			aList.add(a);
		}
		
		if(aList.size() == numberOfElements){
			aList.set(numberOfElements, a);
			aList.remove(numberOfElements);
		}
	}
	
	public static void finalSort (List<Alignment> aList){
		Collections.sort(aList , new AlignmentComparator());
		int lsize = aList.size()-1;
		while (aList.size() > numberOfElements){
			aList.remove(lsize);
			lsize--;
		}
	}
	
}
	
