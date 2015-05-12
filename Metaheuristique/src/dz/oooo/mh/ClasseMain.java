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
		Formule f=new Formule("/home/samy/Documents/TPM1S1/TPcomplexite/TP3bis/uf50-01.cnf");
		long debut=System.nanoTime();
		//System.out.println(f.toString());
		Solution s=f.rechercheTaboue();
		long fin=System.nanoTime();
		System.out.println(s.toString()+"\n "+((fin-debut)/1000000000.0));
	}

}
