package alignment;

import java.util.Comparator;

import jaligner.Alignment;

public class AlignmentComparator implements Comparator<Alignment> {
	
	public int compare (Alignment a1, Alignment a2){
		Float a1Score = a1.calculateScore();
		Float a2Score = a2.calculateScore();
		return (a1Score.compareTo(a2Score));
	}

}
