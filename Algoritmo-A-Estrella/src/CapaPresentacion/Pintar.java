package CapaPresentacion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Pintar extends JFrame{
	private int N;
	private int M;
	private JButton[][] matrizBotones;
	
	
	public Pintar(int n, int m){
		this.N = n;
		this.M = m;
		for(int i = 0; i < this.N; i++){
			for(int j = 0; j < this.M; j++){
				this.matrizBotones[i][j] = new JButton();
				this.matrizBotones[i][j].addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						
					}
				});
			}
		}
	}
	
}
