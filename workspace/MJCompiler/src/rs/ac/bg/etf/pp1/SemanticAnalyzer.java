package rs.ac.bg.etf.pp1;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

public class SemanticAnalyzer extends VisitorAdaptor {

	boolean errorDetected = false;
	int nVars;
	private Obj trenutna_metoda = null;
	Struct pomocni_tip = null;
	Struct pomocni_tip_const = null;
	private static Struct boolType = Tab.insert(Obj.Type, "bool", new Struct(Struct.Bool)).getType();
	
	Logger log = Logger.getLogger(getClass());
	
	//DA BUDEM SIGURNA DA MOGU DA IDEM DALJE U GENERISANJE KODA!
	public boolean passed(){
	    	return !errorDetected;
	}
	
	/*** ISPIS GRESAKA ***/
	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message); 
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.info(msg.toString());
	} 

	/* ---------------------------------------------------------------------------------- */
	//OBRADA IMENA PROGRAMA, DODAVANJE PROGRAMA U UNIVERSE OPSEG i OTVARANJE NOVOG OPSEGA
	public void visit(ProgName progName) {
		progName.obj = Tab.insert(Obj.Prog, progName.getProgramName(), Tab.noType);
		Tab.openScope();
	}
	
	//KRAJ PROGRAMA - Ulancavanje svih promenljivih i zatvaranje SCOPE
	public void visit(Program program) {
		Obj main = Tab.find("main");
		if(main == Tab.noObj) {
			report_error("Greska na liniji " + program.getLine() + " U programu mora postojati main metoda! " , null);
		} else {
			if(main.getKind() != Obj.Meth) {
				report_error("Greska na liniji " + program.getLine() + " U programu mora postojati main metoda! " , null);
			}
		}
		nVars = Tab.currentScope.getnVars();
		Tab.chainLocalSymbols(program.getProgName().obj);
		Tab.closeScope();
	}
	/* ---------------------------------------------------------------------------------- */
	
	//VAR JEDNOSTAVNA PROMENLJIVA
	public void visit(DeoDeklaracijaPromenljiva identifikator) {	
		String ime = identifikator.getVarName();
		if(Tab.currentScope().findSymbol(ime)!= null) {
			report_error("Greska na liniji: " + identifikator.getLine() +": Promenljiva " + ime + " je vec deklarisana u ovom opsegu!", null);
	} else {
			Tab.insert(Obj.Var, ime, pomocni_tip);
			report_info("Deklarisana promenljiva " + ime + " na liniji "+ identifikator.getLine(), null);
		}
	}	
	
	//VAR JEDNOSTAVNA PROMENLJIVA TIPA NIZ
	public void visit(DeoDeklaracijeNiz niz) {
		String ime = niz.getVarName();
		if(Tab.currentScope().findSymbol(ime)!= null) {
			report_error("Greska na liniji: " + niz.getLine() +": Promenljiva " + ime + " je vec deklarisana u ovom opsegu!", null);
	} else {
			Tab.insert(Obj.Var, ime, new Struct(Struct.Array, pomocni_tip));
			report_info("Deklarisana promenljiva " + ime + " na liniji "+ niz.getLine(), null);
		}
	}
	
	//DODAVANJE PROMENLJIVE U TABELU SIMBOLA
	public void visit(VarJednaDeklaracija dekl) {
	
	} 
	
	//DODAVANJE PROMENLJIVE U TABELU SIMBOLA
	public void visit(VarViseDeklaracija dekl) {
	
	}
		
	//RESETOVANJE POMOCNOG TIPA KOJI MI JE SLUZIO DA ZNAM KOG SU TIPA PROMENLJIVE KOJE UBACUJEM U TABELU SIMBOLA
	//BEZ NJEGA NISAM USPELA DA DODJEM DO TOG PODATKA
	public void visit(VarDeclOpstaSmena promenljiva) {
		pomocni_tip = null;
	}
	
	/* ---------------------------------------------------------------------------------- */
	
	//OBILAZAK KONSTANTE I PAMCENJE TIPA KONSTANTE
	public void visit(Number_Const number) {
		number.obj = new Obj(Obj.Con, null, Tab.intType);
		number.obj.setAdr(number.getBroj());
	}
	
	//OBILAZAK KONSTANTE I PAMCENJE TIPA KONSTANTE
	public void visit(Character_Const karakter) {
		karakter.obj = new Obj(Obj.Con, null, Tab.charType);
		karakter.obj.setAdr(karakter.getKarakter());
	}
	
	//OBILAZAK KONSTANTE I PAMCENJE TIPA KONSTANTE - DORADI KAD DODAS BOOL!!!
	public void visit(Boolean_Const flag) {
		flag.obj = new Obj(Obj.Con, null, boolType);
		flag.obj.setAdr(flag.getB1());
	}
	
	//DODAVANJE KONSTANTE U TABELU SIMBOLA - PO SINTAKSNOJ ANALIZI JE ZADOVOLJENO DA KONSTANTA NE MOZE BITI NIZ
	public void visit(DodelaVrednosti dodela) {
		String constName = dodela.getConstName();
		if(Tab.find(constName)!=Tab.noObj) {
			report_error("Greska na liniji " + dodela.getLine() + " : " + "! Konstanta je vec deklarisana", null);
		}
		else {
			if(pomocni_tip_const == dodela.getVrednost().obj.getType()) {
				dodela.obj = Tab.insert(Obj.Con, dodela.getConstName(), dodela.getVrednost().obj.getType());
				dodela.obj.setAdr(dodela.getVrednost().obj.getAdr());
				report_info("Deklarisana simbolicka konstanta " + constName + " na liniji "+ dodela.getLine(), null);
			} else {
				report_error("Greska na liniji " + dodela.getLine() + ": Tip konstante "+ dodela.getConstName() + " i tip dodeljene vrednosti se ne poklapaju! ", null);
			}	
		}
	}
	
	//DODAVANJE KONSTANTE U TABELU SIMBOLA - PO SINTAKSNOJ ANALIZI JE ZADOVOLJENO DA KONSTANTA NE MOZE BITI NIZ
	public void visit(DodelaViseVrednosti dodele) {
		String constName = dodele.getIdentifikator();
		if(Tab.find(constName)!=Tab.noObj) {
			report_error("Greska na liniji " + dodele.getLine() + " : " + "! Konstanta je vec deklarisana", null);
		}
		else {
			if(pomocni_tip_const == dodele.getVrednost().obj.getType()) {
				dodele.obj = Tab.insert(Obj.Con, dodele.getIdentifikator(), dodele.getVrednost().obj.getType());
				dodele.obj.setAdr(dodele.getVrednost().obj.getAdr());
				report_info("Deklarisana simbolicka konstanta " + constName + " na liniji "+ dodele.getLine(), null);
			} else {
				report_error("Greska na liniji " + dodele.getLine() + ": Tip konstante "+ dodele.getIdentifikator() + " i tip dodeljene vrednosti se ne poklapaju! ", null);
			}
		}
	}
	
	 public void visit(ConstDecl decl) {
		 pomocni_tip_const = null;
	 }
	
	/* ---------------------------------------------------------------------------------- */
		
	//PROVERA TIPA - DA LI TIP POSTOJI U TABELI SIMBOLA ILI NE
	public void visit(Type type){
    	Obj typeNode = Tab.find(type.getTip());
    	if(typeNode == Tab.noObj){
    		report_error("Greska na liniji " + type.getLine() + ": Nije pronadjen tip " + type.getTip() + " u tabeli simbola! ", null);
    		type.struct = Tab.noType;
    	}else{
    		//potrebno je da se proveri da li pronadjeni objektni cvor zaista predstavlja tip, da slucajno npr nisam neku promenljivu nazvala int
    		if(Obj.Type == typeNode.getKind()){
    			type.struct = typeNode.getType();
    			//treba mi zbog dodavanja u tabelu simbola globalnih i lokalnih promenljivih
    				pomocni_tip = type.struct;
    				pomocni_tip_const = type.struct;
    		}else{
    			report_error("Greska: Ime " + type.getTip() + " ne predstavlja tip!", type);
    			type.struct = Tab.noType;
    		}
    	}
    }
	
	/* ---------------------------------------------------------------------------------- */

	//POSETA IMENU METODE I OTVARANJE NOVOG SCOPE
	public void visit(MethodDeclName method) {
		String naziv = method.getNazivMetode();
		Obj meth = Tab.find(naziv);
		if(meth == Tab.noObj) {
			if(naziv.equals("main")) {
				if(method.getPovratna().struct == Tab.noType) {
					trenutna_metoda = Tab.insert(Obj.Meth, method.getNazivMetode(), method.getPovratna().struct);
					method.obj = trenutna_metoda;
					report_info("Deklarisana metoda " + method.getNazivMetode() + " na liniji " + method.getLine(), null);
					Tab.openScope();
				} else {
					trenutna_metoda = Tab.insert(Obj.Meth, method.getNazivMetode(), method.getPovratna().struct);
					report_error("Greska na liniji " + method.getLine() + ": Main metoda mora imati povratnu vrednost VOID! " , null);
					Tab.openScope();
					method.obj = Tab.noObj;
					//trenutna_metoda = Tab.noObj;
				}
			} else {
				//ukoliko se radi o nekoj drugoj metodi, nije potrebno za nivo A
			}
		}
		else {
			//ukoliko pokusam da navedem dve fje da imaju isto ime!
			trenutna_metoda = Tab.noObj;
			report_error("Greska na liniji "+method.getLine() + ": Metoda sa nazivom "+ naziv +" vec postoji u programu!", null);
		}
	}	

	//ZAVRSENA OBRADA METODE I ZATVARANJE SCOPE!
	public void visit(MethodDecl method) {
		if(trenutna_metoda != Tab.noObj) {
			Tab.chainLocalSymbols(trenutna_metoda);
			report_info("Zavrsena obrada metode " + method.getMethodDeclName().getNazivMetode() + " na liniji " + method.getLine(), null);
			Tab.closeScope();
			trenutna_metoda = null;
		}
	}
	
	//OBILAZAK ARGUMENATA METODA, u slucaju metode MAIN, ona ne sme imati argumente!
	public void visit(FormParams parametri) {
		if(trenutna_metoda.getName().equals("main")) {
			report_error("Greska na liniji " + parametri.getLine() + ": Main metoda ne sme imati parametre! " , null);
		} else {
			//radi se o nekoj drugoj metodi koja nije main
		}
	}
	
	//TIP POVRATNE VREDNOSTI koji nije VOID
	public void visit(Tip_Povratna neki_tip) {
		neki_tip.struct = neki_tip.getType().struct;
	}

	//VOID TIP POVRATNE VREDNOSTI
	public void visit(Void_Povratna povratna) {
		povratna.struct = Tab.noType;
	}
	
	/* ---------------------------------------------------------------------------------- */
	//OBILAZAK PROMENLJIVE
	public void visit(DesignatorIdentifikator ident) {
		String ime = ident.getImePromenljive();
		Obj obj = Tab.find(ime);
		if(obj == Tab.noObj) {
			report_error("Greska na liniji " + ident.getLine() + " : " + " Promenljiva " + ident.getImePromenljive() + " nije deklarisana!", null);
		}
		else {
			if(obj.getKind() == Obj.Con) {
				report_info("Detektovana simbolicka konstanta " + ident.getImePromenljive(), ident);
			} else if (obj.getKind()==Obj.Var){
				if(obj.getLevel() == 0) {
					report_info("Detektovana globalna promenljiva " + ident.getImePromenljive() + " na liniji " + ident.getLine(), null);
				} else {
					report_info("Detektovana lokalna promenljiva " + ident.getImePromenljive() + " na liniji " + ident.getLine(), null);
				}
			}	
		}
		ident.obj = obj;	
	 }
	
	//OBILAZAK ELEMENTA NIZA
	public void visit(DesignatorArrayName ident) {
		String ime = ident.getImeNiza();
		Obj obj = Tab.find(ime);
		if(obj == Tab.noObj) {
			report_error("Greska na liniji " + ident.getLine() + ": Promenljiva " + ime + " nije deklarisanja!", null);
		} else {
			if (obj.getKind()==Obj.Var){
				if(obj.getLevel() == 0) {
					report_info("Detektovana globalna promenljiva " + ime + " na liniji " + ident.getLine(), null);
				} else {
					report_info("Detektovana lokalna promenljiva " + ime + " na liniji " + ident.getLine(), null);
				}
			} 
		}
		ident.obj = obj;
	}
	
	public void visit(DesignatorArray ident) {
		Obj obj = ident.getDesignatorArrayName().obj;
		//ukoliko postoji u tabeli simbola ta promenljiva, ona mora biti definisana kao niz!
		if(obj != Tab.noObj) {
			if(obj.getType().getKind() != Struct.Array) {
				report_error("Greska na liniji " + ident.getLine() + " : " + " Promenljiva " +  obj.getName() + " mora biti NIZ!", null);
			} else {
				ident.obj = new Obj(Obj.Elem, obj.getName(), obj.getType().getElemType());
			}
		} else {
			ident.obj = Tab.noObj;
		}
		//ukoliko je exp takodje niz, potrebno je da proverim da li je su njegovi elementi tipa int
		if(ident.getExpr().struct != Tab.intType) {
			report_error("Greska na liniji " + ident.getLine() + " : " + " Pristup promenljivoj " +  obj.getName() + " se mora izvrsiti indeksiranjem sa INT tipom!", null);
		}
	}
	
	/* ---------------------------------------------------------------------------------- */
	//INKREMENTIRANJE PROMENLJIVE ILI ELEMENTA NIZA
	public void visit(Increment inc) {
		//report_info("Detektovana promenljiva " + inc.getDesignator().obj.getName() + " na liniji " + inc.getLine(), null);
		if(inc.getDesignator().obj != Tab.noObj && (inc.getDesignator().obj.getKind() == Obj.Var || inc.getDesignator().obj.getKind() == Obj.Elem)) {
			//report_info("Inkrementirao na liniji " + inc.getLine(), null);
			Struct tip = inc.getDesignator().obj.getType();
				if(Tab.intType != tip) {
					report_error("Greska na liniji " + inc.getLine() + " : Promenljiva " + inc.getDesignator().obj.getName() + " mora biti promenljiva ili element niza tipa INT!", null);
				} else {
					report_info("Izvrseno inkrementiranje promenljive " + inc.getDesignator().obj.getName(), null);
				}
		} else {
			report_error("Greska na liniji " + inc.getLine() + " : Promenljiva " + inc.getDesignator().obj.getName() + " mora biti promenljiva ili element niza!", null);
		}
	}
	
	//DEKREMENTIRANJE PROMENLJIVE ILI ELEMENTA NIZA
	public void visit(Decrement dec) {
		//report_info("Detektovana promenljiva " + dec.getDesignator().obj.getName() + " na liniji " + dec.getLine(), null);
		if(dec.getDesignator().obj != Tab.noObj && (dec.getDesignator().obj.getKind() == Obj.Var || dec.getDesignator().obj.getKind() == Obj.Elem)) {
			Struct tip = dec.getDesignator().obj.getType();
				if(Tab.intType != tip) {
					report_error("Greska na liniji " + dec.getLine() + " : Promenljiva " + dec.getDesignator().obj.getName() + " mora biti promenljiva ili element niza tipa INT!", null);
				} else {
					report_info("Izvrseno dekrementiranje promenljive " + dec.getDesignator().obj.getName(), null);
				}
		} else {
			report_error("Greska na liniji " + dec.getLine() + " : Promenljiva " + dec.getDesignator().obj.getName() + " mora biti promenljiva ili element niza!", null);
		}
	}
	
	/* ---------------------------------------------------------------------------------- */
	
	//READ STATEMENT
	public void visit(ReadStatement read) {
		//report_info("Detektovana promenljiva " + read.getDesignator().obj.getName() + " na liniji " + read.getLine(), null);
		if( (read.getDesignator().obj.getKind() == Obj.Var || read.getDesignator().obj.getKind() == Obj.Elem)) {
			Struct tip = read.getDesignator().obj.getType();	
				if(Tab.intType != tip && Tab.charType!=tip && boolType != tip) {
					report_error("Greska na liniji " + read.getLine() + " : Promenljiva " + read.getDesignator().obj.getName() + " mora biti tipa INT!", null);
				} else {
					report_info("Izvrseno citanje promenljive " + read.getDesignator().obj.getName(), null);
				}
		} else {
			report_error("Greska na liniji " + read.getLine() + " : Promenljiva " + read.getDesignator().obj.getName() + " mora biti promenljiva ili element niza!", null);
		}
	}
	
	//PRINT STATEMENT
	public void visit(PrintStatement print) {
		Struct tip = print.getExpr().struct;
		
		if(tip != Tab.charType && tip!= Tab.intType && boolType!=tip) {
			report_error("Greska na liniji " + print.getLine() + " : Argument instrukcije print mora biti promenljiva ili element niza tipa INT, CHAR ILI BOOL!", null);
		}
	}
		
	//PRINT STATEMENT
	public void visit(PrintStatementComma print) {
		Struct tip = print.getExpr().struct;
			
		if(tip != Tab.charType && tip!= Tab.intType && boolType!=tip) {
			report_error("Greska na liniji " + print.getLine() + " : Argument instrukcije print mora biti tipa promenljiva ili element niza tipa INT, CHAR ILI BOOL!", null);
		}
	}
	
	/* ---------------------------------------------------------------------------------- */
	
	public void visit(FactorDesignator factorDesignator) {
		//report_info("Detektovana promenljiva " + factorDesignator.getDesignator().obj.getName() + " na liniji " + factorDesignator.getLine(), null);
		factorDesignator.struct = factorDesignator.getDesignator().obj.getType();
	}
	
	public void visit(FactorNumber factorNumber) {
		factorNumber.struct = Tab.intType;
	}
		
	public void visit(FactorChar factorChar) {
		factorChar.struct = Tab.charType;
	}
	
	//DORADI OVU METODU KAD DODAS BOOL!!!
	public void visit(FactorBool factorBool) {
		factorBool.struct = boolType;
	}
	
	//ovo sluzi za prioritiranje operacija uz pomoc zagrada! Nije za pozive metoda/fja!
	public void visit(FactorExpr factorExpr) {
		factorExpr.struct = factorExpr.getExpr().struct;
	}
	
	//DEO IZRAZA ZA PRAVLJENJE NOVOG NIZA
	public void visit(FactorNewArray factorArray) {
		if(factorArray.getExpr().struct != Tab.intType) {
			report_error("Greska na liniji : " + factorArray.getLine() + " Tip promenljive koja govori koliko elemenata niza zelimo da alociramo, mora biti tipa INT!", null);
			factorArray.struct = Tab.noType;
		} else {
			factorArray.struct = new Struct(Struct.Array, factorArray.getType().struct);
		}
	}
	
	public void visit(ExprWithoutMinus expr) {
		expr.struct = expr.getTerm().struct;
	}
	
	/* ---------------------------------------------------------------------------------- */
	//OBILAZAK KLASE ZA DODELU VREDNOSTI! - izmenila sam koristeci assignable
	public void visit(Assign_Designator_Klasa assign) {
	 if(assign.getDesignator().obj != Tab.noObj) {
		if(assign.getDesignator().obj.getKind() == Obj.Var || assign.getDesignator().obj.getKind() == Obj.Elem ) {
		  if(assign.getDesignator().obj.getType().assignableTo(assign.getExpr().struct)) {
				report_info("Izraz dodele uspesno izvrsen na liniji " + assign.getLine(), null);
		  } else {
				report_error("Greska na liniji : " + assign.getLine() + " Vrednosti nisu kompatibilne pri izrazu dodele!", null);
		  }
		} else {
			report_error("Greska na liniji " + assign.getLine() + ": Ne mozete dodeliti vrednost necemu sto nije promenljiva ni element niza!", null);
		}} else {
			report_error("Greska na liniji " + assign.getLine() + ": Promenljiva kojoj pokusavate da dodelite vrednost nije deklarisana!",null);
		}
	}
	
	/* ---------------------------------------------------------------------------------- */
	
	public void visit(MinusExpr minus) {
		Struct tip = minus.getTerm().struct;
		if(tip != Tab.intType) {
			report_error("Greska na liniji " + minus.getLine() + " : Operand u izrazu mora biti tipa INT!", null);
			minus.struct = Tab.noType;
		} else {
			minus.struct = tip;
		}
	}
	
	public void visit(Term_0 term) {
		term.struct = term.getFactor().struct;
	}
	
	/* ---------------------------------------------------------------------------------- */
	
	public void visit(MinusExprLista lista) {
		Struct tip = lista.getTerm().struct;
		if(tip != Tab.intType) {
			report_error("Greska na liniji " + lista.getLine() + " : Operand u izrazu mora biti tipa INT!", null);
		} else {
			report_info("Izvrsena operacija sabiranja!", null);
		}
		lista.struct = tip;
	}
	
	public void visit(ExprWithoutMinusLista lista) {
		Struct tip = lista.getTerm().struct;
		if(tip != Tab.intType) {
			report_error("Greska na liniji " + lista.getLine() + " : Operand u izrazu mora biti tipa INT!", null);
		} 
		else {
			report_info("Izvrsena operacija sabiranja",null);
		}
		lista.struct = tip;
	}
	
	public void visit(AddoptListaViseOperacija lista) {
		Struct tip = lista.getTerm().struct;
		
		if(tip != Tab.intType) {
			report_error("Greska na liniji " + lista.getLine() + " : Operand u izrazu mora biti tipa INT!", null);
		}
		lista.struct = tip;
	}
	
	public void visit(AddoptListaJednaOperacija lista) {
		Struct tip = lista.getTerm().struct;
		
		if(tip != Tab.intType) {
			report_error("Greska na liniji " + lista.getLine() + " : Operand u izrazu mora biti tipa INT!", null);
		}
		lista.struct = tip;
	}
	
	/* ---------------------------------------------------------------------------------- */
	
	public void visit(ViseTermova lista) {
		Struct tip = lista.getFactor().struct;
	
		if(tip != Tab.intType) {
			report_error("Greska na liniji " + lista.getLine() + " : Operand u izrazu mora biti tipa INT!", null);
		}
		lista.struct = tip;	
	}
	
	public void visit(MulopFactor lista) {
		Struct tip = lista.getFactor().struct;
	
		if(tip != Tab.intType) {
			report_error("Greska na liniji " + lista.getLine() + " : Operand u izrazu mora biti tipa INT!", null);
		}
		lista.struct = tip;	
	}
	
	public void visit(MulopFactorLista lista) {
		Struct tip = lista.getFactor().struct;
	
		if(tip != Tab.intType) {
			report_error("Greska na liniji " + lista.getLine() + " : Operand u izrazu mora biti tipa INT!", null);
		}
		lista.struct = tip;	
	}
	
	/* ---------------------------------------------------------------------------------- */
	/*** TERNARNI OPERATOR ***/
	
	public void visit(Expr_obican expr) {
		expr.struct = expr.getExpr1().struct;
	}
	
	public void visit(Ternarni_operator tern) {
		
		/*** U SLUCAJU DA SE NIZU PRISTUPA SA EXPR, a ne EXPR1***/
		//moram da proverim da li cu da elementu niza pristupam s intom
//		if(tern.getParent().getClass() == DesignatorArray.class) {
//			if(!(tern.getExpr1().struct.equals(tern.getExpr11().struct) && tern.getExpr1().struct == Tab.intType)) {
//				report_error("Greska na liniji " + tern.getLine() + ": Izrazi u ternarnom operatoru, zbog pristupa elementu niza, moraju biti tipa INT!", null);
//			}
//		}
		/********************************************************/
		
		if(!tern.getExpr1().struct.equals(tern.getExpr11().struct)) {
			report_error("Greska na liniji " + tern.getLine() + ": Izrazi u ternarnom operatoru moraju biti istog tipa!", null);
		}
		tern.struct = tern.getExpr1().struct;
	}
	
	public void visit(CondFactBoolean cond) {
		if(cond.getExpr1().struct != boolType) {
			report_error("Greska na liniji " + cond.getLine()+ ": Uslov ternarnog operatora mora biti tipa BOOL!", null);
		}
	}
	
	public void visit(CondFactRelation cond) {
		Obj relop = cond.getRelop().obj;
		if((relop.getName().equals("jednako") || relop.getName().equals("razlicito")) && cond.getExpr1().struct == Tab.intType && cond.getExpr11().struct==Tab.intType) {
			//report_info("prvi", null);
		} else if((relop.getName().equals("jednako") || relop.getName().equals("razlicito")) && cond.getExpr1().struct == Tab.charType && cond.getExpr11().struct==Tab.charType) {
			//report_info("drugi", null);
		} else if((relop.getName().equals("jednako") || relop.getName().equals("razlicito")) && cond.getExpr1().struct == boolType && cond.getExpr11().struct==boolType) {
			//report_info("treci", null);
		} else if((relop.getName().equals("vece") || relop.getName().equals("manje") || relop.getName().equals("manjejednako") || relop.getName().equals("vecejednako")) && cond.getExpr1().struct == Tab.intType && cond.getExpr11().struct == Tab.intType) {
			//report_info("cetvrti", null);
		} else if((relop.getName().equals("vece") || relop.getName().equals("manje") || relop.getName().equals("manjejednako") || relop.getName().equals("vecejednako")) && cond.getExpr1().struct == Tab.charType && cond.getExpr11().struct == Tab.charType) {
			//report_info("peti", null);
		} else {
			report_error("Greska na liniji "+cond.getLine()+": Vrednosti u uslovu ternarnog operatora nisu kompatibilne!", null);
		}
	}
	
	public void visit(EqOperacija op) {
		op.obj = new Obj(Code.eq, "jednako", null);
	}
	  
	public void visit(NeOperacija op) {
	    op.obj = new Obj(Code.ne, "razlicito", null);
	}
	  
	public void visit(GtOperacija op) {
		op.obj = new Obj(Code.gt, "vece", null);
	}
	  
	public void visit(Ltperacija op) {
		op.obj = new Obj(Code.lt, "manje", null);
	}
	  
	public void visit(LeOperacija op) {
		op.obj = new Obj(Code.le, "manjejednako", null);
	}
	  
	public void visit(GeOperacija op) {
		op.obj = new Obj(Code.ge, "vecejendako", null);
	}
	
}

