package ch.supsi.editor2d.service;

import ch.supsi.editor2d.controller.IImageService;
import ch.supsi.editor2d.controller.ImageController;
import ch.supsi.editor2d.repository.ImageRepository;
import ch.supsi.editor2d.service.model.ImageWrapper;
import ch.supsi.editor2d.utils.exceptions.FileReadingException;

import java.io.File;
import java.io.IOException;

public class ImageService implements IImageService
{
    private IImageRepository repositoryLayer;
    private static ImageService mySelf;

    public static ImageService getInstance() {
        if (mySelf     == null) {
            mySelf = new ImageService(ImageRepository.getInstance());
        }
        return mySelf;
    }

    private ImageService(IImageRepository repositoryLayer){
        this.repositoryLayer = repositoryLayer;
    }

    @Override
    public ImageWrapper loadImage(String path) throws FileReadingException, IOException {
        int pointPosition = path.lastIndexOf(".");
        if(pointPosition == (-1))
            throw new FileReadingException("File extension not valid!");
        String extension = path.substring(pointPosition+1);
        return repositoryLayer.handleLoadImage(path, extension);
    }

    @Override
    public boolean exportImage(String path, String extension, ImageWrapper image) throws IOException {
        return repositoryLayer.handleExportImage(path,extension,image);
    }
}
