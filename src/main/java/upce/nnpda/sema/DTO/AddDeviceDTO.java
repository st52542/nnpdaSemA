package upce.nnpda.sema.DTO;

import upce.nnpda.sema.Entity.TypeOfSenzor;

public class AddDeviceDTO {
    private String description;
    private TypeOfSenzor type;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TypeOfSenzor getType() {
        return type;
    }

    public void setType(TypeOfSenzor type) {
        this.type = type;
    }
}
