package CapaNegocio;

import java.util.ArrayList;
import java.util.Random;

public class Logica {
	private int dimensionN;
	private int dimensionM;
	private TNodo[][] matriz;
	private ArrayList<TNodo> abierta;
	private ArrayList<TNodo> cerrada;
	private Coordenadas meta;
	private Coordenadas inicio;
	private Coordenadas actual;
	
	public Logica(int n, int m){
		// Inicializo los atributos 
		this.dimensionM = m;
		this.dimensionN = n;
		this.abierta = new ArrayList<>();
		this.cerrada = new ArrayList<>();
		this.matriz = new TNodo[n][m];
		
		for(int i = 0; i < n; i++){
			for(int j = 0;j < m; j++){
				this.matriz[i][j] = new TNodo(false, -1, -1, -1, "Normal");
			}
		}
		this.generarTableroAleatoriamente();
		this.calcularCamino();
	}
	
	public void generarTableroAleatoriamente(){
		int tam = this.dimensionM*this.dimensionN;
		this.inicio.setI((int) (Math.random()*tam));
		this.inicio.setJ((int) (Math.random()*tam));
		this.meta.setI((int) (Math.random()*tam));
		this.meta.setJ((int) (Math.random()*tam));
		// Para que no salga un mismo punto de Inicio y de Meta
		while((this.meta.getI()!=this.inicio.getI()) && (this.meta.getJ()!= this.inicio.getJ())){
			this.meta.setI((int) (Math.random()*tam));
			this.meta.setJ((int) (Math.random()*tam));
		}
		// Ya generados los puntos de Inicio y Meta		
	}
	public void calcularCamino(){
		int posI, posJ;
		boolean salir = false;
		this.actual = this.inicio;
	
		while(this.actual != this.meta && !salir){
			
			for(int i= 0; i < 8; i++){
				if(i == 0){// Arriba a la izquierda
					posI = this.actual.getI()-1;
					posJ = this.actual.getJ()-1;
					if(posI >= 0){// Esta bien
						
					}
				}
				
			}
		}
	}
	
	
}
