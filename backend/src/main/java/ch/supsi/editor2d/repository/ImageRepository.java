package ch.supsi.editor2d.repository;

import ch.supsi.editor2d.controller.TranslationsController;
import ch.supsi.editor2d.repository.reader.PBMReader;
import ch.supsi.editor2d.repository.reader.PGMReader;
import ch.supsi.editor2d.repository.reader.PNMReader;
import ch.supsi.editor2d.repository.reader.PPMReader;
import ch.supsi.editor2d.repository.writer.PBMWriter;
import ch.supsi.editor2d.repository.writer.PGMWriter;
import ch.supsi.editor2d.repository.writer.PNMWriter;
import ch.supsi.editor2d.repository.writer.PPMWriter;
import ch.supsi.editor2d.service.IImageRepository;
import ch.supsi.editor2d.service.model.ImageWrapper;
import ch.supsi.editor2d.utils.exceptions.FileReadingException;

import java.io.*;
import java.util.Set;
import java.util.HashSet;

public class ImageRepository implements IImageRepository {
    private static final Set<String> SUPPORTED_EXTENSIONS = new HashSet<>(Set.of("PBM", "PGM", "PPM"));

    private final PNMReader reader;
    private final PNMWriter writer;
    private static IImageRepository instance;
    protected TranslationsController translationsController = TranslationsController.getInstance();


    public static IImageRepository getInstance() {
        if (instance == null) {
            instance = new ImageRepository();
        }
        return instance;
    }

    private ImageRepository() {
        PNMReader pbmReader = new PBMReader();
        PNMReader pgmReader = new PGMReader();
        PNMReader ppmReader = new PPMReader();

        // Impostiamo la catena
        pbmReader.setSuccessor(pgmReader);
        pgmReader.setSuccessor(ppmReader);

        this.reader = pbmReader; // Il primo lettore nella catena

        PNMWriter pbmWriter = new PBMWriter();
        PNMWriter pgmWriter = new PGMWriter();
        PNMWriter ppmWriter = new PPMWriter();

        // Impostiamo la catena
        pbmWriter.setSuccessor(pgmWriter);
        pgmWriter.setSuccessor(ppmWriter);

        this.writer = pbmWriter; // Il primo scrittore nella catena
    }

    @Override
    public ImageWrapper handleLoadImage(final String path, final String extension) throws FileReadingException, IOException {
        // Check for extension; the reader is responsible for the reading only
        if (!SUPPORTED_EXTENSIONS.contains(extension.toUpperCase())) {
            throw new FileReadingException(translationsController.translate("label.cantHandle"));
        }
        return reader.read(path);
    }

    @Override
    public boolean handleExportImage(final String path, final String extension, ImageWrapper image) throws IOException {
        // Implementa l'export dell'immagine se necessario
        if (!SUPPORTED_EXTENSIONS.contains(extension.toUpperCase())) {
            throw new FileReadingException(translationsController.translate("label.cantHandle"));
        }
        return writer.write(path, extension, image);
    }
}
