package goeuro.domain

import groovy.transform.CompileStatic

@CompileStatic
interface StorageService {

    void store(List<Suggestion> suggestions, String filePath)

}