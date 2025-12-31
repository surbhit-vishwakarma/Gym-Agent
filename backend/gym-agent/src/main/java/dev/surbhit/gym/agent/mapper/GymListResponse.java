package dev.surbhit.gym.agent.mapper;

import dev.surbhit.gym.agent.model.db.Gym;

import java.util.Objects;

public class GymListResponse {
    private Gym gym;

    public GymListResponse() {
    }

    public Gym getGym() {
        return gym;
    }

    public void setGym(Gym gym) {
        this.gym = gym;
    }

    public GymListResponse(Gym gym) {
        this.gym = gym;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GymListResponse that = (GymListResponse) o;
        return Objects.equals(gym, that.gym);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(gym);
    }

    @Override
    public String toString() {
        return "GymListResponse{" +
                "gym=" + gym +
                '}';
    }
}
