package CapaNegocio;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Logica {
	private int dimensionN;
	private int dimensionM;
	private TNodo[][] matriz;
	
	private Coordenadas meta;
	private Coordenadas inicio;
	private Coordenadas actual;
	private boolean noPudo;
	private boolean finBucle;
	private double valorPenalizacion;
	private double valorPenalizacionMin;
	
	// Tiene que ordenar en funcion de la f() de menor a mayor
	private PriorityQueue <TNodo> abierta;
	private ArrayList<TNodo> cerrada;
	private ArrayList<Integer> listaCamino;
	
	
	public Logica(int n, int m, int Inicio, ArrayList<Integer> listaMetas, ArrayList<Integer> listaBloqueos, ArrayList<Integer> listaPenalizaciones){
		this.dimensionM = m;
		this.dimensionN = n;
		this.noPudo = false;
		this.finBucle = false;
		this.valorPenalizacion = Math.pow(this.dimensionN, 2)+Math.pow(this.dimensionM, 2);
		this.valorPenalizacion *= 0.7;
		this.valorPenalizacionMin = this.valorPenalizacion*0.1;
		this.listaCamino = new ArrayList<>();
		
		
		inicializar(Inicio, listaBloqueos, listaPenalizaciones);	
		// Inicializo la meta
		int siguienteMeta = 0;
		
		// Esto es en el caso de que solo hubiera una meta
		int indiceDeMeta = listaMetas.get(siguienteMeta);
		for(int i = 0; i < this.dimensionN; i++){
			for(int j = 0; j < this.dimensionM; j++){
				if(indiceDeMeta == this.matriz[i][j].getIndice()){
					this.meta = this.matriz[i][j].getC();
				}
			}
		}
		// Asignar valores		
		this.calcularCamino();
		cogerCamino();
		if(listaMetas.isEmpty()){
			this.noPudo = true;
		}
		
		// Aqui es donde nos recorremos la ListaDeMetas
		while(!this.noPudo && !listaMetas.isEmpty() && !this.finBucle){	
			// Me guardo el Inicio del siguiente camino
			Inicio = listaMetas.get(siguienteMeta);
			// Borra la meta antigua de la lista ya que la hemos pasado como inicio
			listaMetas.remove(siguienteMeta);
			// Inicializo otra vez toda la matriz para volver a calcular todos los posibles caminos
			inicializar(Inicio, listaBloqueos, listaPenalizaciones);
			// Compruebo que aun quedan metas o "way points" en la lisat
			if(!listaMetas.isEmpty()){
				// Busco las coordenadas de la meta dentro de la matriz
				indiceDeMeta = listaMetas.get(siguienteMeta);
				for(int i = 0; i < this.dimensionN; i++){
					for(int j = 0; j < this.dimensionM; j++){
						if(indiceDeMeta == this.matriz[i][j].getIndice()){
							this.meta = this.matriz[i][j].getC();
						}
					}
				}
				// Asignar valores		
				this.calcularCamino();
				cogerCamino();
			}
			// La lista de metas esta vacia, por tanto, hemos recorrido todas las metas y salimos del bucle
			else {
				this.finBucle = true;
			}
		}
		System.out.println("FIN del PROGRAMA");
	}
	
	// Metodo que se encarga de guardar el camino que se tiene que recorrer para llegar a la meta
	private void cogerCamino() {
		// Si hemos podido llegar a la meta, en caso contrario finalizara la ejecucion
		if (!this.noPudo) {
			// Para guardar el principio del camino
			int i = 1;
			System.out.println("Pintamos el camino para llegar");
			TNodo nod = this.matriz[this.actual.getI()][this.actual.getJ()];
			System.out.println("Coordenadas: " + i + " I: " + this.actual.getI() + " J: " + this.actual.getJ());
			
			this.listaCamino.add(nod.getIndice());
			i++;

			boolean cambiar = true;
			boolean salir = false;
			// Esto es una cosa complicada por que en algunas casos cojo al hijo 
			// y luego al padre y despues de coger al padre cojo al hijo asi hasta acabar
			while (!salir) {
				if (cambiar) {
					// Aqui pillo al padre
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
					// Aqui cojo al hijo
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
	}
	// Metodo que inicializa los atributos tales como listaAbirta, listaCerrada, matriz[][]
	private void inicializar(int Inicio, ArrayList<Integer> listaBloqueos, ArrayList<Integer> listaPenalizaciones) {
		//Comparador para la cola de prioridad, que pone al principio los TNodos con un camino mas corto
		Comparator<TNodo> comparardor = new Comparator<TNodo>() {

			@Override
			public int compare(TNodo o1, TNodo o2) {
				if(o1.getF() < o2.getF()){// OJO al cambiar esto se hace bien, pero ya no tiene encuenta en funcion de la heuristica
					return 0;// A lo mejor -1
				}
				return 1;
			}
			
		};
		this.abierta = new PriorityQueue<>(comparardor);
		this.cerrada = new ArrayList<>();
		this.matriz = new TNodo[this.dimensionN][this.dimensionM];
		this.inicio = new Coordenadas(0,0);
		this.meta = new Coordenadas(1,3);
		
		
		int indice = 0;
		
		// Inicializacion de los valores de cada TNodo de la matriz
		for(int i = 0; i < this.dimensionN; i++){
			for(int j = 0;j < this.dimensionM; j++){
				boolean encontrado = false;
				Coordenadas c = new Coordenadas(i,j);
				TNodo nodo = new TNodo(false, 0, 0, 0, Tipos.BUENO, c, indice);
				// Cogemos el inicio
				if(indice == Inicio){
					this.inicio = nodo.getC();
				}
				else{
					// Marcamos los TNodos que sean PROHIBIDOS
					for(int k = 0; k < listaBloqueos.size() && !encontrado; k++){
						if(indice == listaBloqueos.get(k)){
							encontrado = true;
							nodo.setTipo(Tipos.PROHIBIDO);
						}
					}
					// Marcamos los TNodos que sean PENALIZACIONES
					for(int k = 0; k < listaPenalizaciones.size() && !encontrado; k++){
						if(indice == listaPenalizaciones.get(k)){
							encontrado = true;
							nodo.setTipo(Tipos.PENALIZACION);
						}
					}
				}
				indice++;
				this.matriz[i][j] = nodo;				
			}
		}
	}	
	// Metodo que se encarga de hacer las correspondientes operaciones para ver cual es el mejor camino
	public void calcularCamino(){
		boolean salir = false;
		Coordenadas aux = new Coordenadas(0,0);
		TNodo coge;
		
		// Inicializo la lista
		this.actual = this.inicio;
		this.cerrada.add(this.matriz[this.actual.getI()][this.actual.getJ()]);
		this.matriz[this.actual.getI()][this.actual.getJ()].setUsado(true);
		
		// Daremos vueltas hasta llegar a la meta
		while (!salir) {
			// Mirare si puedo desplazarme en cualquiera de las 8 direcciones posibles
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
				// Elegir el siguiente nodo a tratar
				this.actual = coge.getC();
				this.abierta.poll();
				this.cerrada.add(coge);
			
				// Compruebo que no he llegado a la meta
				if (coge.getC().getI() == this.meta.getI() && coge.getC().getJ() == this.meta.getJ()) {
					salir = true;
				}
			}
			// Si esta vacia, es que no hay mas soluciones
			else{
				// Compruebo si hemos llegado al final
				if(this.abierta.isEmpty() && this.actual.getI() != this.meta.getI() && this.actual.getJ() != this.meta.getJ()){
					salir = true;
					this.noPudo = true;
				}
			}	
		}
	}
	public void hacerLoMismo(TNodo coge, Coordenadas aux, boolean sumar){
		// No hemos pasado por ese TNodo y el TNodo no es de tipo PROHIBIDO
		if(!this.matriz[aux.getI()][aux.getJ()].getUsado() && this.matriz[aux.getI()][aux.getJ()].getTipo() != Tipos.PROHIBIDO){
			coge.setUsado(true);
			TNodo padre = this.matriz[this.actual.getI()][this.actual.getJ()];
			this.matriz[aux.getI()][aux.getJ()].setPadre(padre);
			this.matriz[aux.getI()][aux.getJ()].calcularH(aux, this.meta);
			this.matriz[aux.getI()][aux.getJ()].calcularG();
			this.matriz[aux.getI()][aux.getJ()].calcularF();
			
			
			// Le sumo a la distancia de raiz de 2
			if(sumar){
				this.matriz[aux.getI()][aux.getJ()].setDistancia(Math.sqrt(2)+padre.getDistancia());
			}
			// Le sumo a la distancia de 1
			else{
				this.matriz[aux.getI()][aux.getJ()].setDistancia(1+padre.getDistancia());
			}
			// Si el TNodo es de tipo PENALIZACION, se la añado
			if(this.matriz[aux.getI()][aux.getJ()].getTipo() == Tipos.PENALIZACION){
				double penalizacion = elegirPenalizacion();
				this.matriz[aux.getI()][aux.getJ()].setF(this.matriz[aux.getI()][aux.getJ()].getF()+ penalizacion);
				this.matriz[aux.getI()][aux.getJ()].setDistancia(this.matriz[aux.getI()][aux.getJ()].getDistancia()+penalizacion);
			}
			this.abierta.add(this.matriz[aux.getI()][aux.getJ()]);
		}
		// Esto quiere decir que TNodo ya esta en la lista de prioridad
		/*else if(this.matriz[aux.getI()][aux.getJ()].getUsado()){
			TNodo antiguo = this.matriz[aux.getI()][aux.getJ()];
			TNodo padre = this.matriz[this.actual.getI()][this.actual.getJ()];
			this.matriz[aux.getI()][aux.getJ()].setPadre(padre);
			this.matriz[aux.getI()][aux.getJ()].calcularH(aux, this.meta);
			this.matriz[aux.getI()][aux.getJ()].calcularG();
			this.matriz[aux.getI()][aux.getJ()].calcularF();
			
			
			// Le sumo a la distancia de raiz de 2
			if(sumar){
				this.matriz[aux.getI()][aux.getJ()].setDistancia(Math.sqrt(2)+padre.getDistancia());
			}
			// Le sumo a la distancia de 1
			else{
				this.matriz[aux.getI()][aux.getJ()].setDistancia(1+padre.getDistancia());
			}
			// Si el TNodo es de tipo PENALIZACION, se la añado
			if(this.matriz[aux.getI()][aux.getJ()].getTipo() == Tipos.PENALIZACION){
				double penalizacion = elegirPenalizacion();
				this.matriz[aux.getI()][aux.getJ()].setF(this.matriz[aux.getI()][aux.getJ()].getF()+ penalizacion);
				this.matriz[aux.getI()][aux.getJ()].setDistancia(this.matriz[aux.getI()][aux.getJ()].getDistancia()+penalizacion);
			}
			TNodo nuevo = this.matriz[aux.getI()][aux.getJ()];
			// Lo volvemos a poner el antiguo
			if(antiguo.getF() <= nuevo.getF()){
				this.matriz[aux.getI()][aux.getJ()] = antiguo;
			}
			// Hay que guardalo en la lista de prioridad(ponemos el nuevo)
			else {
				ArrayList<TNodo> abiertaAux = new ArrayList<>();
				boolean sal = false;
				for(int i = 0; i < this.abierta.size() && !sal; i++){
					if(this.abierta.peek().getIndice() == nuevo.getIndice()){
						sal = true;
						this.abierta.poll();
						this.abierta.add(nuevo);
					}
					else{
						abiertaAux.add(this.abierta.poll());
					}
				}
				for(int i = 0; i < abiertaAux.size(); i++){
					this.abierta.add(abiertaAux.get(i));
				}
			}
		}*/
	}
	// Metodo que calcula aleotiramente la penalizacion de la casilla
	private double elegirPenalizacion() {
		return (Math.random() * (this.valorPenalizacion-this.valorPenalizacionMin)) + this.valorPenalizacionMin;
	}

	// Getter para que en la capa de Presentacion en la clase Pintar podamos ver si se ha podido llegar a la meta o no
	public ArrayList<Integer> getListaCamino(){
		return this.listaCamino;
	}
	
}
