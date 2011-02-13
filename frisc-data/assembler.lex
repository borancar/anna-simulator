/*
	JFlex lexer za ANNA FRISC assembler 	
	Osnovu drpio sa PPJ projekta "Poskok"
		
	Author: Ivan
	
	TODO:
		- Doraditi dojavu grešaka
*/

package hr.fer.anna.frisc.assembler;

import java_cup.*;
import java_cup.runtime.*;  
import hr.fer.anna.GUI.Postman;
%%

%line
%column
%unicode
%class LexicalAnalyser
%public
%cup
%ignorecase

%{
    private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn);
    }
    
    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
    }
%}

krajReda = \r|\n|\r\n|\u2028|\u2029|\u000B|\u000C|\u0085
praznina = [ \t\f]+
binarna_konstanta = [01][01]*
dekadska_konstanta = [1-9][0-9]*
heksa_konstanta = [0-9A-Fa-f][0-9A-Fa-f]*
oktalna_konstanta = 0[0-7]+
labela = [A-Za-z][A-Za-z0-9_]*
registar = [rR][0-7]
komentar = ;[^\r\n\u2028\u2029\u000B\u000C\u0085]*

%%
   
<YYINITIAL> {
	"SR"			{ return symbol(sym.SR_REGISTAR); }
	"ADD"			{ return symbol(sym.ADD, Byte.valueOf((byte) 0x04)); }
	"ADC"			{ return symbol(sym.ADC, Byte.valueOf((byte) 0x05)); }
	"SUB"			{ return symbol(sym.SUB, Byte.valueOf((byte) 0x06)); }
	"SBC"			{ return symbol(sym.SBC, Byte.valueOf((byte) 0x07)); }
	"AND"			{ return symbol(sym.AND, Byte.valueOf((byte) 0x02)); }
	"OR"			{ return symbol(sym.OR, Byte.valueOf((byte) 0x01)); }
	"XOR"			{ return symbol(sym.XOR, Byte.valueOf((byte) 0x03)); }
	"SHL"			{ return symbol(sym.SHL, Byte.valueOf((byte) 0x0A)); }
	"SHR"			{ return symbol(sym.SHR, Byte.valueOf((byte) 0x0B)); }
	"ASHR"		{ return symbol(sym.ASHR, Byte.valueOf((byte) 0x0C)); }
	"ROTL"		{ return symbol(sym.ROTL, Byte.valueOf((byte) 0x08)); }
	"ROTR"		{ return symbol(sym.ROTR, Byte.valueOf((byte) 0x09)); }
	"LOAD"		{ return symbol(sym.LOAD, Byte.valueOf((byte) 0x16)); }
	"LOADB"		{ return symbol(sym.LOADB, Byte.valueOf((byte) 0x12)); }
	"LOADH"		{ return symbol(sym.LOADH, Byte.valueOf((byte) 0x14)); }
	"STORE"		{ return symbol(sym.STORE, Byte.valueOf((byte) 0x17)); }
	"STOREB"		{ return symbol(sym.STOREB, Byte.valueOf((byte) 0x13)); }
	"STOREH"		{ return symbol(sym.STOREH, Byte.valueOf((byte) 0x15)); }
	"POP"			{ return symbol(sym.POP, Byte.valueOf((byte) 0x10)); }
	"PUSH"		{ return symbol(sym.PUSH, Byte.valueOf((byte) 0x11)); }
	"MOVE"		{ return symbol(sym.MOVE, Byte.valueOf((byte) 0x00)); }
	"JP"			{ return symbol(sym.JP, Byte.valueOf((byte) 0x18)); }
	"JR"			{ return symbol(sym.JR, Byte.valueOf((byte) 0x1A)); }
	"CALL"		{ return symbol(sym.CALL, Byte.valueOf((byte) 0x19)); }
	"RET"			{ return symbol(sym.RET, Byte.valueOf((byte) 0x1B)); }
	"RETI"		{ return symbol(sym.RETI, Byte.valueOf((byte) 0x1B)); }
	"RETN"		{ return symbol(sym.RETN, Byte.valueOf((byte) 0x1B)); }
	"CMP"			{ return symbol(sym.CMP, Byte.valueOf((byte) 0x0D)); }
	"HALT"		{ return symbol(sym.HALT, Byte.valueOf((byte) 0x1F)); }
	
	"^"			{ return symbol(sym.POCETNA_PRAZNINA); }
    ","			{ return symbol(sym.ZAREZ); }
	"("			{ return symbol(sym.L_ZAGRADA); }
	")"			{ return symbol(sym.D_ZAGRADA); }
	"+"			{ return symbol(sym.PLUS); }
	"%"			{ return symbol(sym.PSEUDO_POSTO); }
	
	"`BASE"		{ return symbol(sym.AP_BASE); }
	"`ORG"		{ return symbol(sym.AP_ORG); }
	"`END"		{ return symbol(sym.AP_END); }
	"`EQU"		{ return symbol(sym.AP_EQU); }
	"`DS"			{ return symbol(sym.AP_DS); }
	"`DW"			{ return symbol(sym.AP_DW); }
	"DW"			{ return symbol(sym.DW); }
	"DB"			{ return symbol(sym.DB); }
	"DH"			{ return symbol(sym.DH); }
	"B"			{ return symbol(sym.PSEUDO_B); }
	"D"			{ return symbol(sym.PSEUDO_D); }
	"H"			{ return symbol(sym.PSEUDO_H); }
	"O"			{ return symbol(sym.PSEUDO_O); }
	
	"&C"			{ return symbol(sym.UVJ_C, Byte.valueOf((byte) 3)); }
	"&NC"			{ return symbol(sym.UVJ_NC, Byte.valueOf((byte) 4)); }
	"&V"			{ return symbol(sym.UVJ_V, Byte.valueOf((byte) 5)); }
	"&NV"			{ return symbol(sym.UVJ_NV, Byte.valueOf((byte) 6)); }
	"&N"			{ return symbol(sym.UVJ_N, Byte.valueOf((byte) 1)); }
	"&NN"			{ return symbol(sym.UVJ_NN, Byte.valueOf((byte) 2)); }
	"&M"			{ return symbol(sym.UVJ_M, Byte.valueOf((byte) 1)); }
	"&P"			{ return symbol(sym.UVJ_P, Byte.valueOf((byte) 2)); }
	"&Z"			{ return symbol(sym.UVJ_Z, Byte.valueOf((byte) 7)); }
	"&NZ"			{ return symbol(sym.UVJ_NZ, Byte.valueOf((byte) 8)); }
	"&EQ"			{ return symbol(sym.UVJ_EQ, Byte.valueOf((byte) 7)); }
	"&NE"			{ return symbol(sym.UVJ_NE, Byte.valueOf((byte) 8)); }
	"&ULE"		{ return symbol(sym.UVJ_ULE, Byte.valueOf((byte) 9)); }
	"&UGT"		{ return symbol(sym.UVJ_UGT, Byte.valueOf((byte) 10)); }
	"&ULT"		{ return symbol(sym.UVJ_ULT, Byte.valueOf((byte) 3)); }
	"&UGE"		{ return symbol(sym.UVJ_UGE, Byte.valueOf((byte) 4)); }
	"&SLE"		{ return symbol(sym.UVJ_SLE, Byte.valueOf((byte) 12)); }
	"&SGT"		{ return symbol(sym.UVJ_SGT, Byte.valueOf((byte) 14)); }
	"&SLT"		{ return symbol(sym.UVJ_SLT, Byte.valueOf((byte) 11)); }
	"&SGE"		{ return symbol(sym.UVJ_SGE, Byte.valueOf((byte) 13)); }
	
    /* Nasli smo dekadsku konstantu */
    {binarna_konstanta}		{
	 							return symbol(sym.BINARNA_KONSTANTA, yytext());
 						 	}
 						 	
    {oktalna_konstanta}		{
 								return symbol(sym.OKTALNA_KONSTANTA, yytext());
							}

    {dekadska_konstanta}	{
	 							return symbol(sym.DEKADSKA_KONSTANTA, yytext());
 						 	}
 						 	
	{heksa_konstanta}  		{
 								return symbol(sym.HEKSA_KONSTANTA, yytext());
	 						}    	 						


    /* Lovac na greske s brojevima tipa (123abcd) */
    {dekadska_konstanta}{labela} 	{
										Postman.get().sendErrorMsg("Greška (" + Integer.toString(yyline) + "," + Integer.toString(yycolumn) + "): Neprihvatljiv niz " + yytext() + "! Labela ne smije započinjati brojem!");
    								}
	
	{registar}				{
								Integer i =  Integer.parseInt(yytext().substring(1));
								return symbol(sym.REGISTAR, i);
							}
	
	{komentar}				{
								return symbol(sym.KOMENTAR, yytext());
							}
							
	{labela}				{
								return symbol(sym.LABELA, yytext());
							}
   
    {praznina}				{}
							
	{krajReda}				{
								return symbol(sym.KRAJ_REDA);
							}   
}

	[^]                    { Postman.get().sendErrorMsg("Greška (" + Integer.toString(yyline) + "," + Integer.toString(yycolumn) + "): Nedozvoljeni znak <"+yytext()+">"); }
