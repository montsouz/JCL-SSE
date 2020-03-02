package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.net.URI;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import jaligner.*;
import alignment.GenerateStructure;

public class DisplaySequenceDetails extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextArea textArea = new JTextArea();
	private Alignment summary = new Alignment();

	/**
	 * Create the frame.
	 */
	public DisplaySequenceDetails() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 952, 604);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(5, 55, 926, 435);
		contentPane.add(scrollPane);
		
		JScrollBar scrollBar = new JScrollBar();
		scrollPane.setRowHeaderView(scrollBar);
		scrollPane.setViewportView(textArea);
	
		
		JButton btnMoreDetails = new JButton("NCBI Link");
		btnMoreDetails.setBounds(803, 515, 104, 23);
		contentPane.add(btnMoreDetails);
		btnMoreDetails.addActionListener(this.callWebPage());
		
		JButton btnNewButton = new JButton("Display Structure");
		btnNewButton.setBounds(689, 515, 104, 23);
		contentPane.add(btnNewButton);
		btnNewButton.addActionListener(this.getStructure());
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				contentPane.setVisible(false);
				dispose();
			}
		});
		btnCancel.setBounds(590, 515, 89, 23);
		contentPane.add(btnCancel);
		
		JLabel lblSummary = new JLabel("Details ");
		lblSummary.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblSummary.setBounds(406, 11, 117, 33);
		contentPane.add(lblSummary);
	}

	public Alignment getSummary() {
		return summary;
	}

	public void setSummary(Alignment summary) {
		this.summary = summary;
		textArea.setText(summary.getSummary());
	}
	
	public ActionListener callWebPage() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					StringBuilder stringBuilder = new StringBuilder();
					stringBuilder.append("https://www.ncbi.nlm.nih.gov/nuccore/");
					stringBuilder.append(getProtName(summary.getName1()));
					URI website = new URI(stringBuilder.toString());
					java.awt.Desktop.getDesktop().browse(website);
				}
				
				catch(Exception e) {
					
					e.printStackTrace();
				}
				
			}
		};
	}
	
	public ActionListener getStructure() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					GenerateStructure.displayStructure();
				}
				
				catch(Exception e) {
					
					e.printStackTrace();
				}
				
			}
		};
	}
	
	public String getSeqID (String seqName) {
		return seqName.substring(0,seqName.indexOf("|"));
	}
	
	public String getProtName (String seqName) {
		String myprot = seqName.substring(seqName.lastIndexOf("s__", seqName.length()));
		return myprot.substring(3);
	}
	
	
}
