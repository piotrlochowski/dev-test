package goeuro.infrastructure.shell

import org.springframework.shell.Bootstrap
import org.springframework.shell.core.CommandResult
import org.springframework.shell.core.JLineShellComponent
import spock.lang.Specification
import spock.lang.Unroll

class GeolocationSuggesterCommandsTest extends Specification {

    private static JLineShellComponent shell;

    def setupSpec() {
        Bootstrap bootstrap = new Bootstrap();
        shell = bootstrap.getJLineShellComponent();

    }

    def cleanupSpec() {
        shell.stop();
    }

    @Unroll
    def "list suggestion command"() {
        expect:
        CommandResult cr = shell.executeCommand("print suggestions for --cityName ${city}");
        cr.success == true
        cr.result == message

        where:
        city          | message
        'Warsaw'      | 'Found 6 suggestions: [Warsaw, Wesoła (Warsaw), Orange Warsaw Festival, Warsaw, Warsaw Chopin, Warsaw–Modlin Mazovia Airport]'
        'Ludwikowice' | 'Found 1 suggestion: [Ludwikowice Klodzkie]'
        'Doesntexist' | 'Found 0 suggestions: []'
    }

    def "save suggestions to file"() {
        expect:
        CommandResult cr = shell.executeCommand("store suggestions for --cityName ${city}");
        cr.success == true
        cr.result == message

        where:
        city          | message
        'Doesntexist' | 'Stored 0 suggestions in result.csv file'
        'Ludwikowice' | 'Stored 1 suggestion in result.csv file'
        'Warsaw'      | 'Stored 6 suggestions in result.csv file'

    }

}
