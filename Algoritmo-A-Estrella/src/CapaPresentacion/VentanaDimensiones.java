package CapaPresentacion;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import CapaNegocio.Logica;

public class VentanaDimensiones extends JFrame{
	public VentanaDimensiones(){
		JFrame ventana = new JFrame("DIMENSIONES");
		JTextField textoN = new JTextField("Introduce el numero de filas");
		JTextField textoM = new JTextField("Introduce el numero de columnas");
		
		ventana.setSize(420, 200);
		ventana.setLocationRelativeTo(null);
		ventana.setLayout(new GridLayout(2, 2));
		JLabel label = new JLabel();
		label.setLayout(new FlowLayout());
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Añadiendo componentes
		JButton boton = new JButton("Confirmar");
		boton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ventana.setVisible(false);
				try{
					int n = Integer.parseInt(textoN.getText());
					int m = Integer.parseInt(textoM.getText());
					Logica l = new Logica(n, m);
				}
				catch(Exception e1){
					JOptionPane.showMessageDialog(null, "Las dimensiones no tienen un buen formato"+System.lineSeparator()+"Recuerda: Tienen que ser numeros sin comas");
					System.exit(0);
				}
				
				
			}
		});
		JButton cancelar = new JButton("Salir");
		cancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		
		ventana.add(textoN);
		ventana.add(textoM);
		ventana.add(label);
		label.add(boton);
		label.add(cancelar);
		
		ventana.add(label);
		ventana.setVisible(true);
		
	}
}
