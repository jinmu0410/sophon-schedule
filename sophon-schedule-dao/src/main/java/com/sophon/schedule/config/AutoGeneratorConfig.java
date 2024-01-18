package com.sophon.schedule.config;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.sql.Types;
import java.util.Collections;

/**
 * TODO
 *
 * @Author jinmu
 * @Date 2023/8/11 16:22
 */
public class AutoGeneratorConfig {

    private static final String outPath = "/Users/jinmu/Downloads/self/sophon-schedule/sophon-schedule-dao" + "/src/main/java";
    private static final String outMapper = "/Users/jinmu/Downloads/self/sophon-schedule/sophon-schedule-dao" + "/src/main/resources/mapper";

    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://47.97.231.182:3306/ry-vue?serverTimezone=Asia/Shanghai", "aifish", "R#m9@L7s$yFhN23")
                .globalConfig(builder -> {
                    builder.author("jinmu") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(outPath); // 指定输出目录
                })
                .dataSourceConfig(builder -> builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                    int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                    if (typeCode == Types.TINYINT) {
                        // 自定义类型转换
                        return DbColumnType.INTEGER;
                    }
                    return typeRegistry.getColumnType(metaInfo);

                }))
                .packageConfig(builder -> {
                    builder.parent("com.aifishfish.member") // 设置父包名
                            //.moduleName("task") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, outMapper)); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("member_user,member_growth,member_benefit,member_grade,member_platform,member_group"); // 设置需要生成的表名
                            //.addTablePrefix(); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
