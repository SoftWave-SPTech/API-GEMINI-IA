package softwave.api_gemini_ia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "softwave.api_gemini_ia.feign")
public class ApiGeminiIaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGeminiIaApplication.class, args);
	}

}
