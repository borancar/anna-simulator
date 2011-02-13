package hr.fer.anna.oisc;

import java.util.LinkedList;
import java.util.List;

import hr.fer.anna.interfaces.IBus;
import hr.fer.anna.interfaces.IBusMaster;
import hr.fer.anna.interfaces.IFlagsRegister;
import hr.fer.anna.interfaces.IInstructionDecoder;
import hr.fer.anna.interfaces.IMicroinstruction;
import hr.fer.anna.microcode.ConditionalMicroinstruction;
import hr.fer.anna.microcode.JumpMicroinstruction;
import hr.fer.anna.microcode.LoadMicroinstruction;
import hr.fer.anna.microcode.MoveMicroinstruction;
import hr.fer.anna.microcode.StoreMicroinstruction;
import hr.fer.anna.microcode.SubtractMicroinstruction;
import hr.fer.anna.model.Word;
import hr.fer.anna.uniform.Constant;
import hr.fer.anna.uniform.Register;

/**
 * Dekodira jedinu OISC instrukciju.
 * @author Boran
 *
 */
public class InstructionDecoder implements IInstructionDecoder {
	
	/** Upravljač sabirnice */
	private IBusMaster busMaster;
	
	/** Sabirnica preko koje se čita i piše */
	private IBus bus;
	
	/** Registar programskog brojila */
	private Register pc;
	
	/** Akumulator */
	private Register accumulator;
	
	/** Podatkovni registar */
	private Register dataRegister;

	/** Registar zastavica */
	private IFlagsRegister flagsRegister;
	
	/**
	 * Konstruktor novog dekodera instrukcija
	 * @param busMaster upravljač sabirnice kojeg programiramo mikroinstrukcijama
	 * @param bus sabirnica preko koje se čita i piše
	 * @param dataRegister podatkovni registar
	 * @param accuReg akumulator
	 * @param pc registar programskog brojila
	 * @param flagsRegister registar zastavica
	 */
	public InstructionDecoder(IBusMaster busMaster, IBus bus, Register dataRegister, Register accuReg, Register pc, IFlagsRegister flagsRegister) {
		this.busMaster = busMaster;
		this.bus = bus;
		this.pc = pc;
		this.dataRegister = dataRegister;
		this.accumulator = accuReg;
		this.flagsRegister = flagsRegister;
	}
	
	/**
	 * subneg address1 address2 address3 - subtract and branch if negative
	 * mem[address2] = mem[address2] - mem[address1]
	 * if negative branch mem-address3
	 * xx|address1(10)|address2(10)|address3(10)
	 */
	public List<IMicroinstruction> decode(Register instructionRegister) {
		
		List<IMicroinstruction> microinstructions = new LinkedList<IMicroinstruction>();
		
		if(instructionRegister.equals(new Constant(0))) {
			return microinstructions;
		}
		
		Word mask = new Constant((1 << 10) - 1);
		
		Word address1 = Word.and(Word.shiftRight(instructionRegister, 20), mask);
		Word address2 = Word.and(Word.shiftRight(instructionRegister, 10), mask);
		Word address3 = Word.and(instructionRegister, mask);
		
		// Učitaj s prve adrese i spremi u akumulator
		microinstructions.add(new LoadMicroinstruction(busMaster, bus, address1));
		microinstructions.add(new MoveMicroinstruction(dataRegister, accumulator));
		
		// Učitaj s druge adrese i oduzmi
		microinstructions.add(new LoadMicroinstruction(busMaster, bus, address2));
		microinstructions.add(new SubtractMicroinstruction(accumulator, dataRegister, accumulator, flagsRegister));
		
		// Pomakni rezultat u podatkovni registar i spremi na lokaciju 2
		microinstructions.add(new MoveMicroinstruction(accumulator, dataRegister));
		microinstructions.add(new StoreMicroinstruction(busMaster, dataRegister, bus, address2));
		
		// Skači ako je postavljena zastavica negative nakon alu operacije
		microinstructions.add(
				new ConditionalMicroinstruction(
						flagsRegister,
						new JumpMicroinstruction(
								pc,
								address3,
								true
								)
						).testNegative(true)
				);
		
		return microinstructions;
	}

}
