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
				System.out.println("Donnez la valeur du nombre minimal de chances");
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
				for(int i=1;i<=5;i++){
					Formule f=new Formule(String.valueOf(i));
					//for(int nbIterationsMax=500;nbIterationsMax<=3000;nbIterationsMax+=500){
					for(int nbIterationsMax=100;nbIterationsMax<=200;nbIterationsMax+=50){
						/*PrintWriter output=new PrintWriter(new BufferedWriter(new FileWriter("outputLocalFichier"+i+"nbI"+nbIterationsMax+".csv")));
						output.print(f.getNbClauses()+",");
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
						output.close();
						
						output=new PrintWriter(new BufferedWriter(new FileWriter("outputTaboueFichier"+i+"nbI"+nbIterationsMax+".csv")));
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
						output.close();
						*/
						//BSO
						PrintWriter output=new PrintWriter(new BufferedWriter(new FileWriter("outputBSOLocalFichier"+i+"nbI"+nbIterationsMax+".csv")));
						Solution best=new Solution();
						double tempsBest=0;
						int bestFlip=0;
						int bestNbChancesMax=0;
						double moyQualite=0;
						double moyTemps=0;
						for(int flip=2;flip<=4;flip+=2){
							for(int nbChancesMax=0;nbChancesMax<100;nbChancesMax+=20){
								long debut=System.nanoTime();
								Solution s=f.beeSwarmOptimisation(nbIterationsMax, flip, nbChancesMax);
								long fin=System.nanoTime();
								double temps=(fin-debut)/1000000000.0;
								moyTemps+=temps;
								output.print(temps+","+s.getNbClausesSat()+",");
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
						output.close();
					}
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		sc.close();
		/*Solution s=new Solution();
		short[] sh={1, 2, -3, -4, -5, -6, -7, -8, 9, 10, -11, 12, -13, -14, -15, 16, 17, 18, -19, 20, 21, 22, 23, -24, 25, 26, 27, -28, -29, -30, 31, 32, 33, 34, -35, -36, 37, 38, 39, 40, 41, -42, -43, 44, 45, 46, -47, 48, 49, 50, 51, 52, -53, 54, -55, 56, -57, 58, -59, -60, -61, -62, 63, 64, -65, 66, -67, -68, 69, -70, 71, -72, 73, 74, 75, -76, 77, 78, 79, 80, 81, -82, -83, -84, -85, 86, 87, 88, -89, 90, -91, -92, -93, 94, -95, -96, -97, 98, 99, -100
};
		for(int i=0;i<sh.length;i++){
			s.getLitteraux().add(sh[i]);
		}
		
		Formule f=new Formule("1");
		
		s.setNbClausesSat(f);
		System.out.println("le nombre de clauses Sat "+s.getNbClausesSat());
		*/
	}

}
