package rs.ac.bg.etf.pp1;
import java_cup.runtime.*;
import org.apache.log4j.*;

%%

%{

	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type) {
		return new Symbol(type, yyline+1, yycolumn);
	}
	
	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type, Object value) {
		return new Symbol(type, yyline+1, yycolumn, value);
	}

%}


%class MJLexer
%cup
%line
%column

%xstate COMMENT

%eofval{
	return new_symbol(sym.EOF);
%eofval}

%%

<YYINITIAL>{
" " 	{ }
"\b" 	{ }
"\t" 	{ }
"\r\n" 	{ }
"\f" 	{ }
"program"   { return new_symbol(sym.PROG, yytext()); }
"print" 	{ return new_symbol(sym.PRINT, yytext()); }
"void" 		{ return new_symbol(sym.VOID, yytext()); }
"true" | "false" {return  new_symbol(sym.BOOL_CONST, new Integer(yytext().equals("true")?1:0));}
"const"		{ return new_symbol(sym.CONST, yytext()); }
"new"		{ return new_symbol(sym.NEW, yytext()); }
"read" 		{ return new_symbol(sym.READ, yytext()); }
"+" 		{ return new_symbol(sym.PLUS, yytext()); }
"-"			{ return new_symbol(sym.MINUS, yytext()); }
"*"			{ return new_symbol(sym.MUL, yytext()); }
"/" 		{ return new_symbol(sym.DIV, yytext()); }
"%"			{ return new_symbol(sym.PERCENTAGE, yytext()); }
"=" 		{ return new_symbol(sym.ASSIGN, yytext()); }
"=="		{ return new_symbol(sym.EQUAL, yytext()); }
"!="		{ return new_symbol(sym.NOT_EQUAL, yytext()); }
">"			{ return new_symbol(sym.GREATER, yytext()); }
">="		{ return new_symbol(sym.GREATER_EQUAL, yytext()); }
"<"			{ return new_symbol(sym.LESSER, yytext()); }
"<=" 		{ return new_symbol(sym.LESSER_EQUAL, yytext()); }
"++"		{ return new_symbol(sym.PLUS_PLUS, yytext()); }
"--"		{ return new_symbol(sym.MINUS_MINUS, yytext()); }
";" 		{ return new_symbol(sym.SEMI, yytext()); }
"," 		{ return new_symbol(sym.COMMA, yytext()); }
"(" 		{ return new_symbol(sym.LPAREN, yytext()); }
")" 		{ return new_symbol(sym.RPAREN, yytext()); }
"{" 		{ return new_symbol(sym.LBRACE, yytext()); }
"}"			{ return new_symbol(sym.RBRACE, yytext()); }
"[" 		{ return new_symbol(sym.LEVA_UGLASTA, yytext()); }
"]"			{ return new_symbol(sym.DESNA_UGLASTA, yytext()); }
"?"			{ return new_symbol(sym.QUESTION_MARK, yytext()); }
":"			{ return new_symbol(sym.DVE_TACKE, yytext()); }
"//" 		     { yybegin(COMMENT); }
[0-9]+  { return new_symbol(sym.NUMBER, new Integer (yytext())); }
([a-z]|[A-Z])[a-z|A-Z|0-9|_]* 	{return new_symbol (sym.IDENT, yytext()); }
"'"[\040-\176]"'" {return new_symbol(sym.CHAR_CONST, new Character(yytext().charAt(1)));}
}

<COMMENT> "\r\n" { yybegin(YYINITIAL); }
<COMMENT> .      { yybegin(COMMENT); }

. { System.err.println("Leksicka greska ("+yytext()+") u liniji "+(yyline+1)+ " i koloni " + (yycolumn+4)); }