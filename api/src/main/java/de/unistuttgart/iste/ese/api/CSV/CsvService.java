package de.unistuttgart.iste.ese.api.CSV;

import java.util.List;

import org.springframework.stereotype.Service;

import de.unistuttgart.iste.ese.api.ToDo.ToDo;
import de.unistuttgart.iste.ese.api.ToDo.ToDoRepository;

/**
 * Service class for handling CSV generation logic.
 * This class interacts with the ToDoRepository to fetch ToDo entities and uses
 * CsvHelper to generate CSV output.
 */
@Service
public class CsvService {

    private final ToDoRepository toDoRepository;

    /**
     * Constructor for CsvService.
     * 
     * @param toDoRepository The repository for accessing ToDo entities from the
     *                       database.
     */
    public CsvService(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    /**
     * Generates a CSV string containing all ToDo entities in the database.
     * 
     * @return A CSV formatted string representing all ToDo entities.
     */
    public String generateCsv() {
        // we cast here since findAll returns an Iterable object
        List<ToDo> todos = (List<ToDo>) toDoRepository.findAll();
        return CsvHelper.toCsv(todos);
    }
}