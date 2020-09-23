package com.wuzx.gendbdoc;

import cn.smallbun.screw.core.Configuration;
import cn.smallbun.screw.core.engine.EngineConfig;
import cn.smallbun.screw.core.engine.EngineFileType;
import cn.smallbun.screw.core.engine.EngineTemplateType;
import cn.smallbun.screw.core.execute.DocumentationExecute;
import cn.smallbun.screw.core.process.ProcessConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class GenDbDocApplication {




    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(GenDbDocApplication.class, args);
        DataSource dataSource = applicationContext.getBean(DataSource.class);

        //配置
        Configuration config = Configuration.builder()
                //版本
                .version("1.0.0")
                //描述
                .description("数据库设计文档生成")
                //数据源
                .dataSource(dataSource)
                //生成配置
                .engineConfig(getEngineConfig())
                //生成配置
                .produceConfig(getProcessConfig())
                .build();
        //执行生成
        new DocumentationExecute(config).execute();

    }

    //生成配置
    private static EngineConfig getEngineConfig() {
        return EngineConfig.builder()
                //生成文件路径
                .fileOutputDir("D:\\")
                //打开目录
                .openOutputDir(true)
                //文件类型(HTML,WORD,MD)
                .fileType(EngineFileType.HTML)
                //生成模板实现
                .produceType(EngineTemplateType.freemarker)
                //自定义文件名称
//                .fileName("test数据库")
                .build();
    }

    /**
     * 配置想要生成的表+ 配置想要忽略的表
     *
     * @return 生成表配置
     */
    public static ProcessConfig getProcessConfig() {
        // 忽略表名
        List<String> ignoreTableName = new ArrayList<>();
        // 忽略表前缀，如忽略a开头的数据库表
        List<String> ignorePrefix = new ArrayList<>();
        // 忽略表后缀
        List<String> ignoreSuffix = new ArrayList<>();
        return ProcessConfig.builder()
                //指定生成逻辑、当存在指定表、指定表前缀、指定表后缀时，将生成指定表，其余表不生成、并跳过忽略表配置
                //根据名称指定表生成
                .designatedTableName(new ArrayList<>())
                //根据表前缀生成
                .designatedTablePrefix(new ArrayList<>())
                //根据表后缀生成
                .designatedTableSuffix(new ArrayList<>())
                //忽略表名
                .ignoreTableName(ignoreTableName)
                //忽略表前缀
                .ignoreTablePrefix(ignorePrefix)
                //忽略表后缀
                .ignoreTableSuffix(ignoreSuffix).build();
    }

}
