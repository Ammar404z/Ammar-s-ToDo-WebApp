package de.unistuttgart.iste.ese.api.CSV;

import de.unistuttgart.iste.ese.api.ToDo.ToDo;
import de.unistuttgart.iste.ese.api.ToDo.ToDoRepository;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CsvService {
    private final ToDoRepository toDoRepository;

    public CsvService(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    public String generateCsv() {
        List<ToDo> todos = (List<ToDo>) toDoRepository.findAll(); //we cast here since findAll returns an Iterable object
        return CsvHelper.toCsv(todos);
    }
}