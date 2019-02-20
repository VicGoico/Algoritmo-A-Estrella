package CapaNegocio;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Logica {
	private int dimensionN;
	private int dimensionM;
	private TNodo[][] matriz;
	//private ArrayList<TNodo> abierta;
	private ArrayList<TNodo> cerrada;
	private Coordenadas meta;
	private Coordenadas inicio;
	private Coordenadas actual;
	
	// Tiene que ordenar en funcion de la f() de menor a mayor
	private PriorityQueue <TNodo> abierta;
	
	public Logica(int n, int m){
		// Inicializo los atributos 
		this.dimensionM = m;
		this.dimensionN = n;
		Comparator<TNodo> comparardor = new Comparator<TNodo>() {

			@Override
			public int compare(TNodo o1, TNodo o2) {
				if(o1.getF() < o2.getF()){
					return 0;// A lo mejor -1
				}
				return 1;
			}
			
		};
		this.abierta = new PriorityQueue<>(comparardor);
		this.cerrada = new ArrayList<>();
		this.matriz = new TNodo[n][m];
		
		this.generarTableroAleatoriamente();
		// Falta poner zonas de paso: prohibidas, con penalizacion, etc
		for(int i = 0; i < n; i++){
			for(int j = 0;j < m; j++){
				Coordenadas c = new Coordenadas(i,j);
				TNodo nodo = new TNodo(false, 0, 0, 0, "Normal", c);
				
				this.matriz[i][j] = nodo;
				
			}
		}
		
		this.calcularCamino();
	}
	
	public void generarTableroAleatoriamente(){
		int tam = this.dimensionM*this.dimensionN;
		this.inicio.setI((int) (Math.random()*tam));
		this.inicio.setJ((int) (Math.random()*tam));
		TNodo aux = this.matriz[this.inicio.getI()][this.inicio.getJ()];
		aux.calcularH(this.inicio, this.meta);
		aux.calcularG();
		aux.calcularF();
		
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
		boolean salir = false;
		Coordenadas aux = new Coordenadas(0,0);
		TNodo coge;
		this.actual = this.inicio;
		
		this.abierta.add(this.matriz[this.actual.getI()][this.actual.getJ()]);	
		this.cerrada.add(this.abierta.peek());
		this.abierta.poll();
		
		while(this.actual != this.meta && !salir){
			// Miro que este usado o no, si esta usado es que ya esta en la lista cerrada
			if(!this.matriz[this.actual.getI()][this.actual.getJ()].getUsado()){
				this.matriz[this.actual.getI()][this.actual.getJ()].setUsado(true);
				for(int i= 0; i < 8; i++){
					if(i == 0){// Arriba a la izquierda
						aux.setI(this.actual.getI()-1);
						aux.setJ(this.actual.getJ()-1);
						if(aux.getI() >= 0 && aux.getJ() >= 0){// Esta bien
							hacerLoMismo(this.matriz[aux.getI()][aux.getJ()], aux);
						}
					}
					else if(i == 1){// Arriba 
						aux.setI(this.actual.getI()-1);
						aux.setJ(this.actual.getJ());
						if(aux.getI() >= 0){// Esta bien
							hacerLoMismo(this.matriz[aux.getI()][aux.getJ()], aux);
						}
					}
					else if(i == 2){// Arriba a la derecha
						aux.setI(this.actual.getI()-1);
						aux.setJ(this.actual.getJ()+1);
						if(aux.getI() >= 0 && aux.getJ()<this.dimensionM){// Esta bien
							hacerLoMismo(this.matriz[aux.getI()][aux.getJ()], aux);
						}
					}
					else if(i == 3){// Izquierda
						aux.setI(this.actual.getI());
						aux.setJ(this.actual.getJ()-1);
						if(aux.getJ() >= 0){// Esta bien
							hacerLoMismo(this.matriz[aux.getI()][aux.getJ()], aux);
						}
					}
					else if(i == 4){// Derecha
						aux.setI(this.actual.getI());
						aux.setJ(this.actual.getJ()+1);
						if(aux.getJ() < this.dimensionM){// Esta bien
							hacerLoMismo(this.matriz[aux.getI()][aux.getJ()], aux);
						}
					}
					else if(i == 5){// Izquierda inferior
						aux.setI(this.actual.getI()+1);
						aux.setJ(this.actual.getJ()-1);
						if(aux.getI() < this.dimensionN && aux.getJ() >= 0){// Esta bien
							hacerLoMismo(this.matriz[aux.getI()][aux.getJ()], aux);
						}
					}
					else if(i == 6){// Abajo
						aux.setI(this.actual.getI()+1);
						aux.setJ(this.actual.getJ());
						if(aux.getI() < this.dimensionN){// Esta bien
							hacerLoMismo(this.matriz[aux.getI()][aux.getJ()], aux);
						}
					}
					else if(i == 7){// Derecha inferior
						aux.setI(this.actual.getI()+1);
						aux.setJ(this.actual.getJ()+1);
						if(aux.getI() < this.dimensionN && aux.getJ() < this.dimensionM){// Esta bien
							hacerLoMismo(this.matriz[aux.getI()][aux.getJ()], aux);
						}
					}
				}
			}
			// Elegir el siguiente nodo a tratar
			this.actual = this.abierta.peek().getC();
			coge = this.abierta.peek();
			this.abierta.poll();
			this.cerrada.add(coge);
		}
	}
	public void hacerLoMismo(TNodo coge, Coordenadas aux){
		// No tenemos que haber pasado por ahi
		if(!coge.getUsado()){
			coge.setUsado(true);
			coge.setPadre(this.matriz[this.actual.getI()][this.actual.getJ()]);
			coge.calcularH(aux, this.meta);
			coge.calcularG();
			coge.calcularF();
			this.abierta.add(coge);
		}
	}
}
