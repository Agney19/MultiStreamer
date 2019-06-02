package app.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.Assert;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.concurrent.Callable;

public class TestUtils {
	private static final ObjectMapper mapper = new Jackson2ObjectMapperBuilder()
			.featuresToEnable(SerializationFeature.INDENT_OUTPUT)
			.featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
			.build();

	public static byte[] toJsonBytes(Object object) {
		return wrapException(() -> mapper.writeValueAsBytes(object));
	}

	public static Matcher<Number> decimal(BigDecimal decimal) {
		return new NumberMatcher(decimal);
	}

	private TestUtils() {
		throw new RuntimeException("not instantiable");
	}

	private static <R> R wrapException(Callable<R> codeBlock) {
		try {
			return codeBlock.call();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static class NumberMatcher extends BaseMatcher<Number> {
		private BigDecimal wanted;

		private NumberMatcher(Number wanted) {
			Objects.requireNonNull(wanted);
			this.wanted = new BigDecimal(wanted.toString());
		}

		@Override
		public boolean matches(Object item) {
			return item instanceof Number && new BigDecimal(item.toString()).compareTo(wanted) == 0;
		}

		@Override
		public void describeTo(Description description) {
			description.appendText(String.valueOf(wanted));
		}
	}
}
