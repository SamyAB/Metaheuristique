package dz.oooo.mh;

public class Clause {
	private short[] litteraux;
	
	public Clause(){
		this.litteraux=new short[3];
	}
	
	public Clause(short l1,short l2,short l3){
		this();
		this.litteraux[0]=l1;
		this.litteraux[1]=l2;
		this.litteraux[2]=l3;
	}

	public short[] getLitteraux() {
		return litteraux;
	}

	public void setLitteraux(short[] litteraux) {
		this.litteraux = litteraux;
	}
	
	public boolean contains(short lit){
		for(int i=0;i<3;i++){
			if(lit==this.litteraux[i]){
				return true;
			}
		}
		return false;
	}
}
