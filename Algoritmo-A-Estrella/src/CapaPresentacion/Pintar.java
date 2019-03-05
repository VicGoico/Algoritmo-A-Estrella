package CapaPresentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import CapaNegocio.Logica;

public class Pintar extends JFrame{
	private JButton[] matrizBotones;
	private JFrame ventanaPrincipal;
	private JPanel arriba;
	private JPanel centro;
	private JPanel abajo;
	private JButton iniciar;
	private JButton limpiar;
	private JButton cambiarModo;
	private JButton salir;
	private JButton cogerDato;
	private JLabel informacion;
	
	private boolean cambiar;
	
	// Para elegir el inicio, la meta y los obstaculos
	private int Inicio = -1;						// Color VERDE
	private int Meta = -1;							// Color GRIS CLARO
	private ArrayList<Integer> listaMetas;			// Color AMARILLO
	private ArrayList<Integer> listaBloqueos;		// Color ROJO
	private ArrayList<Integer> listaPenalizaciones; // Color GRIS OSCURO
	
	public Pintar(int n, int m){
		this.listaMetas = new ArrayList<>();
		this.listaBloqueos = new ArrayList<>();
		this.listaPenalizaciones = new ArrayList<>();
		
		// Inicializa los botones
		init(n, m);
		this.matrizBotones = new JButton[n*m];
		
		
		for(int i = 0; i < n*m; i++){
			this.matrizBotones[i] = new JButton(" " + i + " ");
			// Pinto por defecto todos los botones de azul
			this.matrizBotones[i].setBackground(Color.BLUE); 
			
			this.matrizBotones[i].addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					String s = e.paramString();
					String [] a = s.split(" ");
					int indice = Integer.parseInt(a[1]);
					// Para elegir el INICIO
					if(Inicio == -1 && matrizBotones[indice].getBackground() != Color.RED && matrizBotones[indice].getBackground() != Color.YELLOW && matrizBotones[indice].getBackground() != Color.DARK_GRAY && matrizBotones[indice].getBackground() != Color.LIGHT_GRAY){
						matrizBotones[indice].setBackground(Color.GREEN);
						Inicio = indice;
					}
					// Para despintar el inicio
					else if (Inicio == indice) {
						matrizBotones[indice].setBackground(Color.BLUE);
						Inicio = -1;
					}
					// Para designar la meta
					else if(Meta == -1 && matrizBotones[indice].getBackground() != Color.RED && matrizBotones[indice].getBackground() != Color.YELLOW && matrizBotones[indice].getBackground() != Color.DARK_GRAY && matrizBotones[indice].getBackground() != Color.GREEN ){
						matrizBotones[indice].setBackground(Color.LIGHT_GRAY);
						Meta = indice;
					}
					// Borrar la meta
					else if(Meta == indice){
						matrizBotones[indice].setBackground(Color.BLUE);
						Meta = -1;
					}
					// Para elegir la "way points"
					// Si esta pintado de azul, la cogemos y la pintamos de amarillo
					else if(matrizBotones[indice].getBackground() == Color.BLUE){				
						matrizBotones[indice].setBackground(Color.YELLOW);
						listaMetas.add(indice);						
					}
					// Para elegir los PROHIBIDOS, hacer 2 clicks
					else if(matrizBotones[indice].getBackground() == Color.YELLOW){
						for(int i = 0; i < listaMetas.size(); i++){
							if(indice == listaMetas.get(i)){
								listaMetas.remove(i);
							}
						}
						matrizBotones[indice].setBackground(Color.RED);
						listaBloqueos.add(indice);
					}
					// Para elegir las PENALIZACIONES, hacer 3 clicks
					else if(matrizBotones[indice].getBackground() == Color.RED || matrizBotones[indice].getBackground() == Color.DARK_GRAY){
						for(int i = 0; i < listaBloqueos.size(); i++){
							if(indice == listaBloqueos.get(i)){
								listaBloqueos.remove(i);
							}
						}
						boolean sal = false;
						for(int i = 0; i < listaPenalizaciones.size() && !sal; i++){
							if(indice == listaPenalizaciones.get(i)){
								sal = true;
								listaPenalizaciones.remove(i);
							}
						}
						// Lo añado
						if(!sal){
							matrizBotones[indice].setBackground(Color.DARK_GRAY);
							listaPenalizaciones.add(indice);
						}
						// Lo elimino
						else{
							matrizBotones[indice].setBackground(Color.BLUE);
						}
					}
					
				}
			});
			
			// Lo añado al panel ARRIBA donde se mostrar mas tarde por pantalla
			this.centro.add(this.matrizBotones[i]);
		}
		
		
		// Creacion del boton INICIAR
		this.iniciar = new JButton("Iniciar");
		this.iniciar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// Comprueba que esten los valores minimos
				if(comprobarDatosMinimos()){
					ArrayList<Integer> metas = new ArrayList<>();
					for(int i = 0; i < listaMetas.size(); i++){
						metas.add(listaMetas.get(i));
					}
					ArrayList<Integer> penalizaciones = new ArrayList<>();
					for(int i = 0; i < listaPenalizaciones.size(); i++){
						penalizaciones.add(listaPenalizaciones.get(i));
					}
					listaMetas.add(Meta);
					Logica l = new Logica(n, m, Inicio, listaMetas, listaBloqueos, listaPenalizaciones);
					if(l.getListaCamino().isEmpty()){
						JOptionPane.showMessageDialog(null, "No se pudo llegar a la meta");
					}
					else {
						for (int i = 0; i < l.getListaCamino().size(); i++) {
							matrizBotones[l.getListaCamino().get(i)].setBackground(Color.WHITE);
						}
						// Repinto el inicio, las metas y las penalizaciones
						matrizBotones[Inicio].setBackground(Color.GREEN);
						for(int i = 0; i < metas.size(); i++){
							matrizBotones[metas.get(i)].setBackground(Color.YELLOW);
						}
						matrizBotones[Meta].setBackground(Color.LIGHT_GRAY);
						for(int i = 0; i < penalizaciones.size(); i++){
							matrizBotones[penalizaciones.get(i)].setBackground(Color.DARK_GRAY);
						}
					}
					iniciar.setEnabled(false);
					limpiar.setEnabled(true);
				}
				else{
					JOptionPane.showMessageDialog(null, "Falta la meta o el inicio o ambos(colores amarillo y verde)");
				}
			}

			private boolean comprobarDatosMinimos() {
				if(Inicio != -1){
					if(Meta != -1){
						return true;
					}
				}
				return false;
			}
		});
		this.abajo.add(this.iniciar);
		
		
		// Creacion boton limpiar
		this.limpiar = new JButton("Limpiar");
		this.limpiar.setEnabled(false);
		this.limpiar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				limpiarTablero();
				limpiar.setEnabled(false);
				iniciar.setEnabled(true);
			}
		});
		this.abajo.add(this.limpiar);
		
		// Creacion del boton Cambiar modo
		this.cambiarModo = new JButton("Cambiar MODO");
		this.cambiarModo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// Modo normal
				if(cambiar){
					cambiar = false;
					informacion.setText("MODO CON ALTURAS");
					//
					
				}
				// Modo de alturas
				else {
					cambiar = true;
					informacion.setText("MODO SIN ALTURAS");
					
				}
			}
		});		
		// Creacion del boton SALIR
		this.salir = new JButton("Salir");
		this.salir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Hasta luego");
				System.exit(0);
			}
		});
		
		this.abajo.add(this.salir);
		
		this.ventanaPrincipal.add(this.arriba, BorderLayout.NORTH);
		this.ventanaPrincipal.add(this.centro, BorderLayout.CENTER);
		this.ventanaPrincipal.add(this.abajo, BorderLayout.SOUTH);
		
		this.ventanaPrincipal.setVisible(true);
	}
	
	private void init(int n, int m) {
		this.ventanaPrincipal = new JFrame("Algoritmo A Estrella");
		this.ventanaPrincipal.setLocationRelativeTo(null);
		// Mirar lo del tamaño de la ventana cuando se abre
		this.ventanaPrincipal.setSize(600, 100);
		this.ventanaPrincipal.setLayout(new BorderLayout());
		this.ventanaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.arriba = new JPanel();
		// AQUI SETLAYOUT
		this.informacion = new JLabel("MODO SIN ALTURAS");
		this.cambiar = true;
		
		this.centro = new JPanel();
		this.centro.setLayout(new GridLayout(n, m));
		this.abajo = new JPanel();
		this.abajo.setLayout(new FlowLayout());
	}
	private void limpiarTablero(){
		this.Inicio = -1;
		this.listaMetas = new ArrayList<>();
		this.listaBloqueos = new ArrayList<>();
		this.listaPenalizaciones = new ArrayList<>();
		for(int i = 0; i < this.matrizBotones.length; i++){
			this.matrizBotones[i].setBackground(Color.BLUE);
		}
	}
	
}
