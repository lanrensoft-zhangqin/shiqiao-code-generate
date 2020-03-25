package org.shiqiao.generate.core.bind;


import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.FileResourceLoader;
import org.shiqiao.generate.core.utils.PathKit;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created by zhangqin
 */
public class BeetlTemplateGenUtils {

    static {
        String root = PathKit.getRootClassPath() + File.separator + "template";
        FileResourceLoader resourceLoader = new FileResourceLoader(root, "utf-8");
        Configuration cfg = null;
        try {
            cfg = Configuration.defaultConfiguration();
        } catch (IOException e) {
            e.printStackTrace();
        }
        gt = new GroupTemplate(resourceLoader, cfg);
    }

    private static final GroupTemplate gt;

    private static    Template getTemplate(String name) {
        return gt.getTemplate("/" + name);
    }

    public static String bindToString(Map<String,Object> map, String templateName) {
        Template template = getTemplate(templateName);
        template.binding(map);
        return template.render();
    }

}
