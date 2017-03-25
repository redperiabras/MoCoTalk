package AUTOMATA;

import java.util.ArrayList;

public class Auto {

	static class State{
		ArrayList<Transition> trans = new ArrayList<>();
		char output;
		
		public State(char output) {
			this.output = output;
		}
		
		public State() {
		}
		
		void addTransition(char input, State state){
			trans.add(new Transition(input, state));
		}
		
		State checkInput(char c) {
			State state=null;
			for(int x=0; x<trans.size(); x++) {
				if(trans.get(x).input == c) {
					state = trans.get(x).adjState;
				}
			}
			return state;
		}

		char getOutput() {
			return output;
		}
	
		boolean hasTransition() {
			return trans.isEmpty();
		}
	}
	
	static class Transition{
		char input;
		State adjState;
		
		public Transition(char input, State state) {
			this.input = input;
			adjState = state;
		}
		
	}

}
