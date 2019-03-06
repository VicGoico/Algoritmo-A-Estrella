package CapaPresentacion;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class VentanaDimensiones extends JFrame{
	private JFrame ventana;
	
	private JPanel arriba;
	private JPanel centro;
	private JPanel abajo;
	
	private JLabel infoN;
	private JLabel infoM;
	
	private JTextField textoN;
	private JTextField textoM;
	
	private JButton confirmar;
	private JButton salir;
	
	public VentanaDimensiones(){
		init();
		
		
		// Añadiendo componentes
		this.confirmar = new JButton("Confirmar");
		this.confirmar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//ventana.setVisible(false);
				try{
					
					int n = Integer.parseInt(textoN.getText());
					int m = Integer.parseInt(textoM.getText());
					ventana.setVisible(false);
					Pintar p = new Pintar(n, m);
					
				}
				catch(Exception fallo){
					textoN.setText("");
					textoM.setText("");
					JOptionPane.showMessageDialog(null, "Las dimensiones no tienen un buen formato"+System.lineSeparator()+"Recuerda: Tienen que ser numeros sin comas");
				}
				
				
			}
		});
		this.salir = new JButton("Salir");
		this.salir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		this.abajo.add(this.confirmar);
		this.abajo.add(this.salir);
		
		this.ventana.add(this.arriba, BorderLayout.NORTH);
		this.ventana.add(this.centro, BorderLayout.CENTER);
		this.ventana.add(this.abajo, BorderLayout.SOUTH);
		this.ventana.setVisible(true);
		this.ventana.pack();
		
	}

	private void init() {
		this.ventana = new JFrame("DIMENSIONES");
		
		
		this.ventana.setSize(420, 200);
		this.ventana.setLocationRelativeTo(null);
		this.ventana.setLayout(new BorderLayout());
		this.ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.arriba = new JPanel();
		this.arriba.setLayout(new GridLayout(1, 2));
		
		this.infoN = new JLabel("Numero de filas");
		this.infoM = new JLabel("Numero de columnas");
		this.arriba.add(this.infoN);
		this.arriba.add(this.infoM);
		
		
		
		this.centro = new JPanel();
		this.centro.setLayout(new GridLayout(1,2));
		
		
		this.textoN = new JTextField();
		
		//this.textoN.setBounds(200,  20,  600,  400);
		this.textoM = new JTextField();
	
		
		this.centro.add(this.textoN);
		this.centro.add(this.textoM);
		
		this.abajo = new JPanel();
		this.abajo.setLayout(new FlowLayout());
		
		
		
		
	}
}
