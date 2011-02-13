package hr.fer.anna.oisc;

import java.util.ArrayList;
import java.util.List;

import hr.fer.anna.model.Word;
import hr.fer.anna.uniform.Constant;
import hr.fer.anna.exceptions.SyntaxError;

/**
 * OISC asembler.
 * NAPOMENE:<br />
 *   Labele nisu podržane.<br />
 *   Kod počinje od 0. pozicije, ne nakon taba.<br />
 *   Argumenti su odvojeni s ' '.<br />
 * Kodovi:<br />
 *  SUBNEG: xx|address1(10)|address2(10)|address3(10)
 *  HALT: 0
 *  mem[address2] = mem[address2] - mem[address1]
 *  if negative jump address3
 */
public class Assembler {
	
	/**
	 * Dekodiranje jedne naredbe.
	 * 
	 * @param instruction Naredba.
	 * @return Byte-code naredbe.
	 */
	private static Word decode(String instruction) throws SyntaxError {
		String[] inst = instruction.split(" ");
		
		Word instructionWord = new Word();
		int value = 0;
		
		if (inst[0].equals("SUBNEG")) {
			value = Integer.parseInt(inst[1]);
			value <<= 10;
			value |= Integer.parseInt(inst[2]);
			value <<= 10;
			value |= Integer.parseInt(inst[3]);
		}
		else throw new SyntaxError("Syntax error: " + instruction);
		
		instructionWord.set(new Constant(value));
		
		return instructionWord;
	}

	/**
	 * Punjenje memorije asembliranim byte-codeom.
	 * 
	 * @param code Kod koji moramo asemblirati.
	 * @return kod zapisan u polju bajtova
	 */
	public static Word[] assemble(String code) throws SyntaxError {
		String[] instruction = code.split("\n");

		List<Word> byteCode = new ArrayList<Word>();
		
		for (int i = 0; i < instruction.length; i++){
			try {
				byteCode.add(decode(instruction[i]));
			} catch (SyntaxError e) {
				throw new SyntaxError("Syntax error on line " +  Integer.toString(i + 1));
			}
		}
		
		return byteCode.toArray(new Word[0]);
	}
}
