package CapaNegocio;

public class TNodo {
	private boolean usado;
	private double h; 	// Formula h(n) = sqrt((x1-x2)^2+(y1-y2)^2)
	private double g;	// Formula g(n) = SUMA(h(n))
	private double f;	// Formula f(n) = h(n) + g(n)
	private String tipo;
	private TNodo padre;
	private Coordenadas c;
	
	public TNodo(boolean usado, double h, double g, double f, String tipo, Coordenadas c){
		this.usado = usado;
		this.h = h;
		this.g = g;
		this.f = f;
		this.tipo = tipo;
		this.c = c;
		this.padre = null;
	}

	public void calcularG(){
		this.g = this.h;
		TNodo save = this.padre;
		while(this.padre != null){
			this.g += this.padre.h; 
			this.padre = this.padre.getPadre();
		}
		this.padre = save;
	}
	public void calcularH(Coordenadas mias, Coordenadas meta){
		double suma = Math.pow((mias.getI()-meta.getI()), 2)+Math.pow((mias.getJ()-meta.getJ()), 2);
		this.h = Math.sqrt(suma);
		
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
	
	public String getTipo(){
		return this.tipo;
	}
	public void setTipo(String tipo){
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
	// Acaban los setter y getters
	
	
	
	
}
