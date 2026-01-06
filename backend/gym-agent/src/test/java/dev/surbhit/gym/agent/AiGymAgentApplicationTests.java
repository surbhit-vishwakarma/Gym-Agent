package dev.surbhit.gym.agent;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest(
		properties = {
				"embabel.agent.enabled=false"
		}
)
class AiGymAgentApplicationTests {

	@Test
	void contextLoads() {
	}

}
