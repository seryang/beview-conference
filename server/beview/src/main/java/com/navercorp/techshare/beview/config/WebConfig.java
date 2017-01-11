package com.navercorp.techshare.beview.config;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Created by Naver on 2017. 1. 10..
 */
@EnableWebMvc
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.defaultContentType(MediaType.APPLICATION_JSON);

		configurer.favorPathExtension(false)
			.favorParameter(true)
			.parameterName("format")
			.ignoreAcceptHeader(true)
			.useJaf(false)
			.defaultContentType(MediaType.APPLICATION_JSON)
			.mediaType("xml", MediaType.APPLICATION_XML)
			.mediaType("json", MediaType.APPLICATION_JSON);
	}

//	@Override
//	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//		StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
//		stringHttpMessageConverter.setSupportedMediaTypes(Arrays.asList(new MediaType("text", "html", Charset.forName("UTF-8"))));
//		stringHttpMessageConverter.setWriteAcceptCharset(false);
//
//		converters.add(new ByteArrayHttpMessageConverter());
//		converters.add(stringHttpMessageConverter);
//		converters.add(new SourceHttpMessageConverter<>());
//		converters.add(new FormHttpMessageConverter());
//		converters.add(new AllEncompassingFormHttpMessageConverter());
//		converters.add(new MappingJackson2HttpMessageConverter());
//
//		MappingJackson2HttpMessageConverter jackson = new MappingJackson2HttpMessageConverter();
//		jackson.setSupportedMediaTypes(Arrays.asList(new MediaType("application", "json", Charset.forName("UTF-8"))));
//		jackson.getObjectMapper().getSerializationConfig().without(SerializationFeature.FAIL_ON_EMPTY_BEANS);
//
//		converters.add(jackson);
//	}
}