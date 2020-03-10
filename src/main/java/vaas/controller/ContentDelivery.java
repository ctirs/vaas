package vaas.controller;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.server.types.files.SystemFile;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import java.io.File;

@Controller("/{site}/{dialog}/")
@Slf4j
public class ContentDelivery {

    @Get
    public SystemFile getHome() {
        File file = new File(
                getClass().getClassLoader().getResource("public/index.html").getFile()
        );
        return new SystemFile(file, MediaType.TEXT_HTML_TYPE);
    }

}
