package alignment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.Future;

import implementations.dm_kernel.user.JCL_FacadeImpl;
import interfaces.kernel.JCL_facade;
import interfaces.kernel.JCL_result;
import jaligner.Alignment;
import jaligner.Sequence;
import jaligner.matrix.Matrix;


public class PerformJCL{
	

	
	LoadDataBase Db = new LoadDataBase();
	JalignmentTask aTask = new JalignmentTask();	
	JCL_facade jcl = JCL_FacadeImpl.getInstance();
	
	private int numberOfElements;
	
	
	public int getNumberOfElements() {
		return numberOfElements;
	}

	public void setNumberOfElements(int numberOfElements) {
		this.numberOfElements = numberOfElements;
	}

	List<Alignment> allAlignments = new ArrayList<>(numberOfElements);
	
	
	/* 
	 * Performing my JCL task using Jaligner to do the alignments
	 */
	
	@SuppressWarnings("unchecked")
	public List<Alignment> performWithJaligner (Sequence targetSeq, Matrix matrix,File database) 
			throws Exception{
		
		File[] userjars = {	
							new File("../JCL-SSE/useful jars/jalignmentTask.jar"),
							new File("../JCL-SSE/useful jars/jalignmentComparator.jar"),
							new File("../JCL-SSE/useful jars/jaligner.jar"),
							new File("../JCL-SSE/useful jars/fastaReader.jar")
							
							
						  };
		
//		 System.out.println(jcl.register(JalignmentTask.class, "JalignmentTask"));
			System.out.println(jcl.register(userjars, "JalignmentTask"));
		 
			List<Entry<String, String>> nodes = jcl.getDevices();
			Entry<String,String> host;
			int numberOfNodes = nodes.size();
			System.out.println("Number Of Nodes:" + numberOfNodes);
			int numberOfCores = 0;
	
			for(int i=0; i<numberOfNodes; i++){
				
				host = nodes.get(i);
				numberOfCores = jcl.getDeviceCore(host);
				
				Object[] args = {targetSeq, matrix,numberOfCores};	
				Future<JCL_result> ticket =  jcl.executeOnDevice(host, "JalignmentTask","justPerform", args);
				JCL_result jcl_result = ticket.get();
				List<Alignment> al = (List<Alignment>) jcl_result.getCorrectResult();
				allAlignments.addAll(al);
					
				
			}
 
			jcl.cleanEnvironment();
			jcl.destroy();
			return allAlignments;
				
			 
		}
		
		
	}
	
	

