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
	private int n;
	private int m;
	// IMPORTAMTE Esto hay que convertirlo en una matriz
	private JButton[] matrizBotones;
	private JFrame ventanaPrincipal;
	private JPanel arriba;
	private JPanel abajo;
	private JButton iniciar;
	private JButton salir;
	
	// Para elegir el inicio, la meta y los obstaculos
	private int Inicio = -1;
	private int Meta = -2;
	private ArrayList<Integer> listaBloqueos;
	
	public Pintar(int n, int m){
		this.listaBloqueos = new ArrayList<>();
		this.n = n;
		this.m = m;
		// Inicializa los botones
		init();
		this.matrizBotones = new JButton[n*m];
		
		
		for(int i = 0; i < this.n*this.m; i++){
			this.matrizBotones[i] = new JButton(" " + i + " ");
			this.matrizBotones[i].setBackground(Color.BLUE); 
			
			this.matrizBotones[i].addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					String s = e.paramString();
					String [] a = s.split(" ");
					int indice = Integer.parseInt(a[1]);
					// Para elegir el INICIO
					if(Inicio == -1 && indice != Meta){
						matrizBotones[indice].setBackground(Color.GREEN);
						Inicio = indice;
					}
					// Para elegir la META
					else if(Meta == -2 && indice != Inicio){
						
						matrizBotones[indice].setBackground(Color.YELLOW);
						Meta = indice;
					}
					// Para elegir los PROHIBIDOS
					else if(Inicio != -1 && Inicio != indice && Meta != indice && Meta != -2){
						boolean puede =true;
						int save = 0;
						for(int i = 0; i < listaBloqueos.size(); i++){
							if((listaBloqueos.get(i)) == indice){
								puede = false;
								save = i;
							}
						}
						// Para marcar los PROHIBIDOS
						if(puede){
							matrizBotones[indice].setBackground(Color.RED);
							listaBloqueos.add(indice);
						}
						// Para desmarcar el PROHIBIDO
						else{
							listaBloqueos.remove(save);
							matrizBotones[indice].setBackground(Color.BLUE);
						}
					}
					// Para desmarcar cualquier otra señal
					else {
						// Para desmarcar el INICIO
						if(Inicio == indice){
							Inicio = -1;
							matrizBotones[indice].setBackground(Color.BLUE);
						}
						// Para desmarcar la META
						else if(Meta == indice){
							Meta = -2;
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
					Logica l = new Logica(n, m, Inicio, Meta, listaBloqueos);
					if(l.getListaCamino().isEmpty()){
						JOptionPane.showMessageDialog(null, "No se pudo llegar a la meta");
					}
					else {
						for (int i = 0; i < l.getListaCamino().size(); i++) {
							matrizBotones[l.getListaCamino().get(i)].setBackground(Color.ORANGE);
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
					if(Meta != -1){
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
	
	private void init() {
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
