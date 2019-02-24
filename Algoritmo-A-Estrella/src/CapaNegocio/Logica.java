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
		this.inicio = new Coordenadas(0,0);
		this.meta = new Coordenadas(1,1);
		
		
		
		// Falta poner zonas de paso: prohibidas, con penalizacion, etc
		for(int i = 0; i < n; i++){
			for(int j = 0;j < m; j++){
				Coordenadas c = new Coordenadas(i,j);
				TNodo nodo = new TNodo(false, 0, 0, 0, "Normal", c);
				
				this.matriz[i][j] = nodo;				
			}
		}
		this.generarTableroAleatoriamente();
		this.calcularCamino();
		int i = 0;
		System.out.println("Pintamos el camino para llegar");
		TNodo nod = this.matriz[this.actual.getI()][this.actual.getJ()];
		while(nod.getPadre() != null){
			nod = this.matriz[this.actual.getI()][this.actual.getJ()].getPadre();
			System.out.println("Coordenadas: "+ i +  " I: "+ this.actual.getI()  +" J: " + this.actual.getJ());
			if(nod.getPadre() == null){
				System.out.println("NULLLLLL");
			}
			this.actual.setI(nod.getPadre().getC().getI());
			this.actual.setJ(nod.getPadre().getC().getJ());
			i++;
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
		this.actual = this.inicio;
		
		this.abierta.add(this.matriz[this.actual.getI()][this.actual.getJ()]);	
		this.cerrada.add(this.abierta.peek());
		this.abierta.poll();
		
		while(!salir){
			// Miro que este usado o no, si esta usado es que ya esta en la lista cerrada
			if(!this.matriz[this.actual.getI()][this.actual.getJ()].getUsado()){
				for(int i= 0; i < 8; i++){
					if(i == 0){// Arriba a la izquierda
						aux.setI(this.actual.getI()-1);
						aux.setJ(this.actual.getJ()-1);
						if(aux.getI() >= 0 && aux.getJ() >= 0){// Esta bien
							coge = this.matriz[aux.getI()][aux.getJ()];
							hacerLoMismo(coge, aux);
						}
					}
					else if(i == 1){// Arriba 
						aux.setI(this.actual.getI()-1);
						aux.setJ(this.actual.getJ());
						if(aux.getI() >= 0){// Esta bien
							coge = this.matriz[aux.getI()][aux.getJ()];
							hacerLoMismo(coge, aux);
						}
					}
					else if(i == 2){// Arriba a la derecha
						aux.setI(this.actual.getI()-1);
						aux.setJ(this.actual.getJ()+1);
						if(aux.getI() >= 0 && aux.getJ()<this.dimensionM){// Esta bien
							coge = this.matriz[aux.getI()][aux.getJ()];
							hacerLoMismo(coge, aux);
						}
					}
					else if(i == 3){// Izquierda
						aux.setI(this.actual.getI());
						aux.setJ(this.actual.getJ()-1);
						if(aux.getJ() >= 0){// Esta bien
							coge = this.matriz[aux.getI()][aux.getJ()];
							hacerLoMismo(coge, aux);
						}
					}
					else if(i == 4){// Derecha
						aux.setI(this.actual.getI());
						aux.setJ(this.actual.getJ()+1);
						if(aux.getJ() < this.dimensionM){// Esta bien
							coge = this.matriz[aux.getI()][aux.getJ()];
							hacerLoMismo(coge, aux);
							if(this.matriz[0][1].getPadre() == null){
								System.out.println("Es null el padre");
							}
							System.out.println("Padre: I: " + this.matriz[0][1].getPadre().getC().getI()+ " J: "+this.matriz[0][1].getPadre().getC().getI());
						}
					}
					else if(i == 5){// Izquierda inferior
						aux.setI(this.actual.getI()+1);
						aux.setJ(this.actual.getJ()-1);
						if(aux.getI() < this.dimensionN && aux.getJ() >= 0){// Esta bien
							coge = this.matriz[aux.getI()][aux.getJ()];
							hacerLoMismo(coge, aux);
						}
					}
					else if(i == 6){// Abajo
						/*if(this.actual.getI() == 3 && this.actual.getJ() == 4){
							System.out.println("para");
						}*/
						aux.setI(this.actual.getI()+1);
						aux.setJ(this.actual.getJ());
						if(aux.getI() < this.dimensionN){// Esta bien
							coge = this.matriz[aux.getI()][aux.getJ()];
							hacerLoMismo(coge, aux);
						}
					}
					else if(i == 7){// Derecha inferior
						aux.setI(this.actual.getI()+1);
						aux.setJ(this.actual.getJ()+1);
						if(aux.getI() < this.dimensionN && aux.getJ() < this.dimensionM){// Esta bien
							coge = this.matriz[aux.getI()][aux.getJ()];
							hacerLoMismo(coge, aux);
						}
					}
				}
				
			}
			coge = this.abierta.peek();
			System.out.println("Coordenadas "+coge.getC().getI()+" "+ coge.getC().getJ());
			//
			//System.out.println("Para");
			
			
			// Compruebo que no he llegado a la meta
			if(coge.getC().getI() == this.meta.getI() && coge.getC().getJ() == this.meta.getJ()){
				salir = true;
			}
			
			// Elegir el siguiente nodo a tratar
			this.actual = coge.getC();
			this.abierta.poll();
			this.cerrada.add(coge);
			this.matriz[this.actual.getI()][this.actual.getJ()].setUsado(true);
			
			
		}
		
	}
	public void hacerLoMismo(TNodo coge, Coordenadas aux){
		// No tenemos que haber pasado por ahi
		if(!this.matriz[aux.getI()][aux.getJ()].getUsado()){
			//coge.setUsado(true);
			TNodo padre = this.matriz[this.actual.getI()][this.actual.getJ()];
			this.matriz[aux.getI()][aux.getJ()].setPadre(padre);
			System.out.println("Coordenadas dentro al cambiar de padre: I: "+aux.getI()+ " J: "+aux.getJ());
			//System.out.println("Padre: I: " + this.matriz[aux.getI()][aux.getJ()].getPadre().getC().getI()+ " J: "+this.matriz[aux.getI()][aux.getJ()].getPadre().getC().getI());
			// Importante es lo que guarda el padre del nodo
			// this.matriz[coge.getC().getI()][coge.getC().getJ()].setPadre(this.matriz[this.actual.getI()][this.actual.getJ()]);	
			this.matriz[aux.getI()][aux.getJ()].calcularH(aux, this.meta);
			this.matriz[aux.getI()][aux.getJ()].calcularG();
			this.matriz[aux.getI()][aux.getJ()].calcularF();
			this.abierta.add(this.matriz[aux.getI()][aux.getJ()]);
		}
	}
}
