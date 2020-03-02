package alignment;

import org.biojava.nbio.structure.Structure;
import org.biojava.nbio.structure.StructureIO;
import org.biojava.nbio.structure.align.gui.jmol.StructureAlignmentJmol;

public class GenerateStructure {
	
	public static void displayStructure(){
		
		  try {
	            
	            Structure struc = StructureIO.getStructure("4hhb");

	            StructureAlignmentJmol jmolPanel = new StructureAlignmentJmol();

	            jmolPanel.setStructure(struc);

	            // send some commands to Jmol
	            jmolPanel.evalString("select * ; color chain;");            
	            jmolPanel.evalString("select *; spacefill off; wireframe off; cartoon on;  ");
	            jmolPanel.evalString("select ligands; cartoon off; wireframe 0.3; spacefill 0.5; color cpk;");
	            
	        } catch (Exception e){
	            e.printStackTrace();
	        }
	}

}
