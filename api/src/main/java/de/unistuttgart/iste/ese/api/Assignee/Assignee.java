package de.unistuttgart.iste.ese.api.Assignee;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "assignees")
public class Assignee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Automatische ID-Generierung
    private long id;

    @NotNull
    @Size(min = 1)
    private String prename;

    @NotNull
    @Size(min = 1)
    private String name;

    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.uni-stuttgart\\.de$",
        message = "Email must be valid and end with uni-stuttgart.de")
    private String email;

    // empty constructor for JPA
    public Assignee(){}

    public Assignee(String prename, String name, String email){
        this.prename = prename;
        this.name = name;
        this.email = email;
    }

    // getters and setters

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
