package de.unistuttgart.iste.ese.api.CSV;

import java.io.StringWriter;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import de.unistuttgart.iste.ese.api.ToDo.ToDo;

public class CsvHelper {

    // Converts a list of ToDos into a CSV string
    public static String toCsv(List<ToDo> todos) {
        try (
                // StringWriter to collect the CSV content
                StringWriter writer = new StringWriter();

                // CSVPrinter to format and write the CSV content
                CSVPrinter csvPrinter = new CSVPrinter(writer,
                        CSVFormat.Builder.create(CSVFormat.DEFAULT)
                                .setHeader("id", "title", "description", "finished", "assignees", "createdDate",
                                        "dueDate", "finishedDate", "category") // Define headers
                                .build())) {

            for (ToDo todo : todos) {
                // Combine assignees into a single string with "+" as the delimiter
                String assignees = todo.getAssigneeList().stream()
                        .map(a -> a.getPrename() + " " + a.getName())
                        .reduce((a, b) -> a + "+" + b)
                        .orElse(""); // If no assignees, use an empty string

                // Write each ToDo as a row in the CSV
                csvPrinter.printRecord(
                        todo.getId(),
                        todo.getTitle(),
                        todo.getDescription(),
                        todo.isFinished(),
                        assignees, // Assignees combined as a string
                        todo.getCreatedDate() != 0
                                ? Instant.ofEpochMilli(todo.getCreatedDate()).atZone(ZoneId.systemDefault())
                                        .toLocalDate() // Format createdDate
                                : "", // Leave empty if not present
                        todo.getDueDate() != null
                                ? Instant.ofEpochMilli(todo.getDueDate()).atZone(ZoneId.systemDefault()).toLocalDate() // Format
                                                                                                                       // dueDate
                                : "", // Leave empty if null
                        todo.getFinishedDate() != 0
                                ? Instant.ofEpochMilli(todo.getFinishedDate()).atZone(ZoneId.systemDefault())
                                        .toLocalDate() // Format finishedDate
                                : "", // Leave empty if default (0)
                        todo.getCategory());
            }

            // Flush and return the CSV content as a string
            csvPrinter.flush();
            return writer.toString();

        } catch (Exception e) {
            // Handle exceptions and log the error
            throw new RuntimeException("Failed to generate CSV", e);
        }
    }

}