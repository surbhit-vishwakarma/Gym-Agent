package dev.surbhit.gym.agent.model.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "calories")
public class Calorie {

    @Id
    @GeneratedValue
    private UUID id;

    private String gender;
    private String activityLevel;
    private String target;
    private int weight;
    private int height;

    @Override
    public String toString() {
        return "Calorie{" +
                "id=" + id +
                ", gender='" + gender + '\'' +
                ", activityLevel='" + activityLevel + '\'' +
                ", target='" + target + '\'' +
                ", weight=" + weight +
                ", height=" + height +
                ", age=" + age +
                ", targetCalories=" + targetCalories +
                ", actualCalories=" + actualCalories +
                ", date=" + date +
                '}';
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    private int age;
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Calorie calorie = (Calorie) o;
        return weight == calorie.weight && height == calorie.height && targetCalories == calorie.targetCalories && actualCalories == calorie.actualCalories && Objects.equals(id, calorie.id) && Objects.equals(gender, calorie.gender) && Objects.equals(activityLevel, calorie.activityLevel) && Objects.equals(target, calorie.target) && Objects.equals(date, calorie.date) && Objects.equals(appUser, calorie.appUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, gender, activityLevel, target, weight, height, targetCalories, actualCalories, date, appUser);
    }

    public String getActivityLevel() {
        return activityLevel;
    }

    public void setActivityLevel(String activityLevel) {
        this.activityLevel = activityLevel;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    private int targetCalories;
    private int actualCalories;

    private LocalDate date;

    public Calorie() {
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getTargetCalories() {
        return targetCalories;
    }

    public void setTargetCalories(int targetCalories) {
        this.targetCalories = targetCalories;
    }

    public int getActualCalories() {
        return actualCalories;
    }

    public void setActualCalories(int actualCalories) {
        this.actualCalories = actualCalories;
    }
    @JsonIgnore
    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser appUser;
}