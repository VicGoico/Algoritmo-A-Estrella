package CapaNegocio;

public class TNodo {
	private boolean usado;
	private double h;
	private double g;
	private double distancia;
	
	public TNodo(boolean usado, double h, double g, double distancia){
		this.setUsado(usado);
		this.setH(h);
		this.setG(g);
		this.setDistancia(distancia);
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
		return this.distancia;
	}

	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}	
}
