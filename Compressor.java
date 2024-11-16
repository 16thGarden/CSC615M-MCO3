import java.io.*;
import java.util.*;

public class Compressor {
	private int limit = 1000;
	
	private String s;
	private Node[] nodes;
	private int nodeCount;
	private Node tree;
	private Node[] stack;
	private int stackCount;
	private String[] characters;
	private String[] codes;
	private int codesCount;
	
	private boolean showProgress = true;
	
	public String compress(String s) {
		nodes = new Node[limit];
		nodeCount = 0;
		tree = null;
		stack = new Node[limit];
		stackCount = 0;
		characters = new String[limit];
		codes = new String[limit];
		codesCount = 0;
		
		this.s = s;
		getFrequencies();
		//printNodes();
		buildTree();
		traverse();
		sortByCodeLength();
		//printCodes();
		return createCompressedBinary();
	}
	
	public String[] getCharacters() {
		return characters;
	}
	
	public String[] getCodes() {
		return codes;
	}
	
	public int getCodesCount() {
		return codesCount;
	}
	
	class Node {
		public String c;
		public int n;
		public Node l;
		public Node r;
		public String code;
		
		public Node(String c) {
			this.c = c;
			n = 1;
			l = null;
			r = null;
			code = "";
		}
	}
	
	private int findCharInNodes(String c) {
		for (int i = 0; i < nodeCount; i++) {
			if (nodes[i].c.equals(c)) {
				return i;
			}
		}
		
		return -1;
	}
	
	private void getFrequencies() {		
		for (int i = 0; i < s.length(); i++) {
			if (showProgress) {
				print("Getting frequencies...\t" + i + "\t/\t" + s.length());
			}
			
			String curChar = s.charAt(i) + "";
			if (curChar.equals("\n")) {
				curChar = "[newline]";
			}
			
			int index = findCharInNodes(curChar);
			if (index == -1) {
				nodes[nodeCount] = new Node(curChar);
				nodeCount++;
			} else {
				nodes[index].n++;
			}
		}
	}
		
	private void sortNodes() {
		int n = nodeCount;
		
		for (int i = 0; i < n - 1; i++) {
			for (int j = 0; j < n - i - 1; j++) {
				if (nodes[j].n < nodes[j + 1].n) {
					Node temp = nodes[j];
					nodes[j] = nodes[j + 1];
					nodes[j + 1] = temp;
				}
			}
		}
	}
	
	private void buildTree() {
		while (nodeCount >= 2) {
			sortNodes();
			
			Node combined = new Node("");
			combined.n = nodes[nodeCount - 1].n + nodes[nodeCount - 2].n;
			combined.l = nodes[nodeCount - 1];
			combined.r = nodes[nodeCount - 2];
			nodes[nodeCount - 2] = combined;
			
			nodeCount--;
		}
		
		tree = nodes[0];
		nodeCount = 0;
	}
	
	private void push(Node n) {
		stack[stackCount] = n;
		stackCount++;
	}
	
	private Node pop() {
		stackCount--;
		return stack[stackCount];
	}
	
	private void traverse() {
		push(tree);
		
		while (stackCount > 0) {
			Node curNode = pop();
			if (curNode.l == null && curNode.r == null) {
				characters[codesCount] = curNode.c;
				codes[codesCount] = curNode.code;
				codesCount++;
			} else {
				curNode.l.code = curNode.code + "0";
				curNode.r.code = curNode.code + "1";
				push(curNode.l);
				push(curNode.r);
			}
		}
	}
	
	public void sortByCodeLength() {
		int n = codesCount;
		
		for (int i = 0; i < n - 1; i++) {
			for (int j = 0; j < n - i - 1; j++) {
				if (codes[j].length() > codes[j + 1].length()) {
					String temp = characters[j];
					characters[j] = characters[j + 1];
					characters[j + 1] = temp;
					
					temp = codes[j];
					codes[j] = codes[j + 1];
					codes[j + 1] = temp;
				}
			}
		}
	}
		
	private String findCode(String c) {
		if (c.equals("\n")) {
			c = "[newline]";
		}
		
		for (int i = 0; i < codesCount; i++) {
			if (characters[i].equals(c)) {
				return codes[i];
			}
		}
		
		return "#";
	}
		
	private String createCompressedBinary() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			if (showProgress) {
				print("Building compressed binary...\t" + i + "\t/\t" + s.length());
			}
			builder.append(findCode(s.charAt(i) + ""));
		}
		
		return builder.toString();
	}
	
	// debugging functions
	
	public void print(String s) {
		System.out.println(s);
	}
	
	public void printNodes() {		
		print("--------------------------");
		for (int i = 0; i < nodeCount; i++) {
			String name = nodes[i].c;
			if (nodes[i].c == "\n" || nodes[i].c == "\r") {
				print("found newline");
				name = "nl";
			}
			print(name + "\t" + nodes[i].n);
		}
		print("--------------------------");
	}
	
	public void printNode(Node node) {
		String name = node.c;
		if (node.c == "") {
			name = "#";
		}
		print(name + "\t" + node.n);
		
		
		if (node.l == null) {
			print("\t" + "left : empty");
		} else {
			print("\t" + "left : " + node.l.c + " " + node.l.n);
		}
		
		if (node.r == null) {
			print("\t" + "right: empty");
		} else {
			print("\t" + "right: " + node.r.c + " " + node.r.n);
		}
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