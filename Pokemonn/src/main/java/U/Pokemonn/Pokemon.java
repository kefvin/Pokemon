package U.Pokemonn;

public class Pokemon {
	
	double salut;
	String nom;
	int pes;
	int atac;
	int tipus;
	
	public Pokemon(String anom, int apes, int aatac, int atipus){
		
		salut = 200;
		nom = anom;
		pes = apes;
		atac = aatac;
		tipus = atipus;		
	}
	
	
	public double debilita(double ataque){
		salut = salut - ataque;
		return salut;
	}
	
	public double getSalut(){
		return salut;
	}
	
	public int golpeCuerpo(){
		return atac;
	}
	
	public int velocidad(){
		return pes;
	}
	
	public int getTipo(){
		return tipus;
	}
	
	public String nombre(){
		return nom;
	}
	
}
