package cn.cebest.framework.conf;

import org.mybatis.spring.annotation.MapperScan;
import org.quartz.Scheduler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import cn.cebest.framework.quartz.QuartzTaskService;
import cn.cebest.framework.quartz.SpringJobFactory;
import cn.cebest.framework.quartz.impl.QuartzTaskServiceImpl;


/**
 *  定时任务配置
  * @author maming  
  * @date 2018年8月28日
 */
@Configuration
@ConditionalOnClass(QuartzTaskService.class)
@ConditionalOnProperty(prefix="gc.quartz", value="enable", matchIfMissing = false)
@MapperScan("cn.cebest.framework.quartz.dao")
public class QuartzConfig {

	@Bean
	@ConditionalOnMissingBean(SpringJobFactory.class)
	public SpringJobFactory springJobFactory(){
		return new SpringJobFactory(); 
	}
	
	@Bean
	@ConditionalOnMissingBean(QuartzTaskService.class)
	public QuartzTaskService quartzTaskService(){
		return new QuartzTaskServiceImpl();
	}
	
	@Bean
	public SchedulerFactoryBean schedulerFactoryBean() {
		SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
		schedulerFactoryBean.setJobFactory(springJobFactory());
		 //延时启动 应用启动完20秒后 QuartzScheduler 再启动 
        schedulerFactoryBean.setStartupDelay(20);
		return schedulerFactoryBean;
	}

	@Bean
	public Scheduler scheduler() {
		return schedulerFactoryBean().getScheduler();
	}
}
