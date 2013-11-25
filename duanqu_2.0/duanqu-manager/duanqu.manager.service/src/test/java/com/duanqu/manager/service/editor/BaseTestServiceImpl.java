package com.duanqu.manager.service.editor;

import org.junit.runner.RunWith;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml" })

@Transactional
@TransactionConfiguration(transactionManager = "transactionManager")
public abstract class BaseTestServiceImpl extends
		AbstractTransactionalDataSourceSpringContextTests {
	static {
		// PropertyConfigurator.configure(BaseTestServiceImpl.class.getClassLoader().getResource("log4j.properties"));
	}
}