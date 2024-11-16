import java.io.*;
import java.lang.ModuleLayer.Controller;
import java.util.*;

public class Main {
    public static void main(String[] args) {		
		Gui gui = new Gui();
		Compressor compressor = new Compressor();
		Uncompressor uncompressor = new Uncompressor();
		Manager manager = new Manager(gui, compressor, uncompressor);
    }
}