package ika.mvctraining.config;

import ika.mvctraining.models.Person;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan("ika.mvctraining")                 // Ищем @Component/@Service/@Repository/@Controller в проекте
@EnableWebMvc                                    // Включаем Spring MVC
@PropertySource("classpath:hibernate.properties")// Грузим настройки БД/Hibernate из файла
@EnableTransactionManagement                     // Включаем поддержку @Transactional (через PlatformTransactionManager)
public class SpringConfig implements WebMvcConfigurer {

    private final ApplicationContext applicationContext;
    private final Environment env;

    @Autowired
    public SpringConfig(ApplicationContext applicationContext, Environment env) {
        this.applicationContext = applicationContext;
        this.env = env;
    }

    // ================== Thymeleaf (MVC) ==================

    /** Где лежат HTML-шаблоны и какой у них суффикс. */
    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver r = new SpringResourceTemplateResolver();
        r.setApplicationContext(applicationContext);
        r.setPrefix("/WEB-INF/views/");
        r.setSuffix(".html");
        return r;
    }

    /** Движок шаблонов Thymeleaf + поддержка Spring EL. */
    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine e = new SpringTemplateEngine();
        e.setTemplateResolver(templateResolver());
        e.setEnableSpringELCompiler(true);
        return e;
    }

    /** Подключаем Thymeleaf как ViewResolver для контроллеров. */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        ThymeleafViewResolver vr = new ThymeleafViewResolver();
        vr.setTemplateEngine(templateEngine());
        registry.viewResolver(vr);
    }

    // ================== Hibernate (чистый SessionFactory) ==================

    /** Источник подключений к БД. Значения берем из hibernate.properties. */
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(env.getRequiredProperty("hibernate.driver_class"));
        ds.setUrl(env.getRequiredProperty("hibernate.connection.url"));
        ds.setUsername(env.getRequiredProperty("hibernate.connection.username"));
        ds.setPassword(env.getRequiredProperty("hibernate.connection.password"));
        return ds;
    }

    /** Свойства Hibernate: диалект/лог/DDL/контекст сессии для Spring. */
    private Properties hibernateProps() {
        Properties p = new Properties();
        // Диалект можно не указывать — Hibernate 6 умеет определить, но оставлю, если у тебя есть в properties:
        if (env.containsProperty("hibernate.dialect"))
            p.put("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));

        p.put("hibernate.show_sql", env.getProperty("hibernate.show_sql", "true"));
        p.put("hibernate.format_sql", env.getProperty("hibernate.format_sql", "true"));
        p.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto", "update"));

        // Ключевая настройка: привязка currentSession к транзакции Spring
        p.put("hibernate.current_session_context_class",
                "org.springframework.orm.hibernate5.SpringSessionContext");

        // Если нужно: p.put("hibernate.jdbc.time_zone", "UTC");
        return p;
    }

    /**
     * Чистый Hibernate-бутстрап без JPA.
     * ВАЖНО: здесь нужно явным образом перечислить все @Entity классы.
     */
    @Bean
    public SessionFactory sessionFactory() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySettings(hibernateProps())
                .applySetting("hibernate.connection.datasource", dataSource())
                .build();

        MetadataSources sources = new MetadataSources(registry).addAnnotatedClass(Person.class);
                // TODO: перечисли свои сущности:
                // .addAnnotatedClass(ika.mvctraining.models.Person.class)
                // .addAnnotatedClass(ika.mvctraining.models.OtherEntity.class)
                ;

        Metadata metadata = sources.buildMetadata();
        return metadata.buildSessionFactory();
    }

    /** Менеджер транзакций под Hibernate SessionFactory — включает @Transactional для Session. */
    @Bean
    public PlatformTransactionManager transactionManager(SessionFactory sf) {
        return new HibernateTransactionManager(sf);
    }
}
