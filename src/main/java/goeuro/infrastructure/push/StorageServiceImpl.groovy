package goeuro.infrastructure.push

import au.com.bytecode.opencsv.CSVWriter
import goeuro.domain.StorageService
import goeuro.domain.Suggestion
import groovy.transform.CompileStatic
import org.springframework.stereotype.Service

@Service
@CompileStatic
class StorageServiceImpl implements StorageService {

    @Override
    void store(List<Suggestion> suggestions, String filePath) {
        Writer writer = new FileWriter(filePath)
        def w = new CSVWriter(writer)
        w.writeNext(Suggestion.headers.values() as String[])
        suggestions.each { suggestion ->
            w.writeNext(suggestion.toOrderedArray())
        }
        writer.close()
    }
}
