package goeuro.infrastructure.shell

import goeuro.domain.Suggestion
import goeuro.domain.SuggestionBroker
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.shell.core.CommandMarker
import org.springframework.shell.core.annotation.CliAvailabilityIndicator
import org.springframework.shell.core.annotation.CliCommand
import org.springframework.shell.core.annotation.CliOption
import org.springframework.stereotype.Component

@CompileStatic
@Component
class GeolocationSuggesterCommands implements CommandMarker {

    private SuggestionBroker broker
    private String defaultFilePath

    @Autowired
    GeolocationSuggesterCommands(SuggestionBroker broker, @Value('${resultFile.path}') String defaultFilePath) {
        this.defaultFilePath = defaultFilePath
        this.broker = broker
    }

    @CliAvailabilityIndicator(["!", "//", "script", "system properties", "date"])
    boolean isAvailable() { false }

    @CliCommand(value = "print suggestions for", help = "Prints list of possible geo locations")
    public String print(
            @CliOption(key = "cityName", mandatory = true, help = "Name of the city") final String cityName) {
        List<Suggestion> result = broker.find(cityName)
        return "Found ${result.size()} suggestion${result.size() != 1 ? 's' : ''}: ${result.collect { it.name }}"
    }

    @CliCommand(value = "store suggestions for", help = "Saves possible geolocations in csv file")
    public String store(
            @CliOption(key = "cityName", mandatory = true, help = "Name of the city") final String cityName,
            @CliOption(key = "defaultFilePath", mandatory = false, help = "Name of the file") String filePath) {
        filePath = filePath ?: defaultFilePath
        Long nrOfResults = broker.findAndStore(cityName, filePath)
        return "Stored ${nrOfResults} suggestion${nrOfResults != 1 ? 's' : ''} in ${filePath} file"
    }

}
