package dev.surbhit.gym.agent.model.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "gyms")
public class Gym {
    public Gym() {
    }

    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private String location;

    private UUID ownerId;

    @OneToMany(mappedBy = "gym", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GymMachine> machines = new ArrayList<>();

    @OneToMany(mappedBy = "gym")
    @JsonIgnore
    private List<AppUser> users = new ArrayList<>();

    public List<AppUser> getUsers() {
        return users;
    }

    public void setUsers(List<AppUser> users) {
        this.users = users;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    public List<GymMachine> getMachines() {
        return machines;
    }

    public void setMachines(List<GymMachine> machines) {
        this.machines = machines;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gym gym = (Gym) o;
        return Objects.equals(id, gym.id) && Objects.equals(name, gym.name) && Objects.equals(location, gym.location) && Objects.equals(ownerId, gym.ownerId) && Objects.equals(machines, gym.machines);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, location, ownerId, machines);
    }

    @Override
    public String toString() {
        return "Gym{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", ownerId=" + ownerId +
                '}';
    }

    public Gym(UUID id, String location, String name, UUID ownerId, List<GymMachine> machines) {
        this.id = id;
        this.location = location;
        this.name = name;
        this.ownerId = ownerId;
        this.machines = machines;
    }
}