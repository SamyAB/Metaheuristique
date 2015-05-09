package dz.oooo.mh;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

public class Formule {
	private ArrayList<Clause> clauses;
	private int nbLitteraux;
	private int nbClauses;
	
	public Formule(){
		this.clauses=new ArrayList<Clause>();
	}
	
	public Formule(String fichier){
		this();
		try {
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(fichier)));
			String chaine;
			chaine=br.readLine();
			while(chaine.charAt(0)!='p'){
				chaine=br.readLine();
			}
			String [] mots=chaine.split(" ++");
			this.nbClauses=Integer.parseInt(mots[3]);
			this.nbLitteraux=Integer.parseInt(mots[2]);
			
			chaine=br.readLine();
			mots=chaine.split(" ++");
			this.clauses.add(new Clause(Short.parseShort(mots[1]),Short.parseShort(mots[2]),Short.parseShort(mots[3])));
			int i=1;
			while(i<this.nbClauses){
				chaine=br.readLine();
				mots=chaine.split(" ++");
				System.out.println(mots[0]+" "+mots[1]+" "+mots[2]);
				this.clauses.add(new Clause(Short.parseShort(mots[0]),Short.parseShort(mots[1]),Short.parseShort(mots[2])));
				i++;
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Clause> getClauses() {
		return clauses;
	}

	public void setClauses(ArrayList<Clause> clauses) {
		this.clauses = clauses;
	}

	public int getNbLitteraux() {
		return nbLitteraux;
	}

	public void setNbLitteraux(int nbLitteraux) {
		this.nbLitteraux = nbLitteraux;
	}

	public int getNbClauses() {
		return nbClauses;
	}

	public void setNbClauses(int nbClauses) {
		this.nbClauses = nbClauses;
	}
	
	public String toString(){
		String formule="";
		Iterator<Clause> clauses=this.clauses.iterator();
		while(clauses.hasNext()){
			Clause clause=clauses.next();
			formule+=(Short.toString(clause.getLitteraux()[0])+" "+Short.toString(clause.getLitteraux()[1])+" "+Short.toString(clause.getLitteraux()[2])+"\n");
		}
		return formule;
	}
	
	public Solution genererRandom(){
		Solution random=new Solution();
		for(int i=1;i<=nbLitteraux;i++){
			short litt=(short) (i);
			short signe=(short) ((Math.random()>0.5) ? 1 : -1);
			random.getLitteraux().add((short) (litt*signe));
		}
		random.setTauxSat(this);
		return random;
	}
	
	public Solution rechercheLocale(){
		Solution s=genererRandom();
		int i=0;
		while(s.getTauxSat()!=this.nbClauses && i<ClasseMain.getNombreIteration()){
			Solution tmp=s.bestVoisin(this);
			if(s==tmp){
				s=genererRandom();
			}
			else{
				s=tmp;
			}
		}
		return s;
	}

}
