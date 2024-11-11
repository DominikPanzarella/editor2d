package ch.supsi.editor2d.controller;

import ch.supsi.editor2d.service.algorithm.Filter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FiltersControllerTest {

    private FiltersController filtersController;

    @BeforeEach
    public void setUp() {
        filtersController = FiltersController.getInstance();
    }

    @Test
    public void testGetFiltersName() {
        List<Filter> filters = filtersController.getFiltersName();

        // Assicurati che la lista non sia vuota
        assertFalse(filters.isEmpty(), "La lista dei filtri non dovrebbe essere vuota");

        // Controlla che ci siano filtri e che siano nomi di classi valide
        for (Filter filter : filters) {
            try {
                // Verifica che la classe esista nel pacchetto
                Class<?> filterClass = Class.forName("ch.supsi.editor2d.service.algorithm." + filter.getName());

                // Assicurati che sia un filtro
                assertTrue(Filter.class.isAssignableFrom(filterClass), filter.getName() + " non è un filtro valido");
            } catch (ClassNotFoundException e) {
                assertTrue(false, "La classe " + filter.getName() + " non è stata trovata");
            }
        }
    }
}
