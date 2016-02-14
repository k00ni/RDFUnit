package org.aksw.rdfunit.sources;

import org.aksw.rdfunit.enums.TestAppliesTo;
import org.aksw.rdfunit.io.reader.RdfReader;
import org.aksw.rdfunit.io.reader.RdfReaderFactory;
import org.aksw.rdfunit.utils.CacheUtils;
import org.aksw.rdfunit.utils.StringUtils;
import org.aksw.rdfunit.utils.UriToPathUtils;

/**
 * <p>SourceFactory class.</p>
 *
 * @author Dimitris Kontokostas
 *         Source factory
 * @since 10/3/13 6:45 PM
 * @version $Id: $Id
 */
public final class SchemaSourceFactory {

    private SchemaSourceFactory() {
    }

    /**
     * <p>createSchemaSourceFromCache.</p>
     *
     * @param baseFolder a {@link java.lang.String} object.
     * @param prefix a {@link java.lang.String} object.
     * @param uri a {@link java.lang.String} object.
     * @return a {@link org.aksw.rdfunit.sources.SchemaSource} object.
     */
    public static SchemaSource createSchemaSourceFromCache(String baseFolder, String prefix, String uri) {
        return createSchemaSourceFromCache(baseFolder, prefix, uri, uri);
    }

    /**
     * <p>createSchemaSourceFromCache.</p>
     *
     * @param baseFolder a {@link java.lang.String} object.
     * @param prefix a {@link java.lang.String} object.
     * @param uri a {@link java.lang.String} object.
     * @param schema a {@link java.lang.String} object.
     * @return a {@link org.aksw.rdfunit.sources.SchemaSource} object.
     */
    public static SchemaSource createSchemaSourceFromCache(String baseFolder, String prefix, String uri, String schema) {
        String cacheFile = CacheUtils.getSchemaSourceCacheFilename(baseFolder, TestAppliesTo.Schema, prefix, uri);
        RdfReader reader = RdfReaderFactory.createFileOrDereferenceReader(cacheFile, schema);
        return createSchemaSourceSimple(prefix, uri, schema, reader);
    }

    /**
     * <p>createSchemaSourceDereference.</p>
     *
     * @param prefix a {@link java.lang.String} object.
     * @param uri a {@link java.lang.String} object.
     * @return a {@link org.aksw.rdfunit.sources.SchemaSource} object.
     */
    public static SchemaSource createSchemaSourceDereference(String prefix, String uri) {
        return createSchemaSourceDereference(prefix, uri, uri);
    }

    /**
     * <p>createSchemaSourceDereference.</p>
     *
     * @param prefix a {@link java.lang.String} object.
     * @param uri a {@link java.lang.String} object.
     * @param schema a {@link java.lang.String} object.
     * @return a {@link org.aksw.rdfunit.sources.SchemaSource} object.
     */
    public static SchemaSource createSchemaSourceDereference(String prefix, String uri, String schema) {
        return createSchemaSourceSimple(prefix, uri, schema, RdfReaderFactory.createDereferenceReader(schema));
    }

    /**
     * <p>createEnrichedSchemaSourceFromCache.</p>
     *
     * @param baseFolder a {@link java.lang.String} object.
     * @param prefix a {@link java.lang.String} object.
     * @param uri a {@link java.lang.String} object.
     * @return a {@link org.aksw.rdfunit.sources.EnrichedSchemaSource} object.
     */
    public static EnrichedSchemaSource createEnrichedSchemaSourceFromCache(String baseFolder, String prefix, String uri) {
        String cacheFile = CacheUtils.getSchemaSourceCacheFilename(baseFolder, TestAppliesTo.EnrichedSchema, prefix, uri);
        RdfReader reader = RdfReaderFactory.createFileOrDereferenceReader(cacheFile, uri);
        return new EnrichedSchemaSource(new SourceConfig(prefix, uri), reader);
    }

    /**
     * <p>createSchemaSourceFromText.</p>
     *
     * @param namespace a {@link java.lang.String} object.
     * @param text a {@link java.lang.String} object.
     * @param format a {@link java.lang.String} object.
     * @return a {@link org.aksw.rdfunit.sources.SchemaSource} object.
     */
    public static SchemaSource createSchemaSourceFromText(String namespace, String text, String format) {

        String uri = namespace + StringUtils.getHashFromString(text);
        String prefix = UriToPathUtils.getAutoPrefixForURI(uri);

        return createSchemaSourceSimple(prefix, uri, RdfReaderFactory.createReaderFromText(text, format));
    }


    /**
     * <p>createSchemaSourceSimple.</p>
     *
     * @param uri a {@link java.lang.String} object.
     * @param reader a {@link RdfReader} object.
     * @return a {@link org.aksw.rdfunit.sources.SchemaSource} object.
     * @since 0.7.19
     */
    public static SchemaSource createSchemaSourceSimple(String uri, RdfReader reader) {

        return createSchemaSourceSimple(UriToPathUtils.getAutoPrefixForURI(uri), uri, uri, reader);
    }

    /**
     * <p>createSchemaSourceSimple.</p>
     *
     * @param prefix a {@link java.lang.String} object.
     * @param uri a {@link java.lang.String} object.
     * @param reader a {@link RdfReader} object.
     * @return a {@link org.aksw.rdfunit.sources.SchemaSource} object.
     */
    public static SchemaSource createSchemaSourceSimple(String prefix, String uri, RdfReader reader) {

        return createSchemaSourceSimple(prefix, uri, uri, reader);
    }

    /**
     * <p>createSchemaSourceSimple.</p>
     *
     * @param prefix a {@link java.lang.String} object.
     * @param uri a {@link java.lang.String} object.
     * @param schema a {@link java.lang.String} object.
     * @param reader a {@link RdfReader} object.
     * @return a {@link org.aksw.rdfunit.sources.SchemaSource} object.
     */
    public static SchemaSource createSchemaSourceSimple(String prefix, String uri, String schema, RdfReader reader) {

        return new SchemaSource(new SourceConfig(prefix, uri), schema, reader);
    }

    /**
     * <p>copySchemaSource.</p>
     *
     * @param schemaSource a {@link org.aksw.rdfunit.sources.SchemaSource} object.
     * @return a {@link org.aksw.rdfunit.sources.SchemaSource} object.
     */
    public static SchemaSource copySchemaSource(SchemaSource schemaSource) {

        return new SchemaSource(schemaSource);
    }
}
