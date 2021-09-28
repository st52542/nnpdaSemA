package upce.nnpda.sema.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 45, nullable = false)
    private String description;
    @ManyToOne
    private Type type;
    @ManyToOne
    private Sensor sensor;
    @JsonIgnore
    @OneToMany(mappedBy = "id")
    private Set<ListOfDevices> listOfDevices;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public Set<ListOfDevices> getListOfDevices() {
        return listOfDevices;
    }

    public void setListOfDevices(Set<ListOfDevices> listOfDevices) {
        this.listOfDevices = listOfDevices;
    }

}
