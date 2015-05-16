package dz.oooo.mh;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class ClasseMain {
	private static int nbChances;
	
	public static int getNbChances(){
		return ClasseMain.nbChances;
	}
	
	public static void setNbChances(int n){
		ClasseMain.nbChances=n;
	}

	public static void main(String[] args) {
		System.out.println("====== Bienvenue !======");
		System.out.println("========= Menu =========");
		System.out.println("1.Tester sur un fichier");
		System.out.println("2.Test rapide");
		System.out.println("3.Executer sur un benchmark");
		System.out.println("Faites votre choix: 1, 2, ou 3");
		Scanner sc=new Scanner(System.in);
		char choix=sc.nextLine().charAt(0);
		if(choix=='1'){
			System.out.println("Entrez le chemin relatif ou absolu du fichier");
			Formule f=new Formule(sc.nextLine());
			System.out.println("Le nombre de clauses de la formule est "+f.getNbClauses());
			System.out.println("Quel méthode voulez vous utliser :");
			System.out.println("1.Recherche locale");
			System.out.println("2.Recherche taboue");
			System.out.println("3.Optimisation par essaim d'abeilles");
			System.out.println("4.Les trois précédantes");
			System.out.println("Faites votres choix: 1, 2, 3, ou 4");
			choix=sc.nextLine().charAt(0);
			System.out.println("Donnez le nombre maximal d'itérations");
			int nbIterationsMax=sc.nextInt();
			sc.nextLine();
			switch(choix){
			case '1':
				long debutLocale=System.nanoTime();
				Solution sLocale=f.rechercheLocale(nbIterationsMax);
				long finLocale=System.nanoTime();
				System.out.println("La meilleure solution trouvée est \n"+sLocale.toString());
				System.out.println("Cette solution à été trouvée en "+((finLocale-debutLocale)/1000000000.0)+" secondes");
				break;
			case '2':
				long debutTaboue=System.nanoTime();
				Solution sTaboue=f.rechercheTaboue(nbIterationsMax);
				long finTaboue=System.nanoTime();
				System.out.println("La meilleure solution trouvée est \n"+sTaboue.toString());
				System.out.println("Cette solution à été trouvée en "+((finTaboue-debutTaboue)/1000000000.0)+" secondes");
				break;
			case '3':
				System.out.println("Donnez la valeur du flip");
				int flip=sc.nextInt();
				sc.nextLine();
				System.out.println("Donnez la valeur du nombre maimal de chances");
				int nbChancesMax=sc.nextInt();
				sc.nextLine();
				long debutBSO=System.nanoTime();
				Solution sBSO=f.beeSwarmOptimisation(nbIterationsMax,flip,nbChancesMax);
				long finBSO=System.nanoTime();
				System.out.println("La meilleure solution touvée est \n"+sBSO.toString());
				System.out.println("Cette solution à été trouvée en "+((finBSO-debutBSO)/1000000000.0)+" secondes");
				break;
			case '4':
				long debutLocale2=System.nanoTime();
				Solution sLocale2=f.rechercheLocale(nbIterationsMax);
				long finLocale2=System.nanoTime();
				long debutTaboue2=System.nanoTime();
				Solution sTaboue2=f.rechercheTaboue(nbIterationsMax);
				long finTaboue2=System.nanoTime();
				System.out.println("Donnez la valeur du flip");
				int flip2=sc.nextInt();
				sc.nextLine();
				System.out.println("Donnez la valeur du nombre maimal de chances");
				int nbChancesMax2=sc.nextInt();
				sc.nextLine();
				long debutBSO2=System.nanoTime();
				Solution sBSO2=f.beeSwarmOptimisation(nbIterationsMax,flip2,nbChancesMax2);
				long finBSO2=System.nanoTime();
				System.out.println("La meilleure solution trouvée en recherche locale est \n"+sLocale2.toString());
				System.out.println("Cette solution à été trouvée en "+((finLocale2-debutLocale2)/1000000000.0)+" secondes");
				System.out.println("La meilleure solution trouvée en recherche taboue est \n"+sTaboue2.toString());
				System.out.println("Cette solution à été trouvée en "+((finTaboue2-debutTaboue2)/1000000000.0)+" secondes");
				System.out.println("La meilleure solution touvée en optimisation par essaim d'abeilles est \n"+sBSO2.toString());
				System.out.println("Cette solution à été trouvée en "+((finBSO2-debutBSO2)/1000000000.0)+" secondes");
				break;
			}
		}
		else if(choix=='2'){
			int flip=4,nbChancesMax=3,nbIterationsMax=100;
			Formule f=new Formule("1");
			System.out.println("Test sur formule de "+f.getNbClauses());
			System.out.println("Nombre d'itérations maximum = "+nbIterationsMax);
			System.out.println("Nombre de chances maximum avant remontée de qualité = "+nbChancesMax);
			System.out.println("Flip = "+flip);
			long debutLocale2=System.nanoTime();
			Solution sLocale2=f.rechercheLocale(nbIterationsMax);
			long finLocale2=System.nanoTime();
			long debutTaboue2=System.nanoTime();
			Solution sTaboue2=f.rechercheTaboue(nbIterationsMax);
			long finTaboue2=System.nanoTime();
			long debutBSO2=System.nanoTime();
			Solution sBSO2=f.beeSwarmOptimisation(nbIterationsMax,flip,nbChancesMax);
			long finBSO2=System.nanoTime();
			System.out.println("La meilleure solution trouvée en recherche locale est \n"+sLocale2.toString());
			System.out.println("Cette solution à été trouvée en "+((finLocale2-debutLocale2)/1000000000.0)+" secondes");
			System.out.println("La meilleure solution trouvée en recherche taboue est \n"+sTaboue2.toString());
			System.out.println("Cette solution à été trouvée en "+((finTaboue2-debutTaboue2)/1000000000.0)+" secondes");
			System.out.println("La meilleure solution touvée en optimisation par essaim d'abeilles est \n"+sBSO2.toString());
			System.out.println("Cette solution à été trouvée en "+((finBSO2-debutBSO2)/1000000000.0)+" secondes");
		}
		else{
			try {
				PrintWriter output=new PrintWriter(new BufferedWriter(new FileWriter("fichierOutput.csv")));
				for(int i=1;i<=7;i++){
					Formule f=new Formule(String.valueOf(i));
					output.print(f.getNbClauses()+",");
					for(int nbIterationsMax=100;nbIterationsMax<100000;nbIterationsMax*=10){
						//Recherche Locale
						Solution best=new Solution();
						double moyTemps=0,moyQualite=0,tempsBest=0;
						for(int j=0;j<6;j++){
							long debut=System.nanoTime();
							Solution s=f.rechercheLocale(nbIterationsMax);
							long fin=System.nanoTime();
							double temps=(fin-debut)/1000000000.0;
							moyTemps+=temps;
							moyQualite+=(s.getNbClausesSat()/(double)f.getNbClauses());
							if(s.getNbClausesSat()>best.getNbClausesSat()){
								best=s;
								tempsBest=temps;
							}
						}
						moyTemps/=6;
						moyQualite/=6;
						output.print(moyTemps+","+moyQualite+",");
						output.print(tempsBest+","+(best.getNbClausesSat()/f.getNbClauses())+",");
						
						//Recherche taboue
						best=new Solution();
						tempsBest=0;
						moyQualite=0;
						moyTemps=0;
						for(int j=0;j<6;j++){
							long debut=System.nanoTime();
							Solution s=f.rechercheTaboue(nbIterationsMax);
							long fin=System.nanoTime();
							double temps=(fin-debut)/1000000000.0;
							moyTemps+=temps;
							moyQualite+=(s.getNbClausesSat()/(double)f.getNbClauses());
							if(s.getNbClausesSat()>best.getNbClausesSat()){
								best=s;
								tempsBest=temps;
							}
						}
						moyTemps/=6;
						moyQualite/=6;
						output.print(moyTemps+","+moyQualite+",");
						output.print(tempsBest+","+(best.getNbClausesSat()/f.getNbClauses())+",");
						
						//BSO
						best=new Solution();
						tempsBest=0;
						int bestFlip=0;
						int bestNbChancesMax=0;
						moyQualite=0;
						moyTemps=0;
						for(int flip=2;flip<=4;flip+=2){
							for(int nbChancesMax=2;nbChancesMax<5;nbChancesMax++){
								long debut=System.nanoTime();
								Solution s=f.beeSwarmOptimisation(nbIterationsMax, flip, nbChancesMax);
								long fin=System.nanoTime();
								double temps=(fin-debut)/1000000000.0;
								moyTemps+=temps;
								moyQualite+=(s.getNbClausesSat()/(double)f.getNbClauses());
								if(s.getNbClausesSat()>best.getNbClausesSat()){
									best=s;
									tempsBest=temps;
									bestFlip=flip;
									bestNbChancesMax=nbChancesMax;
								}
							}
						}
						moyTemps/=6;
						moyQualite/=6;
						output.print(moyTemps+","+moyQualite+",");
						output.print(tempsBest+","+(best.getNbClausesSat()/f.getNbClauses())+","+bestFlip+","+bestNbChancesMax+",");
					}
					output.print("\n");
				}
				output.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		sc.close();
	}

}
