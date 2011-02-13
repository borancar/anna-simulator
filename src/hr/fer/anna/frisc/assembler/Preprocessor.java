package hr.fer.anna.frisc.assembler;

import java.io.IOException;
import java.io.StringReader;

/**
 * Procesiranje prije asembliranja.
 * - Dodavanje oznake '^' umjesto praznine na početku linije.
 * - Mijenja uvjete iz _EQ u &EQ (radi problema sa lexom)
 * 
 * @author Ivan
 *
 */
public class Preprocessor {
	
	public static String process(String code) {
		try {
			code = hackCondition(code);
			code = addBeginningSpaceMark(code);
			return code;
		} catch (IOException e) {
			return null;
		}
	}
	
	/**
	 * Zaobilazak problema sa lexom.
	 * Izraz JR_EQ lex prepoznaje (iz nepoznatog razloga) kao labelu
	 * umjesto naredbu i uvjet.
	 * Sad su uvjeti &EQ umjesto _EQ.
	 * 
	 * @param code
	 * @return
	 */
	private static String hackCondition(String code) {
		return code.replaceAll("(?i)(JP|JR|CALL|RET|RETI|RETN|HALT)_([C|NC|V|NV|N|NN|M|P|Z|NZ|EQ|NE|ULE|UGT|ULT|UGE|SLE|SGT|SLT|SGE])", "$1&$2");
	}

	/**
	 * Svim linijama koje počinju s prazninom dodaje posebnu
	 * oznaku '^' umjesto praznine da bi se znalo da je to
	 * početna praznina.
	 * 
	 * @param code Programski kod.
	 * @return Kod s označenim početnim prazninama.
	 * @throws IOException 
	 */
	private static String addBeginningSpaceMark(String code) throws IOException {
		StringReader reader = new StringReader(code);
		StringBuilder sb = new StringBuilder(code.length());
		
		boolean nl = true;
		while (true) {
			int ch = reader.read();
			if (ch == -1) break;
			
			if ((ch == ' ' || ch == '\t') && nl) {
				sb.append('^');
			} else {
				sb.append((char) ch);
			}
			
			nl = (ch == '\n');
		}
		
		return sb.toString();
	}

}
