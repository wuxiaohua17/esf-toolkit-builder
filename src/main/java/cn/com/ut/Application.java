package cn.com.ut;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;

//@EnableDiscoveryClient
@Configuration
@ComponentScan
@PropertySource({ "classpath:/cn/com/ut/config/properties/database.properties" })
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class Application extends BaseApplication {

	public static void main(String[] args) {

		SpringApplication application = new SpringApplication(Application.class);
		application.setBannerMode(Banner.Mode.OFF);
		application.run(args);
	}
}
