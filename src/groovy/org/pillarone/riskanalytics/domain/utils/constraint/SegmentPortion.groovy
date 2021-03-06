package org.pillarone.riskanalytics.domain.utils.constraint

import org.pillarone.riskanalytics.core.parameterization.IMultiDimensionalConstraints
import org.pillarone.riskanalytics.domain.utils.marker.ISegmentMarker

/**
 * @author stefan (dot) kunz (at) intuitive-collaboration (dot) com
 */
class SegmentPortion implements IMultiDimensionalConstraints {

    public static final String IDENTIFIER = "SEGMENT_PORTION"

    boolean matches(int row, int column, Object value) {
        if (column == 0) {
            return value instanceof String
        }
        else {
            return value instanceof Number
        }
    }

    String getName() {
        return IDENTIFIER
    }

    Class getColumnType(int column) {
        return column == 0 ? ISegmentMarker : Double
    }

    Integer getColumnIndex(Class marker) {
        if (ISegmentMarker.isAssignableFrom(marker)) {
            return 0
        }
        else if (BigDecimal.isAssignableFrom(marker)) {
            return 1
        }
        return null;
    }

    boolean emptyComponentSelectionAllowed(int column) {
        return false
    }
}