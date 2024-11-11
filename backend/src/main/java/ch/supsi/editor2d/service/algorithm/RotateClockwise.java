package ch.supsi.editor2d.service.algorithm;

import ch.supsi.editor2d.service.model.PixelWrapper;

public class RotateClockwise extends Rotate{

    public RotateClockwise() {
        super("RotateClockwise");
    }

    @Override
    protected PixelWrapper[][] rotate(PixelWrapper[][] matrix) {
        final int height = matrix.length;
        final int width = matrix[0].length;

        PixelWrapper[][] newMatrix = new PixelWrapper[width][height];

        for(int y=0; y<height; y++){
            for(int x=0; x<width; x++){
                newMatrix[x][height - 1- y] = matrix[y][x];
            }
        }

        return newMatrix;
    }
}
