package filebreaker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import fastaReader.FASTADataErrorException;
import implementations.dm_kernel.user.JCL_FacadeImpl;
import interfaces.kernel.JCL_facade;
import jaligner.Sequence;


public class DistribuitedFasta {
	
	
	static JCL_facade jcl = JCL_FacadeImpl.getInstance();
	

	public static void catchAllHosts (File fastaFileName) throws FASTADataErrorException, Exception {
		
		File[] userjars =  {	new File("../JCL-SSE/useful jars/FileBreaker.jar"),
								new File("../JCL-SSE/useful jars/jaligner.jar"),
								new File("../JCL-SSE/useful jars/fastaReader.jar")
							};
		
		System.out.println(jcl.register(userjars, "FileBreaker"));
	
		List<Entry<String, String>> hosts = jcl.getDevices(); // number of devices in the cluster
		System.out.println(hosts);
		List<ArrayList<Sequence>> mySequences = new ArrayList<ArrayList<Sequence>>(); // a list of my sequences
		
		//here are the arguments to properly execute the FileBreaker 
		
		Object [] args = new Object [2];// 2 arguments (The list with the sequence and the number of cores)
		
		int numberOfDeviceCores = 0; 
		
		System.out.println("Number of Devices : " + hosts.size());

		mySequences=FileBreaker.breakIntoSmallerLists(fastaFileName, hosts.size());// fill my list of lists 
		
		
		for (int i=0 ; i < hosts.size() ; i++){
			
			Entry<String, String> host = hosts.get(i);
			numberOfDeviceCores = jcl.getDeviceCore(host);
			List<Sequence> alist = mySequences.get(i);
			System.out.println("List that is being passed: " + alist);
			args[0] = alist;
			args[1] = numberOfDeviceCores;
			jcl.executeOnDevice(host, "FileBreaker", "fileBreaker", args);
			
		}
		
		jcl.cleanEnvironment();
		jcl.destroy();
	}

}
