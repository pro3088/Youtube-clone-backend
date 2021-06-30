package hcb.gad.youtube_counterfeit.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("hcb.gad.youtube_counterfeit")
@EnableJpaRepositories("hcb.gad.youtube_counterfeit")
@EnableTransactionManagement
public class DomainConfig {
}
