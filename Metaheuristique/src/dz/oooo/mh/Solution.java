package dz.oooo.mh;

import java.util.ArrayList;
import java.util.Iterator;

public class Solution {
	private ArrayList<Short> litteraux;
	private short nbClausesSat;
	private boolean unsat; 
	
	public Solution(){
		this.litteraux=new ArrayList<Short>();
		this.nbClausesSat=0;
		this.unsat=false;
	}

	public ArrayList<Short> getLitteraux() {
		return litteraux;
	}

	public void setLitteraux(ArrayList<Short> litteraux) {
		this.litteraux = litteraux;
	}

	public short getTauxSat() {
		return nbClausesSat;
	}
	
	public boolean isUnsat() {
		return unsat;
	}

	public void setUnsat(boolean unsat) {
		this.unsat = unsat;
	}

	public void setTauxSat(short tauxSat) {
		this.nbClausesSat = tauxSat;
	}
	
	public void setTauxSat(Formule f){
		int nbClausesSat=0;
		byte[] valSat=new byte[f.getNbClauses()];
		for(int i=0;i<this.litteraux.size() && !this.unsat;i++){
			Iterator<Clause> clauses=f.getClauses().iterator();
			int j=0;
			while(clauses.hasNext()){
				Clause clause=clauses.next();
				if(valSat[j]==-1){
					j++;
					continue;
				}
				if(clause.contains((short)(-1*this.litteraux.get(i)))){
					valSat[j]++;
					if(valSat[j]>=3){
						this.unsat=true;
						nbClausesSat=0;
						break;
					}
				}
				if(clause.contains((short)(this.litteraux.get(i)))){
					valSat[j]=-1;
					nbClausesSat++;
				}
				j++;
			}
		}
		this.nbClausesSat=(short)nbClausesSat;
	}
	
	public String toString(){
		String solution="";
		Iterator<Short> litteraux=this.litteraux.iterator();
		solution=litteraux.next().toString();
		while(litteraux.hasNext()){
			solution+=(", "+litteraux.next().toString());
		}
		return solution;
	}
	
}
