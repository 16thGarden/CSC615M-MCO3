import java.io.*;
import java.util.*;

public class Uncompressor {	
	public String uncompress(String binary, String[] characters, String[] codes, int codesCount, int extraBitsCount) {		
		StringBuilder builder = new StringBuilder();
		int originalSize = binary.length();
		while(binary.length() != extraBitsCount) {
			print("decoding bits...\t" + (originalSize - binary.length()) + "\t/\t" + originalSize);
			for (int i = 0; i < codesCount; i++) {
				if (binary.startsWith(codes[i])) {
					if (characters[i].equals("[newline]")) {
						builder.append("\n");
					} else {
						builder.append(characters[i]);
					}
					binary = binary.substring(codes[i].length());
				}
			}
		}
		
		return builder.toString();
	}
	
	// debugging functions
	
	public void print(String s) {
		System.out.println(s);
	}
}