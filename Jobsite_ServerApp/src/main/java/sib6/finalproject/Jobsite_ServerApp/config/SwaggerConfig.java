package sib6.finalproject.Jobsite_ServerApp.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("JOB-SITE OPEN API")
                        .description(
                                "<br><br> <b>USER</b>: <br>" +
                                "GET /api/user<br>" +
                                "GET /api/user/{id}<br>" +
                                "GET /api/user/profile<br>" +
                                "GET /api/job<br>" +
                                "GET /api/job/{id}<br>" +
                                "GET /api/company<br>" +
                                "GET /api/company/{id}<br>" +
                                "POST /api/applicant/create/{jobId}<br>" +
                                "POST /api/feedback/create/{applicantId}<br>" +
                                "PUT /api/user/update<br>" +
                                "DELETE /api/user/delete<br>" +

                                "<br><b>COMPANY:</b><br>" +
                                "GET /api/company<br>" +
                                "GET /api/company/{id}<br>" +
                                "GET /api/company/profile<br>" +
                                "GET /api/user<br>" +
                                "GET /api/user/{id}<br>" +
                                "GET /api/job<br>" +
                                "GET /api/job/{id}<br>" +
                                "POST /api/job/create<br>" +
                                "PUT /api/applicant/update-status/{id}<br>" +
                                "PUT /api/job/update/{id}<br>" +
                                "PUT /api/job/update/status-job/{id}<br>" +
                                "PUT /api/company/update<br>" +
                                "DELETE /api/job/delete/{id}<br>" +
                                "DELETE /api/company/delete<br>" +

                                "<br><b>ADMIN: </b> <br>" +
                                "GET /api/company<br>" +
                                "GET /api/company/{id}<br>" +
                                "GET /api/user<br>" +
                                "GET /api/user/{id}<br>" +
                                "DELETE /api/user/delete<br>" +
                                "DELETE /api/company/delete<br>" +

                                "<br><b>ALL PERMIT: </b> <br>" +
                                "POST /api/auth/login<br>" +
                                "POST /api/auth/register/user<br>" +
                                "POST /api/auth/register/admin<br>" +
                                "POST /api/auth/register/company<br>" +
                                "GET /api/company<br>" +
                                "GET /api/company/{id}<br>" +
                                "GET /api/user<br>" +
                                "GET /api/user/{id}<br>" +
                                "GET /api/job<br>" +
                                "GET /api/job/{id}<br>"
                        )
                        .version("1.0.0"));
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}
