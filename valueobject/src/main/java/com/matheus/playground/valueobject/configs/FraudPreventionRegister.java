package com.matheus.playground.valueobject.configs;

import com.matheus.playground.valueobject.observables.CustomListener;
import com.matheus.playground.valueobject.observables.FraudPrevention;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FraudPreventionRegister {

    private final ApplicationContext applicationContext;

    @Bean
    public FraudPrevention fraudPrevention() {
        var fraudPrevention = new FraudPrevention();
        applicationContext.getBeansOfType(CustomListener.class)
                .forEach((a,b) -> fraudPrevention.addNewListener(b));
        return fraudPrevention;
    }
}
