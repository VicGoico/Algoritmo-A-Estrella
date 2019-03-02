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
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import CapaNegocio.Logica;

public class Pintar extends JFrame{
	private JButton[] matrizBotones;
	private JFrame ventanaPrincipal;
	private JPanel arriba;
	private JPanel abajo;
	private JButton iniciar;
	private JButton salir;
	
	// Para elegir el inicio, la meta y los obstaculos
	private int Inicio = -1;
	private ArrayList<Integer> listaMetas;
	private ArrayList<Integer> listaBloqueos;
	
	public Pintar(int n, int m){
		this.listaBloqueos = new ArrayList<>();
		this.listaMetas = new ArrayList<>();
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
					if(Inicio == -1 && matrizBotones[indice].getBackground() != Color.RED && matrizBotones[indice].getBackground() != Color.YELLOW){
						matrizBotones[indice].setBackground(Color.GREEN);
						Inicio = indice;
					}
					// Para despintar el inicio
					else if (Inicio == indice) {
						matrizBotones[indice].setBackground(Color.BLUE);
						Inicio = -1;
					}

					// Para elegir la META
					// Si esta pintado de azul, la cogemos y la pintamos de amarillo
					else if(matrizBotones[indice].getBackground() == Color.BLUE){				
						matrizBotones[indice].setBackground(Color.YELLOW);
						listaMetas.add(indice);						
					}
					// Para elegir los PROHIBIDOS, hacer 2 click
					else if(matrizBotones[indice].getBackground() == Color.YELLOW || matrizBotones[indice].getBackground() == Color.RED){
						for(int i = 0; i < listaMetas.size(); i++){
							if(indice == listaMetas.get(i)){
								listaMetas.remove(i);
							}
						}
						boolean sal = false;
						for(int i = 0; i < listaBloqueos.size() && !sal; i++){
							if(indice == listaBloqueos.get(i)){
								sal = true;
								listaBloqueos.remove(i);
							}
						}
						// Lo añado
						if(!sal){
							matrizBotones[indice].setBackground(Color.RED);
							listaBloqueos.add(indice);
						}
						// Lo elimino
						else{
							matrizBotones[indice].setBackground(Color.BLUE);
						}
					}
					
				}
			});
			
			this.arriba.add(this.matrizBotones[i]);
		}
		// Creacion del boton INICIAR
		this.iniciar = new JButton("Iniciar");
		this.iniciar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// Comprueba que esten los valores minimos
				if(comprobarDatosMinimos()){
					ArrayList<Integer> aux = new ArrayList<>();
					for(int i = 0; i < listaMetas.size(); i++){
						aux.add(listaMetas.get(i));
					}
					Logica l = new Logica(n, m, Inicio, listaMetas, listaBloqueos);
					if(l.getListaCamino().isEmpty()){
						JOptionPane.showMessageDialog(null, "No se pudo llegar a la meta");
					}
					else {
						for (int i = 0; i < l.getListaCamino().size(); i++) {
							matrizBotones[l.getListaCamino().get(i)].setBackground(Color.WHITE);
						}
						// Repinto el inicio y las metas
						matrizBotones[Inicio].setBackground(Color.GREEN);
						for(int i = 0; i < aux.size(); i++){
							matrizBotones[aux.get(i)].setBackground(Color.YELLOW);
						}
					}
					iniciar.setEnabled(false);
				}
				else{
					JOptionPane.showMessageDialog(null, "Falta la meta o el inicio o ambos(colores amarillo y verde)");
				}
			}

			private boolean comprobarDatosMinimos() {
				if(Inicio != -1){
					if(!listaMetas.isEmpty()){
						return true;
					}
				}
				return false;
			}
		});
		this.abajo.add(this.iniciar);
		
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
		
		this.ventanaPrincipal.add(this.arriba, BorderLayout.CENTER);
		this.ventanaPrincipal.add(this.abajo, BorderLayout.SOUTH);
		
		this.ventanaPrincipal.setVisible(true);
	}
	
	private void init(int n, int m) {
		this.ventanaPrincipal = new JFrame("Algoritmo A Estrella");
		this.ventanaPrincipal.setLocationRelativeTo(null);
		this.ventanaPrincipal.setSize(420, 200);
		this.ventanaPrincipal.setLayout(new BorderLayout());
		this.ventanaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.arriba = new JPanel();
		this.arriba.setLayout(new GridLayout(n, m));
		this.abajo = new JPanel();
		this.abajo.setLayout(new FlowLayout());
	}
	
}
