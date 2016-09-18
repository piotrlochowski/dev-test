package goeuro.infrastructure.pull

import goeuro.domain.GeolocationSuggestionService
import goeuro.domain.Suggestion
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@CompileStatic
class GeolocationSuggestionServiceImpl implements GeolocationSuggestionService {

    private SuggestionsSource source

    @Autowired
    GeolocationSuggestionServiceImpl(SuggestionsSource source) {
        this.source = source
    }

    @Override
    List<Suggestion> find(String cityName) {
        return source.find(cityName)
    }

}
