package AUTOMATA;

import AUTOMATA.Auto.*;

public class Automaton {

	State q0 = new State();
	State q1 = new State('E');
	State q2 = new State('T');
	State q3 = new State('I');
	State q4 = new State('A');
	State q5 = new State('N');
	State q6 = new State('M');
	State q7 = new State('S');
	State q8 = new State('U');
	State q9 = new State('R');
	State q10 = new State('W');
	State q11 = new State('D');
	State q12 = new State('K');
	State q13 = new State('G');
	State q14 = new State('O');
	State q15 = new State('H');
	State q16 = new State('V');
	State q17 = new State('F');
	State q18 = new State();
	State q19 = new State('L');
	State q20 = new State();
	State q21 = new State('P');
	State q22 = new State('J');
	State q23 = new State('B');
	State q24 = new State('X');
	State q25 = new State('C');
	State q26 = new State('Y');
	State q27 = new State('Z');
	State q28 = new State('Q');
	State q29 = new State();
	State q30 = new State();
	State q31 = new State('5');
	State q32 = new State('4');
	State q33 = new State();
	State q34 = new State('3');
	State q35 = new State();
	State q36 = new State('2');
	State q37 = new State('+');
	State q38 = new State();
	State q39 = new State('1');
	State q40 = new State('6');
	State q41 = new State('=');
	State q42 = new State('/');
	State q43 = new State();
	State q44 = new State('7');
	State q45 = new State();
	State q46 = new State();
	State q47 = new State('Ñ');
	State q48 = new State('8');
	State q49 = new State('9');
	State q50 = new State('0');
	State q51 = new State();
	State q52 = new State('?');
	State q53 = new State('.');
	State q54 = new State('@');
	State q55 = new State('\'');
	State q56 = new State('-');
	State q57 = new State(';');
	State q58 = new State(',');
	State q59 = new State(':');
	State q60 = new State('$');
	
	public Automaton() {
		
		q0.addTransition('·', q1);
		q0.addTransition('-', q2);
		
		q1.addTransition('·', q3);
		q1.addTransition('-', q4);
		
		q2.addTransition('·', q5);
		q2.addTransition('-', q6);
		
		q3.addTransition('·', q7);
		q3.addTransition('-', q8);
		
		q4.addTransition('·', q9);
		q4.addTransition('-', q10);
		
		q5.addTransition('·', q11);
		q5.addTransition('-', q12);
		
		q6.addTransition('·', q13);
		q6.addTransition('-', q14);
		
		q7.addTransition('·', q15);
		q7.addTransition('-', q16);
		
		q8.addTransition('·', q17);
		q8.addTransition('-', q18);
		
		q9.addTransition('·', q19);
		q9.addTransition('-', q20);
		
		q10.addTransition('·', q21);
		q10.addTransition('-', q22);
		
		q11.addTransition('·', q23);
		q11.addTransition('-', q24);
		
		q12.addTransition('·', q25);
		q12.addTransition('-', q26);
		
		q13.addTransition('·', q27);
		q13.addTransition('-', q28);
		
		q14.addTransition('·', q29);
		q14.addTransition('-', q30);
		
		q15.addTransition('·', q31);
		q15.addTransition('-', q32);
		
		q16.addTransition('·', q33);
		q16.addTransition('-', q34);
		
		q18.addTransition('·', q35);
		q18.addTransition('-', q36);
		
		q20.addTransition('·', q37);
		
		q21.addTransition('-', q38);
		
		q22.addTransition('-', q39);
		
		q23.addTransition('·', q40);
		q23.addTransition('-', q41);
		
		q24.addTransition('·', q42);
		
		q25.addTransition('-', q43);
		
		q27.addTransition('·', q44);
		q27.addTransition('-', q45);
		
		q28.addTransition('·', q46);
		q28.addTransition('-', q47);
		
		q29.addTransition('·', q48);
		
		q30.addTransition('·', q49);
		q30.addTransition('-', q50);
		
		q33.addTransition('·', q51);
		
		q33.addTransition('·', q52);
		
		q37.addTransition('-', q53);
		
		q38.addTransition('·', q54);
		
		q39.addTransition('·', q55);
		
		q40.addTransition('-', q56);
		
		q43.addTransition('·', q57);
		
		q45.addTransition('-', q58);
		
		q48.addTransition('·', q59);
		
		q46.addTransition('-', q60);
		
		q0.addTransition(' ', q0);
		q1.addTransition(' ', q0);
		q2.addTransition(' ', q0);
		q3.addTransition(' ', q0);
		q4.addTransition(' ', q0);
		q5.addTransition(' ', q0);
		q6.addTransition(' ', q0);
		q7.addTransition(' ', q0);
		q8.addTransition(' ', q0);
		q9.addTransition(' ', q0);
		q10.addTransition(' ', q0);
		q11.addTransition(' ', q0);
		q12.addTransition(' ', q0);
		q13.addTransition(' ', q0);
		q14.addTransition(' ', q0);
		q15.addTransition(' ', q0);
		q16.addTransition(' ', q0);
		q17.addTransition(' ', q0);
		q18.addTransition(' ', q0);
		q19.addTransition(' ', q0);
		q20.addTransition(' ', q0);
		q21.addTransition(' ', q0);
		q22.addTransition(' ', q0);
		q23.addTransition(' ', q0);
		q24.addTransition(' ', q0);
		q25.addTransition(' ', q0);
		q26.addTransition(' ', q0);
		q27.addTransition(' ', q0);
		q28.addTransition(' ', q0);
		q29.addTransition(' ', q0);
		q30.addTransition(' ', q0);
		q31.addTransition(' ', q0);
		q32.addTransition(' ', q0);
		q33.addTransition(' ', q0);
		q34.addTransition(' ', q0);
		q35.addTransition(' ', q0);
		q36.addTransition(' ', q0);
		q37.addTransition(' ', q0);
		q38.addTransition(' ', q0);
		q39.addTransition(' ', q0);
		q40.addTransition(' ', q0);
		q41.addTransition(' ', q0);
		q42.addTransition(' ', q0);
		q43.addTransition(' ', q0);
		q44.addTransition(' ', q0);
		q45.addTransition(' ', q0);
		q46.addTransition(' ', q0);
		q47.addTransition(' ', q0);
		q48.addTransition(' ', q0);
		q49.addTransition(' ', q0);
		q50.addTransition(' ', q0);
		q51.addTransition(' ', q0);
		q52.addTransition(' ', q0);
		q53.addTransition(' ', q0);
		q54.addTransition(' ', q0);
		q55.addTransition(' ', q0);
		q56.addTransition(' ', q0);
		q57.addTransition(' ', q0);
		q58.addTransition(' ', q0);
		q59.addTransition(' ', q0);
		q60.addTransition(' ', q0);
	}

	public boolean codeExist(String input) {
		boolean exist = true;
		char test[] = input.toCharArray();
		
		State curState = q0;
		State newState;
		
		for(int x=0; x<test.length; x++) {
				newState = curState.checkInput(test[x]);
				if(newState==null) {
					exist = false;
				}
				else if((newState!=null)) {
					curState = newState;
					if(curState.hasTransition()) {
						if(curState.getOutput()=='\u0000') {
							exist = false;
						}
					}
					
					/*if(curState.getOutput()=='\u0000') {
						if(!curState.hasTransition())
							exist = false;
					}*/
				}
//			}
		}
		return exist;
	}

	public String genOutput(String input) {
		input += " ";
		String output = "";
		
		State curState = q0;
//		State newState;
		
		for(int x=0; x<input.length(); x++) {
			if(input.charAt(x) == ' ') {
				output += curState.getOutput();
				curState = q0;
			}
			else if(input.charAt(x) == '/' && input.charAt(x+1) == ' ') {
				x++;
				output += " ";
			}
			else{
				System.out.println(input.charAt(x));
				curState = curState.checkInput(input.charAt(x)); 
			}
		}
		return output;
	}
	
}
