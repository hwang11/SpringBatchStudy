package com.example.batch.job;

import com.example.batch.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.mybatis.spring.batch.builder.MyBatisPagingItemReaderBuilder;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@RequiredArgsConstructor
@Slf4j
@Configuration
public class UserJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;
    private final SqlSessionFactory sqlSessionFactory;

    private final String JOB_NAME = "user";
    private static final int chunkSize = 10;

    @Bean
    public Job job(){
        return jobBuilderFactory.get(JOB_NAME)
                .start(step())
                .build();
    }

    @Bean
    public Step step(){
        return stepBuilderFactory.get(JOB_NAME+"_step")
                .<User,User>chunk(chunkSize)
                .reader(reader())
                .build();
    }

    @Bean
    public MyBatisPagingItemReader<User> reader(){
        return new MyBatisPagingItemReaderBuilder<User>()
                .sqlSessionFactory(sqlSessionFactory)
                .queryId("com.example.batch.mapper.UserMapper.selectUser")
                .build();
    }
}
