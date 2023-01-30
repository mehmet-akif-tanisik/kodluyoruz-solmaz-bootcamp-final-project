package com.solmaz.ticketplannermainservice.configuration;

import com.solmaz.ticketplannermainservice.dto.request.VoyageRequest;
import com.solmaz.ticketplannermainservice.dto.response.VoyageResponse;
import com.solmaz.ticketplannermainservice.model.Voyage;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class ModelMapperConfiguration {

    private final ModelMapper modelMapper = new ModelMapper();

    @Bean
    public ModelMapper getModelMapper() {

        stringToLocalDateTimeConverter();
        localDateTimeToStringConverter();

        return modelMapper;
    }

    private void localDateTimeToStringConverter() {
        Converter<LocalDateTime, String> toString = new AbstractConverter<LocalDateTime, String>() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            @Override
            protected String convert(LocalDateTime source) {
                return source.format(formatter);
            }
        };
        modelMapper.addConverter(toString);
        modelMapper.createTypeMap(Voyage.class, VoyageResponse.class)
                .addMappings(mapper -> mapper.using(toString)
                        .map(Voyage::getVoyageDateTime, VoyageResponse::setVoyageDateTime));
    }

    private void stringToLocalDateTimeConverter() {
        Converter<String, LocalDateTime> toLocalDateTime = new AbstractConverter<String, LocalDateTime>() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            @Override
            protected LocalDateTime convert(String source) {
                return LocalDateTime.parse(source, formatter);
            }
        };
        modelMapper.addConverter(toLocalDateTime);

        modelMapper.createTypeMap(VoyageRequest.class, Voyage.class)
                .addMappings(mapper -> mapper.using(toLocalDateTime)
                        .map(VoyageRequest::getVoyageDateTime, Voyage::setVoyageDateTime));
    }
}
