import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

class Manager {
	private Gui gui;
    private Compressor compressor;
	private Uncompressor uncompressor;
	
	private String cText;
	private String uText;
	
	private int limit = 1000;
	
	private int extraBitsCount;
	private int codesCount;
	private String[] characters;
	private String[] codes;
	
	public Manager(Gui gui, Compressor compressor, Uncompressor uncompressor) {
		this.gui = gui;
		this.compressor = compressor;
		this.uncompressor = uncompressor;
		
		characters = new String[limit];
		codes = new String[limit];
		
		this.gui.addCExecuteListener(new CExecuteListener());
		this.gui.addCSaveListener(new CSaveListener());
		this.gui.addUExecuteListener(new UExecuteListener());
		this.gui.addUSaveListener(new USaveListener());
	}
	
	private boolean readTextFile() {
		String inputFileName = gui.getCInputTextField();
		try (BufferedReader br = new BufferedReader(new FileReader(inputFileName + ".txt"))) {
			String line;
			StringBuilder builder = new StringBuilder();
            while ((line = br.readLine()) != null) {
				builder.append(line);
				builder.append("\n");
            }
			
			cText = builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
			return false;
        }
		
		return true;
	}
	
	class CExecuteListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			gui.setEnabledCExecuteButton(false);
			gui.setEnabledCSaveButton(false);
			gui.setEnabledUExecuteButton(false);
			gui.setEnabledUSaveButton(false);
			gui.setCStatus("Reading File...");
			
			boolean success = readTextFile();
			
			if (success) {
				gui.setCStatus("Compressing text...");
			} else {
				gui.displayErrorMessage("Something went wrong when reading the file.");
				gui.setCStatus("Something went wrong when reading the file.");
				gui.setEnabledCExecuteButton(true);
				return;
			}
			
			SwingUtilities.invokeLater(() -> {
				cText = compressor.compress(cText);

				gui.setCStatus("Finished compressing text, ready to save");
				gui.setEnabledCExecuteButton(true);
				gui.setEnabledCSaveButton(true);
				gui.setEnabledUExecuteButton(true);
				gui.setEnabledUSaveButton(true);
			});
		}
	}
	
	private boolean saveTable() {
		int codesCount = compressor.getCodesCount();
		String[] characters = compressor.getCharacters();
		String[] codes = compressor.getCodes();
		
		int extraBitsCount = (8 - cText.length() % 8) % 8;
		
		String outputFileName = gui.getCOutputTextField();
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName + ".table"))) {
			writer.write(extraBitsCount + "\n");
			writer.write(codesCount + "\n");

			for (int i = 0; i < codesCount; i++) {
				writer.write(characters[i] + "\t" + codes[i]);
				if (i != codesCount - 1) {
					writer.newLine();
				}
			}
			
        } catch (IOException e) {
            e.printStackTrace();
			return false;
        }
		
		return true;
	}
	
	private boolean saveBinary() {
		int extraBitsCount = (8 - cText.length() % 8) % 8;
		
		if (extraBitsCount != 0) {
			for (int i = 0; i < extraBitsCount; i++) {
				cText = cText + "0";
			}
		}
		
		List<String> strings = new ArrayList<String>();
		int index = 0;
		while (index < cText.length()) {
			strings.add(cText.substring(index, Math.min(index + 8, cText.length())));
			index += 8;
		}
		
		byte[] output = new byte[strings.size()];
		for (int i = 0; i < strings.size(); i++) {
			Integer placeholder = Integer.parseInt(strings.get(i), 2);
			output[i] = placeholder.byteValue();
		}
		
		String outputFileName = gui.getCOutputTextField();
		try (FileOutputStream writer = new FileOutputStream(outputFileName + ".bin")) {
			for (int i = 0; i < strings.size(); i++) {
				writer.write(output[i]);
			}		
        } catch (IOException e) {
            e.printStackTrace();
			return false;
        }
		
		return true;
	}
	
	class CSaveListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			gui.setEnabledCExecuteButton(false);
			gui.setEnabledCSaveButton(false);
			gui.setEnabledUExecuteButton(false);
			gui.setEnabledUSaveButton(false);
			
			String outputFileName = gui.getCOutputTextField();
			gui.setCStatus("Saving to " + outputFileName + ".table...");
			
			SwingUtilities.invokeLater(() -> {
				boolean success1 = saveTable();
				boolean success2 = saveBinary();
			
				if (success1 && success2) {
					gui.setCStatus("Files saved!");
				} else {
					gui.displayErrorMessage("Something went wrong when saving the files.");
					gui.setCStatus("Something went wrong when saving the files.");
					gui.setEnabledCSaveButton(true);
				}
				
				gui.setEnabledCExecuteButton(true);
				gui.setEnabledUExecuteButton(true);
				gui.setEnabledUSaveButton(true);
			});
		}
	}
	
	private boolean readTableFile() {
		String inputFileName = gui.getUInputTextField();
		try (BufferedReader br = new BufferedReader(new FileReader(inputFileName + ".table"))) {
            String line;
			
			extraBitsCount = Integer.parseInt(br.readLine());
			codesCount = Integer.parseInt(br.readLine());
			
            for (int i = 0; i < codesCount; i++) {
				line = br.readLine();
				String[] lineSplit = line.split("\t");
				characters[i] = lineSplit[0];
				codes[i] = lineSplit[1];
            }		
        } catch (IOException e) {
            e.printStackTrace();
			return false;
        }
		
		return true;
	}
	
	private boolean readBinaryFile() {
		String inputFileName = gui.getUInputTextField();
		try(FileInputStream reader = new FileInputStream(inputFileName + ".bin")) {
			byte[] bytes = reader.readAllBytes();
			
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				builder.append(String.format("%8s", Integer.toBinaryString(bytes[i] & 0xFF)).replace(" ", "0"));
				print("transforming bytes...\t" + i + "\t/\t" + bytes.length);
			}
			uText = builder.toString();
		} catch (IOException e) {
            e.printStackTrace();
			return false;
        }
		
		return true;
	}
	
	class UExecuteListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			gui.setEnabledCExecuteButton(false);
			gui.setEnabledCSaveButton(false);
			gui.setEnabledUExecuteButton(false);
			gui.setEnabledUSaveButton(false);
			
			gui.setUStatus("Uncompressing text...");
			
			SwingUtilities.invokeLater(() -> {
				readTableFile();
				readBinaryFile();
				uText = uncompressor.uncompress(uText, characters, codes, codesCount, extraBitsCount);
				
				gui.setUStatus("Finished uncompressing text, ready to save");
				gui.setEnabledCExecuteButton(true);
				gui.setEnabledCSaveButton(true);
				gui.setEnabledUExecuteButton(true);
				gui.setEnabledUSaveButton(true);
			});
			
		}
	}
	
	private boolean saveTextFile() {
		String outputFileName = gui.getUOutputTextField();
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName + ".txt"))) {
			writer.write(uText);			
        } catch (IOException e) {
            e.printStackTrace();
			return false;
        }
		
		return true;
	}
	
	class USaveListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			gui.setEnabledCExecuteButton(false);
			gui.setEnabledCSaveButton(false);
			gui.setEnabledUExecuteButton(false);
			gui.setEnabledUSaveButton(false);
			
			String outputFileName = gui.getUOutputTextField();
			gui.setUStatus("Saving to " + outputFileName + ".txt...");
			
			SwingUtilities.invokeLater(() -> {
				boolean success = saveTextFile();
			
				if (success) {
					gui.setUStatus("File saved!");
				} else {
					gui.displayErrorMessage("Something went wrong when saving the file.");
					gui.setUStatus("Something went wrong when saving the file.");
					gui.setEnabledUSaveButton(true);
				}
				
				gui.setEnabledCExecuteButton(true);
				gui.setEnabledUExecuteButton(true);
				gui.setEnabledUSaveButton(true);
			});
		}
	}
	
	// debugging functions
	
	public void print(String s) {
		System.out.println(s);
	}

	public void printCodes() {
		for (int i = 0; i < codesCount; i++) {
			if (characters[i] == "\n") {
				print("nl" + "\t" + codes[i]);
			}
			print(characters[i] + "\t" + codes[i]);
		}
	} 
}