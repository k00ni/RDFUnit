package org.aksw.rdfunit.model.readers.results;

import com.google.common.collect.ImmutableSet;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.vocabulary.DCTerms;
import com.hp.hpl.jena.vocabulary.RDF;
import org.aksw.rdfunit.model.helper.PropertyValuePairSet;
import org.aksw.rdfunit.model.impl.results.ExtendedTestCaseResultImpl;
import org.aksw.rdfunit.model.interfaces.results.ExtendedTestCaseResult;
import org.aksw.rdfunit.model.interfaces.results.RLOGTestCaseResult;
import org.aksw.rdfunit.model.readers.ElementReader;
import org.aksw.rdfunit.vocabulary.PROV;
import org.aksw.rdfunit.vocabulary.RDFUNITv;
import org.aksw.rdfunit.vocabulary.RLOG;

import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Reads an argument
 *
 * @author Dimitris Kontokostas
 * @since 6/17/15 5:07 PM
 * @version $Id: $Id
 */
public final class ExtendedTestCaseResultReader implements ElementReader<ExtendedTestCaseResult> {

    private ExtendedTestCaseResultReader(){}

    /**
     * <p>create.</p>
     *
     * @return a {@link ExtendedTestCaseResultReader} object.
     */
    public static ExtendedTestCaseResultReader create() { return new ExtendedTestCaseResultReader();}

    /** {@inheritDoc} */
    @Override
    public ExtendedTestCaseResult read(final Resource resource) {
        checkNotNull(resource);

        RLOGTestCaseResult test = RLOGTestCaseResultReader.create().read(resource);

        PropertyValuePairSet annotationSet = PropertyValuePairSet.create();

        Set<Property> excludesProperties = ImmutableSet.of(RLOG.level, RLOG.resource, RLOG.message, PROV.wasGeneratedBy, DCTerms.date, RDFUNITv.testCase);
        Set<Resource> excludesTypes = ImmutableSet.of(RDFUNITv.RLOGTestCaseResult, RDFUNITv.TestCaseResult, RLOG.Entry);

        for (Statement smt: resource.listProperties().toList()) {
            if (excludesProperties.contains(smt.getPredicate())) {
                continue;
            }
            if (RDF.type.equals(smt.getPredicate()) && excludesTypes.contains(smt.getObject().asResource())) {
                continue;
            }
            annotationSet.add(smt.getPredicate(), smt.getObject());
        }

        return new ExtendedTestCaseResultImpl(resource, test.getTestCaseUri(), test.getSeverity(), test.getMessage(), test.getTimestamp(), test.getFailingResource(), annotationSet.getAnnotations());
    }
}
