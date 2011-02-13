package hr.fer.anna.frisc.assembler;

import java.io.File;

public class Test {

	public static void main(String[] args) throws Exception {
		Assemble.assemble(new File("tests-data/frisc-assembler/ik42696_lab1_vj1.a"));
	}
}
