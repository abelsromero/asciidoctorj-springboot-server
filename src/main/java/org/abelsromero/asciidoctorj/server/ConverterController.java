package org.abelsromero.asciidoctorj.server;

import org.apache.commons.io.IOUtils;
import org.asciidoctor.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@RestController
public class ConverterController {

    static final Logger logger = LoggerFactory.getLogger(ConverterController.class);

    @GetMapping("/convert")
    public String convert() throws IOException {

        final Asciidoctor asciidoctor = Asciidoctor.Factory.create();

        AttributesBuilder attributes = AttributesBuilder.attributes()
                .linkCss(false)
                .dataUri(true)
                .tableOfContents(true)
                .tableOfContents(Placement.LEFT)
                .sourceHighlighter("rouge")
                .attribute("value", LocalDateTime.now());

        OptionsBuilder options = OptionsBuilder.options()
                .safe(SafeMode.UNSAFE)
                .headerFooter(true)
                .attributes(attributes);

        final String sourceContent = IOUtils.toString(file("sample.adoc"), StandardCharsets.UTF_8);
        logger.info("Converting source of size: " + sourceContent.length());

        return asciidoctor.convert(sourceContent, options);
    }

    public InputStream file(String filename) {
        return this.getClass().getClassLoader().getResourceAsStream(filename);
    }

}
