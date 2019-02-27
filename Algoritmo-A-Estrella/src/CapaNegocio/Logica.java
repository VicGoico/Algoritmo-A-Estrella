package CapaNegocio;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Logica {
	private int dimensionN;
	private int dimensionM;
	private TNodo[][] matriz;
	private ArrayList<TNodo> cerrada;
	private Coordenadas meta;
	private Coordenadas inicio;
	private Coordenadas actual;
	private boolean noPudo;
	
	
	// Tiene que ordenar en funcion de la f() de menor a mayor
	private PriorityQueue <TNodo> abierta;
	
	private ArrayList<Integer> listaCamino;
	private ArrayList<Integer> listaMetas;
	
	public Logica(int n, int m, int Inicio, ArrayList<Integer> Meta, ArrayList<Integer> listaBloqueos){
		// Inicializo los atributos 
		this.dimensionM = m;
		this.dimensionN = n;
		this.noPudo = false;
		this.listaCamino = new ArrayList<>();
		this.listaMetas = new ArrayList<>();
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
		this.inicio = new Coordenadas(0,0);
		this.meta = new Coordenadas(1,3);
		
		
		int indice = 0;
		
		// Falta poner zonas de paso: prohibidas, con penalizacion, etc
		for(int i = 0; i < n; i++){
			for(int j = 0;j < m; j++){
				boolean encontrado = false;
				Coordenadas c = new Coordenadas(i,j);
				TNodo nodo = new TNodo(false, 0, 0, 0, Tipos.BUENO, c, indice);
				if(indice == Inicio){
					this.inicio = nodo.getC();
				}
				else{
					for(int k = 0; k < listaBloqueos.size() && !encontrado; k++){
						if(indice == listaBloqueos.get(k)){
							encontrado = true;
							nodo.setTipo(Tipos.PROHIBIDO);
						}
					}
				}
				indice++;
				this.matriz[i][j] = nodo;				
			}
		}
		this.listaMetas = Meta;
		
		// aqui es donde va la chicha
		while(!this.listaMetas.isEmpty()){
			
		}
		// Asignar valores		
		this.calcularCamino();
		
		if (!this.noPudo) {
			int i = 1;
			System.out.println("Pintamos el camino para llegar");
			TNodo nod = this.matriz[this.actual.getI()][this.actual.getJ()];
			System.out.println("Coordenadas: " + i + " I: " + this.actual.getI() + " J: " + this.actual.getJ());
			this.listaCamino.add(nod.getIndice());
			i++;

			boolean cambiar = true;
			boolean salir = false;
			while (!salir) {
				if (cambiar) {
					this.actual.setI(nod.getPadre().getC().getI());
					this.actual.setJ(nod.getPadre().getC().getJ());
					System.out.println("Coordenadas: " + i + " I: " + this.actual.getI() + " J: " + this.actual.getJ());
					this.listaCamino.add(this.matriz[this.actual.getI()][this.actual.getJ()].getIndice());
					nod = this.matriz[this.actual.getI()][this.actual.getJ()].getPadre();
					if (nod == null) {
						salir = true;
					}
					cambiar = false;
				} else {
					cambiar = true;
					this.listaCamino.add(nod.getIndice());
					System.out.println("Coordenadas: " + i + " I: " + nod.getC().getI() + " J: " + nod.getC().getJ());
					if (nod.getPadre() == null) {
						salir = true;
					}
				}
				i++;
			}
		}
		System.out.println("FIN del PROGRAMA");
	}
	
	public void generarTableroAleatoriamente(){
		/*this.inicio.setI((int) (Math.random()*this.dimensionN));
		this.inicio.setJ((int) (Math.random()*this.dimensionM));
		System.out.println("Inicio: "+this.inicio.getI()+ " " + this.inicio.getJ());
		
		
		this.meta.setI((int) (Math.random()*this.dimensionN));
		this.meta.setJ((int) (Math.random()*this.dimensionM));
		// Para que no salga un mismo punto de Inicio y de Meta
		while((this.meta.getI()!=this.inicio.getI()) && (this.meta.getJ()!= this.inicio.getJ())){
			this.meta.setI((int) (Math.random()*this.dimensionN));
			this.meta.setJ((int) (Math.random()*this.dimensionM));
		}*/
		TNodo aux = this.matriz[this.inicio.getI()][this.inicio.getJ()];
		System.out.println("Meta: "+this.meta.getI()+ " " + this.meta.getJ());
		
		
		/*double suma = Math.pow((this.inicio.getI()-meta.getI()), 2)+Math.pow((inicio.getJ()-meta.getJ()), 2);
		double sol = Math.sqrt(suma);
		System.out.println("Solucion: " + sol);*/
		aux.calcularH(this.inicio, this.meta);
		aux.setG(0);
		aux.calcularF();
		
		// Ya generados los puntos de Inicio y Meta		
	}
	public void calcularCamino(){
		boolean salir = false;
		Coordenadas aux = new Coordenadas(0,0);
		TNodo coge;
		
		// Inicializo la lista
		this.actual = this.inicio;
		this.cerrada.add(this.matriz[this.actual.getI()][this.actual.getJ()]);
		this.matriz[this.actual.getI()][this.actual.getJ()].setUsado(true);
		
		while (!salir) {
			for (int i = 0; i < 8; i++) {
				if (i == 0) {// Arriba a la izquierda
					aux.setI(this.actual.getI() - 1);
					aux.setJ(this.actual.getJ() - 1);
					if (aux.getI() >= 0 && aux.getJ() >= 0) {// Esta bien
												
						coge = this.matriz[aux.getI()][aux.getJ()];
						hacerLoMismo(coge, aux, true);
					}
				} else if (i == 1) {// Arriba
					aux.setI(this.actual.getI() - 1);
					aux.setJ(this.actual.getJ());
					if (aux.getI() >= 0) {// Esta bien
						coge = this.matriz[aux.getI()][aux.getJ()];
						hacerLoMismo(coge, aux, false);
					}
				} else if (i == 2) {// Arriba a la derecha
					aux.setI(this.actual.getI() - 1);
					aux.setJ(this.actual.getJ() + 1);
					if (aux.getI() >= 0 && aux.getJ() < this.dimensionM) {// Esta
																			// bien
						coge = this.matriz[aux.getI()][aux.getJ()];
						hacerLoMismo(coge, aux, true);
					}
				} else if (i == 3) {// Izquierda
					aux.setI(this.actual.getI());
					aux.setJ(this.actual.getJ() - 1);
					if (aux.getJ() >= 0) {// Esta bien
						coge = this.matriz[aux.getI()][aux.getJ()];
						hacerLoMismo(coge, aux, false);
					}
				} else if (i == 4) {// Derecha
					aux.setI(this.actual.getI());
					aux.setJ(this.actual.getJ() + 1);
					if (aux.getJ() < this.dimensionM) {// Esta bien
						coge = this.matriz[aux.getI()][aux.getJ()];
						hacerLoMismo(coge, aux, false);
					}
				} else if (i == 5) {// Izquierda inferior
					aux.setI(this.actual.getI() + 1);
					aux.setJ(this.actual.getJ() - 1);
					if (aux.getI() < this.dimensionN && aux.getJ() >= 0) {// Esta
																			// bien
						coge = this.matriz[aux.getI()][aux.getJ()];
						hacerLoMismo(coge, aux, true);
					}
				} else if (i == 6) {// Abajo
					aux.setI(this.actual.getI() + 1);
					aux.setJ(this.actual.getJ());
					if (aux.getI() < this.dimensionN) {// Esta bien
						coge = this.matriz[aux.getI()][aux.getJ()];
						hacerLoMismo(coge, aux, false);
					}
				} else if (i == 7) {// Derecha inferior
					aux.setI(this.actual.getI() + 1);
					aux.setJ(this.actual.getJ() + 1);
					if (aux.getI() < this.dimensionN && aux.getJ() < this.dimensionM) {// Esta
																						// bien
						coge = this.matriz[aux.getI()][aux.getJ()];
						hacerLoMismo(coge, aux, true);
					}
				}
			}
			
			// Comprobaciones
			if(!this.abierta.isEmpty()){
				coge = this.abierta.peek();
				System.out.println("Coordenadas " + coge.getC().getI() + " " + coge.getC().getJ());
				
				// Compruebo que no he llegado a la meta
				if (coge.getC().getI() == this.meta.getI() && coge.getC().getJ() == this.meta.getJ()) {
					salir = true;
				}

				// Elegir el siguiente nodo a tratar
				this.actual = coge.getC();
				this.abierta.poll();
				this.cerrada.add(coge);
			}
			else{
				if(this.abierta.isEmpty() && this.actual.getI() != this.meta.getI() && this.actual.getJ() != this.meta.getJ()){
					salir = true;
					this.noPudo = true;
				}
			}	
		}
	}
	public void hacerLoMismo(TNodo coge, Coordenadas aux, boolean sumar){
		// No tenemos que haber pasado por ahi
		if(!this.matriz[aux.getI()][aux.getJ()].getUsado() && this.matriz[aux.getI()][aux.getJ()].getTipo() != Tipos.PROHIBIDO){
			this.matriz[aux.getI()][aux.getJ()].setUsado(true);
			TNodo padre = this.matriz[this.actual.getI()][this.actual.getJ()];
			this.matriz[aux.getI()][aux.getJ()].setPadre(padre);
			this.matriz[aux.getI()][aux.getJ()].calcularH(aux, this.meta);
			this.matriz[aux.getI()][aux.getJ()].calcularG();
			this.matriz[aux.getI()][aux.getJ()].calcularF();
			if(sumar){// raiz de 2
				this.matriz[aux.getI()][aux.getJ()].setDistancia(Math.sqrt(2)+padre.getDistancia());
			}
			else{// 1
				this.matriz[aux.getI()][aux.getJ()].setDistancia(1+padre.getDistancia());
			}
			this.abierta.add(this.matriz[aux.getI()][aux.getJ()]);
		}
		// Revisar
		/*else if(this.matriz[aux.getI()][aux.getJ()].getUsado()){
			TNodo antiguo = this.matriz[aux.getI()][aux.getJ()];
			TNodo padre = this.matriz[this.actual.getI()][this.actual.getJ()];
			this.matriz[aux.getI()][aux.getJ()].setPadre(padre);
			if(sumar){// raiz de 2
				this.matriz[aux.getI()][aux.getJ()].setDistancia(Math.sqrt(2)+padre.getDistancia());
			}
			else{// 1
				this.matriz[aux.getI()][aux.getJ()].setDistancia(1+padre.getDistancia());
			}
			if(antiguo.getDistancia()<this.matriz[aux.getI()][aux.getJ()].getDistancia()){
				this.matriz[aux.getI()][aux.getJ()] = antiguo;
			}
		}*/
	}
	
	public TNodo[][] getMatriz(){
		return this.matriz;
	}
	public TNodo getTNodoEspecifico(int i, int j){
		return this.matriz[i][j];
	}
	public ArrayList<Integer> getListaCamino(){
		return this.listaCamino;
	}
	
}
