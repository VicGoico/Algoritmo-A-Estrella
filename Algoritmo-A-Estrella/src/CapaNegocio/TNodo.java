package CapaNegocio;

public class TNodo {
	private boolean usado;
	private double h; 	// Formula h(n) = sqrt((x1+x2)^2+(y1+y2)^2)
	private double g;	// Formula g(n) = SUMA(h(n))
	private double f;	// Formula f(n) = h(n) + g(n)
	private String tipo;
	
	public TNodo(boolean usado, double h, double g, double f, String tipo){
		this.usado = usado;
		this.h = h;
		this.g = g;
		this.f = f;
		this.tipo = tipo;
	}

	// Empiezan los getters y los setters
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

	public double getDistancia() {
		return this.f;
	}

	public void setDistancia(double f) {
		this.f = f;
	}
	// Acaban los setter y getters
	
	
	
	
}
