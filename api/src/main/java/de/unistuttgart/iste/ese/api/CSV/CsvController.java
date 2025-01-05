package de.unistuttgart.iste.ese.api.CSV;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import de.unistuttgart.iste.ese.api.ApiVersion1;

/**
 * REST Controller for handling CSV download requests.
 * This controller provides an endpoint for downloading ToDo data as a CSV file.
 */
@RestController
@ApiVersion1
public class CsvController {

    private final CsvService csvService;

    /**
     * Constructs a CsvController with the specified CsvService.
     *
     * @param csvService The service responsible for generating CSV data.
     */
    public CsvController(CsvService csvService) {
        this.csvService = csvService;
    }

    /**
     * Endpoint for downloading ToDo data as a CSV file.
     * Generates a CSV string using the CsvService and returns it as a downloadable
     * file.
     *
     * @return A ResponseEntity containing the CSV data and appropriate HTTP headers
     *         for downloading.
     */
    @GetMapping("/csv-downloads/todos")
    public ResponseEntity<String> downloadCsv() {
        String csvData = csvService.generateCsv();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/csv"));
        headers.setContentDispositionFormData("attachment", "todos.csv");

        return ResponseEntity.ok()
                .headers(headers)
                .body(csvData);
    }
}