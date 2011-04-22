package test.concordion;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.concordion.api.Resource;
import org.concordion.api.Source;

public class FilterSource implements Source {

    private final Source source;
    private String prefix;

    public FilterSource(Source source, String filterPrefix) {
        this.source = source;
        prefix = filterPrefix;
    }
    
    public InputStream createInputStream(Resource resource) throws IOException {
        if (resource.getPath().startsWith(prefix)) {
            return new ByteArrayInputStream("x".getBytes("UTF-8"));
        }
        return source.createInputStream(resource);
    }

    public boolean canFind(Resource resource) {
        return true;
    }
}
