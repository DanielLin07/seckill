package com.daniel.seckill;

import com.daniel.seckill.util.SecurityUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SeckillApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
    public void testSecurityUtil() {

	    String username = "daniel";
	    String password = "675844";

        Map<String, String> passwordAndSalt = SecurityUtil.genEncryptPasswordAndSalt(username, password);
        System.out.println("password:" + passwordAndSalt.get("password"));
        System.out.println("salt:" + passwordAndSalt.get("salt"));
    }

}
