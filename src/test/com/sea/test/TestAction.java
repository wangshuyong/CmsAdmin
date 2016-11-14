package test.com.sea.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.sea.action.RegisterAction;
import com.sea.util.FileUtils;

public class TestAction {

	@Test
	public void test() {
		RegisterAction ra = new RegisterAction();
		
		assertEquals("success", ra.excute());
	}
	@Test
	public void testProperty() {
		String s =FileUtils.getInstance().getProperty("uploadFilePath");
		System.out.println(s);
	}

}
