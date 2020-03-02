# JCL-SSE

A search engine for DNA/RNA/Protein sequences using JCL (JavaCá&amp;Lá)

JavaCa&La Sequence Search Engine or simply JCL-SSE is a tool that runs over a cluster of multicore computers, enabling a huge number of protein and DNA sequences alignments. In summary, it makes simple the utilization of JCL (www.javacaela.org) High Performance Computing (HPC) middleware Application Programming Interface (API) and for that it implements a Graphical User Interface (GUI) where users can index, query and rank similar sequences. Internally, the alignment implements the Smith-Waterman algorithm with the Gotoh improvement to perform the pairwise alignments. The FASTAReader and BioJava Libraries are employed to read the data from a FASTA file database and process both Protein and DNA sequences. 

## How do I Run It ? 

It is necessary, first, to start a JCL cluster. In the JCL website there are all the necessary guides for JCL deployment and development. In sequence, you have to follow the steps:

You must import the JCL-SSE Eclipse project. File -> Import -> Existing Projects Into Workspace.

After everything is set up, you can run the SearchMainWindow.java in the GUI folder. 


