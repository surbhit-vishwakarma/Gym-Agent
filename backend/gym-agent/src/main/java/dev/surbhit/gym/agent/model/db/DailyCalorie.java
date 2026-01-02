package dev.surbhit.gym.agent.model.db;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(
        name = "daily_calorie",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"user_id", "date"}
        )
)
public class DailyCalorie {

    @Id
    @GeneratedValue
    private UUID id;

    private int targetCalorie;
    private int currentCalorie;
    private LocalDate date;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    @Override
    public String toString() {
        return "DailyCalorie{" +
                "targetCalorie=" + targetCalorie +
                ", currentCalorie=" + currentCalorie +
                ", date=" + date +
                ", appUser=" + user +
                '}';
    }

    public DailyCalorie() {
    }

    public int getTargetCalorie() {
        return targetCalorie;
    }

    public void setTargetCalorie(int targetCalorie) {
        this.targetCalorie = targetCalorie;
    }

    public int getCurrentCalorie() {
        return currentCalorie;
    }

    public void setCurrentCalorie(int currentCalorie) {
        this.currentCalorie = currentCalorie;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public AppUser getAppUser() {
        return user;
    }

    public void setAppUser(AppUser appUser) {
        this.user = appUser;
    }
}
