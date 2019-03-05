package CapaPresentacion;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class VentanaAltura extends JFrame{
	private JFrame ventana;
	
	private JPanel arriba;
	private JPanel centro;
	private JPanel abajo;
	
	private JLabel info;
	
	private JTextField texto;
	private JButton confirmar;
	
	private int dato;
	
	public VentanaAltura(){
		init();
		
		
		this.ventana.add(this.arriba, BorderLayout.NORTH);
		this.ventana.add(this.centro, BorderLayout.CENTER);
		this.ventana.add(this.abajo, BorderLayout.SOUTH);
		this.ventana.setVisible(true);
	}
	
	private void init() {
		this.ventana = new JFrame("DATO DE ALTURA");
		
		
		this.ventana.setSize(420, 200);
		this.ventana.setLocationRelativeTo(null);
		this.ventana.setLayout(new BorderLayout());
		this.ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.arriba = new JPanel();
		this.arriba.setLayout(new FlowLayout());
		
		this.info = new JLabel("Numero de filas");
		this.arriba.add(this.info);
		
		this.centro = new JPanel();
		this.centro.setLayout(new FlowLayout());
		
		
		this.texto = new JTextField();
		
		this.centro.add(this.texto);
		
		this.abajo = new JPanel();
		this.abajo.setLayout(new FlowLayout());
	}

	public int getDato(){
		return this.dato;
	}
}
