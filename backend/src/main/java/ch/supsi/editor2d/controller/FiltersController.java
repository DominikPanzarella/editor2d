package ch.supsi.editor2d.controller;

import ch.supsi.editor2d.service.ImageService;
import ch.supsi.editor2d.service.algorithm.Applicable;
import ch.supsi.editor2d.service.algorithm.Filter;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class FiltersController {

    private static FiltersController mySelf;

    public static FiltersController getInstance() {
        if (mySelf     == null) {
            mySelf = new FiltersController();
        }
        return mySelf;
    }

    protected FiltersController(){}

    public List<Filter> getFiltersName() {
        List<Filter> filters = new ArrayList<>();
        try {
            // Cerca tutte le classi nel package
            List<Class<?>> classes = getClasses("ch.supsi.editor2d.service.algorithm");
            for (Class<?> clazz : classes) {
                // Verifica se la classe è un filtro, non è un'interfaccia, non è astratta e non è anonima
                if (Filter.class.isAssignableFrom(clazz) && !clazz.isInterface() && !Modifier.isAbstract(clazz.getModifiers()) && !clazz.isAnonymousClass()) {
                    Filter filter = (Filter) clazz.getDeclaredConstructor().newInstance();
                    filters.add(filter);
                }
            }
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace(); // Gestione errori
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return filters;
    }

    private String decodeUrl(String url) throws UnsupportedEncodingException {
        return URLDecoder.decode(url, StandardCharsets.UTF_8.name());
    }

    private List<Class<?>> getClasses(String packageName) throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            // Decodifica l'URL prima di utilizzarlo come file
            String decodedPath = decodeUrl(resource.getFile());
            dirs.add(new File(decodedPath));
        }
        List<Class<?>> classes = new ArrayList<>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes;
    }

    private List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        if (files == null) {
            return classes;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                // Rimuove l'estensione .class e carica la classe
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }
}
