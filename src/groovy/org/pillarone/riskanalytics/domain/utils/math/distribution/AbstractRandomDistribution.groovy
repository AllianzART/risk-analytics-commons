package org.pillarone.riskanalytics.domain.utils.math.distribution

import org.pillarone.riskanalytics.core.parameterization.AbstractParameterObject
import umontreal.iro.lecuyer.probdist.Distribution
import org.apache.commons.lang.builder.HashCodeBuilder

/**
 * @author jessika.walter (at) intuitive-collaboration (dot) com
 */
abstract class AbstractRandomDistribution extends AbstractParameterObject {

    Distribution distribution
    Map parameters


    abstract DistributionType getDistributionType();

    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder()
        builder.append(distribution.class)

        def sortedParameters = parameters.entrySet().sort {Map.Entry it -> it.key}

        sortedParameters.each {Map.Entry entry ->
            builder.append(entry.value)
        }
        builder.toHashCode()
    }

    /**
     * regards objects as equal iff their formal types and all parameter values agree
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof RandomDistribution) || !distribution.class.equals(((RandomDistribution)obj).distribution.class)) {
            return false
        }
        for (Object parameter : parameters.keySet()) {
            if (!parameters[parameter].equals(obj.parameters[parameter])) {
                return false
            }
        }
        return true
    }

}
