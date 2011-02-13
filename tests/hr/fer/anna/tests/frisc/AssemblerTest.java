package hr.fer.anna.tests.frisc;

import java.io.File;
import java.io.FileNotFoundException;

import hr.fer.anna.exceptions.SyntaxError;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Ivan
 * 
 */
public class AssemblerTest {

//	/**
//	 * Asembliranje jednostavnog jednolinijskog koda.
//	 * 
//	 * @throws SyntaxError
//	 */
//	@Test
//	public void assembleTest01() throws SyntaxError {
//		String code = "\tADD R7, R2, R1";
//		byte[] assembled = Assembler.assemble(code);
//		byte[] ispravno = new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0xf4,
//				(byte) 0x20 };
//
//		Assert.assertEquals(new ByteArray(ispravno), new ByteArray(assembled));
//	}
//
//	/**
//	 * Asembliranje jednolinijskog koda uz dodatak komentara i višestrukih
//	 * tabova.
//	 * 
//	 * @throws SyntaxError
//	 */
//	@Test
//	public void assembleTest02() throws SyntaxError {
//		String code = "\tADD R7, R2, R1\t\t \t; komentar\tkk da";
//		byte[] assembled = Assembler.assemble(code);
//		byte[] ispravno = intToByteArray(0x20F40000);
//
//		Assert.assertEquals(new ByteArray(ispravno), new ByteArray(assembled));
//	}
//
//	/**
//	 * Asembliranje dvolinijskog koda uz dodatak komentara i višestrukih tabova.
//	 * 
//	 * @throws SyntaxError
//	 */
//	@Test
//	public void assembleTest03() throws SyntaxError {
//		String code = "\tADD R7, R2, R1\t\t \t; komentar\tkk da\n"
//				+ "\tADD r1, %D 123,r1;kom .\n";
//		byte[] assembled = Assembler.assemble(code);
//		ByteArray ispravno = new ByteArray(intToByteArray(0x20F40000));
//		ispravno.append(intToByteArray(0x2490007B));
//
//		Assert.assertEquals(ispravno, new ByteArray(assembled));
//	}
//
//	/**
//	 * Asembliranje dvolinijskog koda uz dodatak komentara, višestrukih tabova
//	 * te praznog reda i reda koji se sastoji samo od komentara.
//	 * 
//	 * @throws SyntaxError
//	 */
//	@Test
//	public void assembleTest04() throws SyntaxError {
//		String code = "\tADD R7, R2, R1\t\t \t; komentar\tkk da\n"
//				+ "\n"
//				+ "\t;blah\n"
//				+ "\tADD r1, %D 123,r1;kom .\n";
//		byte[] assembled = Assembler.assemble(code);
//		ByteArray ispravno = new ByteArray(intToByteArray(0x20F40000));
//		ispravno.append(intToByteArray(0x2490007B));
//
//		Assert.assertEquals(ispravno, new ByteArray(assembled));
//	}
//	
//	/**
//	 * Provjera baza konstanti (%H|D|O|B oznaka).
//	 * 
//	 * @throws SyntaxError
//	 */
//	@Test
//	public void assembleTest05() throws SyntaxError {
//		String code = "\tADD r1, %D 123,r1;kom .\n"
//				+ "\tADD r1, %H 123,r1;kom .\n"
//				+ "\tADD r1, %B 1010,r1;kom .\n"
//				+ "\tADD r1, %O 123,r1;kom .\n";
//		byte[] assembled = Assembler.assemble(code);
//		ByteArray ispravno = new ByteArray(intToByteArray(0x2490007B));
//		ispravno.append(intToByteArray(0x24900123));
//		ispravno.append(intToByteArray(0x2490000A));
//		ispravno.append(intToByteArray(0x24900053));
//
//		Assert.assertEquals(ispravno, new ByteArray(assembled));
//	}
//	
//	/**
//	 * Provjera baza konstanti (%H|D|O|B oznaka + `BASE pseudonaredba).
//	 * 
//	 * @throws SyntaxError
//	 */
//	@Test
//	public void assembleTest06() throws SyntaxError {
//		String code = "\tADD r1, %D 123,r1;kom .\n"
//				+ "\tADD r1, 123,r1;kom .\n"
//				+ "\t`Base B\n"
//				+ "\tADD r1, 1010,r1;kom .\n"
//				+ "\t`Base O\n"
//				+ "\tADD r1, 123,r1;kom .\n"
//				+ "\tADD r1, 124,r1;kom .\n";
//		
//		byte[] assembled = Assembler.assemble(code);
//		ByteArray ispravno = new ByteArray(intToByteArray(0x2490007B));
//		ispravno.append(intToByteArray(0x24900123));
//		ispravno.append(intToByteArray(0x2490000A));
//		ispravno.append(intToByteArray(0x24900053));
//		ispravno.append(intToByteArray(0x24900054));
//
//		Assert.assertEquals(ispravno, new ByteArray(assembled));
//	}
//	
//	/**
//	 * Ispitivanje labela. EQU + naredba.
//	 * 
//	 * @throws SyntaxError
//	 */
//	@Test
//	public void assembleTest07() throws SyntaxError {
//		String code = "LBL `EQU\t%D 123\n" 
//				+ "\tADD r1, LBL,r1;kom .\n";
//		byte[] assembled = Assembler.assemble(code);
//		ByteArray ispravno = new ByteArray(intToByteArray(0x2490007B));
//
//		Assert.assertEquals(ispravno, new ByteArray(assembled));
//	}
//	
//	/**
//	 * Ispitivanje pseudonaredbi `ORG i DW.
//	 * 
//	 * @throws SyntaxError
//	 */
//	@Test
//	public void assembleTest08() throws SyntaxError {
//		String code = "\t DW 1\n"
//				+ "\t`ORG 8\n" 
//				+ "\tDW 15;kom .";
//		byte[] assembled = Assembler.assemble(code);
//		ByteArray ispravno = new ByteArray(intToByteArray(1));
//		ispravno.append(intToByteArray(0));
//		ispravno.append(intToByteArray(0x15));
//
//		Assert.assertEquals(ispravno, new ByteArray(assembled));
//	}
//	
//	/**
//	 * Finalni test 01: 1. labos iz ARH1, vj. 1 
//	 * 
//	 * @throws SyntaxError
//	 * @throws FileNotFoundException 
//	 */
//	@Test
//	public void assembleTest09() throws SyntaxError, FileNotFoundException {
//		//byte[] assembled = 
//			Assembler.assemble(new File("tests-data/frisc-assembler/ik42696_lab1_vj1.a"));
////		ByteArray ispravno = new ByteArray(intToByteArray(1));
////		ispravno.append(intToByteArray(0));
////		ispravno.append(intToByteArray(0x15));
////
////		Assert.assertEquals(ispravno, new ByteArray(assembled));
//	}
//	
//	/**
//	 * Prebacivanje integera u niz byteova.
//	 * 
//	 * @param num Broj koji želimo rastaviti na byteove.
//	 * @return Niz byteova dobivenih rastavljanjem predatog broja.
//	 */
//	private byte[] intToByteArray(int num) {
//		return new byte[] { (byte) (num & 0xFF), (byte) ((num >> 8) & 0xFF),
//				(byte) ((num >> 16) & 0xFF), (byte) ((num >> 24) & 0xFF) };
//	}
//
//	/**
//	 * Klasa za maskiranje byte[] tipa podataka radi usporedbe jednakosti.
//	 */
//	private class ByteArray {
//		/** Osnovni niz. */
//		byte[] array;
//
//		/**
//		 * Konstruktor.
//		 * 
//		 * @param array Niz koji maskiramo.
//		 */
//		public ByteArray(byte[] array) {
//			this.array = array;
//		}
//
//		/**
//		 * Metoda za uspoređivanje.
//		 */
//		@Override
//		public boolean equals(Object obj) {
//			if (!(obj instanceof ByteArray))
//				return false;
//
//			ByteArray ba = (ByteArray) obj;
//			try {
//				for (int i = 0; i < this.array.length; i++) {
//					if (ba.array[i] != this.array[i])
//						return false;
//				}
//			} catch (IndexOutOfBoundsException e) {
//				return false;
//			}
//
//			return true;
//		}
//
//		@Override
//		public String toString() {
//			StringBuilder sb = new StringBuilder();
////			int num = 0;
////			int pomak = 0;
//
//			for (int i = 0; i < array.length; i++) {
//				sb.append(array[i] + "-");
////				num |= (array[i] << pomak) & (0xFF << pomak);
////				pomak += 8;
////				if (pomak == 32) {
////					pomak = 0;
////					sb.append(Integer.toHexString(num));
////					num = 0;
////				}
//			}
//
////			if (array.length % 4 != 0) {
////				sb.append(Integer.toHexString(num));
////			}
//
//			return sb.toString();
//		}
//
//		public byte[] append(byte[] dodatni) {
//			byte[] novi = new byte[this.array.length + dodatni.length];
//
//			for (int i = 0; i < this.array.length; i++) {
//				novi[i] = this.array[i];
//			}
//
//			for (int i = this.array.length; i < this.array.length
//					+ dodatni.length; i++) {
//				novi[i] = dodatni[i - this.array.length];
//			}
//
//			this.array = novi;
//			return this.array;
//		}
//
//		/**
//		 * Getter arraya.
//		 * 
//		 * @return Array sa podatcima.
//		 */
//		public byte[] getArray() {
//			return this.array;
//		}
//	}
}
