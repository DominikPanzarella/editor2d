package ch.supsi.editor2d.controller;

import ch.supsi.editor2d.service.algorithm.Filter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class FiltersController {

    private static FiltersController mySelf;

    public static FiltersController getInstance() {
        if (mySelf == null) {
            mySelf = new FiltersController();
        }
        return mySelf;
    }

    protected FiltersController() {}

    public List<Filter> getFiltersName() {
        List<Filter> filters = new ArrayList<>();
        try {
            List<String> filterClassNames = loadFilterClassNames();
            for (String className : filterClassNames) {
                Class<?> clazz = Class.forName(className);
                if (Filter.class.isAssignableFrom(clazz)) {
                    Filter filter = (Filter) clazz.getDeclaredConstructor().newInstance();
                    filters.add(filter);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filters;
    }

    private List<String> loadFilterClassNames() throws IOException {
        List<String> filterClassNames = new ArrayList<>();
        Properties properties = new Properties();

        try (InputStream input = getClass().getClassLoader().getResourceAsStream("filters.properties")) {
            if (input == null) {
                throw new IOException("Cannot find filters.properties file");
            }
            properties.load(input);
            String filters = properties.getProperty("filters");
            if (filters != null) {
                String[] classNames = filters.split(",");
                for (String className : classNames) {
                    filterClassNames.add(className.trim());
                }
            }
        }
        return filterClassNames;
    }
}