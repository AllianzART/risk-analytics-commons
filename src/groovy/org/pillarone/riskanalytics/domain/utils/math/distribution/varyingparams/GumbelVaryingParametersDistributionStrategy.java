package org.pillarone.riskanalytics.domain.utils.math.distribution.varyingparams;

import org.apache.commons.lang.ArrayUtils;
import org.pillarone.riskanalytics.core.parameterization.ConstrainedMultiDimensionalParameter;
import org.pillarone.riskanalytics.core.parameterization.ConstraintsFactory;
import org.pillarone.riskanalytics.core.parameterization.IParameterObjectClassifier;
import org.pillarone.riskanalytics.core.util.GroovyUtils;
import org.pillarone.riskanalytics.domain.utils.InputFormatConverter;
import org.pillarone.riskanalytics.domain.utils.constraint.PeriodDistributionsConstraints;
import org.pillarone.riskanalytics.domain.utils.math.distribution.DistributionParams;
import org.pillarone.riskanalytics.domain.utils.math.distribution.RandomDistribution;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author stefan.kunz (at) intuitive-collaboration (dot) com
 */
public class GumbelVaryingParametersDistributionStrategy extends AbstractVaryingParameterDistributionStrategy {

    private ConstrainedMultiDimensionalParameter betaAndDelta = new ConstrainedMultiDimensionalParameter(
            GroovyUtils.convertToListOfList(new Object[]{1, 1d, 1d}),
            Arrays.asList(DistributionParams.PERIOD, DistributionParams.BETA, DistributionParams.DELTA),
            ConstraintsFactory.getConstraints(PeriodDistributionsConstraints.IDENTIFIER));

    public IParameterObjectClassifier getType() {
        return VaryingParametersDistributionType.GUMBEL;
    }

    public Map getParameters() {
        Map<String, ConstrainedMultiDimensionalParameter> parameters = new HashMap<String, ConstrainedMultiDimensionalParameter>(1);
        parameters.put("betaAndDelta", betaAndDelta);
        return parameters;
    }

    protected TreeMap<Integer, RandomDistribution> initDistributions() {
        TreeMap<Integer, RandomDistribution> distributionPerPeriod = new TreeMap<Integer, RandomDistribution>();
        int betaColumnIndex = betaAndDelta.getColumnIndex(DistributionParams.BETA.toString());
        int deltaColumnIndex = betaAndDelta.getColumnIndex(DistributionParams.DELTA.toString());

        for (int row = betaAndDelta.getTitleRowCount(); row < betaAndDelta.getRowCount(); row++) {
            int period = InputFormatConverter.getInt((betaAndDelta.getValueAt(row, periodColumnIndex))) - 1;
            double betaParam = InputFormatConverter.getDouble(betaAndDelta.getValueAt(row, betaColumnIndex));
            double deltaParam = InputFormatConverter.getDouble(betaAndDelta.getValueAt(row, deltaColumnIndex));
            distributionPerPeriod.put(period, (RandomDistribution) VaryingParametersDistributionType.getStrategy(
                    VaryingParametersDistributionType.GUMBEL, ArrayUtils.toMap(
                        new Object[][]{{DistributionParams.BETA, betaParam}, {DistributionParams.DELTA, deltaParam}})));
        }
        return distributionPerPeriod;
    }
}
