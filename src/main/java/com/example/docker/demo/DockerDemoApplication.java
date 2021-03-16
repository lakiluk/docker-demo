package com.example.docker.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.util.List;

@SpringBootApplication
public class DockerDemoApplication extends WebMvcConfigurationSupport {

    @Autowired
    private ServerDataInterceptor serverDataInterceptor;

    public static void main(String[] args) {
        SpringApplication.run(DockerDemoApplication.class, args);
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(serverDataInterceptor);
    }
}

@RestController
@RequestMapping("/")
class CitiesController {

    @Autowired
    private CitiesRepository citiesRepository;

    @GetMapping
    public List<City> getCities() {
        return citiesRepository.findAll();
    }

    @PostMapping
    public City save(@RequestBody City city) {
        return citiesRepository.save(city);
    }

    @GetMapping("/kill")
    public void kill(){
        System.exit(0);
    }

}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
class City {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

}

@Repository
interface CitiesRepository extends JpaRepository<City, Long> {
}

@Component
class ServerDataInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        String hostName = InetAddress.getLocalHost().getHostName();
        response.addHeader("hostAddress", hostAddress);
        response.addHeader("hostName", hostName);
        response.addHeader("version", "1");
        return true;
    }
}
