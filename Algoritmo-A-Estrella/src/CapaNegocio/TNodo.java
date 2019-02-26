package CapaNegocio;

public class TNodo {
	private int indice;
	private boolean usado;
	private double h; 	// Formula h(n) = sqrt((x1-x2)^2+(y1-y2)^2)
	private double g;	// Formula g(n) = SUMA(h(n))
	private double f;	// Formula f(n) = h(n) + g(n)
	private Tipos tipo;
	private TNodo padre;
	private Coordenadas c;
	
	public TNodo(boolean usado, double h, double g, double f, Tipos tipo, Coordenadas c, int indice){
		this.usado = usado;
		this.h = h;
		this.g = g;
		this.f = f;
		this.tipo = Tipos.BUENO;
		this.c = c;
		this.indice = indice;
		this.padre = null;
	}

	public void calcularG(){
		this.g = 0;
		TNodo primero = this.padre;
		TNodo segundo = this;
		while(primero != null){
			
			Coordenadas izq = primero.getC();
			Coordenadas der = segundo.getC();
			this.g += calcularRaiz(izq, der);
			
			segundo = primero;
			primero = primero.getPadre();
		}
	}
	
	public void calcularH(Coordenadas mias, Coordenadas meta){
		this.h =calcularRaiz(mias, meta);
	}
	
	private double calcularRaiz(Coordenadas mias, Coordenadas meta){
		double suma = Math.pow((mias.getI()-meta.getI()), 2)+Math.pow((mias.getJ()-meta.getJ()), 2);
		double result = Math.sqrt(suma);
		return result;
	}
	public void calcularF(){
		this.f = this.h+this.g;
	}
	
	
	// Empiezan los getters y los setters
	public Coordenadas getC(){
		return this.c;
	}
	public void setC(Coordenadas c){
		this.c = c;
	}
	
	public TNodo getPadre(){
		return this.padre;
	}
	public void setPadre(TNodo padre){
		this.padre = padre;
	}
	
	public Tipos getTipo(){
		return this.tipo;
	}
	public void setTipo(Tipos tipo){
		this.tipo = tipo;
	}
	public boolean getUsado() {
		return this.usado;
	}

	public void setUsado(boolean usado) {
		this.usado = usado;
	}

	public double getH() {
		return this.h;
	}

	public void setH(double h) {
		this.h = h;
	}

	public double getG() {
		return this.g;
	}

	public void setG(double g) {
		this.g = g;
	}

	public double getF() {
		return this.f;
	}

	public void setF(double f) {
		this.f = f;
	}
	
	public int getIndice(){
		return this.indice;
	}
	
	public void setIndice(int indice){
		this.indice = indice;
	}
	// Acaban los setter y getters
	
	
	
	
}
