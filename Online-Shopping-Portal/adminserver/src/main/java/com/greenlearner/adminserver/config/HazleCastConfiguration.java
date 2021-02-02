package com.greenlearner.adminserver.config;

import com.hazelcast.config.*;
import com.hazelcast.map.merge.PutIfAbsentMapMergePolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static de.codecentric.boot.admin.server.config.AdminServerHazelcastAutoConfiguration.DEFAULT_NAME_EVENT_STORE_MAP;
import static de.codecentric.boot.admin.server.config.AdminServerHazelcastAutoConfiguration.DEFAULT_NAME_SENT_NOTIFICATIONS_MAP;
import static java.util.Collections.singletonList;

/**
 * @author - GreenLearner(https://www.youtube.com/c/greenlearner)
 */
@Configuration
public class HazleCastConfiguration {

    @Bean
    public Config hazelcastConfig() {
        // This map is used to store the events.
        // It should be configured to reliably hold all the data,
        // Spring Boot Admin will compact the events, if there are too many
        MapConfig eventStoreMap = new MapConfig(DEFAULT_NAME_EVENT_STORE_MAP).setInMemoryFormat(InMemoryFormat.OBJECT)
                .setBackupCount(1).setEvictionPolicy(EvictionPolicy.NONE)
                .setMergePolicyConfig(new MergePolicyConfig(PutIfAbsentMapMergePolicy.class.getName(), 100));

        // This map is used to deduplicate the notifications.
        // If data in this map gets lost it should not be a big issue as it will atmost
        // lead to
        // the same notification to be sent by multiple instances
        MapConfig sentNotificationsMap = new MapConfig(DEFAULT_NAME_SENT_NOTIFICATIONS_MAP)
                .setInMemoryFormat(InMemoryFormat.OBJECT).setBackupCount(1).setEvictionPolicy(EvictionPolicy.LRU)
                .setMergePolicyConfig(new MergePolicyConfig(PutIfAbsentMapMergePolicy.class.getName(), 100));

        Config config = new Config();
        config.addMapConfig(eventStoreMap);
        config.addMapConfig(sentNotificationsMap);
        config.setProperty("hazelcast.jmx", "true");

        // WARNING: This setups a local cluster, you change it to fit your needs.
        config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
        TcpIpConfig tcpIpConfig = config.getNetworkConfig().getJoin().getTcpIpConfig();
        tcpIpConfig.setEnabled(true);
        tcpIpConfig.setMembers(singletonList("127.0.0.1"));
        return config;
    }
}
