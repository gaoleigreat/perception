package com.lego.framework.mybatis.config;/*
package com.framework.mybatis.config;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.transaction.UserTransaction;

*/
/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/9/23 12:54
 * @desc :
 *//*

@Configuration
public class TransactionManagerConfig {

    */
/**
     * 分布式事务使用JTA管理，不管有多少个数据源只要配置一个 JtaTransactionManager
     * @return
     *//*

    @Bean(value = "xatx")
    public JtaTransactionManager transactionManager(){
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        UserTransaction userTransaction = new UserTransactionImp();
        return new JtaTransactionManager(userTransaction, userTransactionManager);
    }

}
*/
