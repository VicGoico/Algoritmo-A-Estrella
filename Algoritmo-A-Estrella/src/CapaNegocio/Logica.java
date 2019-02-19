package CapaNegocio;

import java.util.ArrayList;

public class Logica {
	private int dimensionN;
	private int dimensionM;
	private TNodo[][] matriz;
	private ArrayList<TNodo> abierta;
	private ArrayList<TNodo> cerrada;
	private Coordenadas meta;
	
	public Logica(int n, int m){
		// Inicializo los atributos 
		this.dimensionM = m;
		this.dimensionN = n;
		this.abierta = new ArrayList<>();
		this.cerrada = new ArrayList<>();
		this.matriz = new TNodo[n][m];
		
		for(int i = 0; i < n; i++){
			for(int j = 0;j < m; j++){
				this.matriz[i][j] = new TNodo(false, -1, -1, -1);
			}
		}
		this.generarTableroAleatoriamente();
	}
	
	public void generarTableroAleatoriamente(){
		
	}
	
	
}
