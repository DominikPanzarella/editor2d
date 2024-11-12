package ch.supsi.editor2d.service.algorithm;

import ch.supsi.editor2d.service.model.ImageWrapper;
import ch.supsi.editor2d.service.model.PixelWrapper;

public class Negative extends Filter {

    public Negative() {
        super("Negative");
    }

    @Override
    public ImageWrapper apply(ImageWrapper image) {
        // Clona l'immagine per mantenere inalterato l'originale
        ImageWrapper newImage = image.clone();

        // Ottieni la matrice di pixel dell'immagine originale
        PixelWrapper[][] oldMatrix = image.getData();

        // Applica il filtro negativo alla matrice di pixel
        PixelWrapper[][] newMatrix = applyNegative(oldMatrix);

        // Imposta la nuova matrice di pixel nell'immagine clonata
        newImage.setData(newMatrix);

        return newImage;
    }

    // Metodo che applica il filtro negativo ai pixel, supponendo che il tipo di immagine sia già noto
    private PixelWrapper[][] applyNegative(PixelWrapper[][] matrix) {
        int height = matrix.length;
        int width = matrix[0].length;
        PixelWrapper[][] negativeMatrix = new PixelWrapper[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                PixelWrapper pixel = matrix[i][j];

                // Inverti i valori RGB (se è PBM, PGM o PPM, la logica di inversione sarà la stessa)
                float invertedRed = 1.0f - pixel.getRed();
                float invertedGreen = 1.0f - pixel.getGreen();
                float invertedBlue = 1.0f - pixel.getBlue();

                negativeMatrix[i][j] = new PixelWrapper(invertedRed, invertedGreen, invertedBlue);
            }
        }

        return negativeMatrix;
    }
}
