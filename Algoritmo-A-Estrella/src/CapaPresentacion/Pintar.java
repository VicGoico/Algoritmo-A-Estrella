package CapaPresentacion;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Pintar extends JFrame{
	private int n;
	private int m;
	private JButton[] matrizBotones;
	private JFrame ventanaPrincipal;
	private JPanel arriba;
	private JPanel abajo;
	
	private int Inicio = -1;
	private int Meta = -1;
	private ArrayList<Integer> listaBloqueos;
	
	public Pintar(int n, int m){
		
		//Logica l = new Logica(n, m);
		this.listaBloqueos = new ArrayList<>();
		this.n = n;
		this.m = m;
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
					if(Inicio == -1){
						matrizBotones[indice].setBackground(Color.GREEN);
						Inicio = indice;
					}
					else if(Meta == -1){
						matrizBotones[indice].setBackground(Color.YELLOW);
						Meta = indice;
					}
					else if(Inicio != -1 && Inicio != indice && Meta != indice && Meta != -1){
						boolean puede =true;
						int save = 0;
						for(int i = 0; i < listaBloqueos.size(); i++){
							if((listaBloqueos.get(i)) == indice){
								puede = false;
								save = i;
							}
						}
						if(puede){
							matrizBotones[indice].setBackground(Color.RED);
							listaBloqueos.add(indice);
						}
						else{
							listaBloqueos.remove(save);
							matrizBotones[indice].setBackground(Color.BLUE);
						}
						
					}
					else {
						if(Inicio == indice){
							Inicio = -1;
							matrizBotones[indice].setBackground(Color.BLUE);
						}
						else if(Meta == indice){
							Meta = -1;
							matrizBotones[indice].setBackground(Color.BLUE);
						}						
					}
				}
			});
			
			this.ventanaPrincipal.add(this.matrizBotones[i]);
		}
		// Primero el inicio
		/*for(int i = 0; i < this.N; i++){
			for(int j = 0; j < this.M; j++){
				this.matrizBotones[i][j].setCambiar(1);
			}
		}*/
		/*this.ventanaPrincipal.add(this.arriba);
		this.ventanaPrincipal.add(this.abajo);*/
		this.ventanaPrincipal.setVisible(true);
		
		
		
		
		
	}

	private boolean comprobar(){
		if(this.Inicio != -1){
			return true;
		}
		else{
			return false;
		}
	}
	
	private void init() {
		this.ventanaPrincipal = new JFrame("Algoritmo A Estrella");
		this.ventanaPrincipal.setLocationRelativeTo(null);
		this.ventanaPrincipal.setLayout(new GridLayout(n, m));
		this.ventanaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.arriba = new JPanel();
		this.arriba.setLayout(new GridLayout(n, m));
		this.abajo = new JPanel();
		this.abajo.setLayout(new FlowLayout());
		
		
		//ventana.setVisible(true);
	}
	
}
