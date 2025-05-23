package catmap;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;

public class catMap {

    public static BufferedImage aplicarUnaIteracionArnold(BufferedImage img) {
        int n = img.getWidth();
        BufferedImage temp = new BufferedImage(n, n, img.getType());
        for (int x = 0; x < n; x++) {
            for (int y = 0; y < n; y++) {
                int newX = (x + y) % n;
                int newY = (x + 2 * y) % n;
                temp.setRGB(newX, newY, img.getRGB(x, y));
            }
        }
        return temp;
    }

    public static Image escalarImagen(BufferedImage img, int ancho, int alto) {
        return img.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
    }

    public static void main(String[] args) {
        try {
            File archivo = new File("imagen.jpg"); 
            BufferedImage original = ImageIO.read(archivo);

            int[] fases = {0, 1, 3, 132, 155, 157, 200, 211, 240, 275, 299, 300};

            BufferedImage[] imagenes = new BufferedImage[fases.length];
            String[] etiquetas = new String[fases.length];

            BufferedImage temp = original;
            int iterActual = 0;
            int siguienteFaseIndex = 0;

            while (iterActual <= fases[fases.length - 1]) {
                if (iterActual == fases[siguienteFaseIndex]) {
                    imagenes[siguienteFaseIndex] = temp;
                    siguienteFaseIndex++;
                }
                temp = aplicarUnaIteracionArnold(temp);
                iterActual++;
            }

            JFrame frame = new JFrame(" Arnold Cat Map");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new GridLayout(4, 3, 5, 5));

            int anchoImagen = 150;  
            int altoImagen = 150;

            for (int i = 0; i < imagenes.length; i++) {
                JPanel panel = new JPanel(new BorderLayout());

                Image imgEscalada = escalarImagen(imagenes[i], anchoImagen, altoImagen);
                JLabel etiquetaImagen = new JLabel(new ImageIcon(imgEscalada));
                etiquetaImagen.setHorizontalAlignment(JLabel.CENTER);

                JLabel etiquetaTexto = new JLabel(etiquetas[i], JLabel.CENTER);

                panel.add(etiquetaImagen, BorderLayout.CENTER);
                panel.add(etiquetaTexto, BorderLayout.SOUTH);

                frame.add(panel);
            }

            frame.pack();
            frame.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
