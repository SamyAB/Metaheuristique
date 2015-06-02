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
				Solution sBSO=f.BSO(nbIterationsMax,flip,nbChancesMax);
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
				Solution sBSO2=f.BSO(nbIterationsMax, flip2, nbChancesMax2);
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
			Solution sBSO2=f.BSO(nbIterationsMax,flip,nbChancesMax);
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
				for(int fichier=1;fichier<=7;fichier++){
					Formule f=new Formule(String.valueOf(fichier));
					PrintWriter output=new PrintWriter(new BufferedWriter(new FileWriter("output"+fichier+".csv")));
					output.println("nombre d'itérations,temps recherche locale, qualité recherche locale, temps recherche taboue, qualité recherche taboue, temps BSO, qualité BSO");
					for(int nbi=5;nbi<=20;nbi+=5){
						output.print(nbi+",");
						Solution bestLocale=new Solution();
						double bestTempsL=0;
						Solution bestTaboue=new Solution();
						double bestTempsT=0;
						for(int i=0;i<=3;i++){
							long debutLocale=System.nanoTime();
							Solution tmpL=f.rechercheLocale(nbi);
							long finLocale=System.nanoTime();

							long debutTaboue=System.nanoTime();
							Solution tmpT=f.rechercheTaboue(nbi);
							long finTaboue=System.nanoTime();

							if(bestLocale.getNbClausesSat()<tmpL.getNbClausesSat()){
								bestLocale=tmpL;
								bestTempsL=(finLocale-debutLocale)/1000000000.0;
							}
							if(bestTaboue.getNbClausesSat()<tmpT.getNbClausesSat()){
								bestTaboue=tmpT;
								bestTempsT=(finTaboue-debutTaboue)/1000000000.0;
							}
						}
						output.print(bestTempsL+","+(bestLocale.getNbClausesSat()/(double)f.getNbClauses())+","+bestTempsT+","+(bestTaboue.getNbClausesSat()/(double)f.getNbClauses())+",");
						
						long debutBSO=System.nanoTime();
						Solution sBSO=f.BSO(nbi, 4, nbi/2);
						long finBSO=System.nanoTime();
						
						output.print((finBSO-debutBSO)/1000000000.0+","+(sBSO.getNbClausesSat()/(double)f.getNbClauses())+"\n");

					}
					output.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		sc.close();
		
	}

}
