package CapaNegocio;

// Clase que se guarda las coordenadas de un TNodo que esta dentro de la matriz
public class Coordenadas {
	private int i;
	private int j;
	
	public Coordenadas(int i, int j){
		this.i = i;
		this.j = j;
	}
	public int getI(){
		return this.i;
	}
	public int getJ(){
		return this.j;
	}
	public void setI(int i){
		this.i = i;
	}
	public void setJ(int j){
		this.j = j;
	}
}
