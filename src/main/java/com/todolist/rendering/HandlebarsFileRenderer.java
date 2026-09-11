package com.todolist.rendering;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import io.javalin.http.Context;
import io.javalin.rendering.FileRenderer;

import java.util.Map;

public class HandlebarsFileRenderer implements FileRenderer {
    private final Handlebars handlebars;

    public HandlebarsFileRenderer() {
        ClassPathTemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("");
        loader.setSuffix("");
        this.handlebars = new Handlebars(loader);
    }

    @Override
    public String render(String filePath, Map<String, Object> model, Context context) throws Exception {
        Template template = handlebars.compile(filePath);
        return template.apply(model);
    }
}
