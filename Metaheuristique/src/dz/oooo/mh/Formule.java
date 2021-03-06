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

	//Création de formule à partir d'un fichier
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

	//Fonction de génération de solution aléatoire
	public Solution genererRandom(){
		Solution random=new Solution();
		for(int i=1;i<=nbLitteraux;i++){
			short litt=(short) (i);
			short signe=(short) ((Math.random()>0.5) ? 1 : -1);
			random.getLitteraux().add((short) (litt*signe));
		}
		random.setNbClausesSat(this);
		return random;
	}

	//Méthode de recherche locale
	public Solution rechercheLocale(int nbIterationsMax){
		//Première solution générée aléatoirement
		Solution s=genererRandom(),best=s.clone();

		//Initialisation du nombre d'itérations
		int nbIterations=0;

		//Tant que le nombre de clauses SAT est différant du nombre total de clauses
		//Et que le nombre d'itérations max n'est pas atteint 
		while(s.getNbClausesSat()!=this.nbClauses && nbIterations<nbIterationsMax){
			Solution tmp=s.bestVoisin(this);
			if(tmp.getId()==-1){
				if(best.getNbClausesSat()<s.getNbClausesSat()){
					best=s.clone();
				}
				s=genererRandom();
			}
			else{
				s=tmp;
			}
			nbIterations++;
		}
		if(best.getNbClausesSat()>s.getNbClausesSat()){
			best=s.clone();
		}
		return best;
	}

	public Solution rechercheLocale(int nbIterationsMax,ArrayList<Solution> LT,Solution s){
		//Première solution générée aléatoirement
		Solution best=new Solution();

		//Initialisation du nombre d'itérations
		int nbIterations=0;

		//Tant que le nombre de clauses SAT est différant du nombre total de clauses
		//Et que le nombre d'itérations max n'est pas atteint 
		while(s.getNbClausesSat()!=this.nbClauses && nbIterations<nbIterationsMax){
			Solution tmp=s.bestVoisinCercle(this, LT);
			if(tmp.getNbClausesSat()<s.getNbClausesSat()){
				if(best.getNbClausesSat()<s.getNbClausesSat()){
					best=s;
				}
				break;
			}
			else{
				s=tmp;
			}
			nbIterations++;
		}
		return best;
	}

	//Méthode de recherche taboue
	public Solution rechercheTaboue(int nbIterationsMax){
		//Première solutions générée aléatoirement
		Solution s=genererRandom(),best=s.clone();

		//Initialisations
		int tailleTaboue=this.nbLitteraux;
		ArrayList<Solution> listeTaboue=new ArrayList<Solution>();
		int nbIterations=0,indiceTaboue=0;

		//Tant que le nombre de clauses SAT est différant du nombre total de clauses
		//Et que le nombre d'itérations max n'est pas atteint 
		while(s.getNbClausesSat()!=this.nbClauses && nbIterations<nbIterationsMax){
			Solution tmp=s.bestVoisin(this,listeTaboue);
			if(tmp.getId()==-1){
				if(best.getNbClausesSat()<s.getNbClausesSat()){
					best=s.clone();
				}
				s=genererRandom();
			}
			else{
				s=tmp;
				if(listeTaboue.size()>=tailleTaboue){
					listeTaboue.remove(indiceTaboue);
				}
				listeTaboue.add(indiceTaboue,s);
				indiceTaboue=(indiceTaboue+1)%tailleTaboue;
			}
			nbIterations++;
		}

		if(best.getNbClausesSat()>s.getNbClausesSat()){
			best=s.clone();
		}
		return best;
	}

	//Méthode beeInit -problème 3SAT-
	public Solution beeInit(){
		Solution s=new Solution();
		short[] tab=new short[this.nbLitteraux];
		for(int i=0;i<this.nbLitteraux;i++){ 
			tab[i]=0;
		}

		Iterator<Clause> clauses=this.clauses.iterator();
		while(clauses.hasNext()){
			Clause tmp=clauses.next();
			if(tmp.getLitteraux()[0]>0){
				tab[tmp.getLitteraux()[0]-1]++;
			}
			else{
				tab[-1*tmp.getLitteraux()[0]-1]--;
			}
			if(tmp.getLitteraux()[1]>0){
				tab[tmp.getLitteraux()[1]-1]++;
			}
			else{
				tab[-1*tmp.getLitteraux()[1]-1]--;
			}
			if(tmp.getLitteraux()[2]>0){
				tab[tmp.getLitteraux()[2]-1]++;
			}
			else{
				tab[-1*tmp.getLitteraux()[2]-1]--;
			}
		}

		for(int i=0;i<this.nbLitteraux;i++){
			if(tab[i]<0){
				s.getLitteraux().add(i,(short)(-1*(i+1)));
			}
			else{
				s.getLitteraux().add(i,(short)(i+1));
			}

		}

		return s;
	}

	//Méthode de choix de la solution sRef de la méthode beeSwarmOptimisation
	public Solution choixSRef(ArrayList<Solution> LT,ArrayList<Solution> danse,Solution bestSRef,int nbChancesMax){
		//Initialisations
		int maxNbClausesSat=0;
		Iterator<Solution> danses=danse.iterator();
		Solution sRef=null;

		//Boucle de calcule de la meilleure solution (en qualité) de la table dance 
		//Tant qu'il reste des éléments dans la table danse
		while(danses.hasNext()){
			Solution tmp=danses.next();
			//System.out.println("Comparaison: "+tmp.getNbClausesSat());
			if(tmp.getNbClausesSat()>maxNbClausesSat && !LT.contains(tmp)){
				maxNbClausesSat=tmp.getNbClausesSat();
				sRef=tmp;
			}
		}

		if(sRef==null){
			return null;
		}

		//Calcule de la différence de qualité en la meilleur solution de la table dance et le précedent sRef
		int difference=sRef.getNbClausesSat()-bestSRef.getNbClausesSat();

		//Si la meilleure solution de la table danse est meilleure que le précédant sRef
		if(difference>0){	
			ClasseMain.setNbChances(nbChancesMax); 
		}
		else{
			//Décrémentation du nombre de chances
			ClasseMain.setNbChances(ClasseMain.getNbChances()-1);
			//Si le nombre de chances restant n'est pas nul
			if(ClasseMain.getNbChances()<=0){
				//Reset du nombre de chances au nombre max de chances
				ClasseMain.setNbChances(nbChancesMax); 

				//Calcule de la meilleure solution (non taboue) en diversité
				short bestDiversite=0;
				danses=danse.iterator();
				while(danses.hasNext()){
					Solution tmp=danses.next();
					tmp.setDiversite(LT);
					short tmpDiversite=tmp.getDiversite();
					if(tmpDiversite>bestDiversite&& !LT.contains(tmp)){
						bestDiversite=tmpDiversite;
						sRef=tmp;
					}
				}
			}
		}
		return sRef;

	}

	//BeeSwarmOptimisation
	public Solution BSO(int nbIterationsMax,int flip,int nbChancesMax){
		//Initialisation des variables
		Solution sRef=beeInit(),best=sRef.clone();
		ArrayList<Solution> listeTaboue=new ArrayList<Solution>();
		int tailleTaboue=this.nbLitteraux,indiceTaboue=0;
		
		//Tant que le nombre d'itérations maximal n'est pas atteint
		for(int nbIterations=0;nbIterations<nbIterationsMax && sRef.getNbClausesSat()<this.nbClauses;nbIterations++){
			//Si la taille de la liste taboue est attain, supprimer le plus ancien élément
			if(listeTaboue.size()>=tailleTaboue){
				listeTaboue.remove(indiceTaboue);
				//Inserer le sRef dans la liste taboue
				listeTaboue.add(indiceTaboue, sRef);
			}
			else{
				listeTaboue.add(sRef);
			}
			//Incrémenter l'indice de la liste taboue
			indiceTaboue=(indiceTaboue+1)%tailleTaboue;
			
			//Génération de la searchArea grace à la méthode flip
			Iterator<Solution> searchArea=sRef.flipPeriodique(flip).iterator();
			
			//Création de la table danse vide
			ArrayList<Solution> danse=new ArrayList<Solution>();
			
			//Parcourt de la searchArea
			while(searchArea.hasNext()){
				Solution tmp=searchArea.next();
				danse.add(tmp.rechercheVoisinage(listeTaboue,this,20));
			}
			
			//Choix du prochain sRef
			sRef=choixSRef(listeTaboue, danse, best, nbChancesMax);
			if(sRef==null){
				System.out.println("rien à faire toujours null...");
				return best;
			}
			
			//Mise à jour du best si elle a lieu d'être
			if(best.getNbClausesSat()<sRef.getNbClausesSat()){
				best=sRef;
				System.out.println("nouveau best "+best.getNbClausesSat());
			}
			
		}
		
		return best;
	}

}
