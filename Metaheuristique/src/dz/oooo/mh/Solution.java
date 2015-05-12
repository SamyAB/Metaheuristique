package dz.oooo.mh;

import java.util.ArrayList;
import java.util.Iterator;

public class Solution {
	private ArrayList<Short> litteraux;
	private short nbClausesSat;
	private boolean unsat;
	private int id;
	
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

	public int getId(){
		return this.id;
	}
	
	public void setId(int id){
		this.id=id;
	}
	
	public void setTauxSat(short tauxSat) {
		this.nbClausesSat = tauxSat;
	}

	public void setTauxSat(Formule f){
		this.unsat=false;
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
		this.nbClausesSat=(short) nbClausesSat;
	}

	public String toString(){
		String solution="Le nombre de clases SAT est de : "+this.nbClausesSat+"\n";
;
		Iterator<Short> litteraux=this.litteraux.iterator();
		solution+=litteraux.next().toString();
		while(litteraux.hasNext()){
			solution+=(", "+litteraux.next().toString());
		}
		return solution;
	}

	public ArrayList<Solution> voisinage(){
		ArrayList<Solution> voisins=new ArrayList<Solution>();
		for(int i=0;i<this.litteraux.size();i++){
			Solution s=(Solution) this.clone();
			s.getLitteraux().set(i, (short) (-1*this.litteraux.get(i)));
			voisins.add(s);
		}
		return voisins;
	}

	public ArrayList<Solution> voisinage(Formule f){
		ArrayList<Solution> voisins=new ArrayList<Solution>();
		for(int i=0;i<this.litteraux.size();i++){
			Solution s=(Solution) this.clone();
			s.getLitteraux().set(i, (short) (-1*this.litteraux.get(i)));
			s.setTauxSat(f);
			voisins.add(s);
		}
		return voisins;
	}

	public Solution clone(){
		Solution s=new Solution();
		s.setLitteraux(new ArrayList<Short> (this.litteraux));
		s.setTauxSat(this.nbClausesSat);
		s.setUnsat(this.unsat);
		return s;
	}

	public Solution bestVoisin(Formule f){
		this.setId(-1);
		Solution best=this;
		for(int i=0;i<this.litteraux.size();i++){
			Solution s=(Solution) this.clone();
			s.getLitteraux().set(i, (short) (-1*this.litteraux.get(i)));
			s.setTauxSat(f);
			s.setId(i);
			if(s.getTauxSat()>best.getTauxSat()){
				best=s;
			}
		}
	 	return best;
	}
	
	public Solution bestVoisin(Formule f,ArrayList<Solution> LT){
		this.setId(-1);
		Solution best=this;
		for(int i=0;i<this.litteraux.size();i++){
			Solution s=(Solution) this.clone();
			s.getLitteraux().set(i, (short) (-1*this.litteraux.get(i)));
			s.setTauxSat(f);
			s.setId(i);
			if(s.getTauxSat()>best.getTauxSat() && !LT.contains(s)){
				best=s;
			}
		}
	 	return best;
	}

}
