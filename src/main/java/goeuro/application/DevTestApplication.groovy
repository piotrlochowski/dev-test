package goeuro.application

import groovy.transform.CompileStatic
import org.springframework.shell.Bootstrap

@CompileStatic
class DevTestApplication {

    static void main(String[] args) throws IOException {
        if (args) {
            new Bootstrap().getJLineShellComponent().
                    executeCommand("store suggestions for --cityName '${args.join(' ')}'");
        } else {
            Bootstrap.main(args)
        };
    }

}
