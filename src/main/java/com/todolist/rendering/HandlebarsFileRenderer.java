package com.todolist.rendering;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import io.javalin.http.Context;
import io.javalin.rendering.FileRenderer;

import java.io.IOException;
import java.util.Map;

public class HandlebarsFileRenderer implements FileRenderer {
    private final Handlebars handlebars;

    public HandlebarsFileRenderer() {
        ClassPathTemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".hbs");
        this.handlebars = new Handlebars(loader);
    }

    @Override
    public String render(String filePath, Map<String, ? extends Object> model, Context context) {
        try {
            Template template = handlebars.compile(filePath);
            return template.apply(model);
        } catch (IOException e) {
            throw new RuntimeException("Error al renderizar el template " + filePath, e);
        }
    }
}
