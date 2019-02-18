package vdsale.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class CustomDateDeserializer extends JsonDeserializer<Date> {

    private final static SimpleDateFormat FORMATTER = new SimpleDateFormat("dd-MM-yyyy");

    public CustomDateDeserializer() {
        super();
    }

    @Override
    public Date deserialize(final JsonParser p, final DeserializationContext ctxt) throws IOException {

        final String text = p.getText();
        if (text == null) {
            return null;
        }

        try {
            return FORMATTER.parse(text);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        log.info("M=DateConverter.CustomDateDeserializer.deserialize, message=Fail do convert date");
        return new Date();
    }
}
