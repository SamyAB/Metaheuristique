package dz.oooo.mh;

import java.util.ArrayList;
import java.util.Iterator;

public class Solution {
	private ArrayList<Short> litteraux;
	private short nbClausesSat;
	private short diversite;
	private boolean unsat;
	private int id;
	
	public Solution(){
		this.litteraux=new ArrayList<Short>();
		this.nbClausesSat=0;
		this.unsat=false;
		this.diversite=0;
	}

	public ArrayList<Short> getLitteraux() {
		return litteraux;
	}

	public void setLitteraux(ArrayList<Short> litteraux) {
		this.litteraux = litteraux;
	}
	
	public void addLitteral(short l){
		this.litteraux.add(l);
	}

	public short getNbClausesSat() {
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
	
	public void setNbClausesSat(short nbClausesSat) {
		this.nbClausesSat = nbClausesSat;
	}

	//Méthode objective f -problème 3SAT-
	public void setNbClausesSat(Formule f){
		this.unsat=false;
		int nbClausesSat=0;
		byte[] valSat=new byte[f.getNbClauses()];
		for(int i=0;i<this.litteraux.size();i++){
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
	
	public short getDiversite() {
		return diversite;
	}

	public void setDiversite(short diversite) {
		this.diversite = diversite;
	}
	
	//Calcule de diversité d'une solution à l'aide de la distance de Hamming
	public void setDiversite(ArrayList<Solution> listeTaboue){
		//Initialisation de la distance à la valaure maximal possible càd le nombre de littéraux
		short diversite=(short)this.litteraux.size();
		
		//Parcourt de toute la liste taboue
		Iterator<Solution> LT=listeTaboue.iterator();
		while(LT.hasNext()){
			Solution tmp=LT.next();
			short distance=0;
			
			//Compare chaque littéral de la solution au littéral corréspendant de l'élément de la liste taboue
			for(int i=0;i<this.litteraux.size();i++){
				if(this.litteraux.get(i)!=tmp.getLitteraux().get(i)){
					distance++;
				}
			}
			if(distance<diversite){
				diversite=distance;
			}
		}
		
		//La diversité est égale à la distance minimale des éléments de la liste taboue
		this.diversite=diversite;
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
	
	public boolean equals(Object o){
		Solution s=(Solution) o;
		for(int i=0;i<this.litteraux.size();i++){
			if((short)(this.litteraux.get(i))!=(short)(s.getLitteraux().get(i))){
				return false;
			}
		}
		return true;
	}

	public boolean equals(Solution s){
		for(int i=0;i<this.litteraux.size();i++){
			if((short)(this.litteraux.get(i))!=(short)(s.getLitteraux().get(i))){
				return false;
			}
		}
		return true;
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
			s.setNbClausesSat(f);
			voisins.add(s);
		}
		return voisins;
	}

	public Solution clone(){
		Solution s=new Solution();
		s.setLitteraux(new ArrayList<Short> (this.litteraux));
		s.setUnsat(this.unsat);
		s.setNbClausesSat(this.nbClausesSat);
		return s;
	}

	public Solution bestVoisin(Formule f){
		this.setId(-1);
		Solution best=this;
		for(int i=0;i<this.litteraux.size();i++){
			Solution s=(Solution) this.clone();
			s.getLitteraux().set(i, (short) (-1*this.litteraux.get(i)));
			s.setNbClausesSat(f);
			s.setId(i);
			if(s.getNbClausesSat()>best.getNbClausesSat()){
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
			s.setNbClausesSat(f);
			s.setId(i);
			if(s.getNbClausesSat()>best.getNbClausesSat() && !LT.contains(s)){
				best=s;
			}
		}
	 	return best;
	}
	
	public Solution bestVoisinCercle(Formule f,ArrayList<Solution> LT){
		Solution best=null;
		int bestNbClausesSat=0;
		for(int i=0;i<this.litteraux.size();i++){
			Solution s=(Solution) this.clone();
			s.getLitteraux().set(i, (short) (-1*this.litteraux.get(i)));
			s.setNbClausesSat(f);
			s.setId(i);
			if(s.getNbClausesSat()>bestNbClausesSat && !LT.contains(s)){
				best=s;
			}
		}
	 	return best;
	}
	
	public ArrayList<Solution> flipPeriodique(int flip){
		ArrayList<Solution> searchArea=new ArrayList<Solution>();
		int h=0;
		while(h<flip){
			Solution tmp=this.clone();
			for(int k=0;(flip*k+h)<this.litteraux.size();k++){
				tmp.getLitteraux().set(flip*k+h, (short) (-1*tmp.getLitteraux().get(flip*k+h)));
			}
			searchArea.add(tmp);
			h++;
		}
		return searchArea;
	}
	
	public Solution rechercheVoisinage(ArrayList<Solution> LT,Formule f,int nbIterations){
		Solution best=this,s=new Solution();
		ArrayList<Solution> voisinage=this.voisinage(f);
		
		//Iterations de la recharche locale
		for(int iteration=0;iteration<nbIterations;iteration++){
			int nbClausesSat=0;
			
			//Parcourt du voisinage de s
			for(int i=0;i<voisinage.size();i++){
				//Si le voisin i est meilleur que le nombre de clauses sat et qu'il n'appertient pas à la liste taboue
				if(voisinage.get(i).getNbClausesSat()>nbClausesSat && !LT.contains(voisinage.get(i))){
					nbClausesSat=voisinage.get(i).getNbClausesSat();
					s=voisinage.get(i);
				}
			}
			if(s.getNbClausesSat()>best.nbClausesSat){
				best=s;
			}
			voisinage=s.voisinage(f);
		}
		
		//si le best est le courent
		if(best.equals(this)){
			best=best.diversifier(LT,f);
		}

		return best;
	}
	
	public Solution diversifier(ArrayList<Solution> LT,Formule f){
		Solution div=this.clone();
		
		//parcourt des litteraux
		for(int i=0;i<this.litteraux.size();i++){
			div.getLitteraux().set(i,(short) (div.getLitteraux().get(i)*-1));
			if(!LT.contains(div)){
				div.setNbClausesSat(f);
				break;
			}
		}
		return div;
	}
}
