package com.duanqu.datacenter.service.test;

import org.junit.runner.RunWith;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(  
  locations={  
   "classpath:applicationContext.xml"     
  }     
)

@Transactional
@TransactionConfiguration(transactionManager="transactionManager" )  
public abstract class BaseTest extends
		AbstractTransactionalDataSourceSpringContextTests {
	
}