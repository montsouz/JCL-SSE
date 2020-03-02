package test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.LogManager;

import alignment.JalignmentTask;
import alignment.LoadDataBase;
import alignment.PerformJCL;
import filebreaker.DistribuitedFasta;
import jaligner.Alignment;
import jaligner.Sequence;
import jaligner.matrix.MatrixLoader;
import jaligner.matrix.MatrixLoaderException;
import jaligner.util.SequenceParser;

public class Test {
	
	
	static Sequence targetSequence;
	static File database;
	String path;
	static List<Alignment> allAlignments = new ArrayList<>();
	
	
	public static void main(String args[]) throws MatrixLoaderException, Exception{
		LogManager.getLogManager().reset();
		test();
	}
	
	public static void test() throws MatrixLoaderException, Exception{
	
		
		LoadDataBase Db = new LoadDataBase();
		PerformJCL performApp = new PerformJCL();
		JalignmentTask task = new JalignmentTask();
		
		long tStart = System.currentTimeMillis();// start measuring time elapsed 
		
		File file = new File ("/Users/mac/Documents/MyBioData/Greengene_single.fasta");
		targetSequence=SequenceParser.parse(file);
		targetSequence.setId(targetSequence.getDescription());
		task.setNumberOfElements(10);
		database = new File ("/Users/mac/Documents/MyBioData/Greengene_02.fasta");
		System.out.println(Integer.toString(Db.numberOfSequences(database)));
//		DistribuitedFasta.catchAllHosts(database);
		allAlignments.addAll(performApp.performWithJaligner(targetSequence, MatrixLoader.load("BLOSUM62"), database));
		JalignmentTask.finalSort(allAlignments);
		
		for(int i=0;i<allAlignments.size();i++){
			
			System.out.println(allAlignments.get(i).getScore());
		}
		
		long tEnd = System.currentTimeMillis();
		long tDelta = tEnd - tStart;
		double elapsedSeconds = tDelta / 1000.0;// end time lapsed 
		System.out.println(elapsedSeconds);
	}

}
