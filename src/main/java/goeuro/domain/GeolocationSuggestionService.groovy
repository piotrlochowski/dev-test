package goeuro.domain

import groovy.transform.CompileStatic

@CompileStatic
interface GeolocationSuggestionService {

    List<Suggestion> find(String cityName)

}