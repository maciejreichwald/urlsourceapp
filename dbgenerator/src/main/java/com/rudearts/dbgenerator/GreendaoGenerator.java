package com.rudearts.dbgenerator;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

public class GreendaoGenerator {
    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(2, "com.rudearts.urlsource.model.greendao");

        Entity treaty = schema.addEntity("SiteExternal");
        treaty.setDbName("site");
        treaty.addIdProperty().autoincrement();
        treaty.addStringProperty("url");
        treaty.addStringProperty("source");

        DaoGenerator generator = new DaoGenerator();
        generator.generateAll(schema, "../app/src/main/java");
    }
}
