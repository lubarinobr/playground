package com.matheus.playground.valueobject;

import com.matheus.playground.valueobject.commands.EmailDomainCommand;
import com.matheus.playground.valueobject.observables.CustomListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j
@Component
public class DomainPreventionListener implements CustomListener<EmailDomainCommand> {

    private final Set<String> trustedDomains = Set.of("gmail.com", "hotmail.com", "yahoo.com");

    @Override
    public void run(EmailDomainCommand emailDomainCommand) {
        if (trustedDomains.contains(emailDomainCommand.domain())) {
          log.info("Domain {} is trusted", emailDomainCommand.domain());
          return;
        }

        log.info("Domain {} is not trusted", emailDomainCommand.domain());
    }

    @Override
    public Class<EmailDomainCommand> getType() {
        return EmailDomainCommand.class;
    }
}
