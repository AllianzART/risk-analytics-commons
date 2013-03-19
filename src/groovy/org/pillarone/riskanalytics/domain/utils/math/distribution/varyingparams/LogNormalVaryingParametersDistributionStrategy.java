package org.pillarone.riskanalytics.domain.utils.math.distribution.varyingparams;

import org.apache.commons.lang.ArrayUtils;
import org.pillarone.riskanalytics.core.parameterization.ConstrainedMultiDimensionalParameter;
import org.pillarone.riskanalytics.core.parameterization.ConstraintsFactory;
import org.pillarone.riskanalytics.core.parameterization.IParameterObjectClassifier;
import org.pillarone.riskanalytics.core.util.GroovyUtils;
import org.pillarone.riskanalytics.domain.utils.InputFormatConverter;
import org.pillarone.riskanalytics.domain.utils.constraint.PeriodDistributionsConstraints;
import org.pillarone.riskanalytics.domain.utils.math.distribution.DistributionParams;
import org.pillarone.riskanalytics.domain.utils.math.distribution.DistributionType;
import org.pillarone.riskanalytics.domain.utils.math.distribution.RandomDistribution;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author stefan.kunz (at) intuitive-collaboration (dot) com
 */
public class LogNormalVaryingParametersDistributionStrategy extends AbstractVaryingParameterDistributionStrategy {

    private ConstrainedMultiDimensionalParameter meanAndStDev = new ConstrainedMultiDimensionalParameter(
            GroovyUtils.convertToListOfList(new Object[]{1, 1d, 1d}), Arrays.asList(PERIOD, DistributionParams.MEAN, DistributionParams.STDEV),
            ConstraintsFactory.getConstraints(PeriodDistributionsConstraints.IDENTIFIER));

    public IParameterObjectClassifier getType() {
        return VaryingParametersDistributionType.LOGNORMAL;
    }

    public Map getParameters() {
        Map<String, ConstrainedMultiDimensionalParameter> parameters = new HashMap<String, ConstrainedMultiDimensionalParameter>(1);
        parameters.put("meanAndStDev", meanAndStDev);
        return parameters;
    }

    protected TreeMap<Integer, RandomDistribution> initDistributions() {
        TreeMap<Integer, RandomDistribution> distributionPerPeriod = new TreeMap<Integer, RandomDistribution>();
        int meanColumnIndex = meanAndStDev.getColumnIndex(DistributionParams.MEAN.toString());
        int stDevColumnIndex = meanAndStDev.getColumnIndex(DistributionParams.STDEV.toString());

        for (int row = meanAndStDev.getTitleRowCount(); row < meanAndStDev.getRowCount(); row++) {
            int period = InputFormatConverter.getInt((meanAndStDev.getValueAt(row, periodColumnIndex))) - 1;
            double mean = InputFormatConverter.getDouble(meanAndStDev.getValueAt(row, meanColumnIndex));
            double stDev = InputFormatConverter.getDouble(meanAndStDev.getValueAt(row, stDevColumnIndex));
            distributionPerPeriod.put(period, DistributionType.getStrategy(DistributionType.LOGNORMAL, ArrayUtils.toMap(
                    new Object[][]{{DistributionParams.MEAN, mean}, {DistributionParams.STDEV, stDev}})));
        }
        return distributionPerPeriod;
    }
}
