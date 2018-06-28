package com.cch.autocode;


import com.cch.cz.common.UtilFun;
import com.cch.cz.entity.Message;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.persistence.Id;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class GenerateBase {

    static Configuration cfg = new
            Configuration(Configuration.VERSION_2_3_22);
    static  String templatePath="/com/cch/autocode/template";
    
    static String basePath=System.getProperty("user.dir")+"/src/main/java/";

    public static void main(String[] args) {
        generateBase(new Message());
    }

    public static void generateBase(Object entity){
        generateMapper(entity);
        generateProvider(entity);
        generateService(entity);
        generateServiceImpl(entity);
        generateController(entity);
    }


    public static void generateMapper(Object entity  ) {
        String type="mapper";
        String templateName="mapper.ftl";
        String Classname=entity.getClass().getSimpleName()+UtilFun.upperFristCase(type);
        String packagename = entity.getClass().getPackage().getName();
        packagename=packagename.substring(0,packagename.lastIndexOf("."))+"."+type;
        Map<String, Object> root=setMap(entity, Classname, packagename);
        generate(templateName, Classname, packagename, root);
    }

     public  static  void generateProvider(Object entity ){
        String type="provider";
        String templateName="provider.ftl";
        String Classname=entity.getClass().getSimpleName()+UtilFun.upperFristCase(type);
        String packagename = entity.getClass().getPackage().getName();
        packagename=packagename.substring(0,packagename.lastIndexOf("."))+".mapper."+type;
        Map<String, Object> root=setMap(entity, Classname, packagename);
         generate(templateName, Classname, packagename, root);
    }

    public static void generateService(Object entity){
        String type="service";
        String templateName="service.ftl";
        String Classname=entity.getClass().getSimpleName()+ UtilFun.upperFristCase(type);
        String packagename = entity.getClass().getPackage().getName();
        packagename=packagename.substring(0,packagename.lastIndexOf("."))+"."+type;
        Map<String, Object> root=setMap(entity, Classname, packagename);
        generate(templateName, Classname, packagename, root);
    }

    public static void generateServiceImpl(Object entity){
        String type="impl";
        String templateName="serviceImpl.ftl";
        String Classname=entity.getClass().getSimpleName()+"Service"+UtilFun.upperFristCase(type);
        String packagename = entity.getClass().getPackage().getName();
        packagename=packagename.substring(0,packagename.lastIndexOf("."))+".service."+type;
        Map<String, Object> root=setMap(entity, Classname, packagename);
        generate(templateName, Classname, packagename, root);
    }

    public  static  void generateController(Object entity){
        String type="ctrl";
        String templateName="controller.ftl";
        String Classname=entity.getClass().getSimpleName()+UtilFun.upperFristCase(type);
        String packagename = entity.getClass().getPackage().getName();
        packagename=packagename.substring(0,packagename.lastIndexOf("."))+"."+type;
        Map<String, Object> root=setMap(entity, Classname, packagename);
        generate(templateName, Classname, packagename, root);
    }

    private static void generate(String templateName, String classname, String packagename, Map<String, Object> root) {
        cfg.setClassForTemplateLoading(GenerateBase.class,templatePath);
        try {

            Template template = cfg.getTemplate(templateName);
            writeJava(classname, packagename, root, template);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

    private static void writeJava(String classname, String packagename, Map<String, Object> root, Template template) throws IOException, TemplateException {
        String java = basePath+ packagename.replace(".","/")+"/";
        File javaFile = new File(java+ classname +".java") ;
        if(!javaFile.getParentFile().exists()) javaFile.getParentFile().mkdirs();
        if(!javaFile.exists()) javaFile.createNewFile();

        Writer javaWriter = new FileWriter(javaFile);
        template.process(root, javaWriter);
        javaWriter.flush();
        javaWriter.close();
    }

    private static Map<String, Object> setMap(Object entity, String classname, String packagename) {
        Map<String, Object> root = new HashMap<>();
        Field[] fields=entity.getClass().getDeclaredFields();
        for (Field f:fields) {
            f.setAccessible(true);
            if(f.getAnnotation(Id.class)!=null) {
                root.put("pk",f.getType().getName());
            }

        }
        root.put("pack",packagename);
        root.put("class", classname);
        root.put("entity",entity.getClass().getName());
        root.put("serviceimpl",packagename.substring(0,packagename.lastIndexOf("."))+"."+entity.getClass().getSimpleName()+"Service");
        root.put("service",entity.getClass().getSimpleName()+"Service");
        return root;
    }

}
