package us.states.validation;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Disabled
@ExtendWith(SpringExtension.class)
public class ErrorCodeTest {

	@Test
	public void enumTest() {		
		assertSame(ErrorCode.ME01, ErrorCode.valueOf("ME01"));
	}
	
	@Test
	public void valueTest() {
		assertSame(ErrorCode.ME01, ErrorCode.valueOfDesc("Missing Element : id"));
	}
}
