package com.jsf.utils.generate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: 数据库生成工具配置类
 * User: xujunfei
 * Date: 2018-06-15
 * Time: 11:42
 */
public class GenInfo {

    private String dbUrl;
    private String driver;
    private String schema;
    private String username;
    private String author;
    private String password;
    // 生成路径
    private String globalPath;
    // 是否去除前缀 true(t_user=>User)
    private Boolean removePrefix;
    // 指定表名
    private List<String> tables = new ArrayList<>();

    // builder
    public GenInfo url(String dbUrl) {
        this.dbUrl = dbUrl;
        return this;
    }

    public GenInfo driver(String driver) {
        this.driver = driver;
        return this;
    }

    public GenInfo schema(String schema) {
        this.schema = schema;
        return this;
    }

    public GenInfo username(String username) {
        this.username = username;
        return this;
    }

    public GenInfo password(String password) {
        this.password = password;
        return this;
    }

    public GenInfo path(String globalPath) {
        this.globalPath = globalPath;
        return this;
    }

    public GenInfo author(String author) {
        this.author = author;
        return this;
    }

    public GenInfo removePrefix(Boolean removePrefix) {
        this.removePrefix = removePrefix;
        return this;
    }

    public GenInfo tables(String... tables) {
        this.tables = Arrays.asList(tables);
        return this;
    }

    // get/set

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGlobalPath() {
        return globalPath;
    }

    public void setGlobalPath(String globalPath) {
        this.globalPath = globalPath;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Boolean getRemovePrefix() {
        if (removePrefix == null) {
            removePrefix = true;
        }
        return removePrefix;
    }

    public void setRemovePrefix(Boolean removePrefix) {
        this.removePrefix = removePrefix;
    }

    public List<String> getTables() {
        return tables;
    }

    public void setTables(List<String> tables) {
        this.tables = tables;
    }

    @Override
    public String toString() {
        return "GenInfo{" +
                "dbUrl='" + dbUrl + '\'' +
                ", driver='" + driver + '\'' +
                ", schema='" + schema + '\'' +
                ", username='" + username + '\'' +
                ", author='" + author + '\'' +
                ", password='" + password + '\'' +
                ", globalPath='" + globalPath + '\'' +
                ", removePrefix=" + removePrefix +
                ", tables=" + tables +
                '}';
    }
}
