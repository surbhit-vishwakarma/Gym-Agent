package dev.surbhit.gym.agent.model.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "gym_machines")
public class GymMachine {
    public GymMachine() {
    }

    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private String category;
    private String brand;

    @ManyToOne
    @JoinColumn(name = "gym_id", nullable = false)
    @JsonIgnore
    private Gym gym;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GymMachine that = (GymMachine) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(category, that.category) && Objects.equals(brand, that.brand) && Objects.equals(gym, that.gym);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, category, brand, gym);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Gym getGym() {
        return gym;
    }

    public void setGym(Gym gym) {
        this.gym = gym;
    }

    @Override
    public String toString() {
        return "GymMachine{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", brand='" + brand + '\'' +
                ", gym=" + gym +
                '}';
    }

    public GymMachine(UUID id, String name, String category, String brand, Gym gym) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.brand = brand;
        this.gym = gym;
    }
}