package dz.oooo.mh;

public class ClasseMain {
	private static int nombreIteration=100000;
	
	public static int getNombreIteration() {
		return nombreIteration;
	}

	public static void setNombreIteration(int nombreIteration) {
		ClasseMain.nombreIteration = nombreIteration;
	}

	public static void main(String[] args) {
		Formule f=new Formule("/mnt/Doc1/MEGAsync/m1s2/mh/Projet/hakubi.txt");
		System.out.println(f.toString());
		Solution s=f.rechercheLocale();
		System.out.println(s.toString());
	}

}
