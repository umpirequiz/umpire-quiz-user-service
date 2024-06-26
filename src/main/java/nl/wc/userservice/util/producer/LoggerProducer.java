package nl.wc.userservice.util.producer;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.InjectionPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class LoggerProducer {
    @Produces
    public Logger producer(InjectionPoint iP) {
        return LoggerFactory
                .getLogger(iP
                        .getMember()
                        .getDeclaringClass()
                        .getName());
    }

}
