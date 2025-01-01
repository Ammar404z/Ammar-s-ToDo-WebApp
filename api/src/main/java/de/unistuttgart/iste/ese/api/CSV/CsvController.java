package de.unistuttgart.iste.ese.api.CSV;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import de.unistuttgart.iste.ese.api.ApiVersion1;

@RestController
@ApiVersion1
public class CsvController {

    private final CsvService csvService;

    public CsvController(CsvService csvService) {
        this.csvService = csvService;
    }

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