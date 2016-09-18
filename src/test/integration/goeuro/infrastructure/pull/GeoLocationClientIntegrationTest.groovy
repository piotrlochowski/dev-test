package goeuro.infrastructure.pull

import goeuro.infrastructure.pull.client.GeoLocationSuggestionsClient
import spock.lang.Shared
import spock.lang.Unroll

class GeoLocationClientIntegrationTest extends spock.lang.Specification {

    @Shared
    private static GeoLocationSuggestionsClient client
    private static String uri = 'http://api.goeuro.com/api/v2/position/suggest/en/'

    def setupSpec() {
        client = new GeoLocationSuggestionsClient(uri)
    }

    @Unroll
    def "verify number of location suggestions"() {
        expect:
        client.find(cityName).size() == numberOfSuggestions
        where:
        cityName              | numberOfSuggestions
        'berlin'              | 8
        'Warsaw'              | 6
        'pARIS'               | 8
        'PARIS'               | 8
        'paris'               | 8
        'Doesntexistlocation' | 0
        'Rio de Janeiro'      | 3
        'Ludwikowice'         | 1


    }

    def "if misconfiguration throw exception"() {
        setup:
        String wrongUri = 'http://api.goeuro.com/api/v2/position/suggest/en'
        GeoLocationSuggestionsClient incorrectClient = new GeoLocationSuggestionsClient(wrongUri)
        when:
        incorrectClient.find('warsaw')
        then:
        RuntimeException ex = thrown()
        ex.message.contains('Please verify endpoint configuration')

    }


}
