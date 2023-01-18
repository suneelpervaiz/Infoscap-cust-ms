package tiers.app.customer.config.security.helper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

public class EmailTemplate {

    private String template;
    private Map replacementParams;

    public EmailTemplate(String customTemplate){
        try {
            this.template = loadTemplate(customTemplate);
        } catch (Exception e) {
            this.template = "Empty";
        }
    }

    private String loadTemplate(String customTemplate) throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(customTemplate).getFile());
        String content = "Empty";
        try {
            content = new String(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            throw new Exception("Could not read Template = "+customTemplate);
        }
        return content;
    }

    public String getTemplate(Map<String, String> replacements) {

        String cTemplate = this.template;
        //Replace the String
        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            cTemplate = cTemplate.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }
        return cTemplate;
    }

}
