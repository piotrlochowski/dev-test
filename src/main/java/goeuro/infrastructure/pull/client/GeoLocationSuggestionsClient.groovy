package goeuro.infrastructure.pull.client

import goeuro.domain.Suggestion
import goeuro.infrastructure.pull.SuggestionsSource
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

import static groovyx.net.http.ContentType.JSON

@Component
class GeoLocationSuggestionsClient implements SuggestionsSource {

    private RESTClient geoSuggestions

    @Autowired
    GeoLocationSuggestionsClient(@Value('${geoSuggestions.endpoint}') endpoint) {
        geoSuggestions = new RESTClient(endpoint)
    }

    List<Suggestion> find(String query) {
        List suggestions = Collections.EMPTY_LIST
        try {
            HttpResponseDecorator resp = geoSuggestions.get(path: query) as HttpResponseDecorator
            assert resp.status == 200
            assert resp.contentType == JSON.toString()
            assert (resp.data instanceof List)
            suggestions = resp.data as ArrayList
        }
        catch (HttpResponseException exception) {
            assert exception.response.status == 400
            throw new RuntimeException('Please verify endpoint configuration (have to be ended with"/")')
        }
        return suggestions
                .collect(cl)
    }

    Closure<Suggestion> cl = {
        new Suggestion(
                _id: it['_id'] as Long,
                name: it['name'],
                type: it['type'],
                latitude: it['geo_position'].latitude,
                longitude: it['geo_position'].longitude
        )
    }

}
