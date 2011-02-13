
package hr.fer.anna.frisc.assembler;

import java.util.HashMap;

public class SymLookup {
	private HashMap<Integer, String> hashMap;
	public SymLookup() {
		hashMap = new HashMap<Integer, String>();
		hashMap.put(28,"STOREH");
		hashMap.put(59,"UVJ_Z");
		hashMap.put(75,"OKTALNA_KONSTANTA");
		hashMap.put(27,"STOREB");
		hashMap.put(53,"UVJ_V");
		hashMap.put(6,"PSEUDO_POSTO");
		hashMap.put(72,"KRAJ_REDA");
		hashMap.put(58,"UVJ_P");
		hashMap.put(37,"HALT");
		hashMap.put(55,"UVJ_N");
		hashMap.put(57,"UVJ_M");
		hashMap.put(51,"UVJ_C");
		hashMap.put(64,"UVJ_UGT");
		hashMap.put(71,"POCETNA_PRAZNINA");
		hashMap.put(68,"UVJ_SGT");
		hashMap.put(22,"MOVE");
		hashMap.put(66,"UVJ_UGE");
		hashMap.put(30,"POP");
		hashMap.put(11,"SUB");
		hashMap.put(18,"SHR");
		hashMap.put(70,"UVJ_SGE");
		hashMap.put(25,"LOADH");
		hashMap.put(17,"SHL");
		hashMap.put(24,"LOADB");
		hashMap.put(65,"UVJ_ULT");
		hashMap.put(12,"SBC");
		hashMap.put(61,"UVJ_EQ");
		hashMap.put(77,"KOMENTAR");
		hashMap.put(43,"AP_DW");
		hashMap.put(3,"L_ZAGRADA");
		hashMap.put(42,"AP_DS");
		hashMap.put(69,"UVJ_SLT");
		hashMap.put(26,"STORE");
		hashMap.put(63,"UVJ_ULE");
		hashMap.put(40,"AP_END");
		hashMap.put(76,"LABELA");
		hashMap.put(39,"AP_ORG");
		hashMap.put(8,"SR_REGISTAR");
		hashMap.put(67,"UVJ_SLE");
		hashMap.put(5,"PLUS");
		hashMap.put(2,"ZAREZ");
		hashMap.put(44,"DW");
		hashMap.put(32,"JR");
		hashMap.put(9,"ADD");
		hashMap.put(10,"ADC");
		hashMap.put(31,"JP");
		hashMap.put(29,"PUSH");
		hashMap.put(46,"DH");
		hashMap.put(21,"ROTR");
		hashMap.put(23,"LOAD");
		hashMap.put(45,"DB");
		hashMap.put(20,"ROTL");
		hashMap.put(0,"EOF");
		hashMap.put(4,"D_ZAGRADA");
		hashMap.put(50,"PSEUDO_O");
		hashMap.put(15,"OR");
		hashMap.put(49,"PSEUDO_H");
		hashMap.put(1,"error");
		hashMap.put(48,"PSEUDO_D");
		hashMap.put(47,"PSEUDO_B");
		hashMap.put(34,"RET");
		hashMap.put(7,"REGISTAR");
		hashMap.put(74,"HEKSA_KONSTANTA");
		hashMap.put(60,"UVJ_NZ");
		hashMap.put(41,"AP_EQU");
		hashMap.put(54,"UVJ_NV");
		hashMap.put(38,"AP_BASE");
		hashMap.put(56,"UVJ_NN");
		hashMap.put(73,"DEKADSKA_KONSTANTA");
		hashMap.put(36,"RETN");
		hashMap.put(14,"AND");
		hashMap.put(62,"UVJ_NE");
		hashMap.put(35,"RETI");
		hashMap.put(52,"UVJ_NC");
		hashMap.put(19,"ASHR");
		hashMap.put(33,"CALL");
		hashMap.put(13,"CMP");
		hashMap.put(16,"XOR");
	}

	public String lookup(int sym) {
		return hashMap.get(sym);
	}
}
