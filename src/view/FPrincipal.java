package view;

import javax.swing.*;

import uniandes.dpoo.taller4.modelo.Tablero;
import uniandes.dpoo.taller4.modelo.Top10;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class FPrincipal extends JFrame implements ActionListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel panelLateral;
    private JCheckBox selectedCheckBox = null;
    public Tablero tablero= new Tablero(4);
    public  String nombreJugador;
    public boolean desordenar;
    public int dificultad=10;
    public int tamanoActual=4;
    public boolean cambiarTamano=false;
    public int coordenadaJugadaX;
    public int coordenadaJugadaY;
    public boolean fuePresionado;
	//private int ultima_fila;
	//private int ultima_columna;
    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public MatrixDrawer matrixDrawer;
    public JLabel labelJugadas=null;
    public JLabel jugador=null;
    public int numeroJugadas;
    public Top10 top10=new Top10();
    File file = new File("C:\\Users\\cfvm0\\eclipse-workspace1\\Lab_4_DPOO\\data\\top10.csv"); 
    public FPrincipal() {
    	
    	
        this.setSize(800, 500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("LightsOut");
        this.setLayout(new BorderLayout());
        JLabel labelValorJugador= new JLabel();
        jugador=labelValorJugador;
        WelcomeWindow.showWelcomeWindow(labelValorJugador);
        nombreJugador=labelValorJugador.getText();
        // Dropdown box tamano
        String[] items = {"4x4", "5x5", "6x6", "7x7", "8x8"};
        JLabel labelTamano= new JLabel("Tamaño");
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.addActionListener(this);

        // Checkbox dificultad
        JCheckBox checkBoxFacil = new JCheckBox("Facil");
        JCheckBox checkBoxMedio = new JCheckBox("Medio");
        JCheckBox checkBoxDificil = new JCheckBox("Dificil");
        checkBoxFacil.addActionListener(checkBoxListener);
        checkBoxMedio.addActionListener(checkBoxListener);
        checkBoxDificil.addActionListener(checkBoxListener);

        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.add(labelTamano);
        controlPanel.add(comboBox);
        controlPanel.add(checkBoxFacil);
        controlPanel.add(checkBoxMedio);
        controlPanel.add(checkBoxDificil);

        this.add(controlPanel, BorderLayout.NORTH);

        new JPanel();
        JPanel panelTablero = new JPanel();
        boolean[][] matriz = tablero.darTablero();
        MatrixDrawer matrixDrawer = new MatrixDrawer(matriz);
        this.matrixDrawer=matrixDrawer;
        matrixDrawer.setPreferredSize(new Dimension(390, 390));
        panelTablero.add(matrixDrawer);
        
        this.add(panelTablero, BorderLayout.CENTER);
        detectClicks(matrixDrawer);
        //actualizarTamanoTablero(4); // Initially create a 2x2 grid

        // Botones laterales
        panelLateral = new JPanel();
        panelLateral.setLayout(new BoxLayout(panelLateral, BoxLayout.Y_AXIS));

        JButton botonNuevo = createButton("Nuevo");
        JButton botonReiniciar = createButton("Reiniciar");
        JButton botonTop10 = createButton("Top 10");
        JButton botonCambiarJugador = createButton("Cambiar Jugador");
        panelLateral.add(botonNuevo);
        panelLateral.add(botonReiniciar);
        panelLateral.add(botonTop10);
        panelLateral.add(botonCambiarJugador);
        botonNuevo.addActionListener(botonesListener);
        botonReiniciar.addActionListener(botonesListener);
        botonTop10.addActionListener(botonesListener);
        botonCambiarJugador.addActionListener(botonesListener);
        


        Dimension lateralSize = new Dimension((int) (this.getWidth() * 0.3), panelLateral.getPreferredSize().height);
        panelLateral.setPreferredSize(lateralSize);

        this.add(panelLateral, BorderLayout.EAST); 
        
        //Barra de informacion Inferior
        JPanel panelInferior = new JPanel(new FlowLayout());
        JLabel labelJugadastxt= new JLabel("Jugadas: ");
        JLabel labelValorJugadas= new JLabel();
        this.labelJugadas=labelValorJugadas;
        JLabel labelJugador= new JLabel("Jugador: ");
        
        panelInferior.add(labelJugadastxt);
        panelInferior.add(labelJugadas);
        panelInferior.add(labelJugador);
        panelInferior.add(labelValorJugador);
        
        this.add(panelInferior,BorderLayout.SOUTH);
       
        
        
        
        this.setVisible(true);
    	
    }
    
   /*public void mouseClicked(MouseEvent e) {
        int click_x = e.getX();
        int click_y = e.getY();
        int[] casilla = convertirCoordenadasACasilla(click_x, click_y);
        // Add your game logic here, e.g., update cantidades, principal, and call jugar
        this.ultima_fila = casilla[0];
        this.ultima_columna = casilla[1];
        repaint();
    }
    
    public void mousePressed(MouseEvent e) {
        // Empty, not used
    }

    public void mouseReleased(MouseEvent e) {
        // Empty, not used
    }

    public void mouseEntered(MouseEvent e) {
        // Empty, not used
    }

    public void mouseExited(MouseEvent e) {
        // Empty, not used
    }
    
    private int[] convertirCoordenadasACasilla(int x, int y) {
        int ladoTablero = tablero.darTablero().length;
        int altoPanelTablero = getHeight();
        int anchoPanelTablero = getWidth();
        int altoCasilla = altoPanelTablero / ladoTablero;
        int anchoCasilla = anchoPanelTablero / ladoTablero;
        int fila = (int) (y / altoCasilla);
        int columna = (int) (x / anchoCasilla);
        return new int[]{fila, columna};
    }
    
    private void drawTablero(Graphics g) {
        int ladoTablero = tablero.darTablero().length;
        int altoPanelTablero = getHeight();
        int anchoPanelTablero = getWidth();
        int altoCasilla = altoPanelTablero / ladoTablero;
        int anchoCasilla = anchoPanelTablero / ladoTablero;

        for (int fila = 0; fila < ladoTablero; fila++) {
            for (int columna = 0; columna < ladoTablero; columna++) {
                int x = columna * anchoCasilla;
                int y = fila * altoCasilla;
                if (tablero.darTablero()[fila][columna]) {
                    g.setColor(Color.YELLOW);
                } else {
                    g.setColor(Color.BLUE);
                }
                g.fillRoundRect(x, y, anchoCasilla, altoCasilla, 20, 20);
            }
        }
    }
*/	
    public  void detectClicks(MatrixDrawer matrixDrawer) {
        matrixDrawer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	numeroJugadas+=1;
            	updateLabelWithCounterValue(numeroJugadas);
            	if (matrixDrawer.getGano()==true && top10.esTop10(numeroJugadas)==true) {
            		top10.agregarRegistro(nombreJugador, numeroJugadas);
            		
                    

            		try {
						top10.salvarRecords(file);
					} catch (FileNotFoundException | UnsupportedEncodingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
            	}
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	
    	
        JComboBox<String> comboBox = (JComboBox<String>) e.getSource();
        String selected = (String) comboBox.getSelectedItem();
        if (selected.length() > 0 && Character.isDigit(selected.charAt(0))) {
            int newSize = Character.getNumericValue(selected.charAt(0));
            Tablero tabla = new Tablero(newSize);
            this.tablero = tabla;
            matrixDrawer.actualizarMatrix(tablero.darTablero());
            numeroJugadas=0;
            updateLabelWithCounterValue(numeroJugadas);
            
            //this.tamanoActual=newSize;
            //cambiarTamano=true;
            //actualizarTamanoTablero(newSize);
            //cambiarTamano=false;
            
            
        }
    }
    
    
    //	HECHO CON BOTONES
    /*
    private void actualizarTamanoTablero(int size) {
        buttonPanel.removeAll();
        buttonPanel.setLayout(new GridLayout(size, size));
        if (cambiarTamano==true) {
        Tablero tabla = new Tablero(size);
        this.tablero = tabla;
        cambiarTamano=false;
        }

        // Check if desordenar is true, and if so, update the tabla
        if (desordenar) {
        	
            tablero.desordenar(dificultad);
            desordenar=false;
        }
        if (fuePresionado) {
        	
        	tablero.jugar(coordenadaJugadaX, coordenadaJugadaY);
        	fuePresionado=false;
        }

        boolean[][] matriz = tablero.darTablero();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                JButton button = new JButton(i + "," + j);

                
                button.setActionCommand(i + "," + j);
                
                button.addActionListener(botonesListener); // Add action listener to the button
                
                if (matriz[i][j]) {
                    button.setBackground(Color.YELLOW);
                } else {
                    button.setBackground(Color.BLUE);
                }
                buttonPanel.add(button);
            }
        }
        buttonPanel.revalidate();
        buttonPanel.repaint();
    }*/
    /*private void actualizarTamanoTableroOriginal(int size) {
        buttonPanel.removeAll();
        buttonPanel.setLayout(new GridLayout(size, size));
        if (cambiarTamano==true) {
        Tablero tabla = new Tablero(size);
        this.tablero = tabla;
        cambiarTamano=false;
        }

        
        if (desordenar) {
        	
            tablero.desordenar(dificultad);
            desordenar=false;
        }
        if (fuePresionado) {
        	
        	tablero.jugar(coordenadaJugadaX, coordenadaJugadaY);
        	fuePresionado=false;
        }

        boolean[][] matriz = tablero.darTablero();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                JButton button = new JButton(i + "," + j);

               
                button.setActionCommand(i + "," + j);
                
                button.addActionListener(botonesListener); // Add action listener to the button
                
                if (matriz[i][j]) {
                    button.setBackground(Color.YELLOW);
                } else {
                    button.setBackground(Color.BLUE);
                }
                buttonPanel.add(button);
            }
        }
        buttonPanel.revalidate();
        buttonPanel.repaint();
    }*/


    ActionListener botonesListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton botonPresionado = (JButton) e.getSource();
            String coordenada = botonPresionado.getActionCommand();
           
            
            if(coordenada.equals("Nuevo")) {
            	//cambiarTamano=true;
            	//desordenar=true;
            	Tablero tabla = new Tablero(tamanoActual);
            	tablero=tabla;
            	tablero.desordenar(dificultad);
            	matrixDrawer.actualizarMatrix(tablero.darTablero());
            	numeroJugadas=0;
                updateLabelWithCounterValue(numeroJugadas);
            	//actualizarTamanoTablero(tamanoActual);
            	
            }
            if(coordenada.equals("Reiniciar")) {
            	tablero.reiniciar();
            	matrixDrawer.actualizarMatrix(tablero.darTablero());
            	
            	
            }
            if(coordenada.equals("Cambiar Jugador")) {
            	WelcomeWindow.showWelcomeWindow(jugador);
            	
            }
            if(coordenada.equals("Top 10")) {
            	int x=1;
            	top10.cargarRecords(file);
            	
            }
            
        }
    };
    
    

    

    ActionListener checkBoxListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JCheckBox clickedCheckBox = (JCheckBox) e.getSource();
            if (clickedCheckBox.isSelected()) {
                if (selectedCheckBox != null) {
                    selectedCheckBox.setSelected(false);
                }
                selectedCheckBox = clickedCheckBox;
                String selecionado = selectedCheckBox.getText();
                if (selecionado.equals("Facil")) {
                    tablero.desordenar(10);
                    matrixDrawer.actualizarMatrix(tablero.darTablero());
                    numeroJugadas=0;
                    updateLabelWithCounterValue(numeroJugadas);
                } else if (selecionado.equals("Medio")) {
                	tablero.desordenar(20);
                	matrixDrawer.actualizarMatrix(tablero.darTablero());
                	numeroJugadas=0;
                	updateLabelWithCounterValue(numeroJugadas);
                } else if (selecionado.equals("Dificil")) {
                	tablero.desordenar(30);
                	matrixDrawer.actualizarMatrix(tablero.darTablero());
                	numeroJugadas=0;
                    updateLabelWithCounterValue(numeroJugadas);
                    
                } 
                
            } else {
                selectedCheckBox = null;
            }
        }
    };

    private JButton createButton(String label) {
        JButton button = new JButton(label);
        button.setAlignmentX(Component.CENTER_ALIGNMENT); // Center-align the button
        button.setFont(new Font("Arial", Font.PLAIN, 20)); // Adjust font size
        button.setMargin(new Insets(10, 20, 10, 20)); // Adjust margins
        return button;
    }
    public void updateLabelWithCounterValue(int value) {
    	labelJugadas.setText(Integer.toString(value));
    	labelJugadas.repaint(); 
    }
    
    /*public void checkIfClicked(MatrixDrawer matrixDrawer) {
        if (matrixDrawer.isClicked()) {
        	numeroJugadas+=1;
        	updateLabelWithCounterValue(numeroJugadas);
            matrixDrawer.resetClickStatus(); 
        } 
    }*/
    
    /*public static void ventanaBienvenida() {
        
        JFrame newFrame = new JFrame("LightsOut");
        JPanel panelVentanaNueva= new JPanel();
        panelVentanaNueva.setLayout(new BoxLayout(panelVentanaNueva,BoxLayout.Y_AXIS ));
        
        
        newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newFrame.setSize(300, 200);

        // Add some content to the new window
        JLabel label = new JLabel("Bienvenido Ingrese sus Nombre");
        label.setHorizontalAlignment(JLabel.CENTER);
        panelVentanaNueva.add(label);
        
        JTextField textField = new JTextField(20); // 20 is the preferred width of the text field
        panelVentanaNueva.add(textField);
        JButton submitButton = new JButton("continuar");
        panelVentanaNueva.add(submitButton);
        
       
        

        // Center the new window on the screen
        newFrame.setLocationRelativeTo(null);
        
        newFrame.add(panelVentanaNueva);

        // Make the new window visible
        newFrame.setVisible(true);
        
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textField.getText();
                JOptionPane.showMessageDialog(null, "You entered: " + text);
                nombreJugador=text;
                int a= 1;
            }
        });
        
        

        
        
        
        
        
    }*/

    public static void main(String[] args) throws IOException {
    	/*BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    	String jugador  = reader.readLine();*/
    	SwingUtilities.invokeLater(() -> new FPrincipal());
        
    }
    
}
