import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Gui extends JFrame{
	private JLabel cHeader = new JLabel("File Compressor", SwingConstants.CENTER);
	private JLabel cInputLabel = new JLabel("Input file name: ");
	private JLabel cOutputLabel = new JLabel("Output file name: ");
	private JTextField cInputTextField = new JTextField("input_demo");
	private JTextField cOutputTextField = new JTextField("input_compressed");
	private JLabel cStatus = new JLabel("Waiting for action...", SwingConstants.CENTER);
	private JButton cExecute = new JButton("Compress text");
	private JButton cSave = new JButton("Save to file");
	
	private JLabel uHeader = new JLabel("File Uncompressor", SwingConstants.CENTER);
	private JLabel uInputLabel = new JLabel("Input file name: ");
	private JLabel uOutputLabel = new JLabel("Output file name: ");
	private JTextField uInputTextField = new JTextField("input_compressed");
	private JTextField uOutputTextField = new JTextField("input_uncompressed");
	private JLabel uStatus = new JLabel("Waiting for action...", SwingConstants.CENTER);
	private JButton uExecute = new JButton("Uncompress text");
	private JButton uSave = new JButton("Save to file");
	
	public Gui() {
		super("CSC615M File Compression");
		JPanel panel = new JPanel(null);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 800);
		this.setLocationRelativeTo(null);
		Font font = new Font("Consolas", Font.PLAIN, 18);
		
		cHeader.setBounds(50, 50, 700, 30);
		cInputLabel.setBounds(50, 100, 170, 30);
		cOutputLabel.setBounds(50, 150, 170, 30);
		cInputTextField.setBounds(200, 100, 550, 30);
		cOutputTextField.setBounds(200, 150, 550, 30);
		cInputTextField.setFont(font);
		cOutputTextField.setFont(font);
		cStatus.setBounds(50, 200, 700, 30);
		cExecute.setBounds(100, 250, 250, 30);
		cSave.setBounds(450, 250, 250, 30);
		setEnabledCSaveButton(false);
		panel.add(cHeader);
		panel.add(cInputLabel);
		panel.add(cOutputLabel);
		panel.add(cInputTextField);
		panel.add(cOutputTextField);
		panel.add(cStatus);
		panel.add(cExecute);
		panel.add(cSave);
		
		uHeader.setBounds(50, 450, 700, 30);
		uInputLabel.setBounds(50, 500, 170, 30);
		uOutputLabel.setBounds(50, 550, 170, 30);
		uInputTextField.setBounds(200, 500, 550, 30);
		uOutputTextField.setBounds(200, 550, 550, 30);
		uInputTextField.setFont(font);
		uOutputTextField.setFont(font);
		uStatus.setBounds(50, 600, 700, 30);
		uExecute.setBounds(100, 650, 250, 30);
		uSave.setBounds(450, 650, 250, 30);
		setEnabledUSaveButton(false);
		panel.add(uHeader);
		panel.add(uInputLabel);
		panel.add(uOutputLabel);
		panel.add(uInputTextField);
		panel.add(uOutputTextField);
		panel.add(uStatus);
		panel.add(uExecute);
		panel.add(uSave);
		
		add(panel);
		setVisible(true);
	}

	public String getCInputTextField() {
		return cInputTextField.getText();
	};

	public String getCOutputTextField() {
		return cOutputTextField.getText();
	};
	
	public void setEnabledCExecuteButton(boolean enable) {
		cExecute.setEnabled(enable);
	}
	
	public void setEnabledCSaveButton(boolean enable) {
		cSave.setEnabled(enable);
	}

	public void setCStatus(String s) {
		cStatus.setText(s);
	}
	
	public String getUInputTextField() {
		return uInputTextField.getText();
	};

	public String getUOutputTextField() {
		return uOutputTextField.getText();
	};
	
	public void setEnabledUExecuteButton(boolean enable) {
		uExecute.setEnabled(enable);
	}
	
	public void setEnabledUSaveButton(boolean enable) {
		uSave.setEnabled(enable);
	}
	
	public void setUStatus(String s) {
		uStatus.setText(s);
	}
	
	public void displayErrorMessage(String errorMessage) {
		JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	public void addCExecuteListener(ActionListener listenForCExecuteButton) {
		cExecute.addActionListener(listenForCExecuteButton);
	}

	public void addCSaveListener(ActionListener listenForCSaveButton) {
		cSave.addActionListener(listenForCSaveButton);
	}
	
	public void addUExecuteListener(ActionListener listenForUExecuteButton) {
		uExecute.addActionListener(listenForUExecuteButton);
	}

	public void addUSaveListener(ActionListener listenForUSaveButton) {
		uSave.addActionListener(listenForUSaveButton);
	}
}