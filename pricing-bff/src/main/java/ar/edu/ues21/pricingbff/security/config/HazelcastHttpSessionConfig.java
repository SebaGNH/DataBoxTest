package ar.edu.ues21.pricingbff.security.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapAttributeConfig;
import com.hazelcast.config.MapIndexConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.hazelcast.HazelcastFlushMode;
import org.springframework.session.hazelcast.HazelcastSessionRepository;
import org.springframework.session.hazelcast.PrincipalNameExtractor;
import org.springframework.session.hazelcast.config.annotation.web.http.EnableHazelcastHttpSession;
import org.springframework.web.filter.RequestContextFilter;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@EnableHazelcastHttpSession(maxInactiveIntervalInSeconds = 600, hazelcastFlushMode = HazelcastFlushMode.IMMEDIATE)
@Configuration
public class HazelcastHttpSessionConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(HazelcastHttpSessionConfig.class);

    @Value("${service.name}")
    private String serviceNme;

    @Bean
    public FilterRegistrationBean<RequestContextFilter> requestContextFilter() {
        FilterRegistrationBean<RequestContextFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new RequestContextFilter());
        registration.setOrder(Integer.MIN_VALUE + 1);
        registration.setName("requestContextFilter");
        return registration;
    }

    @Bean
    public HazelcastInstance hazelcastInstance() {
    	List<InetAddress> cluster;
        InetAddress myAddress;
        try {
            InetAddress addr1 = InetAddress.getLocalHost();
            InetAddress tmp = InetAddress.getByName(addr1.getHostName());
            myAddress = tmp;
            InetAddress[] peers = InetAddress.getAllByName("tasks." + serviceNme);
            
            cluster = Arrays.stream(peers).filter(a -> a.getHostAddress() != tmp.getHostAddress())
                    .collect(Collectors.toList());
        } catch (UnknownHostException e) {
            myAddress = null;
            cluster = new ArrayList<>();
        }


        var attributeConfig = new MapAttributeConfig()
                .setName(HazelcastSessionRepository.PRINCIPAL_NAME_ATTRIBUTE)
                .setExtractor(PrincipalNameExtractor.class.getName());

        var config = new Config();
        config.getMapConfig(HazelcastSessionRepository.DEFAULT_SESSION_MAP_NAME)
                .addMapAttributeConfig(attributeConfig).addMapIndexConfig(new MapIndexConfig(
                HazelcastSessionRepository.PRINCIPAL_NAME_ATTRIBUTE, false));

        var network = config.getNetworkConfig();
        if (myAddress != null) {
            LOGGER.info("setting public address {}", myAddress.getHostAddress());
            network.getInterfaces().addInterface(myAddress.getHostAddress());
            network.setPublicAddress(myAddress.getHostAddress() + ":5701");
        }
        network.getJoin().getMulticastConfig().setEnabled(false);
        network.getJoin().getTcpIpConfig().setEnabled(true);

        if (cluster != null && !cluster.isEmpty()) {
            LOGGER.info("Adding cluster members [{}]", cluster);
            cluster.forEach(s -> network.getJoin().getTcpIpConfig().addMember(s.getHostAddress()));
        }

        return Hazelcast.newHazelcastInstance(config);
    }
}
