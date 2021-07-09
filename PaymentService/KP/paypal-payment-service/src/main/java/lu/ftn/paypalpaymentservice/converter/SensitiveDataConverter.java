package lu.ftn.paypalpaymentservice.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = false)
public class SensitiveDataConverter implements AttributeConverter<String, String> {

    private static final String SECRET_ENCRYPTION_KEY = "MySuperSecretKey";

    @Override
    public String convertToDatabaseColumn(String attribute) {
        return null;
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return null;
    }
}
