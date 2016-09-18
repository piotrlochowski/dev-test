package goeuro.infrastructure.pull

import goeuro.domain.Suggestion

interface SuggestionsSource {

    List<Suggestion> find(String cityName);

}