package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.LogManager;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import alignment.JalignmentTask;
import alignment.LoadDataBase;
import alignment.PerformJCL;
import jaligner.Alignment;
import jaligner.Sequence;
import jaligner.matrix.MatrixLoader;
import jaligner.matrix.MatrixLoaderException;
import jaligner.util.SequenceParser;
import jaligner.util.SequenceParserException;

public class SearchMainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JComboBox<Object> comboBox_1 = new JComboBox<Object>();
	private JComboBox<Integer> comboBox_2 = new JComboBox<Integer>();
	private JList<Object> list = new JList<Object>();
	private JLabel totalTime = new JLabel();
	private JLabel numberofSeqs = new JLabel();
	

    /*
     * Jaligner components 
     */
	
	Sequence targetSequence;
	File database;
	String path;
	List<Alignment> allAlignments = new ArrayList<>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LogManager.getLogManager().reset();
					SearchMainWindow frame = new SearchMainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SearchMainWindow() {
	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 756, 743);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(27, 403, 708, 214);
		
		JScrollBar scrollBar = new JScrollBar();
		scrollPane.setRowHeaderView(scrollBar);
		
		JScrollBar scrollBar_1 = new JScrollBar();
		scrollBar_1.setOrientation(JScrollBar.HORIZONTAL);
		scrollPane.setColumnHeaderView(scrollBar_1);
		scrollPane.setViewportView(list);
	
		
		JComboBox<Object> comboBox = new JComboBox<Object>();
		comboBox.setBounds(239, 176, 159, 27);
		comboBox.setModel(new DefaultComboBoxModel<Object>(new String[] {"Jaligner"}));
		
		JLabel lblSequenceAlignmentAlgorithm = new JLabel("Sequence Alignment Algorithm");
		lblSequenceAlignmentAlgorithm.setBounds(32, 174, 216, 29);
		lblSequenceAlignmentAlgorithm.setFont(new Font("Hiragino Sans", Font.PLAIN, 13));
		
		JLabel lblSequencesFrom = new JLabel("Sequences From");
		lblSequencesFrom.setBounds(32, 217, 112, 21);
		lblSequencesFrom.setFont(new Font("Hiragino Sans", Font.PLAIN, 13));
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(608, 255, 117, 29);
		btnSubmit.addActionListener(this.executeJaligner_JCL());
		
		JLabel lblNewLabel = new JLabel("Fasta Input Sequence");
		lblNewLabel.setBounds(32, 96, 147, 21);
		lblNewLabel.setFont(new Font("Hiragino Sans", Font.PLAIN, 13));
		
		textField = new JTextField();
		textField.setBounds(32, 115, 554, 47);
		textField.setColumns(10);
		
		JLabel lblJclsequenceSearchEngine = new JLabel("JCL-Sequence Search Engine (JCL-SSE)");
		lblJclsequenceSearchEngine.setBounds(181, 40, 364, 33);
		lblJclsequenceSearchEngine.setFont(new Font("Dialog", Font.PLAIN, 19));
		
		JLabel lblNewLabel_1 = new JLabel("Most Similar Sequences Found");
		lblNewLabel_1.setBounds(27, 370, 234, 27);
		lblNewLabel_1.setFont(new Font("Hiragino Sans", Font.PLAIN, 13));
		
		JLabel label = new JLabel("");
		label.setBounds(78, 29, 33, 55);
		label.setIcon(new ImageIcon("../JCL-SSE/src/gui/jcl04.jpg"));
		
		JLabel label_1 = new JLabel("");
		label_1.setBounds(588, 40, 137, 41);
		label_1.setIcon(new ImageIcon("../JCL-SSE/src/gui/jcl05.jpg"));
		
		JButton btnLoadSequence = new JButton("Load Sequence");
		btnLoadSequence.setBounds(598, 115, 127, 23);
		btnLoadSequence.addActionListener(this.load());
		
		JButton btnKnowMore = new JButton("Details");
		btnKnowMore.setBounds(629, 635, 106, 23);
		
		JLabel lblNumberOfSequences = new JLabel("Number of Sequences:");
		lblNumberOfSequences.setBounds(32, 644, 141, 14);
		
		JLabel lblTotalTime = new JLabel("Total Time:");
		lblTotalTime.setBounds(239, 644, 74, 14);
		
		JButton btnLoadDataBase = new JButton("Load Data Base");
		btnLoadDataBase.setBounds(598, 139, 127, 29);
		
		JLabel lblNumberOfResults = new JLabel("Number Of Results ");
		lblNumberOfResults.setBounds(32, 260, 123, 16);
		contentPane.setLayout(null);
		contentPane.add(label);
		contentPane.add(lblJclsequenceSearchEngine);
		contentPane.add(label_1);
		contentPane.add(lblSequenceAlignmentAlgorithm);
		contentPane.add(comboBox);
		contentPane.add(lblNumberOfSequences);
		numberofSeqs.setBounds(181, 644, 46, 14);
		contentPane.add(numberofSeqs);
		contentPane.add(lblTotalTime);
		totalTime.setBounds(325, 644, 46, 14);
		contentPane.add(totalTime);
		contentPane.add(btnKnowMore);
		contentPane.add(scrollPane);
		contentPane.add(lblNewLabel_1);
		contentPane.add(lblNumberOfResults);
		contentPane.add(btnSubmit);
		contentPane.add(lblNewLabel);
		contentPane.add(textField);
		contentPane.add(btnLoadSequence);
		contentPane.add(btnLoadDataBase);
		contentPane.add(lblSequencesFrom);
		comboBox_1.setBounds(239, 215, 159, 27);
		contentPane.add(comboBox_1);
		

		comboBox_2.setModel(new DefaultComboBoxModel<Integer>(new Integer[] {10, 15, 25, 50}));
		comboBox_2.setBounds(239, 256, 159, 27);
		contentPane.add(comboBox_2);
		
		
		btnLoadDataBase.addActionListener(this.loadMyDataBase());
		btnKnowMore.addActionListener(this.callSummary());
	}
	
	/*
	 * Loads my target sequence 
	 */
	public ActionListener load(){
	    return  new ActionListener() {
	        public void actionPerformed(ActionEvent arg0) {
	           
	            JFileChooser chooser = new JFileChooser();
	            int returnVal = chooser.showOpenDialog(null); //replace null with your swing container
	            File file = null;
	            if(returnVal == JFileChooser.APPROVE_OPTION){    
	              file = chooser.getSelectedFile();
	              
	              try {
					targetSequence=SequenceParser.parse(file);
					targetSequence.setId(targetSequence.getDescription());
					textField.setText(targetSequence.getDescription());
				} catch (SequenceParserException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	          };
	       }

		
	    };
	}
	
	/*
	 * Loads the fasta data base 
	 */
	public ActionListener loadMyDataBase() {
	    return  new ActionListener() {
	        public void actionPerformed(ActionEvent arg0) {
	           
	        	LoadDataBase Db = new LoadDataBase();
	            JFileChooser chooser = new JFileChooser();
	            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	            int returnVal = chooser.showOpenDialog(null); //replace null with your swing container
	            File file = null;
	            if(returnVal == JFileChooser.APPROVE_OPTION){    
	              file = chooser.getSelectedFile();
	              comboBox_1.setModel(new DefaultComboBoxModel<Object>(Db.returnAllFilesNames(file)));
	              path = file.getAbsolutePath();
	              
	            
	            }

		
	        };
	    };
	
	}
	
	/*
	 * Executes the alignment using Jaligner and JCL 
	 */
	
	public ActionListener executeJaligner_JCL() {
	    return  new ActionListener() {
	        public void actionPerformed(ActionEvent arg0) {
	         	LoadDataBase Db = new LoadDataBase();
	        	try {
	        		
	        		long tStart = System.currentTimeMillis();// start measuring time elapsed 
	        		
	        		PerformJCL performApp = new PerformJCL();
	        		JalignmentTask task = new JalignmentTask();
	        		task.setNumberOfElements((int) (comboBox_2.getSelectedItem()));
	        		database = new File (path + "/" + comboBox_1.getSelectedItem());
	        		numberofSeqs.setText(Integer.toString(Db.numberOfSequences(database)));
					allAlignments.addAll(performApp.performWithJaligner(targetSequence, MatrixLoader.load("BLOSUM62"), database));
					JalignmentTask.finalSort(allAlignments);
					list.setListData((Db.giveMyAlignmentsList(allAlignments)));
					long tEnd = System.currentTimeMillis();
					long tDelta = tEnd - tStart;
					double elapsedSeconds = tDelta / 1000.0;// end time lapsed 
					totalTime.setText(Double.toString(elapsedSeconds));
				
	        	} catch (MatrixLoaderException | InterruptedException | ExecutionException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				
	        	} catch (SequenceParserException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        };
	    };
	
	}
	
	public ActionListener callSummary() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					DisplaySequenceDetails frame = new DisplaySequenceDetails();
					frame.setVisible(true);
					frame.setSummary(allAlignments.get(allAlignments.size() - 1- list.getLeadSelectionIndex()));
				}
				
				catch(Exception e) {
					
					e.printStackTrace();
				}
				
			}
		};
	}
}
