package de.unistuttgart.iste.ese.api.Assignee;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Represents an Assignee entity in the ToDo application.
 * Contains details about an assignee such as prename, name, and email.
 * 
 * 
 * Validation rules ensure:
 * - Prename and name must not be null or empty.
 * - Email must match the pattern of a valid Uni-Stuttgart email.
 * 
 */
@Entity
@Table(name = "assignees")
public class Assignee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Automatically generates a unique ID
    private long id;

    @NotNull(message = "Prename cannot be null")
    @Size(min = 1, message = "Prename must not be empty")
    private String prename;

    @NotNull(message = "Name cannot be null")
    @Size(min = 1, message = "Name must not be empty")
    private String name;

    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.uni-stuttgart\\.de$", message = "Email must be valid and end with uni-stuttgart.de")
    private String email;

    /**
     * Default constructor for JPA.
     */
    public Assignee() {
    }

    /**
     * Parameterized constructor to initialize an Assignee with the given details.
     *
     * @param prename the first name of the assignee
     * @param name    the last name of the assignee
     * @param email   the email address of the assignee
     */
    public Assignee(String prename, String name, String email) {
        this.prename = prename;
        this.name = name;
        this.email = email;
    }

    // Getters and Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPrename() {
        return prename;
    }

    public void setPrename(String prename) {
        this.prename = prename;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}