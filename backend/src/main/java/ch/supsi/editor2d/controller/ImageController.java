package ch.supsi.editor2d.controller;

import ch.supsi.editor2d.service.ImageService;
import ch.supsi.editor2d.service.model.ImageWrapper;

import java.io.File;
import java.io.IOException;

public final class  ImageController {

    private IImageService serviceLayer;
    private static ImageController mySelf;

    public static ImageController getInstance() {
        if (mySelf     == null) {
            mySelf = new ImageController(ImageService.getInstance());
        }
        return mySelf;
    }

    private ImageController(IImageService serviceLayer){
        this.serviceLayer = serviceLayer;
    }

    public ImageWrapper loadImage(final String path) throws IOException {
        return serviceLayer.loadImage(path);
    }

    public boolean exportImage(final String path, final String extension, ImageWrapper image) throws IOException {
       return serviceLayer.exportImage(path, extension, image);
    }
}
