package lu.ftn.bank1service.converter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.yaml")
public class KeyProperty {

    public static String DATABASE_ENCRYPTION_KEY;

    @Value("${database.encryption.key}")
    public void setDatabase(String databaseEncryptionKey) {
        DATABASE_ENCRYPTION_KEY = databaseEncryptionKey;
    }

}
