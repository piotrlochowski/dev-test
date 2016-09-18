package goeuro.domain

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode
@ToString
class Suggestion {

    Long _id
    String name
    String type
    Number latitude
    Number longitude

    String[] toOrderedArray() {
        headers.values().collect { column ->
            this."${column}"
        } as String[]
    }

    enum headers {
        _id,
        name,
        type,
        latitude,
        longitude
    }
}
