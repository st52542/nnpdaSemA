package upce.nnpda.sema.Entity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 45, nullable = false)
    private String username;
    @Column(length = 100, nullable = false, unique = true)
    private String email;
    @Column(length = 100, nullable = false)
    private String password;
    @Column(length = 45, nullable = false)
    private String name;
    @Column(length = 45, nullable = false)
    private String surname;
    @OneToMany(mappedBy = "id")
    private Set<ListOfDevices> listOfDevices;

    public User(String firstname, String lastname, String username, String email, String encode) {
        this.name = firstname;
        this.surname = lastname;
        this.email = email;
        this.password = encode;
        this.username = username;
    }
    public User() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Set<ListOfDevices> getListOfDevices() {
        return listOfDevices;
    }

    public void setListOfDevices(Set<ListOfDevices> listOfDevices) {
        this.listOfDevices = listOfDevices;
    }

}
