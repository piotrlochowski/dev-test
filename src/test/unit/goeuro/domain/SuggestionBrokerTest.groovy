package goeuro.domain

import goeuro.infrastructure.pull.GeolocationSuggestionServiceImpl
import goeuro.infrastructure.pull.SuggestionsSource
import spock.lang.Specification

class SuggestionBrokerTest extends Specification {

    SuggestionBroker broker
    SuggestionsSource source = Mock(SuggestionsSource)
    StorageService storage = Mock(StorageService)

    def setup() {
        GeolocationSuggestionService service = new GeolocationSuggestionServiceImpl(source)
        broker = new SuggestionBroker(service, storage)

        source.find('Warsaw') >> [
                new Suggestion(
                        _id: 398532,
                        name: 'Warsaw',
                        type: 'location',
                        latitude: 52.22977,
                        longitude: 21.01178),
                new Suggestion(
                        _id: 314272,
                        name: "Warsaw Chopin",
                        type: "airport",
                        latitude: 52.16556,
                        longitude: 20.96694),
                new Suggestion(
                        _id: 473111,
                        name: "Warsaw–Modlin Mazovia Airport",
                        type: "airport",
                        latitude: 52.451111,
                        longitude: 20.651667)
        ]

        source.find('Poznan') >> [
                new Suggestion(
                        _id: 398422,
                        name: "Poznan",
                        type: "location",
                        latitude: 52.40692,
                        longitude: 16.92993),
                new Suggestion(
                        _id: 314834,
                        name: "Poznań–Ławica Henryk Wieniawski Airport",
                        type: "airport",
                        latitude: 52.41747,
                        longitude: 16.88414)
        ]

    }

    def "find suggestions"() {
        when: "the broker is asked for suggestions with query 'Warsaw'"
        List<Suggestion> results = broker.find("Warsaw")

        then: "returns list with 3 suggestions"
        results.size() == 3
        results.get(1).name == 'Warsaw Chopin'
    }

    def "find and store suggestions"() {
        when: "the broker is ordered to store suggestions for 'Poznan'"
        Long results = broker.findAndStore("Poznan", "file.csv")

        then: "list of 2 suggestions have to be stored in scv file"
        1 * storage.store({it.size == 2}, "file.csv")
        results == 2

    }

}
