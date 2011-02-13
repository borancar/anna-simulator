package hr.fer.anna.frisc.assembler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

public class Assemble {

	public static String readFile(File f) throws Exception {

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
		} catch (FileNotFoundException ex) {
			throw new Exception("Ne postoji datoteka " + f.getName());
		} catch (Exception e) {
			throw new Exception("Nemogu otvoriti datoteku " + f.getName());
		}

		StringBuilder sb = new StringBuilder();

		try {
			int c;
			while ((c = reader.read()) != -1) {
				sb.append((char) c);
			}
		} catch (Exception e) {
			throw new Exception("Nemogu čitati iz datoteke " + f.getName());
		}

		return sb.toString();
	}

	public static Commands assemble(File f) throws Exception {
		return assemble(readFile(f));
	}

	public static Commands assemble(String code) {
		String preprocessedCode = Preprocessor.process(code);
		Commands naredbe = null;
		Stack<String> cmdLblStack = new Stack<String>();
		Map<String, Integer> labels = new HashMap<String, Integer>();
		System.out.println("Start!");
		Stack<Integer> niz = new Stack<Integer>();
		try {

			//			SymLookup look = new SymLookup();
			//			LexicalAnalyser la = new LexicalAnalyser(new StringReader(preprocessedCode));
			//			while (true) {
			//				Symbol s = la.next_token();
			//				if (s == null || s.sym == AssemblerLabelsSym.EOF) {
			//					break;
			//				} else {
			//					System.out.println(look.lookup(s.sym));
			//				}
			//			}
			//			if (1+1==3-1) {
			//				System.out.println("KRAJ!!!");
			//				return null;
			//			}
			// TODO: Poveži labele
			AssemblerLabels aslc = new AssemblerLabels(
					new LexicalAnalyser(new StringReader(preprocessedCode)));
			aslc.parse();

			LabelsMap.get().printContents();

			//AssemblerCup asm = new AssemblerCup(new LexicalAnalyser(new StringReader(preprocessedCode)));
			//asm.parse() // TODO Neka parse() vraća Commands
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Kraj");
		return naredbe;
	}

}
