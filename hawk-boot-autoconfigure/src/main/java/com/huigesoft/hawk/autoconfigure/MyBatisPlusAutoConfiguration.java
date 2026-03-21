/*
 * Copyright 2025-present the HuiGeSoft or Wangwenhui.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Email: wangwha@126.com
 * 
 */
package com.huigesoft.hawk.autoconfigure;

import static com.huigesoft.hawk.commons.constant.Hawk.TITLE;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusPropertiesCustomizer;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.MybatisMapWrapperFactory;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.huigesoft.hawk.core.mp.HawkMetaObjectHandler;
import com.huigesoft.hawk.core.mp.HawkTenantLineHandler;

/**
 * @ClassName: MyBatisPlusAutoConfiguration
 *
 * @Description: Mybatis Plus 自动配置类
 *
 * @author: 王文辉
 *
 * @date: 2025年9月5日 11:43:42
 * 
 */
@EnableTransactionManagement
@AutoConfiguration
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore(MybatisPlusAutoConfiguration.class)
public class MyBatisPlusAutoConfiguration {

	private Logger logger = LoggerFactory.getLogger(MyBatisPlusAutoConfiguration.class);

	/**
	 * 
	 * @Title: plusPropertiesCustomizer
	 *
	 * @Description: Mybatis Plus 全局缓存配置方法
	 * 
	 * @param: @param  metaObjectHandler 数据填充类
	 * @param: @return
	 *
	 * @return: MybatisPlusPropertiesCustomizer
	 * 
	 * @date: 2023年12月23日 下午9:03:33
	 *
	 * @throws
	 */
	@Bean
	MybatisPlusPropertiesCustomizer plusPropertiesCustomizer(MetaObjectHandler metaObjectHandler) {
		logger.info("[{}] Enable Mybatis GlobalConfig", TITLE);
		return plusProperties -> {
			GlobalConfig globalConfig = plusProperties.getGlobalConfig();
			globalConfig.setBanner(false);
			globalConfig.setEnableSqlRunner(true);
			globalConfig.setMetaObjectHandler(metaObjectHandler);
			globalConfig.getDbConfig().setUpdateStrategy(FieldStrategy.ALWAYS);
			plusProperties.setMapperLocations(new String[] { "classpath:/mapper/*Mapper.xml" });
		};
	}

	/**
	 * 
	 * @Title: mybatisConfigurationCustomizer
	 *
	 * @Description: Mybatis Plus 全局配置，主要配置了下划线转驼峰
	 * 
	 * @param: @return
	 *
	 * @return: ConfigurationCustomizer
	 * 
	 * @date: 2023年12月23日 下午9:04:03
	 *
	 * @throws
	 */
	@Bean
	ConfigurationCustomizer mybatisConfigurationCustomizer() {
		logger.info("[{}] Enable SQL Query MapUnderscoreToCamelCase", TITLE);
		return new ConfigurationCustomizer() {
			@Override
			public void customize(MybatisConfiguration configuration) {
				configuration.setObjectWrapperFactory(new MybatisMapWrapperFactory());
			}
		};
	}

	/**
	 * 
	 * @Title: mybatisPlusInterceptor
	 *
	 * @Description: 自定义mybatis plus 拦截器，包含：租户拦截器、乐观锁、分页等拦截处理器
	 * 
	 * @param: @return
	 *
	 * @return: MybatisPlusInterceptor
	 * 
	 * @date: 2025年9月2日 下午9:02:17
	 *
	 * @throws
	 */
	@Bean
	MybatisPlusInterceptor mybatisPlusInterceptor() {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

		// 乐观锁插件
		interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
		logger.info("[{}] Add OptimisticLockerInnerInterceptor", TITLE);

		// 分页插件
		interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
		logger.info("[{}] Add PaginationInnerInterceptor", TITLE);

		// 多租户插件
		TenantLineInnerInterceptor tenantInterceptor = new TenantLineInnerInterceptor();
		tenantInterceptor.setTenantLineHandler(new HawkTenantLineHandler());
		interceptor.addInnerInterceptor(tenantInterceptor);
		logger.info("[{}] Add TenantLineInnerInterceptor", TITLE);

		// 防止全部更新或删除插件
		interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
		logger.info("[{}] Add BlockAttackInnerInterceptor", TITLE);

		return interceptor;
	}

	@Bean
	MetaObjectHandler hawkMetaObjectHandler() {
		return new HawkMetaObjectHandler();
	}
}
