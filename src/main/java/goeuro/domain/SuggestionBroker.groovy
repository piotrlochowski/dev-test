package goeuro.domain

import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
@CompileStatic
class SuggestionBroker {

    private GeolocationSuggestionService service
    private StorageService storage

    @Autowired
    SuggestionBroker(GeolocationSuggestionService service, StorageService storage) {
        this.storage = storage
        this.service = service
    }

    Long findAndStore(String query, String filePath) {
        List<Suggestion> suggestions = service.find(query)
        storage.store(suggestions, filePath)
        return suggestions.size()
    }

    List<Suggestion> find(String query){
        service.find(query)
    }

}
