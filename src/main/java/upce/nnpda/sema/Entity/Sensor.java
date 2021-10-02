package upce.nnpda.sema.Entity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 45, nullable = false,unique = true)
    private String description;
    @Column(length = 45)
    private TypeOfSenzor type;
    @ManyToOne
    private Device device;

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

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public TypeOfSenzor getType() {
        return type;
    }

    public void setType(TypeOfSenzor type) {
        this.type = type;
    }
}
