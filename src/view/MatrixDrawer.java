package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MatrixDrawer extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean[][] matrix;
    private int cellSize;
    private boolean gano= true;


    public MatrixDrawer(boolean[][] matrix) {
        this.matrix = matrix;
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX() / cellSize;
                int y = e.getY() / cellSize;
                condiciones(x, y);
                repaint();
                //incrementClickCounter();
                
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int rows = matrix.length;
        int cols = matrix[0].length;
        cellSize = Math.min(getWidth() / cols, getHeight() / rows);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                
            	
            	if (matrix[i][j]) {
                    g.setColor(Color.YELLOW);
                } else {
                    g.setColor(Color.BLUE);
                    this.gano = false;
                }
                //x,y
                g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                g.setColor(Color.BLACK);
                g.drawRect(j * cellSize, i * cellSize, cellSize, cellSize);
            }
        }
    }

    private void condiciones(int x, int y) {
        if (x >= 0 && x < matrix[0].length && y >= 0 && y < matrix.length) {
            matrix[y][x] = !matrix[y][x];
            vecinos(x, y);
        }
    }

    private void vecinos(int x, int y) {
        //Casillas al rededor de pos actual mirar ejr de barrio peligroso
    	int[][] directions = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 }, { -1, -1 }, { -1, 1 }, { 1, -1 }, { 1, 1 } };

        for (int[] dir : directions) {
            int newX = x + dir[0];
            int newY = y + dir[1];

            if (newX >= 0 && newX < matrix[0].length && newY >= 0 && newY < matrix.length) {
                matrix[newY][newX] = !matrix[newY][newX];
            }
        }
    }

    public void actualizarMatrix(boolean[][] newMatrix) {
        matrix = newMatrix;
        repaint();
    }

	public boolean getGano() {
		return gano;
	}

	
    
    
    /*private void incrementClickCounter() {
        clickCounter++;
    }

    
    public int getClickCounter() {
        return clickCounter;
    }
    public boolean isClicked() {
        return clicked;
    }

    
    public void resetClickStatus() {
        clicked = false;
    }*/
}
